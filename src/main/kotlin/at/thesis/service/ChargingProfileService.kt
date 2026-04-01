package at.thesis.service

import at.thesis.model.Forecast
import at.thesis.model.TransactionConfig
import at.thesis.model.exceptions.NoDataException
import eu.chargetime.ocpp.model.core.ChargingProfile
import eu.chargetime.ocpp.model.core.ChargingProfileKindType
import eu.chargetime.ocpp.model.core.ChargingProfilePurposeType
import eu.chargetime.ocpp.model.core.ChargingSchedule
import eu.chargetime.ocpp.model.core.ChargingRateUnitType
import eu.chargetime.ocpp.model.core.ChargingSchedulePeriod
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.LocalDateTime

@ApplicationScoped
class ChargingProfileService {
    companion object {
        private const val MAX_SCHEDULES = 24
    }

    @Inject
    private lateinit var forecastService: ForecastService

    @Inject
    private lateinit var configService: ChargingConfigService

    fun generateChargingProfile(configId: String, transactionConfig: TransactionConfig, phases: Int): SetChargingProfileRequest {
        val config = configService.findById(configId)
        if (config != null) {
            val forecast = forecastService.findByConfigId(config.id?.toString() ?: throw IllegalArgumentException())
            if (forecast == null) throw NoDataException()

            val profile = calculateProfile(config.minPvPercentage, config.basePower, forecast, phases)
            profile.transactionId = transactionConfig.transactionId

            return SetChargingProfileRequest(transactionConfig.connectorId, profile)
        } else {
            throw NoDataException()
        }
    }

    private fun calculateProfile(
        minPvPercentage: Double,
        basePower: Double,
        forecast: Forecast,
        phases: Int
    ): ChargingProfile {
        val profileId = 1

        val startTime = LocalDateTime.now()

        val selectedValues =
            forecast.getItems().filter { it.key >= startTime.minusMinutes(30) }

        val periods = selectedValues.map {
            val difference = maxOf(0, java.time.Duration.between(startTime, it.key).seconds).toInt()
            val limitWatts = maxOf(0.0, it.value + it.value * (1.0 - minPvPercentage / 100.0) - basePower)

            val limitAmpere = if (phases == 1) (limitWatts / 230.0) else (limitWatts / (230.0 * phases))

            ChargingSchedulePeriod(difference, limitAmpere)
        }.take(MAX_SCHEDULES)

        val schedule = ChargingSchedule(
            ChargingRateUnitType.A,
            periods.toTypedArray()
        )

        val profile = ChargingProfile(
            profileId,
            5,
            ChargingProfilePurposeType.TxProfile,
            ChargingProfileKindType.Relative,
            schedule
        )

        return profile
    }
}
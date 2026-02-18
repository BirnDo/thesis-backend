package at.thesis.service

import at.thesis.model.Forecast
import at.thesis.model.exceptions.NoDataException
import eu.chargetime.ocpp.model.core.ChargingProfile
import eu.chargetime.ocpp.model.core.ChargingProfileKindType
import eu.chargetime.ocpp.model.core.ChargingProfilePurposeType
import eu.chargetime.ocpp.model.core.ChargingSchedule
import eu.chargetime.ocpp.model.core.ChargingRateUnitType
import eu.chargetime.ocpp.model.core.ChargingSchedulePeriod
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.LocalDateTime

@ApplicationScoped
class ChargingProfileService {
    private final val MAX_SCHEDULES = 24

    @Inject
    private lateinit var forecastService: ForecastService

    @Inject
    private lateinit var configService: ChargingConfigService

    fun generateChargingProfile(configId: String): ChargingProfile {
        val config = configService.findById(configId)
        if (config != null) {
            val forecast = forecastService.findByConfigId(config.id?.toString() ?: throw IllegalArgumentException())
            if (forecast == null) throw NoDataException()

            val profile = calculateProfile(config.minPvPercentage, config.basePower, forecast)

            return profile;
        } else {
            throw NoDataException()
        }
    }

    private fun calculateProfile(minPvPercentage: Double, basePower: Double, forecast: Forecast): ChargingProfile {
        val profileId = 1

        val startTime = LocalDateTime.now()

        val selectedValues = forecast.forecastItems.filter { it.time >= startTime.minusMinutes(30) } //Because the time resolution is 30

        val periods = selectedValues.map {
            val difference = maxOf(0, java.time.Duration.between(startTime, it.time).seconds).toInt()
            val limit = maxOf(0.0, it.value + it.value*(1.0-minPvPercentage/100.0) - basePower)
            ChargingSchedulePeriod(difference, limit)
        }.take(MAX_SCHEDULES)

        val schedule = ChargingSchedule(
            ChargingRateUnitType.W,
            periods.toTypedArray()
        )

        return ChargingProfile(
            profileId,
            5,
            ChargingProfilePurposeType.TxProfile,
            ChargingProfileKindType.Relative,
            schedule
        )
    }
}
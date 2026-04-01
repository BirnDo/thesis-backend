package at.thesis

import at.thesis.repository.ChargingConfigRepository
import at.thesis.repository.PVDimensionsRepository
import at.thesis.service.ForecastService
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
@Startup
class DataFetchingService {
    @Inject
    private lateinit var chargingConfigRepository: ChargingConfigRepository

    @Inject
    private lateinit var pvDimensionsRepository: PVDimensionsRepository

    @Inject
    private lateinit var forecastService: ForecastService

    /*fun fetchProductionData() {
        val configs = chargingConfigRepository.findAll().list()

        configs.forEach {
            val dimensions = pvDimensionsRepository.findById(ObjectId(it.pvDimensionsId))
            if (dimensions != null) {
                val forecastItems = forecastService.getForecastItems(dimensions.pvParts, dimensions.location)
                if (it.id != null) {
                    val forecast = Forecast(
                        configId = it.id,
                        forecastItems = forecastItems,
                    )
                    forecastService.saveForecast(forecast)
                    Log.info("saving $forecast")
                }
            }
        }
    }*/
}
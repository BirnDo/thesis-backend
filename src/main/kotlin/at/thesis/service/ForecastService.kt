package at.thesis.service

import at.thesis.model.Forecast
import at.thesis.model.ForecastItem
import at.thesis.model.Location
import at.thesis.model.PvPart
import at.thesis.repository.ForecastRepository
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.LocalDateTime

@ApplicationScoped
class ForecastService {
    @Inject
    private lateinit var forecastRepository: ForecastRepository

    fun getForecastItems(pvParts: List<PvPart>, location: Location): List<ForecastItem> {
        pvParts.forEach {
            Log.info("getting Forecast for $it at $location")
        }

        val fullProduction = pvParts.map { it.peakPower }.reduce(Double::plus)

        var date = LocalDateTime.now().plusHours(1)
        val forecastItems = mutableListOf<ForecastItem>()
        for (i in 1..6) {
            val forecastItem = ForecastItem(date, (i.toDouble() / 10) * fullProduction * 1000)
            forecastItems.add(forecastItem)
            date = date.plusHours(1)
        }
        for (i in 6.downTo(1)) {
            val forecastItem = ForecastItem(date, (i.toDouble() / 10) * fullProduction * 1000)
            forecastItems.add(forecastItem)
            date = date.plusHours(1)
        }
        return forecastItems
    }

    fun saveForecast(forecast: Forecast) = forecastRepository.persist(forecast)
    fun findByConfigId(configId: String) = forecastRepository.findByConfigId(configId)
}
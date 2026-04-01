package at.thesis.model

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Forecast(
    @BsonProperty("_id")
    var id: ObjectId? = null,
    var configId: ObjectId? = null,
    var timestamp: LocalDateTime? = null,
    var forecastItems: Map<String, Double> = mutableMapOf()
) {
    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }

    fun getItems(): Map<LocalDateTime, Double> {
        return forecastItems.mapKeys { (key, _) ->
            LocalDateTime.parse(key, FORMATTER)
        }.toSortedMap()
    }
}

data class ForecastItem(
    var time: LocalDateTime = LocalDateTime.now(),
    var value: Double = 0.0
)
package at.thesis.model

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Forecast(
    @BsonProperty("_id")
    var id: ObjectId? = null,
    var configId: ObjectId? = null,
    var forecastItems: List<ForecastItem> = listOf()
)

data class ForecastItem(
    var time: LocalDateTime = LocalDateTime.now(),
    var value: Double = 0.0
)
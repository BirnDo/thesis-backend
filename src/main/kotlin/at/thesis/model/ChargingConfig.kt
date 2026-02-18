package at.thesis.model

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class ChargingConfig(
    @BsonProperty("_id")
    var id: ObjectId? = null,
    var basePower: Double = 0.0,
    var minPvPercentage: Double = 100.0,
    var pvDimensionsId: String = ""
)
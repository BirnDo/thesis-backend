package at.thesis.model

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class PVDimensions(
    @BsonProperty("_id")
    var id: ObjectId? = null,
    var location: Location = Location(),
    var pvParts: List<PvPart> = listOf()
)

data class Location(var longitude: Double = 0.0, var latitude: Double = 0.0)

data class PvPart(
    var peakPower: Double = 0.0,
    var azimuth: Double = 0.0,
    var slope: Double = 0.0,
)
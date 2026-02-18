package at.thesis.repository

import at.thesis.model.PVDimensions
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class PVDimensionsRepository : PanacheMongoRepository<PVDimensions> {
    fun findByDimensionId(id: String) = find("_id", ObjectId(id)).firstResult()
}
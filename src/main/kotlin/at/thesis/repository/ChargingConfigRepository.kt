package at.thesis.repository

import at.thesis.model.ChargingConfig
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class ChargingConfigRepository : PanacheMongoRepository<ChargingConfig> {
    fun findByConfigId(id: String) = find("_id", ObjectId(id)).firstResult()
}
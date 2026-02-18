package at.thesis.repository

import at.thesis.model.Forecast
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class ForecastRepository : PanacheMongoRepository<Forecast> {
    fun findByConfigId(configId: String) = find(
        query = "configId = ?1",
        sort = Sort.descending("_id"),
        ObjectId(configId)).firstResult()
}
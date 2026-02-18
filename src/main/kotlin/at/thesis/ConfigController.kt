package at.thesis

import at.thesis.model.ChargingConfig
import at.thesis.model.PVDimensions
import at.thesis.service.ChargingConfigService
import at.thesis.service.PvDimensionsService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.MediaType
import org.bson.types.ObjectId

@Path("/config")
class ConfigController {

    @Inject
    private lateinit var chargingConfigService: ChargingConfigService

    @Inject
    private lateinit var pvDimensionsService: PvDimensionsService

    @GET
    @Path("/{id}")
    fun getConfigById(@PathParam("id") id: String): ChargingConfig? {
        return chargingConfigService.findById(id)
    }

    @GET
    @Path("dimensions/{id}")
    fun getDimensionsById(@PathParam("id") id: String): PVDimensions? {
        return pvDimensionsService.findById(id)
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun createConfig(config: ChargingConfig): ChargingConfig {
        chargingConfigService.persist(config)
        return config
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/dimensions")
    fun createDimensions(dimensions: PVDimensions): PVDimensions {
        pvDimensionsService.persist(dimensions)
        return dimensions
    }
}
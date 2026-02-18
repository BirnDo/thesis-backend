package at.thesis

import at.thesis.service.ChargingProfileService
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam

@Path("/profile")
class ChargingProfileController {
    @Inject
    private lateinit var chargingProfileService: ChargingProfileService

    @GET
    @Path("/{configId}")
    fun generateProfile(@PathParam("configId") configId: String) =
        chargingProfileService.generateChargingProfile(configId)

}
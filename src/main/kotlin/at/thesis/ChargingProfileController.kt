package at.thesis

import at.thesis.model.TransactionConfig
import at.thesis.service.ChargingProfileService
import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.RestPath

@Path("/profile")
class ChargingProfileController {
    @Inject
    private lateinit var chargingProfileService: ChargingProfileService

    @POST
    @Path("/{configId}")
    fun generateProfile(@RestPath("configId") configId: String, transactionConfig: TransactionConfig) =
        chargingProfileService.generateChargingProfile(configId, transactionConfig, 3) //Hard coded for now
}
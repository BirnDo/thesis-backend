package at.thesis.service

import at.thesis.model.ChargingConfig
import at.thesis.repository.ChargingConfigRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ChargingConfigService {

    @Inject
    private lateinit var chargingConfigRepository: ChargingConfigRepository

    fun findById(configId: String) = chargingConfigRepository.findByConfigId(configId)
    fun persist(config: ChargingConfig) = chargingConfigRepository.persistOrUpdate(config)
    fun findAll(): List<ChargingConfig> =
        chargingConfigRepository.findAll().list()
}
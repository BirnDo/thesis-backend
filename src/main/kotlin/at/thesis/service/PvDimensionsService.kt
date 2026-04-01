package at.thesis.service

import at.thesis.model.PVDimensions
import at.thesis.repository.PVDimensionsRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class PvDimensionsService {
    @Inject
    private lateinit var pvDimensionsRepository: PVDimensionsRepository

    fun findById(id: String) = pvDimensionsRepository.findByDimensionId(id)
    fun persist(dimensions: PVDimensions) = pvDimensionsRepository.persistOrUpdate(dimensions)

}
package com.ernestschcneider.marsrovernavigator.domain.usecase

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository

class GetRoverStatusUseCase(private val repository: RoverRepository) {
    suspend operator fun invoke(roverCommandRequest: RoverCommandRequest): RoverApiResponse {
        return repository.getRoverStatus(roverCommandRequest)
    }
}

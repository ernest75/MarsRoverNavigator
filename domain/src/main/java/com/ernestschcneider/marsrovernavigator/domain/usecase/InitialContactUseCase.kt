package com.ernestschcneider.marsrovernavigator.domain.usecase

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepo

class InitialContactUseCase (private val repository: RoverRepo){
    suspend operator fun invoke(): RoverApiResponse {
        return repository.getInitialContact()
    }
}
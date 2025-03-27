package com.ernestschcneider.marsrovernavigator.domain.usecase

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository

class InitialContactUseCase (private val repository: RoverRepository){
    suspend operator fun invoke(): RoverApiResponse {
        return repository.getInitialContact()
    }
}

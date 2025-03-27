package com.ernestschcneider.marsrovernavigator.domain.usecase

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import org.json.JSONObject

class GetRoverStatusUseCase(private val repository: RoverRepository) {
    suspend operator fun invoke(jsonObject: JSONObject): RoverApiResponse {
        return repository.getRoverStatus(jsonObject)
    }
}

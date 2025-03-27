package com.ernestschcneider.marsrovernavigator.domain.usecase

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepo
import org.json.JSONObject

class GetRoverStatusUseCase(private val repository: RoverRepo) {
    suspend operator fun invoke(jsonObject: JSONObject): RoverApiResponse {
        return repository.getRoverStatus(jsonObject)
    }
}

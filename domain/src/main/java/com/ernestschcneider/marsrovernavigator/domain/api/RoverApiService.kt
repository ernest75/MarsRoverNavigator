package com.ernestschcneider.marsrovernavigator.domain.api

import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusApiModel
import org.json.JSONObject

interface RoverApiService {
    suspend fun initialContact(): RoverApiResponse
    suspend fun getRoverStatus(request: RoverCommandRequest): RoverApiResponse
}


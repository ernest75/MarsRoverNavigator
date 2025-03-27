package com.ernestschcneider.marsrovernavigator.domain.api

import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusApiModel
import org.json.JSONObject

interface RoverApiService {
    suspend fun initialContact(): RoverStatusApiModel
    suspend fun getRoverStatus(json: JSONObject): RoverStatusApiModel
}

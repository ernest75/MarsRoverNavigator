package com.ernestschcneider.marsrovernavigator.domain.repository

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import org.json.JSONObject

interface RoverRepo {
    suspend fun getInitialContact(): RoverApiResponse
    suspend fun getRoverStatus(jsonObject: JSONObject): RoverApiResponse
}
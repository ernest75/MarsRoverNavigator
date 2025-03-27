package com.ernestschcneider.marsrovernavigator.domain.api

import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest

interface RoverApiService {
    suspend fun initialContact(): RoverApiResponse
    suspend fun getRoverStatus(request: RoverCommandRequest): RoverApiResponse
}


package com.ernestschcneider.marsrovernavigator.domain.repository

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest

interface RoverRepository {
    suspend fun getInitialContact(): RoverApiResponse
    suspend fun getRoverStatus(request: RoverCommandRequest): RoverApiResponse
}

package com.ernestschcneider.marsrovernavigator.data.repo

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor(
    private val roverApiService: RoverApiService
): RoverRepository {
    override suspend fun getInitialContact(): RoverApiResponse {
        return roverApiService.initialContact()
    }

    override suspend fun getRoverStatus(request: RoverCommandRequest): RoverApiResponse {
        return roverApiService.getRoverStatus(request)
    }
}
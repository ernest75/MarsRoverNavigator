package com.ernestschcneider.marsrovernavigator.data.repo

import com.ernestschcneider.marsrovernavigator.data.mapper.RoverJsonMapper
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import org.json.JSONObject
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor(
    private val roverApiService: RoverApiService,
    private val jsonMapper: RoverJsonMapper
): RoverRepository {
    override suspend fun getInitialContact(): RoverApiResponse {
        return roverApiService.initialContact()
    }

    override suspend fun getRoverStatus(jsonObject: JSONObject): RoverApiResponse {
        return try {
            val request = jsonMapper.fromJson(jsonObject)
            return roverApiService.getRoverStatus(request)
        } catch (e: Exception) {
            RoverApiResponse.Error(e.message ?: "Unknown error")
        }

    }
}
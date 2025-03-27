package com.ernestschcneider.marsrovernavigator.domain.api

import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusApiModel

sealed class RoverApiResponse {
    data class Success(val data: RoverStatusApiModel) : RoverApiResponse()
    data class Error(val message: String) : RoverApiResponse()
}

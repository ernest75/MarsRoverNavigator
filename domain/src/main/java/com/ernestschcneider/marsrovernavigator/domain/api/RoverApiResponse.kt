package com.ernestschcneider.marsrovernavigator.domain.api

import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusModel

sealed class RoverApiResponse {
    data class Success(val data: RoverStatusModel) : RoverApiResponse()
    data class Error(val message: String) : RoverApiResponse()
}

package com.ernestschcneider.marsrovernavigator.feature.navigation

import com.ernestschcneider.marsrovernavigator.domain.model.RoverPositionModel

data class RoverControllerScreenState (
    val roverPosition: RoverPositionModel = RoverPositionModel(0, 0),
    val roverDirection: String = "N",
    val isLoading: Boolean = false,
    val error: String? = null
)

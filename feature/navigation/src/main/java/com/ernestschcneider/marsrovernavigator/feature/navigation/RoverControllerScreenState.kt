package com.ernestschcneider.marsrovernavigator.feature.navigation

import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel

data class RoverControllerScreenState (
    val roverPosition: CoordinatesModel = CoordinatesModel(0, 0),
    val roverDirection: String = "N",
    val isLoading: Boolean = false,
    val error: String? = null,
    val topRightCorner: CoordinatesModel = CoordinatesModel(5,5)
)

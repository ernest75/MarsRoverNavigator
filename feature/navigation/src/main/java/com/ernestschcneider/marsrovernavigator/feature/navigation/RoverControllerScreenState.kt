package com.ernestschcneider.marsrovernavigator.feature.navigation

import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.Direction

data class RoverControllerScreenState (
    val roverPosition: CoordinatesModel = CoordinatesModel(0, 0),
    val roverDirection: String = Direction.N.name,
    val isLoading: Boolean = false,
    val error: String? = null,
    val topRightCorner: CoordinatesModel = CoordinatesModel(0,0)
)

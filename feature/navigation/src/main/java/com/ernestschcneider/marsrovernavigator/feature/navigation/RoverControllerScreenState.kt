package com.ernestschcneider.marsrovernavigator.feature.navigation

import com.ernestschcneider.marsrovernavigator.domain.model.Position

data class RoverControllerScreenState (
    val roverPosition: Position = Position(0, 0),
)
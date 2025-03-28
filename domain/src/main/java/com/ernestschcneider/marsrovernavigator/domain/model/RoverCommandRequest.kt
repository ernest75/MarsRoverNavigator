package com.ernestschcneider.marsrovernavigator.domain.model

data class RoverCommandRequest(
    val topRightCorner: CoordinatesModel,
    val roverPosition: CoordinatesModel,
    val roverDirection: Direction,
    val movements: String
)

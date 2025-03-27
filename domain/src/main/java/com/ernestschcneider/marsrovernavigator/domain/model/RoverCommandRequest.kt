package com.ernestschcneider.marsrovernavigator.domain.model

data class RoverCommandRequest(
    val topRightCorner: Position,
    val roverPosition: Position,
    val roverDirection: Direction,
    val movements: String
)

package com.ernestschcneider.marsrovernavigator.domain.model

data class RoverStatusApiModel (
    val roverPosition: RoverPositionApiModel,
    val roverDirection: String
)
data class RoverPositionApiModel(val x: Int, val y: Int)

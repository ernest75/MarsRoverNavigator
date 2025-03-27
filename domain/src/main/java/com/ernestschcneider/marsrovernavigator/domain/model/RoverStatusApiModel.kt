package com.ernestschcneider.marsrovernavigator.domain.model

data class RoverStatusApiModel (
    val roverPosition: RoverPositionApiModel,
    val plateauSize: Int,
    val roverDirection: String
)
data class RoverPositionApiModel(val x: Int, val y: Int)


package com.ernestschcneider.marsrovernavigator.domain.model

data class RoverStatusModel (
    val roverPosition: RoverPositionModel,
    val roverDirection: String
)
data class RoverPositionModel(val x: Int, val y: Int)

package com.ernestschcneider.marsrovernavigator.domain.model

data class RoverStatusModel (
    val roverPosition: CoordinatesModel,
    val roverDirection: String,
    val plateauTopRightCorner: CoordinatesModel
)

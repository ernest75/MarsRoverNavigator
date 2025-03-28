package com.ernestschcneider.marsrovernavigator.data.network

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusModel

class RoverFakeApiService : RoverApiService {

    private val defaultPlateauTopCorners = CoordinatesModel(5, 5)
    override suspend fun initialContact(): RoverApiResponse {
        val roverInitialCoordinatesModel = CoordinatesModel(0, 0)
        return RoverApiResponse.Success(
            RoverStatusModel(
                roverDirection = Direction.N.name,
                roverPosition = roverInitialCoordinatesModel,
                plateauTopRightCorner = defaultPlateauTopCorners
            )
        )
    }

    override suspend fun getRoverStatus(request: RoverCommandRequest): RoverApiResponse {
        val (topRightCorner, roverPosition, roverDirection, movements) = request
        val maxX = topRightCorner.x
        val maxY = topRightCorner.y

        if (roverPosition.x !in 0..maxX || roverPosition.y !in 0..maxY) {
            return RoverApiResponse.Error("Rover position out of plateau bounds")
        }

        if (!movements.all { it in "LMR" }) {
            return RoverApiResponse.Error("Invalid movement command")
        }

        val directions = listOf(Direction.N, Direction.E, Direction.S, Direction.W)
        val coordinatesModel = CoordinatesModel(roverPosition.x, roverPosition.y)
        var direction = roverDirection

        for (move in movements) {
            when (move) {
                'L' -> direction = directions[(directions.indexOf(direction) + 3) % 4]
                'R' -> direction = directions[(directions.indexOf(direction) + 1) % 4]
                'M' -> {
                    when (direction) {
                        Direction.N -> if (coordinatesModel.y < maxY) coordinatesModel.y++
                        Direction.E -> if (coordinatesModel.x < maxX) coordinatesModel.x++
                        Direction.S -> if (coordinatesModel.y > 0) coordinatesModel.y--
                        Direction.W -> if (coordinatesModel.x > 0) coordinatesModel.x--
                    }
                }
            }
        }

        return RoverApiResponse.Success(
            RoverStatusModel(
                roverDirection = direction.name,
                roverPosition = CoordinatesModel(coordinatesModel.x, coordinatesModel.y),
                plateauTopRightCorner = defaultPlateauTopCorners
            )
        )
    }
}

package com.ernestschcneider.marsrovernavigator.data.network

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.Position
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.model.RoverPositionApiModel
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusApiModel

class RoverFakeApiService : RoverApiService {

    override suspend fun initialContact(): RoverApiResponse {
        return RoverApiResponse.Success(
            RoverStatusApiModel(
                roverDirection = Direction.N.name,
                roverPosition = RoverPositionApiModel(0, 0)
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
        val position = Position(roverPosition.x, roverPosition.y)
        var direction = roverDirection

        for (move in movements) {
            when (move) {
                'L' -> direction = directions[(directions.indexOf(direction) + 3) % 4]
                'R' -> direction = directions[(directions.indexOf(direction) + 1) % 4]
                'M' -> {
                    when (direction) {
                        Direction.N -> if (position.y < maxY) position.y++
                        Direction.E -> if (position.x < maxX) position.x++
                        Direction.S -> if (position.y > 0) position.y--
                        Direction.W -> if (position.x > 0) position.x--
                    }
                }
            }
        }

        return RoverApiResponse.Success(
            RoverStatusApiModel(
                roverDirection = direction.name,
                roverPosition = RoverPositionApiModel(position.x, position.y)
            )
        )
    }
}

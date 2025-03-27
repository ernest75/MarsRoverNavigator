package com.ernestschcneider.marsrovernavigator.data.mapper

import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.MOVEMENTS_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.ROVER_DIRECTION_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.ROVER_POSITION_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.TOP_RIGHT_CORNER_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.X_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.Y_KEY
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.Position
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import org.json.JSONObject

object RoverJsonMapper {
    fun fromJson(json: JSONObject): RoverCommandRequest {
        val plateau = json.getJSONObject(TOP_RIGHT_CORNER_KEY)
        val rover = json.getJSONObject(ROVER_POSITION_KEY)
        val direction = Direction.valueOf(json.getString(ROVER_DIRECTION_KEY))
        val movements = json.getString(MOVEMENTS_KEY)

        return RoverCommandRequest(
            topRightCorner = Position(plateau.getInt(X_KEY), plateau.getInt(Y_KEY)),
            roverPosition = Position(rover.getInt(X_KEY), rover.getInt(Y_KEY)),
            roverDirection = direction,
            movements = movements
        )
    }
}
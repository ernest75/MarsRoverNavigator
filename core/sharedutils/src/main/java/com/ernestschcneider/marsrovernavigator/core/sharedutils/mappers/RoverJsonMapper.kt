package com.ernestschcneider.marsrovernavigator.core.sharedutils.mappers

import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.MOVEMENTS_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.ROVER_DIRECTION_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.ROVER_POSITION_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.TOP_RIGHT_CORNER_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.X_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.Y_KEY
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import org.json.JSONObject
import javax.inject.Inject

class RoverJsonMapper @Inject constructor() {
    fun fromJson(json: JSONObject): RoverCommandRequest {
        val plateau = json.getJSONObject(TOP_RIGHT_CORNER_KEY)
        val rover = json.getJSONObject(ROVER_POSITION_KEY)
        val direction = Direction.valueOf(json.getString(ROVER_DIRECTION_KEY))
        val movements = json.getString(MOVEMENTS_KEY)

        return RoverCommandRequest(
            topRightCorner = CoordinatesModel(plateau.getInt(X_KEY), plateau.getInt(Y_KEY)),
            roverPosition = CoordinatesModel(rover.getInt(X_KEY), rover.getInt(Y_KEY)),
            roverDirection = direction,
            movements = movements
        )
    }

    fun toJson(
        topRightCorner: CoordinatesModel,
        roverPosition: CoordinatesModel,
        roverDirection: String,
        movements: String
    ): JSONObject {
        return JSONObject().apply {
            put(TOP_RIGHT_CORNER_KEY, JSONObject().apply {
                put(X_KEY, topRightCorner.x)
                put(Y_KEY, topRightCorner.y)
            })
            put(ROVER_POSITION_KEY, JSONObject().apply {
                put(X_KEY, roverPosition.x)
                put(Y_KEY, roverPosition.y)
            })
            put(ROVER_DIRECTION_KEY, roverDirection)
            put(MOVEMENTS_KEY, movements)
        }
    }

}
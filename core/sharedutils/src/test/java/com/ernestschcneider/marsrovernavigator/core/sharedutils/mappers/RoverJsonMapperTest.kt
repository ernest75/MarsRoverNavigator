package com.ernestschcneider.marsrovernavigator.core.sharedutils.mappers

import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.MOVEMENTS_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.ROVER_DIRECTION_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.ROVER_POSITION_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.TOP_RIGHT_CORNER_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.X_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsRoverNavigatorConstants.Y_KEY
import com.ernestschcneider.marsrovernavigator.core.sharedutils.testhelper.TestJsonFactory
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import org.json.JSONException
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RoverJsonMapperTest {

    private val mapper = RoverJsonMapper()

    @Test
    fun `GIVEN a valid JSON WHEN fromJson THEN map to RoverCommandRequest`() {
        // Given
        val plateauTopCorners = 5
        val roverX = 1
        val roverY = 2
        val direction = "N"
        val movements = "LMLMLMLMM"
        val json = TestJsonFactory.createRoverJson(
            plateauX = plateauTopCorners,
            plateauY = plateauTopCorners,
            roverX = roverX,
            roverY = roverY,
            direction = direction,
            movements = movements
        )

        // When
        val result = mapper.fromJson(json)

        // Then
        assertEquals(CoordinatesModel(plateauTopCorners, plateauTopCorners), result.topRightCorner)
        assertEquals(CoordinatesModel(roverX, roverY), result.roverPosition)
        assertEquals(Direction.N, result.roverDirection)
        assertEquals(movements, result.movements)
    }

    @Test
    fun `GIVEN valid fields WHEN toJson THEN returns correct JSONObject`() {
        // Given
        val topRightCorners = 5
        val positionX = 1
        val positionY = 2
        val roverDirection = "N"
        val movements = "MMRLL"

        // When
        val json = mapper.toJson(
            topRightCorner = CoordinatesModel(topRightCorners, topRightCorners),
            roverPosition = CoordinatesModel(positionX, positionY),
            roverDirection = roverDirection,
            movements = movements
        )

        // Then
        assertEquals(topRightCorners, json.getJSONObject(TOP_RIGHT_CORNER_KEY).getInt(X_KEY))
        assertEquals(topRightCorners, json.getJSONObject(TOP_RIGHT_CORNER_KEY).getInt(Y_KEY))
        assertEquals(positionX, json.getJSONObject(ROVER_POSITION_KEY).getInt(X_KEY))
        assertEquals(positionY, json.getJSONObject(ROVER_POSITION_KEY).getInt(Y_KEY))
        assertEquals(roverDirection, json.getString(ROVER_DIRECTION_KEY))
        assertEquals(movements, json.getString(MOVEMENTS_KEY))
    }

    @Test
    fun `GIVEN required field missing WHEN fromJson THEN throws JSONException`() {
        // Given
        val json = JSONObject("""
            {
              "topRightCorner": { "x": 5, "y": 5 },
              "roverPosition": { "x": 1, "y": 2 },
              "movements": "LMLMLMLMM"
            }
        """.trimIndent())

        // Then
        assertThrows<JSONException> {
            // When
            mapper.fromJson(json)
        }
    }

    @Test
    fun `GIVEN invalid direction WHEN fromJson THEN should throws IllegalArgumentException`() {
        // Given
        val json = TestJsonFactory.createRoverJson(
            roverX = 1,
            roverY = 2,
            plateauX = 5,
            plateauY = 5,
            direction = "INVALID",
            movements = "LMLMLMLMM"
        )

        // Then
        assertThrows<IllegalArgumentException> {
            // When
            mapper.fromJson(json)
        }
    }
}

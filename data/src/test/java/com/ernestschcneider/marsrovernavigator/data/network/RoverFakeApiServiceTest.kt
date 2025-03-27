package com.ernestschcneider.marsrovernavigator.data.network

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.Position
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Test


class RoverFakeApiServiceTest {

    private val roverApiService: RoverFakeApiService = RoverFakeApiService()

    @Test
    fun `WHEN initialContact THEN default values returned `() = runBlocking {
            // Given
            val defaultXPosition = 0
            val defaultYPosition = 0

            // When
            val response = roverApiService.initialContact()

            // Then
            assertTrue(response is RoverApiResponse.Success)
            val data = (response as RoverApiResponse.Success).data
            assertEquals(defaultXPosition, data.roverPosition.x)
            assertEquals(defaultYPosition, data.roverPosition.y)
            assertEquals(Direction.N.name, data.roverDirection)
    }

    @Test
    fun `GIVEN valid request WHEN getRoverStatus THEN expected result returned`() = runBlocking {
        // Given
        val request = RoverCommandRequest(
            topRightCorner = Position(5, 5),
            roverPosition = Position(1, 2),
            roverDirection = Direction.N,
            movements = "LMLMLMLMM"
        )

        // When
        val response = roverApiService.getRoverStatus(request)

        // Then
        assertTrue(response is RoverApiResponse.Success)
        val data = (response as RoverApiResponse.Success).data
        assertEquals(1, data.roverPosition.x)
        assertEquals(3, data.roverPosition.y)
        assertEquals(Direction.N.name, data.roverDirection)
    }

    @Test
    fun `GIVEN invalid movement command WHEN getRoverStatus THEN return error`() = runBlocking {
        // Given
        val request = RoverCommandRequest(
            topRightCorner = Position(5, 5),
            roverPosition = Position(1, 2),
            roverDirection = Direction.N,
            movements = "MXR" // 'X' is invalid
        )

        // When
        val response = roverApiService.getRoverStatus(request)

        // Then
        assertTrue(response is RoverApiResponse.Error)
        assertEquals("Invalid movement command", (response as RoverApiResponse.Error).message)
    }

    @Test
    fun `GIVEN rover out of bounds WHEN getRoverStatus THEN return error`() = runBlocking {
        // Given
        val request = RoverCommandRequest(
            topRightCorner = Position(5, 5),
            roverPosition = Position(6, 0), // x > maxX
            roverDirection = Direction.N,
            movements = "M"
        )

        // When
        val response = roverApiService.getRoverStatus(request)

        // Then
        assertTrue(response is RoverApiResponse.Error)
        assertEquals("Rover position out of plateau bounds", (response as RoverApiResponse.Error).message)
    }

    @Test
    fun `GIVEN rover moves toward edge WHEN getRoverStatus THEN rover stops at edge`() = runBlocking {
        // Given
        val request = RoverCommandRequest(
            topRightCorner = Position(5, 5),
            roverPosition = Position(0, 0),
            roverDirection = Direction.S, // moving South would go out of bounds
            movements = "MMM"
        )

        // When
        val response = roverApiService.getRoverStatus(request)

        // Then
        assertTrue(response is RoverApiResponse.Success)
        val data = (response as RoverApiResponse.Success).data
        assertEquals(0, data.roverPosition.x)
        assertEquals(0, data.roverPosition.y) // Did not move
        assertEquals(Direction.S.name, data.roverDirection)
    }

    @Test
    fun `GIVEN rover moves but hits East plateau boundary`() = runBlocking {
        // Given
        val request = RoverCommandRequest(
            topRightCorner = Position(5, 5),
            roverPosition = Position(4, 4),
            roverDirection = Direction.E,
            movements = "MM" // will try to move to x=6, which is out of bounds
        )

        // When
        val response = roverApiService.getRoverStatus(request)

        // Then
        assertTrue(response is RoverApiResponse.Success)
        val data = (response as RoverApiResponse.Success).data
        assertEquals(5, data.roverPosition.x)
        assertEquals(4, data.roverPosition.y)
        assertEquals(Direction.E.name, data.roverDirection)
    }
}

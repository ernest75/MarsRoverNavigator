package com.ernestschcneider.marsrovernavigator.data

import com.ernestschcneider.marsrovernavigator.data.repo.RoverRepositoryImpl
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.Position
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.model.RoverPositionModel
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RoverRepositoryImplTest {

    private val roverApiService: RoverApiService = mockk()
    private val repository: RoverRepositoryImpl = RoverRepositoryImpl(roverApiService)


    @Test
    fun `WHEN getInitialContact THEN returns success response`() = runTest {
        // Given
        val expectedResponse = RoverApiResponse.Success(
            RoverStatusModel(
                roverDirection = Direction.N.name,
                roverPosition = RoverPositionModel(0, 0)
            )
        )

        coEvery { roverApiService.initialContact() } returns expectedResponse

        // When
        val result = repository.getInitialContact()

        // Then
        assertTrue(result is RoverApiResponse.Success)
        assertEquals((result as RoverApiResponse.Success).data, expectedResponse.data)

        coVerify(exactly = 1) { roverApiService.initialContact() }
    }

    @Test
    fun `GIVEN a valid request WHEN getRoverStatus THEN returns success response`() = runTest {
        // Given
        val validRequest = RoverCommandRequest(
            topRightCorner = Position(5, 5),
            roverPosition = Position(1, 2),
            roverDirection = Direction.N,
            movements = "MM"
        )

        val expectedResponse = RoverApiResponse.Success(
            RoverStatusModel(
                roverDirection = "N",
                roverPosition = RoverPositionModel(1, 4)
            )
        )

        coEvery { roverApiService.getRoverStatus(validRequest) } returns expectedResponse

        // When
        val result = repository.getRoverStatus(validRequest)

        // Then
        assertTrue(result is RoverApiResponse.Success)
        assertEquals((result as RoverApiResponse.Success).data, expectedResponse.data)

        coVerify(exactly = 1) { roverApiService.getRoverStatus(validRequest) }
    }

    @Test
    fun `GIVEN invalid request WHEN getRoverStatus THEN returns error response`() {
        runTest {
            // Given
            val errorMessage = "Invalid position"
            val invalidRoverPosition = Position(6, 6)
            val request = RoverCommandRequest(
                topRightCorner = Position(5, 5),
                roverPosition = invalidRoverPosition,
                roverDirection = Direction.N,
                movements = "M"
            )

            val expectedResponse = RoverApiResponse.Error(errorMessage)

            coEvery { roverApiService.getRoverStatus(request) } returns expectedResponse

            // When
            val result = repository.getRoverStatus(request)

            // Then
            assertTrue(result is RoverApiResponse.Error)
            assertEquals(errorMessage, (result as RoverApiResponse.Error).message)

            coVerify(exactly = 1) { roverApiService.getRoverStatus(request) }
        }
    }
}
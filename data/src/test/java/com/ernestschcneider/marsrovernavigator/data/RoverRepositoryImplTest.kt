package com.ernestschcneider.marsrovernavigator.data

import com.ernestschcneider.marsrovernavigator.core.sharedutils.testhelper.TestJsonFactory
import com.ernestschcneider.marsrovernavigator.core.sharedutils.mappers.RoverJsonMapper
import com.ernestschcneider.marsrovernavigator.data.repo.RoverRepositoryImpl
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiService
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RoverRepositoryImplTest {

    private val roverApiService: RoverApiService = mockk()
    private val jsonMapper: RoverJsonMapper = mockk()
    private val repository: RoverRepositoryImpl = RoverRepositoryImpl(roverApiService, jsonMapper)


    @Test
    fun `WHEN getInitialContact THEN returns success response`() = runTest {
        // Given
        val expectedResponse = RoverApiResponse.Success(
            RoverStatusModel(
                roverDirection = Direction.N.name,
                roverPosition = CoordinatesModel(0, 0),
                plateauTopRightCorner = CoordinatesModel(5, 5)
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
    fun `GIVEN a valid request WHEN getRoverStatus THEN returns success response`() {
        runTest {
            // Given
            val roverDirection = "S"
            val roverPosition = CoordinatesModel(2, 1)
            val plateauTopRightCorner = CoordinatesModel(5, 5)
            val json = TestJsonFactory.createRoverJson(
                direction = roverDirection,
                roverX = roverPosition.x,
                roverY = roverPosition.y,
                plateauX = plateauTopRightCorner.x,
                plateauY = plateauTopRightCorner.y
            )
            val validRequest = RoverCommandRequest(
                topRightCorner = plateauTopRightCorner,
                roverPosition = roverPosition,
                roverDirection = Direction.valueOf(roverDirection),
                movements = "L"
            )
            val expectedResponse = RoverApiResponse.Success(
                RoverStatusModel(
                    roverDirection = roverDirection,
                    roverPosition = roverPosition,
                    plateauTopRightCorner = plateauTopRightCorner
                )
            )
            every { jsonMapper.fromJson(json) } returns validRequest
            coEvery { roverApiService.getRoverStatus(validRequest) } returns expectedResponse

            // When
            val result = repository.getRoverStatus(json)

            // Then
            assertTrue(result is RoverApiResponse.Success)
            assertEquals((result as RoverApiResponse.Success).data, expectedResponse.data)

            coVerify(exactly = 1) { roverApiService.getRoverStatus(validRequest) }
        }
    }

    @Test
    fun `GIVEN invalid request WHEN getRoverStatus THEN returns error response`() {
        runTest {
            // Given
            val errorMessage = "Invalid position"
            val invalidPosition = 6
            val json = TestJsonFactory.createRoverJson(roverX = invalidPosition)
            val expectedResponse = RoverApiResponse.Error(errorMessage)
            val request = RoverCommandRequest(
                topRightCorner = CoordinatesModel(5, 5),
                roverPosition = CoordinatesModel(invalidPosition, 0),
                roverDirection = Direction.valueOf("N"),
                movements = "L"
            )

            every { jsonMapper.fromJson(json) } returns request
            coEvery { roverApiService.getRoverStatus(request) } returns expectedResponse

            // When
            val result = repository.getRoverStatus(json)

            // Then
            assertTrue(result is RoverApiResponse.Error)
            assertEquals(errorMessage, (result as RoverApiResponse.Error).message)

            coVerify(exactly = 1) { roverApiService.getRoverStatus(request) }
        }
    }
}

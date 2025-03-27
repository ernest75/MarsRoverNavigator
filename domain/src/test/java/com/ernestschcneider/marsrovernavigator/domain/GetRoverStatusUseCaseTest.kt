package com.ernestschcneider.marsrovernavigator.domain

import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.Position
import com.ernestschcneider.marsrovernavigator.domain.model.RoverCommandRequest
import com.ernestschcneider.marsrovernavigator.domain.repository.RoverRepository
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@ExperimentalCoroutinesApi
class GetRoverStatusUseCaseTest {

    private val repo = mockk<RoverRepository>(relaxed = true)
    private val useCase = GetRoverStatusUseCase(repo)

    @Test
    fun `WHEN invoke THEN call to repo happens with object send`() = runTest {
        // Given
        val roverRequest = RoverCommandRequest(
            topRightCorner = Position(0, 0),
            roverPosition =  Position(0, 0),
            roverDirection = Direction.N,
            movements = "L",
        )

        // When
        useCase.invoke(roverRequest)

        // Then
        coVerify(exactly = 1) { repo.getRoverStatus(roverRequest) }
    }
}

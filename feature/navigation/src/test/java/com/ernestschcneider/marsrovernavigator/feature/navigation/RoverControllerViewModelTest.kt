package com.ernestschcneider.marsrovernavigator.feature.navigation

import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusModel
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import com.ernestschcneider.marsrovernavigator.domain.usecase.InitialContactUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoverControllerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val initialContactUseCase: InitialContactUseCase = mockk()
    private val getRoverStatusUseCase: GetRoverStatusUseCase = mockk()
    private lateinit var viewModel: RoverControllerViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RoverControllerViewModel(
            getRoverStatusUseCase = getRoverStatusUseCase,
            initialContactUseCase = initialContactUseCase,
            backgroundDispatcher = UnconfinedTestDispatcher()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a response success WHEN loadInitialContact THEN emits loading then success`() = runTest {
        // Given
        val status = RoverStatusModel(
            roverDirection = "N",
            roverPosition = CoordinatesModel(1, 3),
            plateauTopRightCorner = CoordinatesModel(5, 5)
        )
        coEvery { initialContactUseCase() } returns RoverApiResponse.Success(status)

        // When
        viewModel.loadInitialContact()

        // Wait for coroutine to finish
        advanceUntilIdle()

        // Then
        val state = viewModel.screenState.value
        assertFalse(state.isLoading)
        assertEquals(status.roverPosition, state.roverPosition)
        assertEquals(status.roverDirection, state.roverDirection)
        assertNull(state.error)
    }

    @Test
    fun `GIVEN a response failure WHEN loadInitialContact THEN emits loading then error`() = runTest {
        // Given
        val errorMessage = "Network error"
        val expectedDirection = Direction.N.name
        val expectedPosition = CoordinatesModel(0, 0)
        coEvery { initialContactUseCase() } returns RoverApiResponse.Error(errorMessage)

        // When
        viewModel.loadInitialContact()

        // Wait for coroutine to finish
        advanceUntilIdle()

        // Then
        val state = viewModel.screenState.value
        assertFalse(state.isLoading)
        assertEquals(errorMessage, state.error)
        assertEquals(expectedPosition, state.roverPosition)
        assertEquals(expectedDirection, state.roverDirection)
    }
}

package com.ernestschcneider.marsrovernavigator.feature.navigation

import com.ernestschcneider.marsrovernavigator.core.sharedutils.mappers.RoverJsonMapper
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.domain.model.RoverStatusModel
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import com.ernestschcneider.marsrovernavigator.domain.usecase.InitialContactUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoverControllerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val initialContactUseCase: InitialContactUseCase = mockk()
    private val getRoverStatusUseCase: GetRoverStatusUseCase = mockk()
    private val roverJsonMapper: RoverJsonMapper = mockk()
    private lateinit var viewModel: RoverControllerViewModel


    @BeforeEach
    fun setup() {
        viewModel = RoverControllerViewModel(
            getRoverStatusUseCase = getRoverStatusUseCase,
            initialContactUseCase = initialContactUseCase,
            backgroundDispatcher = testDispatcher,
            roverJsonMapper = roverJsonMapper
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a response success WHEN loadInitialContact THEN emits loading then success`() =
        runTest(testDispatcher) {
            // Given
            Dispatchers.setMain(testDispatcher)
            val status = RoverStatusModel(
                roverDirection = "N",
                roverPosition = CoordinatesModel(1, 3),
                plateauTopRightCorner = CoordinatesModel(5, 5)
            )
            coEvery { initialContactUseCase() } returns RoverApiResponse.Success(status)

            // When
            viewModel.processIntent(RoverControllerIntent.LoadInitialContact)

            // Wait for coroutine to finish
            advanceUntilIdle()

            // Then
            val state = viewModel.screenState.value
            Assertions.assertFalse(state.isLoading)
            Assertions.assertEquals(status.roverPosition, state.roverPosition)
            Assertions.assertEquals(status.roverDirection, state.roverDirection)
            Assertions.assertNull(state.error)
        }

    @Test
    fun `GIVEN a response failure WHEN loadInitialContact THEN emits loading then error`() =
        runTest(testDispatcher) {
            // Given
            Dispatchers.setMain(testDispatcher)
            val errorMessage = "Network error"
            val expectedDirection = Direction.N.name
            val expectedPosition = CoordinatesModel(0, 0)
            coEvery { initialContactUseCase() } returns RoverApiResponse.Error(errorMessage)

            // When
            viewModel.processIntent(RoverControllerIntent.LoadInitialContact)

            // Wait for coroutine to finish
            advanceUntilIdle()

            // Then
            val state = viewModel.screenState.value
            Assertions.assertFalse(state.isLoading)
            Assertions.assertEquals(errorMessage, state.error)
            Assertions.assertEquals(expectedPosition, state.roverPosition)
            Assertions.assertEquals(expectedDirection, state.roverDirection)
        }

    @Test
    fun `GIVEN success response WHEN sendCommandsFromEarth THEN updates screenState with new position and direction`() =
        runTest(testDispatcher) {
            // Given
            Dispatchers.setMain(testDispatcher)
            val currentState = viewModel.screenState.value
            val movementString = "LMR"
            val json = mockk<JSONObject>()
            val initialStatus = RoverStatusModel(
                roverDirection = "N",
                roverPosition = CoordinatesModel(1, 3),
                plateauTopRightCorner = CoordinatesModel(5, 5)
            )

            val updatedStatus = RoverStatusModel(
                roverDirection = "E",
                roverPosition = CoordinatesModel(2, 2),
                plateauTopRightCorner = currentState.topRightCorner
            )

            coEvery {
                roverJsonMapper.toJson(
                    roverPosition = currentState.roverPosition,
                    roverDirection = currentState.roverDirection,
                    topRightCorner = currentState.topRightCorner,
                    movements = movementString
                )
            } returns json

            coEvery { getRoverStatusUseCase(json) } returns RoverApiResponse.Success(updatedStatus)
            coEvery { initialContactUseCase() } returns RoverApiResponse.Success(initialStatus)
            viewModel.processIntent(RoverControllerIntent.LoadInitialContact)
            viewModel.processIntent(RoverControllerIntent.AddCommand("L"))
            viewModel.processIntent(RoverControllerIntent.AddCommand("M"))
            viewModel.processIntent(RoverControllerIntent.AddCommand("R"))

            // When
            viewModel.processIntent(RoverControllerIntent.SendCommands)
            advanceUntilIdle()

            // Then
            val state = viewModel.screenState.value
            Assertions.assertFalse(state.isLoading)
            Assertions.assertEquals(updatedStatus.roverPosition, state.roverPosition)
            Assertions.assertEquals(updatedStatus.roverDirection, state.roverDirection)
            Assertions.assertNull(state.error)
        }

    @Test
    fun `GIVEN error response WHEN sendCommandsFromEarth THEN updates screenState with error`() =
        runTest(testDispatcher) {
            // Given
            Dispatchers.setMain(testDispatcher)
            val currentState = viewModel.screenState.value
            val movementString = "LMR"
            val json = mockk<JSONObject>()
            val errorMessage = "Rover command failed"
            val initialStatus = RoverStatusModel(
                roverDirection = "N",
                roverPosition = CoordinatesModel(1, 3),
                plateauTopRightCorner = CoordinatesModel(5, 5)
            )

            coEvery {
                roverJsonMapper.toJson(
                    roverPosition = currentState.roverPosition,
                    roverDirection = currentState.roverDirection,
                    topRightCorner = currentState.topRightCorner,
                    movements = movementString
                )
            } returns json

            coEvery { getRoverStatusUseCase(json) } returns RoverApiResponse.Error(errorMessage)
            coEvery { initialContactUseCase() } returns RoverApiResponse.Success(initialStatus)

            viewModel.processIntent(RoverControllerIntent.LoadInitialContact)
            viewModel.processIntent(RoverControllerIntent.AddCommand("L"))
            viewModel.processIntent(RoverControllerIntent.AddCommand("M"))
            viewModel.processIntent(RoverControllerIntent.AddCommand("R"))

            // When
            viewModel.sendCommandsFromEarth()
            advanceUntilIdle()

            // Then
            val state = viewModel.screenState.value
            Assertions.assertFalse(state.isLoading)
            Assertions.assertEquals(errorMessage, state.error)
        }
}

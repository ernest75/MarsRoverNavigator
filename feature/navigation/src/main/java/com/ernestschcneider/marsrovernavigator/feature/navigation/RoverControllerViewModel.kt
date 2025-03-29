package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.marsrovernavigator.core.di.IoDispatcher
import com.ernestschcneider.marsrovernavigator.core.sharedutils.mappers.RoverJsonMapper
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.model.Movements
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import com.ernestschcneider.marsrovernavigator.domain.usecase.InitialContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoverControllerViewModel @Inject constructor(
    private val getRoverStatusUseCase: GetRoverStatusUseCase,
    private val initialContactUseCase: InitialContactUseCase,
    private val roverJsonMapper: RoverJsonMapper,
    @IoDispatcher private val backgroundDispatcher: CoroutineDispatcher
) :
    ViewModel() {
    private val _screenState = MutableStateFlow(RoverControllerScreenState())
    val screenState: StateFlow<RoverControllerScreenState> = _screenState.asStateFlow()
    private val movements = mutableListOf<String>()

    fun loadInitialContact() {
        viewModelScope.launch {
        _screenState.update { it.copy(isLoading = true, error = null) }
            val result = withContext(backgroundDispatcher) {
                initialContactUseCase()
            }
            handleRoverStatusResult(result)
        }
    }

    fun addCommand(movement: String) {
        if (movement in Movements.ALL) {
            movements.add(movement)
        }
    }

    fun sendCommandsFromEarth() {
        val roverPosition = screenState.value.roverPosition
        val roverDirection = screenState.value.roverDirection
        val topRightCorner = screenState.value.topRightCorner
        val movementsString: String = movements.joinToString(separator = "")
        movements.clear()
        val json =
            roverJsonMapper.toJson(
                roverPosition = roverPosition,
                roverDirection = roverDirection,
                topRightCorner = topRightCorner,
                movements = movementsString
            )
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true, error = null) }
            val result = withContext(backgroundDispatcher) {
                getRoverStatusUseCase(json)
            }
            handleRoverStatusResult(result)
        }
    }

    private fun handleRoverStatusResult(result: RoverApiResponse) {
        when (result) {
            is RoverApiResponse.Success -> {
                val data = result.data
                _screenState.update {
                    it.copy(
                        topRightCorner = data.plateauTopRightCorner,
                        roverPosition = data.roverPosition,
                        roverDirection = data.roverDirection,
                        isLoading = false
                    )
                }
            }

            is RoverApiResponse.Error -> _screenState.update {
                it.copy(error = result.message, isLoading = false)
            }
        }
    }
}

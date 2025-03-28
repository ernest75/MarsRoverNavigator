package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.marsrovernavigator.core.di.IoDispatcher
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
    @IoDispatcher private val backgroundDispatcher: CoroutineDispatcher
) :
    ViewModel() {
    private val _screenState = MutableStateFlow(RoverControllerScreenState())
    val screenState: StateFlow<RoverControllerScreenState> = _screenState.asStateFlow()
    private val movements = mutableListOf<String>()

    fun loadInitialContact() {
        _screenState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = withContext(backgroundDispatcher) {
                initialContactUseCase()
            }
            when (result) {
                is RoverApiResponse.Success -> {
                    val data = result.data
                    _screenState.update {
                        it.copy(
                            roverPosition = data.roverPosition,
                            roverDirection = data.roverDirection,
                            isLoading = false
                        )
                    }
                }

                is RoverApiResponse.Error -> {
                    _screenState.update {
                        it.copy(error = result.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun addMovement(movement: String) {
        if (movement in Movements.ALL) {
            movements.add(movement)
        }
    }

    fun sendCommandsFromEarth() {
        val roverPosition = screenState.value.roverPosition
        val roverDirection = screenState.value.roverDirection
        val plateauTopRightCorner = screenState.value.topRightCorner
        val movements: String = movements.joinToString(separator = "")
        _screenState.update { it.copy(isLoading = true, error = null) }
    }
}

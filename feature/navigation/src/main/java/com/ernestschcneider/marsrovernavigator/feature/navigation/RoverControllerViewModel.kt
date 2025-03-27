package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestschcneider.marsrovernavigator.domain.api.RoverApiResponse
import com.ernestschcneider.marsrovernavigator.domain.usecase.GetRoverStatusUseCase
import com.ernestschcneider.marsrovernavigator.domain.usecase.InitialContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoverControllerViewModel @Inject constructor(
    private val getRoverStatusUseCase: GetRoverStatusUseCase,
    private val initialContactUseCase: InitialContactUseCase
) :
    ViewModel() {
    private val _screenState = MutableStateFlow(RoverControllerScreenState())
    val screenState: StateFlow<RoverControllerScreenState> = _screenState.asStateFlow()

    fun loadInitialContact() {
        viewModelScope.launch {
            when (val result = initialContactUseCase()) {
                is RoverApiResponse.Success -> {
                    val data = result.data
                   _screenState.update { it.copy(
                       roverPosition = data.roverPosition,
                       roverDirection = data.roverDirection
                   ) }
                }

                is RoverApiResponse.Error -> {

                }
            }
        }
    }
}

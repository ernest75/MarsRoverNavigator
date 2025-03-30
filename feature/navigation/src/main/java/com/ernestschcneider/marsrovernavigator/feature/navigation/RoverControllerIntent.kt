package com.ernestschcneider.marsrovernavigator.feature.navigation

sealed class RoverControllerIntent {
    data object LoadInitialContact : RoverControllerIntent()
    data class AddCommand(val movement: String) : RoverControllerIntent()
    data object SendCommands : RoverControllerIntent()
}

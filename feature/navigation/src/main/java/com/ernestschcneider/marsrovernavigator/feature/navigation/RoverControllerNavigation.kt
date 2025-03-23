package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROVER_CONTROLLER_ROUTE = "roverControllerRoute"

fun NavGraphBuilder.roverControllerScreen() {
    composable(ROVER_CONTROLLER_ROUTE) {
        RoverControllerScreen()
    }
}

fun NavController.navigateRoverControllerScreen() {
    navigate(ROVER_CONTROLLER_ROUTE)
}

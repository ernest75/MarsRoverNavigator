package com.ernestschcneider.marsrovernavigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ernestschcneider.marsrovernavigator.feature.navigation.ROVER_CONTROLLER_ROUTE
import com.ernestschcneider.marsrovernavigator.feature.navigation.roverControllerScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = ROVER_CONTROLLER_ROUTE
    ) {
        roverControllerScreen()
    }
}

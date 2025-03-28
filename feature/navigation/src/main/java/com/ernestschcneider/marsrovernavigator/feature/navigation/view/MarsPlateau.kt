package com.ernestschcneider.marsrovernavigator.feature.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ernestschcneider.marsrovernavigator.domain.model.RoverPositionModel
import com.ernestschcneider.marsrovernavigator.feature.navigation.RoverControllerScreenState
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme

@Composable
fun MarsPlateau(screenState: RoverControllerScreenState) {
    val gridSize = 6
    val roverX = screenState.roverPosition.x
    val roverY = screenState.roverPosition.y

    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(2.dp, MaterialTheme.colorScheme.onBackground)
            .height(360.dp)

    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridSize),
            modifier = Modifier.fillMaxSize(),
            reverseLayout = true
        ) {
            items(gridSize * gridSize) { index ->
                val x = index % gridSize
                val y = index / gridSize

                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onBackground)
                        .background(
                            if (x == roverX && y == roverY) Color.Yellow else MaterialTheme.colorScheme.background
                        )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewPlateau() {
    MarsRoverNavigatorTheme {
        MarsPlateau(screenState = RoverControllerScreenState(roverPosition = RoverPositionModel(0, 0)))
    }
}

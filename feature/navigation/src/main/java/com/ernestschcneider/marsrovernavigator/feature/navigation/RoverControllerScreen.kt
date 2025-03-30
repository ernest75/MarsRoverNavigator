package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.CIRCULAR_PROGRESS_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.feature.navigation.view.MarsPlateau
import com.ernestschcneider.marsrovernavigator.feature.navigation.view.RoverControlPanel
import com.ernestschcneider.marsrovernavigator.feature.navigation.view.Title
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme


@Composable
internal fun RoverControllerScreen(viewModel: RoverControllerViewModel = hiltViewModel()) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(RoverControllerIntent.LoadInitialContact)
    }
    RoverControllerScreenContent(
        screenState = screenState,
        onIntent = viewModel::processIntent
    )
}

@Composable
fun RoverControllerScreenContent(
    screenState: RoverControllerScreenState,
    onIntent: (RoverControllerIntent) -> Unit
) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            if (screenState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(200.dp)
                        .testTag(CIRCULAR_PROGRESS_TEST_TAG),
                    strokeWidth = 8.dp
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .testTag(ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { Title() }
                    item { MarsPlateau(screenState) }
                    item { RoverControlPanel(
                        onCommandAdded = { onIntent(RoverControllerIntent.AddCommand(it)) },
                        onSendCommands = { onIntent(RoverControllerIntent.SendCommands) }
                    ) }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RoverControllerScreenPreview() {
    MarsRoverNavigatorTheme {
        RoverControllerScreenContent(
            screenState = RoverControllerScreenState(topRightCorner = CoordinatesModel(5, 5)),
            onIntent = { }
        )
    }
}

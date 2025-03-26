package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ernestschcneider.marsrovernavigator.feature.navigation.view.MarsPlateau
import com.ernestschcneider.marsrovernavigator.feature.navigation.view.RoverControlPanel
import com.ernestschcneider.marsrovernavigator.view.R
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme


@Composable
internal fun RoverControllerScreen() {
    RoverControllerScreenContent()
}

@Composable
private fun RoverControllerScreenContent() {
    val screenState = RoverControllerScreenState()
    Scaffold { paddingValues ->
        Row(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Title()
                }
                item {
                    MarsPlateau(screenState)
                }

                item {
                    RoverControlPanel()
                }

                item {

                }
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        stringResource(R.string.app_name),
        style = MaterialTheme.typography.headlineMedium
    )
}

@PreviewLightDark
@Composable
private fun RoverControllerScreenPreview() {
    MarsRoverNavigatorTheme {
        RoverControllerScreenContent()
    }
}

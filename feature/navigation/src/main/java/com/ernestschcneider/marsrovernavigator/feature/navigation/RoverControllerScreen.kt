package com.ernestschcneider.marsrovernavigator.feature.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
internal fun RoverControllerScreen(){
    RoverControllerScreenContent()
}

@Composable
private fun RoverControllerScreenContent() {
    Scaffold { paddingValues ->
        Row(modifier = Modifier
            .padding(paddingValues)) {
            Text(text = "Hello RoverControllerScreen!")
        }
    }

}

@Preview
@Composable
private fun RoverControllerScreenPreview() {
    RoverControllerScreenContent()
}


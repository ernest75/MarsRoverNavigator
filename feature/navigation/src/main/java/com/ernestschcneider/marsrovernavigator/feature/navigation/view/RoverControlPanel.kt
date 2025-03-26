package com.ernestschcneider.marsrovernavigator.feature.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ernestschcneider.marsrovernavigator.view.R
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme

@Composable
fun RoverControlPanel() {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { }
            ) {
                Text(stringResource(R.string.left_command))
            }
            Button(
                onClick = { }
            ) {
                Text(stringResource(R.string.move_command))
            }
            Button(
                onClick = { }
            ) {
                Text(
                    text = stringResource(R.string.right_command)
                )
            }
        }
        Button(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.execute_commands))
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewRoverControlPanel() {
    MarsRoverNavigatorTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            RoverControlPanel()
        }
    }
}
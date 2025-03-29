package com.ernestschcneider.marsrovernavigator.feature.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.LEFT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.RIGHT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.SEND_COMMANDS_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.SEND_MOVE_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.domain.model.Movements
import com.ernestschcneider.marsrovernavigator.view.R
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme

@Composable
fun RoverControlPanel(
    onCommandAdded: (String) -> Unit,
    onSendCommands: () -> Unit
    ) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.testTag(LEFT_COMMAND_BUTTON_TEST_TAG),
                onClick = { onCommandAdded(Movements.LEFT) }
            ) {
                Text(stringResource(R.string.left_command))
            }
            Button(
                modifier = Modifier.testTag(SEND_MOVE_BUTTON_TEST_TAG),
                onClick = { onCommandAdded(Movements.MOVE) }
            ) {
                Text(stringResource(R.string.move_command))
            }
            Button(
                modifier = Modifier.testTag(RIGHT_COMMAND_BUTTON_TEST_TAG),
                onClick = { onCommandAdded(Movements.RIGHT) }
            ) {
                Text(
                    text = stringResource(R.string.right_command)
                )
            }
        }
        Button(
            onClick = { onSendCommands() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
                .testTag(SEND_COMMANDS_BUTTON_TEST_TAG)
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
            RoverControlPanel(
                onCommandAdded = {},
                onSendCommands = {}
            )
        }
    }
}

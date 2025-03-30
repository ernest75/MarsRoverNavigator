package com.ernestschcneider.marsrovernavigator.feature.navigation

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.SCREENSHOT_TEST_TAG
import com.ernestschcneider.marsrovernavigator.domain.model.CoordinatesModel
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import com.ernestschcneider.marsrovernavigator.view.ui.theme.MarsRoverNavigatorTheme
import org.junit.Rule
import org.junit.Test

import org.junit.jupiter.api.Tag


@Tag(SCREENSHOT_TEST_TAG)
class RoverControllerSnapShootTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun showLoading() {
        paparazzi.snapshot {
            MarsRoverNavigatorTheme {
                RoverControllerScreenContent(
                    screenState = RoverControllerScreenState(
                        isLoading = true
                    ),
                    onAddCommand = {},
                    onSendCommands = {}
                )
            }
        }
    }

    @Test
    fun showsScreen() {
        paparazzi.snapshot {
            MarsRoverNavigatorTheme {
                RoverControllerScreenContent(
                    screenState = RoverControllerScreenState(
                        isLoading = false,
                        topRightCorner = CoordinatesModel(5,5)
                    ),
                    onAddCommand = {},
                    onSendCommands = {}
                )
            }
        }
    }

    @Test
    fun showsRoverPosition() {
        paparazzi.snapshot {
            MarsRoverNavigatorTheme {
                RoverControllerScreenContent(
                    screenState = RoverControllerScreenState(
                        isLoading = false,
                        topRightCorner = CoordinatesModel(5,5),
                        roverPosition = CoordinatesModel(1,1),
                        roverDirection = Direction.W.name
                    ),
                    onAddCommand = {},
                    onSendCommands = {}
                )
            }
        }
    }
}

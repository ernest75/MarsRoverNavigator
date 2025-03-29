package com.ernestschcneider.marsrovernavigator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.marsrovernavigator.RoverControllerUiTest.RoverControllerRobot
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.CIRCULAR_PROGRESS_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG
import kotlinx.coroutines.test.runTest

fun launchRoverControllerScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: RoverControllerRobot.() -> Unit
): RoverControllerRobot {
    return RoverControllerRobot(rule).apply(block)
}
class RoverControllerScreenRobot {

    class RoverControllerVerification(
        private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
    ) {

        fun onlyLoadingIsVisible() {
            rule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertIsDisplayed()
            rule.onNodeWithTag(ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG).assertIsNotDisplayed()
        }

        fun onScreenVisible() = runTest {
            rule.awaitIdle()
            rule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertIsNotDisplayed()
            rule.onNodeWithTag(ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG).assertIsDisplayed()
        }

        fun verifyDirection(direction: String) {
            rule.waitUntil(timeoutMillis = 2_000) {
                rule
                    .onAllNodesWithText(direction)
                    .fetchSemanticsNodes()
                    .isNotEmpty()
            }
            rule.onNodeWithText(direction).assertIsDisplayed()
        }

        fun verifyPosition(expectedRoverCellTestTag: String) {
            rule.waitUntil(timeoutMillis = 2_000) {
                rule
                    .onAllNodesWithTag(expectedRoverCellTestTag)
                    .fetchSemanticsNodes()
                    .isNotEmpty()
            }
            rule.onNodeWithTag(expectedRoverCellTestTag).assertIsDisplayed()
        }
    }
}

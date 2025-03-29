package com.ernestschcneider.marsrovernavigator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.CIRCULAR_PROGRESS_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.LEFT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.RIGHT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.SEND_COMMANDS_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class RoverControllerUiTest {

    @get:Rule
    val roverControllerRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loadingIsShownInCreation() {
        launchRoverControllerScreen(roverControllerRule) {
            // Nop operation
        } verify {
            onlyLoadingIsVisible()
        }
    }

    @Test
    fun contentIsShownAfterInitialPetitionFinishes() {
        launchRoverControllerScreen(roverControllerRule) {
            // Make sures initial petition finishes
            Thread.sleep(1000)
        } verify {
            onScreenVisible()
        }
    }

    @Test
    fun executeLeftCommandFromNorthSwitchToWest() {
        launchRoverControllerScreen(roverControllerRule) {
            // Make sures initial petition finishes
            Thread.sleep(1000)
            clickOnLCommand()
            clickOnSendCommand()
        } verify {
            verifyDirection(Direction.W.name)
        }
    }

    @Test
    fun executeRightCommandFromNorthSwitchToEast() {
        launchRoverControllerScreen(roverControllerRule) {
            // Make sures initial petition finishes
            Thread.sleep(1000)
            clickOnRCommand()
            clickOnSendCommand()
        } verify {
            verifyDirection(Direction.E.name)
        }
    }

    fun launchRoverControllerScreen(
        rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        block: RoverControllerRobot.() -> Unit
    ): RoverControllerRobot {
        return RoverControllerRobot(rule).apply(block)
    }

    class RoverControllerRobot(
        private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
    ) {
        fun clickOnLCommand() {
            rule.onNodeWithTag(LEFT_COMMAND_BUTTON_TEST_TAG)
                .performClick()
        }

        fun clickOnSendCommand() = runTest {
            rule.onNodeWithTag(SEND_COMMANDS_BUTTON_TEST_TAG)
                .performClick()
        }

        infix fun verify(
            block: RoverControllerVerification.() -> Unit
        ): RoverControllerVerification {
            return RoverControllerVerification(rule).apply(block)
        }

        fun clickOnRCommand() {
            rule.onNodeWithTag(RIGHT_COMMAND_BUTTON_TEST_TAG)
                .performClick()
        }

    }

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

    }
}


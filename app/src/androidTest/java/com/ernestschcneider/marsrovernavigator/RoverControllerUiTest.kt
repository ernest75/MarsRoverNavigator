package com.ernestschcneider.marsrovernavigator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.CIRCULAR_PROGRESS_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.LEFT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.SEND_COMMAND_BUTTON_TEST_TAG
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
    fun contentIsShownAfterIntialPeticionFinishes() {
        launchRoverControllerScreen(roverControllerRule) {

            // Make sures initial petition finishes
            Thread.sleep(1000)
        } verify {
            onScreenVisible()
        }
    }

    @Test
    fun executeLeftCommand() {
        launchRoverControllerScreen(roverControllerRule) {
            Thread.sleep(2000)
            clickOnLCommand()
            clickOnSendCommand()
        } verify {
            directionIsChanged()
        }
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

    fun clickOnSendCommand() {
        rule.onNodeWithTag(SEND_COMMAND_BUTTON_TEST_TAG)
            .performClick()
    }

    infix fun verify(
        block: RoverControllerVerification.() -> Unit
    ): RoverControllerVerification {
        return RoverControllerVerification(rule).apply(block)
    }

}

class RoverControllerVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun directionIsChanged() {
        TODO()
    }

    fun onlyLoadingIsVisible() {
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertIsDisplayed()
        rule.onNodeWithTag(ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG).assertIsNotDisplayed()
    }

    fun onScreenVisible() {
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertIsNotDisplayed()
        rule.onNodeWithTag(ROVER_CONTROLLER_SCREEN_CONTENT_TEST_TAG).assertIsDisplayed()
    }

}


package com.ernestschcneider.marsrovernavigator

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.LEFT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.MOVE_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.RIGHT_COMMAND_BUTTON_TEST_TAG
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.ROVER_CELL_PREFIX_TEST_TAG
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

    @Test
    fun executeMoveCommandFacingNorthMovesNorthOneCell() {
        launchRoverControllerScreen(roverControllerRule) {
            // Make sures initial petition finishes
            Thread.sleep(1000)
            clickOnMoveCommand()
            clickOnSendCommand()
        } verify  {
            val expectedRoverCell = "$ROVER_CELL_PREFIX_TEST_TAG${0}_${1}"
            verifyPosition(expectedRoverCell)
        }
    }

    @Test
    fun executeRightCommandFacingNorthAndMoveCommandMovesWestOneCell() {
        launchRoverControllerScreen(roverControllerRule) {
            // Make sures initial petition finishes
            Thread.sleep(1000)
            clickOnRCommand()
            clickOnMoveCommand()
            clickOnSendCommand()
        } verify  {
            val expectedRoverCell = "$ROVER_CELL_PREFIX_TEST_TAG${1}_${0}"
            verifyPosition(expectedRoverCell)
        }
    }

    @Test
    fun executeMoveOnTheEdgeStaysThePositionDirectionChanged() {

        launchRoverControllerScreen(roverControllerRule) {
            // Make sures initial petition finishes
            Thread.sleep(1000)
            clickOnLCommand()
            clickOnMoveCommand()
            clickOnSendCommand()
        } verify  {
            val initialPosition = "${0}_${0}"
            val expectedRoverCell = "$ROVER_CELL_PREFIX_TEST_TAG$initialPosition"
            verifyDirection(Direction.W.name)
            verifyPosition(expectedRoverCell)
        }
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
            block: RoverControllerScreenRobot.RoverControllerVerification.() -> Unit
        ): RoverControllerScreenRobot.RoverControllerVerification {
            return RoverControllerScreenRobot.RoverControllerVerification(rule).apply(block)
        }

        fun clickOnRCommand() {
            rule.onNodeWithTag(RIGHT_COMMAND_BUTTON_TEST_TAG)
                .performClick()
        }

        fun clickOnMoveCommand() {
            rule.onNodeWithTag(MOVE_BUTTON_TEST_TAG)
                .performClick()
        }
    }
}

package com.ernestschcneider.marsrovernavigator

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ernestschcneider.marsrovernavigator.core.sharedutils.constants.MarsNavigatorUiTestTags.ROVER_CELL_PREFIX_TEST_TAG
import com.ernestschcneider.marsrovernavigator.domain.model.Direction
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
}

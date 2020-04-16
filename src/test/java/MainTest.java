import Actions.FillAction;
import Actions.JumpAction;
import Actions.MoveForwardAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineWorldInitializer;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    GameWorldType gameWorldType;
    GameWorld gameWorld;
    Action fill, jump, moveForward;
    Predicate hasDirt, pitInFront;

    @BeforeEach
    void setUp() {
        gameWorldType = new LineWorldInitializer();
        fill = (FillAction) gameWorldType.getAllActions().get(0);
        jump = (JumpAction) gameWorldType.getAllActions().get(1);
        moveForward = (MoveForwardAction) gameWorldType.getAllActions().get(2);
        hasDirt = (HasDirtPredicate) gameWorldType.getAllPredicates().get(0);
        pitInFront = (PitInFrontPredicate) gameWorldType.getAllPredicates().get(1);
        gameWorld = gameWorldType.createNewGameWorld();
    }

    @AfterEach
    void tearDown() {
        gameWorldType = null;
        moveForward = null;
        fill = null;
        jump = null;
        hasDirt = null;
        pitInFront = null;
        gameWorld= null;
    }

    @Test
    void success() {
        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(fill));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(moveForward));

        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(jump));

        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(fill));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(moveForward));

        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(jump));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(moveForward));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.END, gameWorld.executeAction(moveForward));

        assertThrows(IllegalStateException.class, () -> gameWorld.executeAction(moveForward));
    }

    @Test
    void failure_jumpInPit() {
        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.FAILURE, gameWorld.executeAction(jump));

        assertThrows(IllegalStateException.class, () -> gameWorld.executeAction(moveForward));
    }

    @Test
    void failure_noDirtLeft() {
        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(fill));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(moveForward));

        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertTrue(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(fill));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(moveForward));

        assertFalse(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.SUCCESS, gameWorld.executeAction(moveForward));

        assertTrue(gameWorld.evaluatePredicate(pitInFront));
        assertFalse(gameWorld.evaluatePredicate(hasDirt));
        assertEquals(Result.FAILURE, gameWorld.executeAction(jump));

        assertThrows(IllegalStateException.class, () -> gameWorld.executeAction(moveForward));
    }
}
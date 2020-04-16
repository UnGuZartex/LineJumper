package LineJumper;

import Actions.FillAction;
import Actions.JumpAction;
import Actions.MoveForwardAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineJumperInitializerTest {

    List<Action> actions;
    List<Predicate> predicates;
    GameWorld gameWorld;
    LineWorldInitializer initializer;

    @BeforeEach
    void setUp() {
        initializer = new LineWorldInitializer();
    }

    @AfterEach
    void tearDown() {
        actions = null;
        predicates = null;
        gameWorld = null;
        initializer = null;
    }

    @Test
    void getAllActions() {
        actions = initializer.getAllActions();
        assertEquals(3, actions.size());
        assertTrue(actions.get(0) instanceof FillAction);
        assertTrue(actions.get(1) instanceof JumpAction);
        assertTrue(actions.get(2) instanceof MoveForwardAction);
    }

    @Test
    void getAllPredicates() {
        predicates = initializer.getAllPredicates();
        assertEquals(2, predicates.size());
        assertTrue(predicates.get(0) instanceof HasDirtPredicate);
        assertTrue(predicates.get(1) instanceof PitInFrontPredicate);
    }

    @Test
    void loadFromFile() {
        assertThrows(IllegalStateException.class, () -> initializer.loadFromFile(null));
    }

    @Test
    void createNewGameWorld() {
        gameWorld = initializer.createNewGameWorld();
        assertTrue(gameWorld instanceof LineWorld);
    }
}
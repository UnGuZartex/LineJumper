import Actions.FillAction;
import Actions.JumpAction;
import Actions.MoveForwardAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;
import LineJumper.LineJumperInitializer;
import LineJumper.Player;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest { // TODO tests

    GameWorldType gameWorldType;
    GameWorld gameWorld;
    Action fill, jump, moveForward;
    Predicate hasDirt, pitInFront;

    @BeforeEach
    void setUp() {
        gameWorldType = new LineJumperInitializer();
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
    void dummy() {
//        LineJumperInitializer initializer = new LineJumperInitializer();
//
//        System.out.println(initializer.createNewGameWorld());
//
//        boolean[] testArray = new boolean[] {true, false, false, true, false, false, true, true};
//
//        LineJumper jumper = new LineJumper(testArray, new Player(2,2));
//
//
//        System.out.println(jumper);
//
//        System.out.println(jumper.evaluatePredicate(new HasDirtPredicate()));
//
//        System.out.println(jumper.evaluatePredicate(new PitInFrontPredicate()));
//
//        System.out.println(jumper.executeAction(new FillAction()));
//        System.out.println(jumper);
//
//        System.out.println(jumper.executeAction(new MoveForwardAction()));
//        System.out.println(jumper);
//
//        System.out.println(jumper.executeAction(new JumpAction()));
//        System.out.println(jumper);
//
//        System.out.println(jumper.executeAction(new FillAction()));
//        System.out.println(jumper);
//
//        System.out.println(jumper.executeAction(new MoveForwardAction()));
//        System.out.println(jumper);
//
//        System.out.println(jumper.evaluatePredicate(new HasDirtPredicate()));
//
//        System.out.println(jumper.executeAction(new JumpAction()));
//        System.out.println(jumper);
//        System.out.println(jumper.evaluatePredicate(new PitInFrontPredicate()));
//
//
//
//        System.out.println(jumper.executeAction(new JumpAction()));
//        System.out.println(jumper);
    }
}
package LineJumper;

import Actions.FillAction;
import Actions.MoveForwardAction;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldAPI.History.Snapshot;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LineJumperTest {

    LineJumper jumperBeforePit, jumperNotBeforePit, jumperBeforeEnd;
    Player playerBeforePit, playerNotBeforePit, playerBeforeEnd;
    boolean[] lineBeforePit, lineNotBeforePit, lineBeforeEnd;
    int lineLength, posBeforePit, posNotBeforePit;
    int jumpLengthBeforePit, jumpLengthNotBeforePut, jumpLengthBeforeEnd;
    int amountOfDirtBeforePit, amountOfDirtNotBeforePit, amountOfDirtBeforeEnd;
    Random random;
    static final int MAX_LINE_LENGTH = 10, MIN_LINE_LENGTH = 3, MIN_PLAYER_POS = 0;
    static final int MIN_JUMP_LENGTH = 1, MAX_JUMP_LENGTH = 5;
    static final int MIN_AMOUNT_DIRT = 1, MAX_AMOUNT_DIRT = 10;
    Action moveForward, fill;
    Predicate hasDirt, pitInFront;
    Snapshot snapshot;

    @BeforeEach
    void setUp() {
        random = new Random();

        lineLength = random.nextInt(MAX_LINE_LENGTH + 1 - MIN_LINE_LENGTH) + MIN_LINE_LENGTH;
        posBeforePit = random.nextInt(lineLength - 1 - MIN_PLAYER_POS) + MIN_PLAYER_POS;
        posNotBeforePit = random.nextInt(lineLength - 1 - MIN_PLAYER_POS) + MIN_PLAYER_POS;
        jumpLengthBeforePit = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        jumpLengthNotBeforePut = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        jumpLengthBeforeEnd = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        amountOfDirtBeforePit = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;
        amountOfDirtNotBeforePit = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;
        amountOfDirtBeforeEnd = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;

        playerBeforePit = new Player(jumpLengthBeforePit, amountOfDirtBeforePit);
        while (playerBeforePit.getPosition() < posBeforePit) {
            playerBeforePit.moveForward();
        }
        playerNotBeforePit = new Player(jumpLengthBeforePit, amountOfDirtNotBeforePit);
        while (playerNotBeforePit.getPosition() < posNotBeforePit) {
            playerNotBeforePit.moveForward();
        }
        playerBeforeEnd = new Player(jumpLengthBeforePit, amountOfDirtBeforeEnd);
        while (playerBeforeEnd.getPosition() < lineLength - 1) {
            playerBeforeEnd.moveForward();
        }

        lineBeforePit = new boolean[lineLength];
        lineNotBeforePit = new boolean[lineLength];
        lineBeforeEnd = new boolean[lineLength];
        for (int i = 0; i < lineLength; i++) {
            lineBeforePit[i] = random.nextBoolean();
            lineNotBeforePit[i] = random.nextBoolean();
            lineBeforeEnd[i] = random.nextBoolean();
        }
        lineBeforePit[posBeforePit] = true;
        lineBeforePit[posBeforePit + 1] = false;
        lineNotBeforePit[posNotBeforePit] = true;
        lineNotBeforePit[posNotBeforePit + 1] = true;
        lineBeforeEnd[lineLength - 1] = true;

        jumperBeforePit = new LineJumper(lineBeforePit, playerBeforePit);
        jumperNotBeforePit = new LineJumper(lineNotBeforePit, playerNotBeforePit);
        jumperBeforeEnd = new LineJumper(lineBeforeEnd, playerBeforeEnd);

        pitInFront = new PitInFrontPredicate();
        hasDirt = new HasDirtPredicate();
        moveForward = new MoveForwardAction();
        fill = new FillAction();
    }

    @AfterEach
    void tearDown() {
        random = null;
        jumperBeforeEnd = null;
        jumperBeforePit = null;
        jumperNotBeforePit = null;
        playerBeforePit = null;
        playerNotBeforePit = null;
        playerBeforeEnd = null;
        lineBeforePit = null;
        lineNotBeforePit = null;
        lineBeforeEnd = null;
        pitInFront = null;
        hasDirt = null;
        fill = null;
        moveForward = null;
        snapshot = null;
    }

    @Test
    void lineJumper_invalidLine() {
        assertFalse(LineJumper.isValidLine(new boolean[0]));
        assertTrue(LineJumper.isValidPlayer(playerBeforeEnd));
        assertThrows(IllegalArgumentException.class, () -> new LineJumper(new boolean[0], playerBeforeEnd));
    }

    @Test
    void lineJumper_invalidPlayer() {
        assertTrue(LineJumper.isValidLine(lineBeforeEnd));
        assertFalse(LineJumper.isValidPlayer(null));
        assertThrows(IllegalArgumentException.class, () -> new LineJumper(lineBeforeEnd, null));
    }

    @Test
    void lineJumper_playerCantStandOnLine() {
        lineBeforeEnd[lineLength - 1] = false;
        assertTrue(LineJumper.isValidLine(lineBeforeEnd));
        assertTrue(LineJumper.isValidPlayer(playerBeforeEnd));
        assertFalse(lineBeforeEnd[playerBeforeEnd.getPosition()]);
        assertThrows(IllegalArgumentException.class, () -> new LineJumper(lineBeforeEnd, playerBeforeEnd));
    }

    @Test
    void isValidLine() {
        assertFalse(LineJumper.isValidLine(new boolean[0]));
        assertFalse(LineJumper.isValidLine(null));
        assertTrue(LineJumper.isValidLine(lineBeforeEnd));
        assertTrue(LineJumper.isValidLine(lineBeforePit));
        assertTrue(LineJumper.isValidLine(lineNotBeforePit));
    }

    @Test
    void isValidPlayer() {
        assertFalse(LineJumper.isValidPlayer(null));
        assertTrue(LineJumper.isValidPlayer(playerBeforeEnd));
        assertTrue(LineJumper.isValidPlayer(playerNotBeforePit));
        assertTrue(LineJumper.isValidPlayer(playerBeforePit));
    }

    @Test
    void getPlayer_mayDoAction() {
        assertEquals(playerBeforeEnd, jumperBeforeEnd.getPlayer());
        assertEquals(playerBeforePit, jumperBeforePit.getPlayer());
        assertEquals(playerNotBeforePit, jumperNotBeforePit.getPlayer());
    }

    @Test
    void getPlayer_mayNotDoAction() {
        jumperBeforeEnd.executeAction(moveForward);
        jumperBeforePit.executeAction(moveForward);
        assertThrows(IllegalStateException.class, () -> jumperBeforeEnd.getPlayer());
        assertThrows(IllegalStateException.class, () -> jumperBeforePit.getPlayer());
    }

    @Test
    void fillInFront_success() {
        assertEquals(amountOfDirtBeforePit, playerBeforePit.getAmountOfDirt());
        assertFalse(lineBeforePit[posBeforePit + 1]);
        jumperBeforePit.fillInFront();
        assertEquals(amountOfDirtBeforePit - 1, playerBeforePit.getAmountOfDirt());
        assertTrue(lineBeforePit[posBeforePit + 1]);
    }

    @Test
    void fillInFront_noPit() {
        assertEquals(amountOfDirtNotBeforePit, playerNotBeforePit.getAmountOfDirt());
        assertTrue(lineNotBeforePit[posNotBeforePit + 1]);
        jumperBeforePit.fillInFront();
        assertEquals(amountOfDirtNotBeforePit, playerNotBeforePit.getAmountOfDirt());
        assertTrue(lineNotBeforePit[posNotBeforePit + 1]);
    }

    @Test
    void fillInFront_noDirt() {
        while (playerBeforePit.hasDirt()) {
            playerBeforePit.useDirt();
        }
        assertEquals(0, playerBeforePit.getAmountOfDirt());
        assertFalse(lineBeforePit[posBeforePit + 1]);
        jumperBeforePit.fillInFront();
        assertEquals(0, playerBeforePit.getAmountOfDirt());
        assertFalse(lineBeforePit[posBeforePit + 1]);
    }

    @Test
    void playerHasPitInFront() {
        assertFalse(jumperBeforeEnd.playerHasPitInFront());
        assertTrue(jumperBeforePit.playerHasPitInFront());
        assertFalse(jumperNotBeforePit.playerHasPitInFront());
    }

    @Test
    void executeAction_mayDoAction() {
        assertEquals(Result.END, jumperBeforeEnd.executeAction(moveForward));
        assertEquals(Result.FAILURE, jumperBeforePit.executeAction(moveForward));
        assertEquals(Result.SUCCESS, jumperNotBeforePit.executeAction(moveForward));
    }

    @Test
    void executeAction_mayNotDoAction() {
        assertTrue(jumperBeforeEnd.mayDoAction());
        assertTrue(jumperBeforePit.mayDoAction());
        assertEquals(Result.END, jumperBeforeEnd.executeAction(moveForward));
        assertEquals(Result.FAILURE, jumperBeforePit.executeAction(moveForward));
        assertThrows(IllegalStateException.class, () -> jumperBeforeEnd.executeAction(moveForward));
        assertThrows(IllegalStateException.class, () -> jumperBeforePit.executeAction(moveForward));
    }

    @Test
    void mayDoAction() {
        assertTrue(jumperBeforeEnd.mayDoAction());
        assertTrue(jumperBeforePit.mayDoAction());
        assertTrue(jumperNotBeforePit.mayDoAction());
        jumperBeforeEnd.executeAction(moveForward);
        jumperBeforePit.executeAction(moveForward);
        jumperNotBeforePit.executeAction(moveForward);
        assertFalse(jumperBeforeEnd.mayDoAction());
        assertFalse(jumperBeforePit.mayDoAction());
        assertTrue(jumperNotBeforePit.mayDoAction());
    }

    @Test
    void evaluatePredicate_hasDirt() {
        assertTrue(jumperBeforeEnd.evaluatePredicate(hasDirt));
        assertTrue(jumperBeforePit.evaluatePredicate(hasDirt));
        assertTrue(jumperNotBeforePit.evaluatePredicate(hasDirt));
        while (playerBeforeEnd.hasDirt()) playerBeforeEnd.useDirt();
        assertFalse(jumperBeforeEnd.evaluatePredicate(hasDirt));
    }

    @Test
    void evaluatePredicate_pitInFront() {
        assertFalse(jumperBeforeEnd.evaluatePredicate(pitInFront));
        assertTrue(jumperBeforePit.evaluatePredicate(pitInFront));
        assertFalse(jumperNotBeforePit.evaluatePredicate(pitInFront));
    }

    @Test
    void snapshot() {
        snapshot = jumperBeforePit.createSnapshot();
        jumperBeforePit.executeAction(fill);
        jumperBeforePit.executeAction(moveForward);
        jumperBeforePit.executeAction(moveForward);

        jumperBeforePit.loadSnapshot(snapshot);
        assertEquals(posBeforePit, jumperBeforePit.getPlayer().getPosition());
        assertEquals(amountOfDirtBeforePit, jumperBeforePit.getPlayer().getAmountOfDirt());
        assertTrue(jumperBeforePit.playerHasPitInFront());
    }
}
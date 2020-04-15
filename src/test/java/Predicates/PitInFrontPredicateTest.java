package Predicates;

import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;
import LineJumper.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PitInFrontPredicateTest {

    LineJumper jumper, jumperNoPit;
    Predicate predicate;
    Player player, playerNoPit;
    int jumpLength, jumpLengthNoPit;
    int amountOfDirt, amountOfDirtNoPit;
    int nbOfMoveForward;
    Random random;
    boolean[] line, lineNoPit;
    static final int MIN_JUMP_LENGTH = 1, MAX_JUMP_LENGTH = 5;
    static final int MIN_AMOUNT_DIRT = 1, MAX_AMOUNT_DIRT = 10;
    static final int MIN_NUMBER_STEP = 0, MAX_NUMBER_STEP = 20;
    static final int MAX_NUMBER_EXTRA_POS = 10;

    @BeforeEach
    void setUp() {
        predicate = new PitInFrontPredicate();

        random = new Random();

        jumpLength = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        jumpLengthNoPit = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        amountOfDirt = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;
        amountOfDirtNoPit = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;

        player = new Player(jumpLength, amountOfDirt);
        playerNoPit = new Player(jumpLengthNoPit, amountOfDirtNoPit);

        nbOfMoveForward = random.nextInt(MAX_NUMBER_STEP + 1 - MIN_NUMBER_STEP) + MIN_NUMBER_STEP;
        for (int i = 0; i < nbOfMoveForward; i++) {
            player.moveForward();
            playerNoPit.moveForward();
        }

        line = new boolean[nbOfMoveForward + random.nextInt(MAX_NUMBER_EXTRA_POS + 2) + 2];
        lineNoPit = new boolean[nbOfMoveForward + random.nextInt(MAX_NUMBER_EXTRA_POS + 2) + 2];

        for (int i = 0; i < line.length; i++) {
            line[i] = random.nextBoolean();
        }
        line[nbOfMoveForward] = true;
        line[nbOfMoveForward + 1] = false;

        for (int i = 0; i < lineNoPit.length; i++) {
            lineNoPit[i] = random.nextBoolean();
        }
        lineNoPit[nbOfMoveForward] = true;
        lineNoPit[nbOfMoveForward + 1] = true;

        jumper = new LineJumper(line, player);
        jumperNoPit = new LineJumper(lineNoPit, playerNoPit);
    }

    @AfterEach
    void tearDown() {
        predicate = null;
        random = null;
        player = null;
        playerNoPit = null;
        line = null;
        lineNoPit = null;
        jumper = null;
        jumperNoPit = null;
    }

    @Test
    void getName() {
        assertEquals("Pit in Front", predicate.getName());
    }

    @Test
    void evaluate_pitInFront() {
        assertTrue(predicate.evaluate(jumper));
    }

    @Test
    void evaluate_notPitInFront() {
        assertFalse(predicate.evaluate(jumperNoPit));
    }

}
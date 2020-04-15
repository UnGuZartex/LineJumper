package Predicates;

import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;
import LineJumper.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HasDirtPredicateTest {

    LineJumper jumper, jumperNoDirt;
    Predicate predicate;
    Player player, playerNoDirt;
    int jumpLength, jumpLengthNoDirt;
    int amountOfDirt;
    int nbOfMoveForward;
    Random random;
    boolean[] line, lineNoDirt;
    static final int MIN_JUMP_LENGTH = 1, MAX_JUMP_LENGTH = 5;
    static final int MIN_AMOUNT_DIRT = 1, MAX_AMOUNT_DIRT = 10;
    static final int MIN_NUMBER_STEP = 0, MAX_NUMBER_STEP = 20;
    static final int MAX_NUMBER_EXTRA_POS = 10;

    @BeforeEach
    void setUp() {
        predicate = new HasDirtPredicate();

        random = new Random();

        jumpLength = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        jumpLengthNoDirt = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        amountOfDirt = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;

        player = new Player(jumpLength, amountOfDirt);
        playerNoDirt = new Player(jumpLengthNoDirt, 0);

        nbOfMoveForward = random.nextInt(MAX_NUMBER_STEP + 1 - MIN_NUMBER_STEP) + MIN_NUMBER_STEP;
        for (int i = 0; i < nbOfMoveForward; i++) {
            player.moveForward();
            playerNoDirt.moveForward();
        }

        line = new boolean[nbOfMoveForward + random.nextInt(MAX_NUMBER_EXTRA_POS + 2) + 2];
        lineNoDirt = new boolean[nbOfMoveForward + random.nextInt(MAX_NUMBER_EXTRA_POS + 2) + 2];

        for (int i = 0; i < line.length; i++) {
            line[i] = random.nextBoolean();
        }
        line[nbOfMoveForward] = true;

        for (int i = 0; i < lineNoDirt.length; i++) {
            lineNoDirt[i] = random.nextBoolean();
        }
        lineNoDirt[nbOfMoveForward] = true;

        jumper = new LineJumper(line, player);
        jumperNoDirt = new LineJumper(lineNoDirt, playerNoDirt);
    }

    @AfterEach
    void tearDown() {
        predicate = null;
        random = null;
        player = null;
        playerNoDirt = null;
        line = null;
        lineNoDirt = null;
        jumper = null;
        jumperNoDirt = null;
    }

    @Test
    void getName() {
        assertEquals("Has Dirt", predicate.getName());
    }

    @Test
    void evaluate_hasDirt() {
        assertTrue(predicate.evaluate(jumper));
    }

    @Test
    void evaluate_hasNoDirt() {
        assertFalse(predicate.evaluate(jumperNoDirt));
    }
}
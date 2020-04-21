package Actions;

import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineWorld;
import LineJumper.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FillActionTest {

    LineWorld jumper;
    Action action;
    Player player;
    int jumpLength;
    int amountOfDirt;
    int nbOfMoveForward;
    Random random;
    boolean[] line;
    static final int MIN_JUMP_LENGTH = 1, MAX_JUMP_LENGTH = 5;
    static final int MIN_AMOUNT_DIRT = 1, MAX_AMOUNT_DIRT = 10;
    static final int MIN_NUMBER_STEP = 0, MAX_NUMBER_STEP = 20;
    static final int MAX_NUMBER_EXTRA_POS = 10;

    @BeforeEach
    void setUp() {
        action = new FillAction();

        random = new Random();

        jumpLength = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        amountOfDirt = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;
        player = new Player(jumpLength, amountOfDirt);

        nbOfMoveForward = random.nextInt(MAX_NUMBER_STEP + 1 - MIN_NUMBER_STEP) + MIN_NUMBER_STEP;
        for (int i = 0; i < nbOfMoveForward; i++) {
            player.moveForward();
        }

        line = new boolean[nbOfMoveForward + random.nextInt(MAX_NUMBER_EXTRA_POS + 2) + 2];

        for (int i = 0; i < line.length; i++) {
            line[i] = random.nextBoolean();
        }
        line[nbOfMoveForward] = true;
        line[nbOfMoveForward + 1] = false;
        line[line.length - 1] = true;

        jumper = new LineWorld(line, player);
    }

    @AfterEach
    void tearDown() {
        action = null;
        random = null;
        player = null;
        line = null;
        jumper = null;
    }

    @Test
    void getName() {
        assertEquals("Fill Pit", action.getName());
    }

    @Test
    void execute() {
        assertTrue(jumper.playerHasPitInFront());
        action.execute(jumper);
        assertFalse(jumper.playerHasPitInFront());
    }
}
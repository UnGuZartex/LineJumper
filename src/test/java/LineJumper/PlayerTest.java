package LineJumper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player playerMove, player, playerNoDirt, copy;
    int jumpLengthMove, jumpLength, jumpLengthNoDirt;
    int amountOfDirtMove, amountOfDirt;
    int nbOfMoveForward;
    Random random;
    static final int MIN_JUMP_LENGTH = 1, MAX_JUMP_LENGTH = 5;
    static final int MIN_AMOUNT_DIRT = 1, MAX_AMOUNT_DIRT = 10;
    static final int MIN_NUMBER_STEP = 0, MAX_NUMBER_STEP = 20;

    @BeforeEach
    void setUp() {
        random = new Random();

        jumpLengthMove = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        jumpLength = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        jumpLengthNoDirt = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        amountOfDirtMove = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;
        amountOfDirt = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;

        playerMove = new Player(jumpLengthMove, amountOfDirtMove);
        player = new Player(jumpLength, amountOfDirt);
        playerNoDirt = new Player(jumpLengthNoDirt, 0);

        nbOfMoveForward = random.nextInt(MAX_NUMBER_STEP + 1 - MIN_NUMBER_STEP) + MIN_NUMBER_STEP;
        for (int i = 0; i < nbOfMoveForward; i++) {
            playerMove.moveForward();
        }
    }

    @AfterEach
    void tearDown() {
        random = null;
        playerMove = null;
        player = null;
        playerNoDirt = null;
        copy = null;
    }

    @Test
    void player_invalidJumpLength() {
        assertFalse(Player.isValidJumpLength(-1));
        assertTrue(Player.isValidJumpLength(jumpLength));
        assertThrows(IllegalArgumentException.class, () -> { new Player(-1, jumpLength); });
    }

    @Test
    void player_invalidAmountOfDirt() {
        assertThrows(IllegalArgumentException.class, () -> { new Player(jumpLength, -1); });
    }

    @Test
    void isValidJumpLength() {
        assertTrue(Player.isValidJumpLength(0));
        assertTrue(Player.isValidJumpLength(1));
        assertTrue(Player.isValidJumpLength(jumpLengthMove));
        assertTrue(Player.isValidJumpLength(jumpLength));
        assertTrue(Player.isValidJumpLength(jumpLengthNoDirt));
        assertFalse(Player.isValidJumpLength(-1));
    }

    @Test
    void getPosition() {
        assertEquals(nbOfMoveForward, playerMove.getPosition());
        assertEquals(0, player.getPosition());
        assertEquals(0, playerNoDirt.getPosition());
    }

    @Test
    void getAmountOfDirt() {
        assertEquals(amountOfDirtMove, playerMove.getAmountOfDirt());
        assertEquals(amountOfDirt, player.getAmountOfDirt());
        assertEquals(0, playerNoDirt.getAmountOfDirt());
    }

    @Test
    void getPlayerJumpLength() {
        assertEquals(jumpLengthMove, playerMove.getPlayerJumpLength());
        assertEquals(jumpLength, player.getPlayerJumpLength());
        assertEquals(jumpLengthNoDirt, playerNoDirt.getPlayerJumpLength());
    }

    @Test
    void jump() {
        playerMove.jump();
        assertEquals(nbOfMoveForward + jumpLengthMove, playerMove.getPosition());
        player.jump();
        player.jump();
        assertEquals(2 * jumpLength, player.getPosition());
        playerNoDirt.jump();
        playerNoDirt.jump();
        playerNoDirt.jump();
        playerNoDirt.jump();
        assertEquals(4 * jumpLengthNoDirt, playerNoDirt.getPosition());
    }

    @Test
    void moveForward() {
        playerMove.moveForward();
        assertEquals(nbOfMoveForward + 1, playerMove.getPosition());
        player.moveForward();
        player.moveForward();
        assertEquals(2, player.getPosition());
        playerNoDirt.moveForward();
        playerNoDirt.moveForward();
        playerNoDirt.moveForward();
        playerNoDirt.moveForward();
        assertEquals(4, playerNoDirt.getPosition());
    }

    @Test
    void hasDirt() {
        assertTrue(playerMove.hasDirt());
        assertTrue(player.hasDirt());
        assertFalse(playerNoDirt.hasDirt());
    }

    @Test
    void useDirt() {
        playerMove.useDirt();
        assertEquals(amountOfDirtMove - 1, playerMove.getAmountOfDirt());
        player.useDirt();
        assertEquals(amountOfDirt - 1, player.getAmountOfDirt());
        playerNoDirt.useDirt();
        assertEquals(0, playerNoDirt.getAmountOfDirt());
    }

    @Test
    void copy() {
        copy = playerMove.copy();
        assertNotEquals(playerMove, copy);
        assertEquals(playerMove.getPosition(), copy.getPosition());
        assertEquals(playerMove.hasDirt(), copy.hasDirt());
    }
}
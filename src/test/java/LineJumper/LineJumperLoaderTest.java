package LineJumper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineJumperLoaderTest {

    LineJumperLoader loader;
    LineJumper defaultJumper, randomJumper;

    @BeforeEach
    void setUp() {
        loader = new LineJumperLoader();

    }

    @AfterEach
    void tearDown() {
        loader = null;
        defaultJumper = null;
        randomJumper = null;
    }

    @Test
    void loadDefaultLineJumper() {
        defaultJumper = loader.loadDefaultLineJumper();
        assertTrue(defaultJumper.playerHasPitInFront());
        assertEquals(0, defaultJumper.getPlayer().getPosition());
        assertEquals(2, defaultJumper.getPlayer().getAmountOfDirt());
        defaultJumper.getPlayer().jump(); // position is jump length
        assertEquals(2, defaultJumper.getPlayer().getPosition());
        assertFalse(defaultJumper.playerHasPitInFront());
        // Other places in line can't be checked because of encapsulation.
    }

    @Test
    void loadRandomLineJumper() {
        randomJumper = loader.loadRandomLineJumper();
        assertEquals(0, randomJumper.getPlayer().getPosition());
        assertTrue(randomJumper.getPlayer().getAmountOfDirt() <= LineJumperLoader.MAX_AMOUNT_DIRT);
        assertTrue(randomJumper.getPlayer().getAmountOfDirt() >= LineJumperLoader.MIN_AMOUNT_DIRT);
        randomJumper.getPlayer().jump(); // position is jump length
        assertTrue(randomJumper.getPlayer().getPosition() <= LineJumperLoader.MAX_JUMP_LENGTH);
        assertTrue(randomJumper.getPlayer().getPosition() >= LineJumperLoader.MIN_JUMP_LENGTH);
    }
}
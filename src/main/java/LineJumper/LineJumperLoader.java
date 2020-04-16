package LineJumper;

import java.util.Random;

/**
 * A class to generate line jumpers and load them.
 *
 * @author Alpha-team
 */
public class LineJumperLoader {

    /**
     * Variable referring to the default line for a line jumper, which looks like
     *
     *    __       __       __ __
     *      |__ __|  |__ __|
     *
     */
    private static final boolean[] defaultLine = new boolean[] {true, false, false, true, false, false, true, true};
    /**
     * Variable referring to the default player, which has a jump length of 2 and a amount of
     * dirt of 2.
     */
    private static final Player defaultPlayer = new Player(2,2);

    /**
     * Load the default line jumper.
     *
     * @return A new line jumper with a copy of the default line and default player.
     */
    public LineJumper loadDefaultLineJumper() {
        return new LineJumper(defaultLine.clone(), defaultPlayer.copy());
    }

    /**
     * Variable referring to the random object.
     */
    private final Random random = new Random();
    /**
     * Variables referring to the range of the jump length, the amount of
     * dirt and the line length to generate.
     */
    public static final int MIN_JUMP_LENGTH = 2, MAX_JUMP_LENGTH = 5;
    public static final int MIN_AMOUNT_DIRT = 5, MAX_AMOUNT_DIRT = 10;
    public static final int MIN_LINE_LENGTH = 5, MAX_LINE_LENGTH = 20;

    /**
     * Generate a random line jumper.
     *
     * @return A new random line jumper with a randomly generated line and a
     *         a player with random properties, all staying within the ranges.
     */
    public LineJumper loadRandomLineJumper() {
        int randomJumpLength = random.nextInt(MAX_JUMP_LENGTH + 1 - MIN_JUMP_LENGTH) + MIN_JUMP_LENGTH;
        int randomAmountDirt = random.nextInt(MAX_AMOUNT_DIRT + 1 - MIN_AMOUNT_DIRT) + MIN_AMOUNT_DIRT;
        int randomLineLength = random.nextInt(MAX_LINE_LENGTH + 1 - MIN_LINE_LENGTH) + MIN_LINE_LENGTH;
        Player randomPlayer = new Player(randomJumpLength, randomAmountDirt);
        boolean[] randomLine = generateRandomLine(randomLineLength, randomJumpLength, randomAmountDirt);
        return new LineJumper(randomLine, randomPlayer);
    }

    /**
     * Generate a random line for the LineJumper that is solvable.
     *
     * @param lineLength The length of the line.
     * @param playerJumpLength The jump distance of the player.
     * @param amountOfDirt The amount of dirt the player has.
     *
     * @return A boolean list consisting of the given length. It is solvable for a
     *         player with given properties .
     */
    private boolean[] generateRandomLine(int lineLength, int playerJumpLength, int amountOfDirt) {
        Random random = new Random();
        int falseCount = 0;
        boolean[] returnLine = new boolean[lineLength];
        returnLine[0] = true;
        returnLine[lineLength-1] = true;

        for (int i = 1; i < lineLength - 1; i++) {
            returnLine[i] = random.nextBoolean();

            if (falseCount >= playerJumpLength + amountOfDirt - 1) {
                returnLine[i] = true;
            }
            else if (falseCount >= playerJumpLength) {
                amountOfDirt--;
                if (amountOfDirt < 0) {
                    amountOfDirt = 0;
                }
            }

            falseCount = returnLine[i] ? 0 : ++falseCount;
        }

        return returnLine;
    }
}

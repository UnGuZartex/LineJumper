package LineJumper;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldAPI.History.Snapshot;

import java.util.Arrays;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Random;

public class LineJumper implements GameWorld {

    /**
     * Variable referencing to the line the player is walking on.
     * This line is a boolean list, true is a walkable surface, false is a pit
     */
    private boolean[] line;
    /**
     * Variable referencing to the player walking on the line
     */
    private Player player;

    /**
     * Initialise a new line jumper with given line and player.
     *
     * @param line The line for this line jumper.
     * @param player The player for this line jumper.
     *
     * @post The line of this line jumper is set to the given line.
     * @post The player of this line jumper is set to the given player.
     *
     * @throws IllegalArgumentException
     *         If the given line is invalid.
     * @throws IllegalArgumentException
     *         If the given player is invalid.
     * @throws IllegalArgumentException
     *         If the given player can not stand on the given line.
     */
    public LineJumper(boolean[] line, Player player) throws IllegalArgumentException {
        if (!isValidLine(line)) {
            throw new IllegalArgumentException("The given line is not valid!");
        }
        if (!isValidPlayer(player)) {
            throw new IllegalArgumentException("The given line is not valid!");
        }
        if (!line[player.getPosition()]) {
            throw new IllegalArgumentException("The given player can not stand on the given line!");
        }
        this.line = line;
        this.player = player;
    }

    /**
     * Checks whether or not the given line is valid.
     *
     * @param line The line to check.
     *
     * @return True if and only if the given line is effective and has
     *         at least one position.
     */
    public static boolean isValidLine(boolean[] line) {
        return line != null && line.length > 0;
    }

    /**
     * Checks whether or not the given player is valid.
     *
     * @param player The player to check.
     *
     * @return True if and only if the given player is effective.
     */
    public static boolean isValidPlayer(Player player) {
        return player != null;
    }

    /**
     * Get the player of this line jumper.
     *
     * @return The player in this line jumper.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     *  Fills a pit in front of the player.
     *
     * @post If the player player has dirt and a pit is in front, then the
     *       is the value of the line in front of the player set to true.
     *
     * @effect If the player player has dirt and a pit is in front, then the
     *         player uses dirt.
     */
    public void fillInFront() {
        if (playerHasPitInFront() && player.hasDirt()) {
            line[player.getPosition()+1] = true;
            player.useDirt();
        }
    }

    /**
     * Checks whether a pit is in front of the player.
     *
     * @return True if and only if the line is not walkable in front of the player
     *         end if the line ends before the player.
     */
    public boolean playerHasPitInFront() {
        try {
            return !line[player.getPosition()+1];
        } catch (ArrayIndexOutOfBoundsException ignore) {
            return false;
        }
    }

    /**
     * Execute the given action on this line jumper.
     *
     * @param action The action to execute.
     *
     * @return END result if the player has passed by the last position of
     *         the line, SUCCESS if the player is standing on a walkable
     *         position and FAILURE in all other cases.
     */
    @Override
    public Result executeAction(Action action) {
        action.execute(this);
        int newPosition = player.getPosition();
        if (newPosition >= line.length) return Result.END;
        if (line[newPosition]) return Result.SUCCESS;
        return Result.FAILURE;
    }

    /**
     * Evaluate the given predicate on this line jumper.
     *
     * @param predicate The predicate to evaluate.
     *
     * @return The evaluation of the given predicate.
     */
    @Override
    public boolean evaluatePredicate(Predicate predicate) {
        return predicate.evaluate(this);
    }

    /**
     * Create a new snapshot of this line jumper.
     *
     * @return A new line jumper snapshot.
     */
    @Override
    public Snapshot createSnapshot() {
        return new LineJumperSnapshot();
    }

    /**
     * Load the given snap shot.
     *
     * @param snapshot The snapshot to load.
     *
     * @post The line of this line jumper is set to the line in the given snapshot.
     * @post The player of this line jumper is set to the player in the given snapshot.
     *
     * @throws IllegalArgumentException
     *         If the given snapshot doesn't have a valid line.
     * @throws IllegalArgumentException
     *         If the given snapshot doesn't have a valid player.
     */
    @Override
    public void loadSnapshot(Snapshot snapshot) throws IllegalArgumentException {
        LineJumperSnapshot lineJumperSnapshot = (LineJumperSnapshot) snapshot;
        if (!isValidLine(lineJumperSnapshot.lineMemento)) {
            throw new IllegalArgumentException("The given snapshot doesn't have a valid line!");
        }
        if (!isValidPlayer(lineJumperSnapshot.playerMemento)) {
            throw new IllegalArgumentException("The given snapshot doesn't have a valid player!");
        }
        this.line = lineJumperSnapshot.lineMemento;
        this.player = lineJumperSnapshot.playerMemento;
    }

    @Override
    public void paint(Graphics graphics) {
        //TODO
    }

    /**
     * A private class for snapshots of a line jumpers.
     */
    private class LineJumperSnapshot implements Snapshot {

        /**
         * Variable referring to the line to remember.
         */
        private final boolean[] lineMemento = line.clone();
        /**
         * Variable referring to the player to remember.
         */
        private final Player playerMemento = player.copy();
        /**
         * Variable referring to the creation time of this snapshot.
         */
        private final LocalDateTime creationTime = LocalDateTime.now();

        /**
         * Get the name of this snapshot.
         *
         * @return A string version of the line followed by the player.
         */
        @Override
        public String getName() {
            return Arrays.toString(lineMemento) + "  " + playerMemento.toString();
        }

        /**
         * Get the date of this snapshot.
         *
         * @return he creation time of this snapshot.
         */
        @Override
        public LocalDateTime getSnapshotDate() {
            return creationTime;
        }
    }




    // TODO remove
    public LineJumper(int lineLength, int playerJumpLength, int amountOfDirt) {
        this.line = generateRandomLine(lineLength, playerJumpLength, amountOfDirt);
        this.player = new Player(playerJumpLength, amountOfDirt);
    }

    // TODO naar een loader ofso
    /**
     * Generate a random line for the LineJumper that is solvable
     * @param lineLength the length of the line
     * @param playerJumpLength the jump distance of the player
     * @param amountOfDirt the amount of dirt the player has
     * @return a boolean list consisting out of a line that is solvable with this player.
     */
    private boolean[] generateRandomLine(int lineLength, int playerJumpLength, int amountOfDirt) {

        if (amountOfDirt < 0) {
            // TODO error throw
        }

        if (playerJumpLength <= 1) {
            // TODO error throw
        }

        if (lineLength <= 1) {
            // TODO error throw
        }

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




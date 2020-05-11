package LineJumper;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldAPI.Utility.Snapshot;
import Images.ImageLibrary;

import java.util.Arrays;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * A class that represents the game world of this game.
 *
 * @author Alpha-team
 */
public class LineWorld implements GameWorld {

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
     * Variable referring to the world painter of this line world.
     */
    private final LineWorldPainter worldPainter = new LineWorldPainter();

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
     *         If the given player cannot stand on the given line.
     * @throws IllegalArgumentException
     *         If the given line doesn't have a walkable end.
     */
    public LineWorld(boolean[] line, Player player) throws IllegalArgumentException {
        if (!isValidLine(line)) {
            throw new IllegalArgumentException("The given line is not valid!");
        }
        if (!isValidPlayer(player)) {
            throw new IllegalArgumentException("The given line is not valid!");
        }
        if (!line[player.getPosition()]) {
            throw new IllegalArgumentException("The given player cannot stand on the given line!");
        }
        if (!line[line.length-1]) {
            throw new IllegalArgumentException("The given line doesn't have a walkable end!");
        }
        this.line = line;
        this.player = player;
    }

    /**
     * Checks whether the given line is valid.
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
     *
     * @throws IllegalStateException
     *         If No action may be done. This prevents the player state to change anymore.
     */
    public Player getPlayer() throws IllegalStateException {
        if (!mayDoAction()) {
            throw new IllegalStateException("No action may be done and the player shouldn't be changed anymore!");
        }
        return this.player;
    }

    /**
     *  Fills a pit in front of the player.
     *
     * @post If the player has dirt, and a pit is in front, then the
     *       is the value of the line in front of the player set to true.
     *
     * @effect If the player has dirt, and a pit is in front, then the
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
     * @return END result if the player is on the last position of
     *         the line, SUCCESS if the player is standing on a walkable
     *         position and FAILURE in all other cases.
     *
     * @throws IllegalStateException
     *         If the robot may not do any action any more.
     */
    @Override
    public Result executeAction(Action action) throws IllegalStateException {
        if (!mayDoAction()) {
            throw new IllegalStateException("The player may not do any action!");
        }
        action.execute(this);
        return getResult();
    }

    /**
     * Checks whether an action may be done.
     *
     * @return If the current result is a success.
     */
    public boolean mayDoAction() {
        return getResult() == Result.SUCCESS;
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
        this.line = lineJumperSnapshot.lineMemento.clone();
        this.player = lineJumperSnapshot.playerMemento.copy();
    }

    /**
     * Paint this world.
     *
     * @param g The graphics to paint this level with.
     * @param library The image library containing the drawn images.
     *
     * @effect Paints this line world.
     */
    @Override
    public void paint(Graphics g, ImageLibrary library) {
        worldPainter.paint(g, library, line, player);
    }

    /**
     * Get the result of this line world.
     *
     * @return The ending result if the player is at the end of the line, the
     *         success if the player is on a walkable position and otherwise
     *         the failure result.
     */
    private Result getResult() {
        int newPosition = player.getPosition();
        if (newPosition >= line.length - 1) return Result.END;
        if (line[newPosition]) return Result.SUCCESS;
        return Result.FAILURE;
    }

    /**
     * A private class for snapshots of a line worlds.
     */
    private final class LineJumperSnapshot implements Snapshot {

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
}




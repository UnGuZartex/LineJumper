package LineJumper;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldAPI.History.Snapshot;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    public LineJumper(boolean[] line, int playerJumpLength, int amountOfDirt) {
        this.line = line;
        this.player = new Player(playerJumpLength, amountOfDirt);
    }

    public LineJumper(int lineLength, int playerJumpLength, int amountOfDirt) {
        this.line = generateRandomLine(lineLength, playerJumpLength, amountOfDirt);
        this.player = new Player(playerJumpLength, amountOfDirt);
    }

    public Player getPlayer() {
        return this.player;
    }


    /**
     *  Fills a pit in front of the player with dirt if the player has some
     *
     * @effect the player loses 1 dirt
     * @post the line array is updated such that the pit in front is filled
     */
    public void fillInFront() {
        if (playerHasPitInFront() && player.hasDirt()) {
            line[player.getPosition()+1] = true;
            player.useDirt();
        }
    }

    /**
     * Checks whether or not a player has a pit in front of him/her/it
     * @return true if a pit is in front of the player, false otherwise
     */
    public boolean playerHasPitInFront() {
        return (!line[player.getPosition()+1]);
    }

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

    @Override
    public Result executeAction(Action action) {
        action.execute(this);
        int newPosition = player.getPosition();
        if (newPosition >= line.length-1) return Result.END;
        if (line[newPosition]) return Result.SUCCESS;
        return Result.FAILURE;
    }

    @Override
    public boolean evaluatePredicate(Predicate predicate) {
        return predicate.evaluate(this);
    }

    @Override
    public Snapshot createSnapshot() {
        return new LineJumperSnapshot();
    }

    @Override
    public void loadSnapshot(Snapshot snapshot) {
        LineJumperSnapshot lineJumperSnapshot = (LineJumperSnapshot) snapshot;
        this.player = lineJumperSnapshot.playerMemento;
        this.line = lineJumperSnapshot.lineMemento;
    }

    @Override
    public void undo() {
//TODO
    }

    @Override
    public void redo() {
//TODO

    }

    @Override
    public void reset() {
//TODO
    }

    @Override
    public void paint(Graphics graphics) {
//TODO
    }

    @Override
    public String toString() {
        return Arrays.toString(line) + "   " + player.toString();
    }

    private class LineJumperSnapshot implements Snapshot {

        private final boolean[] lineMemento = line.clone();
        private final Player playerMemento = player.copy();
        private final LocalDateTime snapshotTime = LocalDateTime.now();

        @Override
        public String getName() {
            return Arrays.toString(lineMemento) + "  " + playerMemento.toString() + " @" + snapshotTime;
        }

        @Override
        public LocalDateTime getSnapshotDate() {
            return snapshotTime;
        }
    }
}




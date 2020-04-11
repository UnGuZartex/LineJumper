package LineJumper;

import Actions.LineJumperAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldAPI.History.Snapshot;
import Predicates.LineJumperPredicate;

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
      //TODO idk mss nog beter fixen enz (liefst wel)
        Random random = new Random();
        int falseCount = 0;
        boolean[] returnLine = new boolean[lineLength];
        returnLine[0] = true;
        for (int i = 1; i < lineLength; i++) {
            if (!returnLine[i]) {
                returnLine[i] = random.nextBoolean();
            }
            if (falseCount >= playerJumpLength + amountOfDirt - 1) {
                returnLine[i] = true;
            }
            if (falseCount >= playerJumpLength) {
                amountOfDirt--;
                if (amountOfDirt < 0) {
                    amountOfDirt = 0;
                }
            }
            if (!returnLine[i] && falseCount == 0 && i + playerJumpLength < lineLength && amountOfDirt == 0) {
                returnLine[i+playerJumpLength - 1] = true;
            }
            if (!returnLine[i]) {
                falseCount++;
            }
            else {
                falseCount = 0;
            }
        }
        returnLine[lineLength-1] = true;
        return returnLine;
    }

    @Override
    public Result executeAction(Action action) {
        ((LineJumperAction) action).setLineJumper(this);
        action.execute();

        int newPosition = player.getPosition();
        if (newPosition >= line.length-1) return Result.END;
        if (line[newPosition]) return Result.SUCCESS;
        return Result.FAILURE;
    }

    @Override
    public boolean evaluatePredicate(Predicate predicate) {
        ((LineJumperPredicate) predicate).setLineJumper(this);
        return predicate.evaluate();
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




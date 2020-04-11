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

    // true = walkable, false = pit
    private boolean[] line;
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

    public void fillInFront() {
        if (playerHasPitInFront() && player.hasDirt()) {
            line[player.getPosition()+1] = true;
            player.useDirt();
        }
    }

    private boolean[] generateRandomLine(int lineLength, int playerJumpLength, int amountOfDirt) {
      //TODO idk mss nog beter fixen enz
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
        if (line[newPosition]) return Result.SUCCESS;
        if (newPosition >= line.length-1) return Result.END;
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

    }

    @Override
    public void redo() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void paint(Graphics graphics) {

    }

    @Override
    public String toString() {
        return Arrays.toString(line);
    }

    public boolean playerHasPitInFront() {
        return (!line[player.getPosition()+1]);
    }

    private class LineJumperSnapshot implements Snapshot {

        private final boolean[] lineMemento = line.clone();
        private final Player playerMemento = player.clone();
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




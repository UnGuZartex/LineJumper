package LineJumper;

public class Player {
    private int playerPosition = 0;
    private final int playerJumpLength;
    private int amountOfDirt;

    public Player(int playerJumpLength, int amountOfDirt) {
        this.playerJumpLength = playerJumpLength;
        this.amountOfDirt = amountOfDirt;
    }

    private Player(int playerPosition, int playerJumpLength, int amountOfDirt) {
        this.playerPosition = playerPosition;
        this.playerJumpLength = playerJumpLength;
        this.amountOfDirt = amountOfDirt;
    }

    public int getPosition() {
        return playerPosition;
    }

    public void jump() {
        playerPosition += playerJumpLength;
    }

    public void moveForward() {
        playerPosition++;
    }

    public boolean hasDirt() {
        return amountOfDirt > 0;
    }

    public void useDirt() {
        amountOfDirt--;
    }

    public Player copy() {
        return new Player(playerPosition, playerJumpLength, amountOfDirt);
    }

    @Override
    public String toString() {
        return Integer.toString(playerPosition);
    }

}

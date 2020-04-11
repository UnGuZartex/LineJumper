package LineJumper;

public class Player {
    private int playerPosition = 0;
    private int playerJumpLength;
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

    public boolean hasDirt() {
        return amountOfDirt > 0;
    }

    public void useDirt() {
        amountOfDirt--;
    }

    @Override
    public Player clone() {
        return new Player(playerPosition, playerJumpLength, amountOfDirt);
    }
}

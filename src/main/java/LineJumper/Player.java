package LineJumper;

/**
 * A class for players.
 *
 * @invar The player must have valid amount of dirt at all time.
 *        | isValidJumpLength(jumpLength)
 *
 * @author Alpha-team
 */
public class Player {

    /**
     * Variable referring to the position of the player. Initially
     * this is set to 0.
     */
    private int playerPosition = 0;

    /**
     * Variable referring to the length this player can jump.
     */
    private final int playerJumpLength;

    /**
     * Variable referring to the amount of dirt a player can have.
     */
    private int amountOfDirt;

    /**
     * Initialise a new player with given jump length and amount of dirt.
     *
     * @param playerJumpLength The distance this player can jump.
     * @param amountOfDirt The amount of dirt this player initially has.
     *
     * @post This player's jump length is set to the given jump length.
     * @post This player's amount of dirt is set to the given amount of dirt.
     *
     * @throws IllegalArgumentException
     *         If the given jump length is not valid.
     * @throws IllegalArgumentException
     *         If the given amount of dirt is smaller than 0.
     */
    public Player(int playerJumpLength, int amountOfDirt) throws IllegalArgumentException {
        if (!isValidJumpLength(playerJumpLength)) {
            throw new IllegalArgumentException("The given jump length is not valid!");
        }
        if (amountOfDirt < 0) {
            throw new IllegalArgumentException("The amount of dirt is smaller than 0!");
        }
        this.playerJumpLength = playerJumpLength;
        this.amountOfDirt = amountOfDirt;
    }

    /**
     * Initialise a new player with given position, jump length and amount of dirt.
     *
     * @param playerPosition The position for this player.
     * @param playerJumpLength The distance this player can jump.
     * @param amountOfDirt The amount of dirt this player initially has.
     *
     * @post This player's position is set to the given position.
     * @post This player's jump length is set to the given jump length.
     * @post This player's amount of dirt is set to the given amount of dirt.
     */
    private Player(int playerPosition, int playerJumpLength, int amountOfDirt) {
        this.playerPosition = playerPosition;
        this.playerJumpLength = playerJumpLength;
        this.amountOfDirt = amountOfDirt;
    }

    /**
     * Check whether or not the given jump length is valid.
     *
     * @param jumpLength The jump length to check.
     *
     * @return True if and only if the given jump length is greater than or equal to 0.
     */
    public static boolean isValidJumpLength(int jumpLength) {
        return jumpLength >= 0;
    }

    /**
     * Get the position of this player.
     *
     * @return The position of this player.
     */
    public int getPosition() {
        return playerPosition;
    }

    /**
     * Get the amount of dirt this player has.
     *
     * @return The amount of dirt this player has.
     */
    public int getAmountOfDirt() {
        return amountOfDirt;
    }

    /**
     * Return the player jump length.
     *
     * @return the player jump length.
     */
    public int getPlayerJumpLength() {
        return playerJumpLength;
    }

    /**
     * Make this player jump.
     *
     * @post The player position is increased with the length
     *       this player can jump.
     */
    public void jump() {
        playerPosition += playerJumpLength;
    }

    /**
     * Make this player move forward.
     *
     * @post The player position is incremented.
     */
    public void moveForward() {
        playerPosition++;
    }

    /**
     * Check whether or not this player has dirt left.
     *
     * @return True if and only if the amount of dirt of this
     *         player is greater than 0.
     */
    public boolean hasDirt() {
        return amountOfDirt > 0;
    }

    /**
     * Make this player use dirt.
     *
     * @post The amount of dirt of this player is decremented if this
     *       player still has dirt.
     */
    public void useDirt() {
        if (hasDirt()) {
            amountOfDirt--;
        }
    }

    /**
     * Copy this player.
     *
     * @return A new player with the same position, jump length and
     *         amount of dirt as this player.
     */
    public Player copy() {
        return new Player(playerPosition, playerJumpLength, amountOfDirt);
    }
}

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving game events.
 * The class that is interested in processing a game
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGameListener<code> method. When
 * the game event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GameEvent
 */
public interface GameListener {

    /**
     * Token placed.
     *
     * @param column the column
     * @param token the token
     */
    public void tokenPlaced(int column, Token token);
    
    /**
     * Token hinted.
     *
     * @param column the column
     * @param token the token
     */
    public void tokenHinted(int column, Token token);

    /**
     * Token removed.
     *
     * @param column the column
     * @param token the token
     */
    public void tokenRemoved(int column, Token token);

    /**
     * New turn.
     *
     * @param player the player
     */
    public void newTurn(Player player);

    /**
     * Game started.
     */
    public void gameStarted();

    /**
     * Game won.
     *
     * @param player the player
     * @param col1 the col1
     * @param row1 the row1
     * @param col2 the col2
     * @param row2 the row2
     * @param numTokens the num tokens
     */
    public void gameWon(Player player, 
                        int col1, int row1, int col2, int row2, int numTokens);

    /**
     * Game drawn.
     */
    public void gameDrawn();

}

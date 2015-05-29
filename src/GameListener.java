
/**
 * The listener interface for receiving game events.
 * The class that is interested in processing a game
 * event implements this interface, and the object created
 * with that class is registered with a component. When
 * the game event occurs, that object's appropriate
 * method is invoked.
 *
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
     * @param column the column of token
     * @param token the token hinted
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
     * @param player the player whose turn it is
     */
    public void newTurn(Player player);

    /**
     * Game has started.
     */
    public void gameStarted();

    /**
     * Game is won.
     *
     * @param player the player who won
     * @param col1 the col1 coordinate
     * @param row1 the row1 coordinate
     * @param col2 the col2 coordinate
     * @param row2 the row2 coordinate
     * @param numTokens the num tokens
     */
    public void gameWon(Player player, 
                        int col1, int row1, int col2, int row2, int numTokens);

    /**
     * Game is drawn.
     */
    public void gameDrawn();

}

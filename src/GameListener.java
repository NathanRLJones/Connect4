
public interface GameListener {

    public void tokenPlaced(int column, Token token);
    
    public void tokenHinted(int column, Token token);

    public void tokenRemoved(int column, Token token);

    public void newTurn(Player player);

    public void gameStarted();

    public void gameWon(Player player, 
                        int col1, int row1, int col2, int row2, int numTokens);

    public void gameDrawn();

}

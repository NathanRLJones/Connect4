
public interface GameListener {

    public void tokenPlaced(int column, Token token);

    public void tokenRemoved(int column);

    public void newTurn(Player player);

    public void gameStarted();

    public void gameWon(Player player, 
                        int row1, int col1, int row2, int col2);

    public void gameDrawn();

}
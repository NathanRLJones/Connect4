import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ConnectFour {
	
	public static final int TOKENS_TO_WIN = 4;
	
	private GameListener listener;
	private Board board;
	private Player currentPlayer;
	private List<Player> players;
	private Stack<Move> moveHistory;
	private Stack<Move> undoneMoves;
	private Move suggestedMove;


	/**
	 * Main method for the ConnectFour
	 * @param args are ignored
	 */
	public static void main(String[] args) {
		ConnectFour game = new ConnectFour();
	}

	/**
	 * Connect4 constructor
	 */
	public ConnectFour() {
		listener = new Gui(this);

		ArrayList<Player> players;
		players = new ArrayList<Player>();
		players.add(new HumanPlayer("h", Color.RED));
		players.add(new HumanPlayer("i", Color.GREEN));
		players.add(new AIPlayer("a", Color.YELLOW, 1));
		//players.add(new AIPlayer("p2", Color.BLUE, 2));
		//players.add(new AIPlayer("a2", Color.GREEN, 2));
		newGame(7, 6, players);
	}

	/**
	 * Create new game with board size and players defined
	 */
	public void newGame(int rows, int cols, ArrayList<Player> players) {
		board = new Board(rows, cols);
		this.players = players;
		moveHistory = new Stack<Move>();
		undoneMoves = new Stack<Move>();
		currentPlayer = players.get(0);
		listener.gameStarted();
		listener.newTurn(currentPlayer);

	}

	public BoardInterface getBoard() {
		return board;
	}

	public void setInputSuggestion(int column) {
		suggestedMove = new Move(column, new Token(currentPlayer));
	}



	public void queryPlayers() {
		Move move;
		if (currentPlayer.isInteractive()) {
			move = suggestedMove;
		} else {
			move = currentPlayer.getMove(getBoard(), players);
		}
		System.out.println("Adding a token: " + currentPlayer.getName());
		board.placeToken(move.getColumn(), move.getToken());
		moveHistory.add(move);
		undoneMoves.clear();
		listener.tokenPlaced(move.getColumn(), move.getToken());
	}

	public void advance() {
		int index;
		if (!isGameOver()) {
			// Move to a new player
			index = (players.indexOf(currentPlayer) + 1) % players.size();
			currentPlayer = players.get(index);
			listener.newTurn(currentPlayer);
		} 
	}


	/**
	 * Method to check if the game is over
	 * @return true if there are TOKENS_TO_WIN connected same-colour tokens
	 *			 or if the game is drawn
	 *         false otherwise
	 */
	public boolean isGameOver() {
	
		//find the column and row of the last move made
		Player lastPlayer = moveHistory.peek().getToken().getOwner();
		int height = board.getHeight();
		int width = board.getWidth();
		int col = moveHistory.peek().getColumn();
		int row;
		for(row = height - 1; row >= 0; row--){
			if(board.isSpaceTaken(col, row)){
				break;
			}
		}
		
		//create counters that will store the number of valid tokens in a row in the given direction
		//No need to check directly above the token, since it was the last token placed.
		int NECount = checkValidTokenSeries(lastPlayer, col, row, 1, 1);
		int ECount  = checkValidTokenSeries(lastPlayer, col, row, 1, 0);


		int SECount = checkValidTokenSeries(lastPlayer, col, row, 1, -1);
		int SCount  = checkValidTokenSeries(lastPlayer, col, row, 0, -1);
		int SWCount = checkValidTokenSeries(lastPlayer, col, row, -1, -1);
		int WCount  = checkValidTokenSeries(lastPlayer, col, row, -1, 0);
		int NWCount = checkValidTokenSeries(lastPlayer, col, row, -1, 1);

		//> is used instead of >= because the last placed token will be counted twice
		if (NECount + SWCount > TOKENS_TO_WIN) {
			listener.gameWon(lastPlayer, 
							 col + NECount - 1,	 // col1
							 row + NECount - 1,  // row1
							 col - SWCount + 1,  // col2
							 row - SWCount + 1); // row2
			return true;
		} else if (ECount + WCount > TOKENS_TO_WIN) {
			listener.gameWon(lastPlayer, 
							 col + ECount - 1,   // col1
							 row, 				 // row1
							 col - WCount + 1,   // col2
							 row);				 // row2

			return true;
		} else if (NWCount + SECount > TOKENS_TO_WIN) {
			listener.gameWon(lastPlayer, 
							 col + SECount - 1,	 // col1
							 row - SECount + 1,  // row1
							 col - NWCount + 1,  // col2
							 row + NWCount - 1); // row2

			return true;
		} else if(SCount >= TOKENS_TO_WIN) {
			listener.gameWon(lastPlayer, 
							 col,                // col1
							 row,				 // row1
							 col,				 // col2
							 row - SCount + 1);  // row2
			return true;
		}

		// Check for a tie
		for (col = 0; col < width; col++) {
			if (!board.isColumnFull(col))
				return false;
		}
		listener.gameDrawn();

		return true;
	}
	
	/**
	 * Finds the number of adjacent tokens owned by the given player, where the
	 * next checked token is at column: current column + dCol and row: current row + dRow.
	 * 
	 * @param player The Player whose tokens will be counted as valid.
	 * @param column The starting column.
	 * @param row The starting row.
	 * @param dCol The value to change the current column by to get the next space.
	 * @param dRow The value to change the current row by to get the next space.
	 * @return The number of adjacent player's tokens in the given direction.
	 */
	private int checkValidTokenSeries(Player player, int column, int row, int dCol, int dRow){
		int tokensInSeries = 0;
		while(board.isValidSpace(column, row) && board.isSpaceTaken(column, row) &&
				board.getToken(column, row).getOwner() == player){
			tokensInSeries++;
			column += dCol;
			row += dRow;
		}
		return tokensInSeries;
	}
	
	/**
	 * Method to get the current player
	 * @return the player for the current turn
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Method to undo a move
	 */
	public void undo() {
		if (!moveHistory.isEmpty() && !isGameOver()) {
			Move lastMove = moveHistory.pop();
			Token prevToken = lastMove.getToken();
			Player prevPlayer = prevToken.getOwner();
			board.removeToken(lastMove.getColumn());
			undoneMoves.add(lastMove);
			listener.tokenRemoved(lastMove.getColumn(), prevToken);
			int index = (players.indexOf(currentPlayer) + players.size() - 1) % players.size();
			currentPlayer = players.get(index);
			if(!prevPlayer.isInteractive()) {
				undo();
			} else {
				listener.newTurn(currentPlayer);
			}
		}
	}

	/**
	 * Method to redo a move
	 */
	public void redo() {
		 if (!undoneMoves.isEmpty() && !isGameOver()) {
		 	Move lastUndoneMove = undoneMoves.pop();
		 	board.placeToken(lastUndoneMove.getColumn(), 
		 					 lastUndoneMove.getToken());
		 	moveHistory.add(lastUndoneMove);
			 Token undoneToken = lastUndoneMove.getToken();
			 Player undonePlayer = undoneToken.getOwner();
			 board.placeToken(lastUndoneMove.getColumn(), undoneToken);
			 undoneMoves.add(lastUndoneMove);
			 listener.tokenPlaced(lastUndoneMove.getColumn(), undoneToken);
			 int index = (players.indexOf(currentPlayer) + players.size()) % players.size();
			 currentPlayer = players.get(index);
			 if(!undonePlayer.isInteractive()) {
				 redo();
			 } else {
				 listener.newTurn(currentPlayer);
			 }
		 }
	}
	
}

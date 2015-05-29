/**
 * Implements ConnectFour class
 * 
 * @author      Nathan Jones z5021296
 *              Minjee Son z3330687
 *              Alen Bou-Haidar z5019028
 *              Lawrence z5018371
 *
 * @version     0.1
 * @since       2015-05-29
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The Class ConnectFour.
 */
public class ConnectFour {
		
	/** The game listener (Gui in this case). */
	private GameListener listener;
	
	/** The board. */
	private Board board;
	
	/** The current player. */
	private Player currentPlayer;
	
	/** The list of players. */
	private List<Player> players;
	
	/** The move history. */
	private Stack<Move> moveHistory;
	
	/** The undone moves. */
	private Stack<Move> undoneMoves;
	
	/** The suggested move (for a human player). */
	private Move suggestedMove;
	
	/** The number of tokens to win. */
	private int tokensToWin;

	/**
	 * Main method for the ConnectFour.
	 *
	 * @param args are ignored
	 */
	public static void main(String[] args) {
		ConnectFour game = new ConnectFour();
	}

	/**
	 * Connect4 constructor.
	 */
	public ConnectFour() {
		listener = new Gui(this);
	}

	/**
	 * Start a new game with the given parameters.
	 * @param toWin Number of tokens in a row required to win.
	 * @param rows Number of rows on the board.
	 * @param cols Number of columns on the board.
	 * @param players List of players in the game.
	 */
	public void newGame(int toWin, int rows, int cols, List<Player> players) {
		tokensToWin = toWin;
		board = new Board(rows, cols);
		this.players = players;
		moveHistory = new Stack<Move>();
		undoneMoves = new Stack<Move>();
		currentPlayer = players.get(0);
		listener.gameStarted();
		listener.newTurn(currentPlayer);
	}
	
	/**
	 * Get the number of tokens in a row required to win in the current game.
	 * @return Number of tokens in a row required to win in the current game.
	 */
	public int getTokensToWin(){
		return tokensToWin;
	}

	/**
	 * Get the move history of the current game.
	 * @return Move history of the current game.
	 */
	public Stack<Move> getMoveHistory() {
		return moveHistory;
	}
	
	/**
	 * Get the undone moves of the current game.
	 * @return Undone moves of the current game.
	 */
	public Stack<Move> getUndoneMoves() {
		return undoneMoves;
	}
	
	/**
	 * Get the board of the current game.
	 * @return Board of the current game.
	 */
	public BoardInterface getBoard() {
		return board;
	}

	/**
	 * Suggest a move from user input.
	 * @param column Column of suggested move.
	 */
	public void setInputSuggestion(int column) {
		suggestedMove = new Move(column, new Token(currentPlayer));
	}
	
	/**
	 * Get a hint and get the gui to display it.
	 *
	 * @return the hint
	 */
	public void getHint() {		
		listener.tokenHinted(MoveGenie.getMove(getBoard(), 3, players, 
							currentPlayer, tokensToWin).getColumn(), 
							new Token(currentPlayer));
	}

	/**
	 * Get a move from the current player and execute it.
	 */
	public void queryPlayers() {
		Move move;
		if (currentPlayer.isInteractive()) {
			move = suggestedMove;
		} else {
			move = currentPlayer.getMove(getBoard(), players, tokensToWin);
		}
		System.out.println("Adding a token: " + currentPlayer.getName());
		board.placeToken(move.getColumn(), move.getToken());
		moveHistory.add(move);
		undoneMoves.clear();
		listener.tokenPlaced(move.getColumn(), move.getToken());
	}

	/**
	 * Move the game forward to the next turn.
	 */
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
	 * Method to check if the game is over.
	 *
	 * @return true if there are tokensToWin connected same-colour tokens
	 * 			 or if the game is drawn
	 *         false otherwise
	 */
	public boolean isGameOver() {
	
		if(moveHistory == null || moveHistory.isEmpty()) return false;
		
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
		//if the game was won, tell the gui where the winning tokens are.
		if (NECount + SWCount > tokensToWin) {
			listener.gameWon(lastPlayer, 
							 col + NECount - 1,	 // col1
							 row + NECount - 1,  // row1
							 col - SWCount + 1,  // col2
							 row - SWCount + 1,  // row2
							 tokensToWin);
			return true;
		} else if (ECount + WCount > tokensToWin) {
			listener.gameWon(lastPlayer, 
							 col + ECount - 1,   // col1
							 row, 				 // row1
							 col - WCount + 1,   // col2
							 row,				 // row2
							 tokensToWin);

			return true;
		} else if (NWCount + SECount > tokensToWin) {
			listener.gameWon(lastPlayer, 
							 col + SECount - 1,	 // col1
							 row - SECount + 1,  // row1
							 col - NWCount + 1,  // col2
							 row + NWCount - 1,  // row2
							 tokensToWin);

			return true;
		} else if(SCount >= tokensToWin) {
			listener.gameWon(lastPlayer, 
							 col,                // col1
							 row,				 // row1
							 col,				 // col2
							 row - SCount + 1,    // row2
							 tokensToWin);
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
	 * Method to get the current player.
	 * @return the player for the current turn.
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Method to undo a move.
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
	 * Method to redo a move.
	 */
	public void redo() {
		if (!undoneMoves.isEmpty() && !isGameOver()) {
			Move lastUndoneMove = undoneMoves.pop();
		 	board.placeToken(lastUndoneMove.getColumn(), 
		 					 lastUndoneMove.getToken());
		 	moveHistory.add(lastUndoneMove);
		 	Token undoneToken = lastUndoneMove.getToken();
			undoneMoves.add(lastUndoneMove);
			listener.tokenPlaced(lastUndoneMove.getColumn(), undoneToken);
		}
	}
	
}

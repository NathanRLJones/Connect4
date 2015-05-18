import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.Color;
import javax.swing.SwingUtilities;


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
		players.add(new HumanPlayer("p1", Color.RED));
		players.add(new RandomAI("a1", Color.YELLOW));
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

	public void advance() {
		int index;
		Move move;

		if (currentPlayer.isInteractive()) {
			move = suggestedMove;
		} else {
			move = currentPlayer.getMove(getBoard());
		}

		System.out.println("Adding a token: " + currentPlayer.getName());
		board.placeToken(move.getColumn(), move.getToken());
		moveHistory.add(move);
		undoneMoves.clear();

		listener.tokenPlaced(move.getColumn(), move.getToken());
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
	 *         false otherwise
	 */
	public boolean isGameOver() {
	
		//find the column and row of the last move made
		Player lastPlayer = moveHistory.peek().getToken().getOwner();
		int height = board.getHeight();
		int column = moveHistory.peek().getColumn();
		int row;
		for(row = height - 1; row >= 0; row--){
			if(board.isSpaceTaken(column, row)){
				break;
			}
		}

		int dRow = 0;
		int dCol = 0;
		
		//create counters that will store the number of valid tokens in a row in the given direction
		//No need to check directly above the token, since it was the last token placed.
		int NECount = checkValidTokenSeries(lastPlayer, column, row, 1, 1);
		int ECount = checkValidTokenSeries(lastPlayer, column, row, 1, 0);
		int SECount = checkValidTokenSeries(lastPlayer, column, row, 1, -1);
		int SCount = checkValidTokenSeries(lastPlayer, column, row, 0, -1);
		int SWCount = checkValidTokenSeries(lastPlayer, column, row, -1, -1);
		int WCount = checkValidTokenSeries(lastPlayer, column, row, -1, 0);
		int NWCount = checkValidTokenSeries(lastPlayer, column, row, -1, 1);
		
		//> is used instead of >= because the last placed token will be counted twice
		if(NECount + SECount > TOKENS_TO_WIN) return true;
		if(ECount + WCount > TOKENS_TO_WIN) return true;
		
		if(SCount >= TOKENS_TO_WIN) return true;
		
		return false;
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
		// if (!moveHistory.isEmpty()) {
		// 	Move lastMove = moveHistory.pop();
		// 	board.removeToken(lastMove.getColumn());
		// 	undoneMoves.add(lastMove);
		// }
	}

	/**
	 * Method to redo a move
	 */
	public void redo() {
		// if (!undoneMoves.isEmpty()) {
		// 	Move lastUndoneMove = undoneMoves.pop();
		// 	board.placeToken(lastUndoneMove.getColumn(), 
		// 					 lastUndoneMove.getToken());
		// 	moveHistory.add(lastUndoneMove);
		// }
	}
	
}

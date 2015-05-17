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
		players.add(new Human("p1", Color.RED));
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
		Token currToken;
		int numOfTokens;
		int height = board.getHeight();
		int width = board.getWidth();
		
		// Check for a vertical line of TOKENS_TO_WIN same-colour tokens
		for (int col = 0; col < width; col++) {
			numOfTokens = 0;
			for (int row = 0; row < height; row++) {
				if (height-row+numOfTokens < TOKENS_TO_WIN) break;
				currToken = board.getToken(col, row);
				if (currToken == null) break;
				if (currToken.getOwner() == currentPlayer) {
					numOfTokens++;
					if (numOfTokens == TOKENS_TO_WIN) {
						listener.gameWon(currentPlayer, 
								    col, row-TOKENS_TO_WIN+1, col, row);
						return true;
					}
				} else {
					numOfTokens = 0;
				}
			}
		}

		//Check for a horizontal line of TOKENS_TO_WIN same-colour tokens
		for (int row = 0; row < height; row++) {
			numOfTokens = 0;
			for (int col = 0; col < width; col++) {
				if ( width-col+numOfTokens < TOKENS_TO_WIN) break;
				currToken = board.getToken(col, row);
				if (currToken == null) {
					numOfTokens = 0;
					continue;
				}
				if (currToken.getOwner() == currentPlayer) {
					numOfTokens++;
					if (numOfTokens == TOKENS_TO_WIN) {
						listener.gameWon(currentPlayer, 
								    col-TOKENS_TO_WIN+1, row, col, row);
						return true;
					}
				} else {
					numOfTokens = 0;
				}
			}
		}
		
		//Check for a diagonal line of TOKENS_TO_WIN same-colour tokens
		for (int col = 0; col < width - TOKENS_TO_WIN+1; col++) {
			for (int row = 0; row < height; row++) {
				// Check diagonally upwards
				numOfTokens = 0;
				if (row < height - TOKENS_TO_WIN+1) {
					for (int offset = 0; offset < TOKENS_TO_WIN; offset++) {
						currToken = board.getToken(col+offset, row+offset);
						if (currToken == null) break;
						if (currToken.getOwner() == currentPlayer) {
							numOfTokens++;
							if (numOfTokens == TOKENS_TO_WIN) {
								listener.gameWon(currentPlayer, 
									  col, row, col+offset, row+offset);
								return true;
							}
						} else {
							break;
						}
					}
				}
				// Check diagonally downwards
				numOfTokens = 0;
				if (row > TOKENS_TO_WIN-2) {
					for (int offset = 0; offset < TOKENS_TO_WIN; offset++) {
						currToken = board.getToken(col+offset, row-offset);
						if (currToken == null) break;
						if (currToken.getOwner() == currentPlayer) {
							numOfTokens++;
							if (numOfTokens == TOKENS_TO_WIN) {
								listener.gameWon(currentPlayer, 
									  col, row, col+offset, row-offset);
								return true;
							}
						} else {
							break;
						}
					}
				}
			}
		}
		
		// Check for a tie
		for (int col = 0; col < width; col++) {
			if (!board.isColumnFull(col))
				return false;
		}
		listener.gameDrawn();

		return true;
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

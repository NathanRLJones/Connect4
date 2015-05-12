import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ConnectFour {
	
	private Board board;
	private int turnNumber;
	private List<Player> players;
	private Stack<Move> moveHistory;
	private Stack<Move> undoneMoves;
	private int winTokens = 4;

	/**
	 * Default constructor for creating empty 6x7 board and new players
	 */
	public ConnectFour() {
		board = new Board(6, 7);
		turnNumber = 0;
		players = new ArrayList<Player>();
		moveHistory = new Stack<Move>();
		undoneMoves = new Stack<Move>();
	}
	
	/**
	 * Constructor with board size and players defined
	 */
	public ConnectFour(int cols, int rows, ArrayList<Player> gamePlayers) {
		board = new Board(rows, cols);
		turnNumber = 0;
		players = gamePlayers;
		moveHistory = new Stack<Move>();
		undoneMoves = new Stack<Move>();
	}
	
	public BoardInterface getBoard(){
		return board;
	}

	/**
	 * Method to check if the move is legal
	 * @param move the player's move
	 * @return true if the board is not full and specified player's turn
	 * 		   false otherwise
	 */
	public boolean isLegal(Move move) {
		if(move == null)return false;
		boolean legal = true;
		if(board.isColumnFull(move.getColumn())) legal = false;
		if(whoseTurnIsIt() != move.getToken().getOwner()) legal = false;
		return legal;
	}

	/**
	 * Method to place a token on the board
	 * @param move the player's move
	 */
	public void doMove(Move move) {
		System.out.println("Adding a token: " + move.getToken().getOwner().getName());
		
		turnNumber++;
		Token token = move.getToken();
		int column = move.getColumn();
		
		board.placeToken(column, token);
		moveHistory.add(move);
		undoneMoves.clear();
	}

	/**
	 * Method to check if the game is over
	 * @return true if there are winTokens connected same-colour tokens
	 *         false otherwise
	 */
	public boolean isGameOver() {
		int height = board.getRows();
		int width = board.getColumns();
		Player possibleWinner = null;
		int noOfTokens = 0;
		Player currPlayer;
		Token currToken;
		
		// Check for a vertical line of winTokens same-colour tokens
		for(int col = 0; col < width; col++){
			noOfTokens = 0;
			possibleWinner = null;
			for(int row = 0; row < height; row++){
				if(height-row+noOfTokens < winTokens) break;
				
				currToken = board.getToken(col, row);
				if(currToken == null) break;
				currPlayer = currToken.getOwner();
				if(currPlayer == possibleWinner){
					noOfTokens++;
					if(noOfTokens == winTokens) {
						return true;
					}
				}else{
					possibleWinner = currPlayer;
					noOfTokens = 1;
				}
			}
		}
		
		noOfTokens = 0;
		possibleWinner = null;
		
		//Check for a horizontal line of winTokens same-colour tokens
			for(int row = 0; row < height; row++){
				noOfTokens = 0;
				possibleWinner = null;
				for(int col = 0; col < width; col++){
					if(width-col+noOfTokens < winTokens) break;
					
					currToken = board.getToken(col, row);
					if (currToken == null){
						possibleWinner = null;
						noOfTokens = 0;
						continue;
					}
					currPlayer = currToken.getOwner();
					if(currPlayer == possibleWinner){
						noOfTokens++;
						if(noOfTokens == winTokens) {
							return true;
						}
					}else{
						possibleWinner = currPlayer;
						noOfTokens = 1;
					}
				}
			}
		
		//Check for a diagonal line of winTokens same-colour tokens
		for(int col = 0; col < width - winTokens+1; col++) {
			for(int row = 0; row < height; row++) {
				// Check diagonally upwards
				possibleWinner = null;
				noOfTokens = 0;
				if(row < height - winTokens+1) {
					for(int offset = 0; offset < winTokens; offset++) {
						currToken = board.getToken(col+offset, row+offset);
						if(currToken == null) break;
						currPlayer = currToken.getOwner();
						if(offset == 0) possibleWinner = currPlayer;
						if(currPlayer == possibleWinner){
							noOfTokens++;
							if(noOfTokens == winTokens) {
								return true;
							}
						}else{
							break;
						}
					}
				}
				// Check diagonally downwards
				possibleWinner = null;
				noOfTokens = 0;
				if(row > winTokens-2) {
					for(int offset = 0; offset < winTokens; offset++) {
						currToken = board.getToken(col+offset, row-offset);
						if(currToken == null) break;
						currPlayer = currToken.getOwner();
						if(offset == 0) possibleWinner = currPlayer;
						if(currPlayer == possibleWinner){
							noOfTokens++;
							if(noOfTokens == winTokens) {
								return true;
							}
						}else{
							break;
						}
					}
				}
			}
		}
		
		// Check for a tie
		for(int col = 0; col < width; col++){
			if(!board.isColumnFull(col))
				return false;
		}
		return true;
	}

	/**
	 * Method to check who has to make a move
	 * @return the player based on how many players and turn number
	 */
	public Player whoseTurnIsIt() {
		if(players.size() > 0){
			return players.get(turnNumber % players.size());
		}else{
			return null;
		}
	}

	/**
	 * Method to undo a move
	 */
	public void undo() {
		if(!moveHistory.isEmpty()) {
			turnNumber--;
			Move lastMove = moveHistory.pop();
			board.removeToken(lastMove.getColumn());
			undoneMoves.add(lastMove);
		}
	}

	/**
	 * Method to redo a move
	 */
	public void redo() {
		if(!undoneMoves.isEmpty()) {
			Move lastUndoneMove = undoneMoves.pop();
			board.placeToken(lastUndoneMove.getColumn(), lastUndoneMove.getToken());
			moveHistory.add(lastUndoneMove);
		}
	}

	/**
	 * Method to get the image of the board from a file
	 * @return the image at specified location
	 */
	public Board getBoardImage() {
		// TODO stub
		return null;
	}
	
}

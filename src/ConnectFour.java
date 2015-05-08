import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ConnectFour {
	
	private Board board;
	private int turnNumber;
	private List<Player> players;
	private Stack<Move> moveHistory;
	private Stack<Move> undoneMoves;
	
	public ConnectFour() {
		board = new Board(6, 7);
		turnNumber = 0;
		players = new ArrayList<Player>();
		moveHistory = new Stack<Move>();
		undoneMoves = new Stack<Move>();
	}
	
	public boolean isLegal(Move move) {
		boolean legal = true;
		if(board.isColumnFull(move.getColumn())) legal = false;
		if(whoseTurnIsIt() != move.getToken().getOwner()) legal = false;
		return legal;
	}
	
	public void doMove(Move move) {
		Token token = move.getToken();
		int column = move.getColumn();
		
		board.placeToken(column, token);
		moveHistory.add(move);
		undoneMoves.clear();
	}
	
	public boolean isGameOver() {
		int height = board.getHeight();
		int width = board.getWidth();
		Player possibleWinner = null;
		int noOfTokens = 0;
		Player currPlayer;
		Token currToken;
		
		// Check for a vertical line of 4 same-colour tokens
		columnloop:
			for(int col = 0; col < width; col++){
				rowloop:
					for(int row = 0; row < height; row++){
						if(height-row+noOfTokens < 4) continue columnloop;
						
						currToken = board.getToken(col, row);
						if(currToken == null) break;
						currPlayer = currToken.getOwner();
						if(currPlayer == possibleWinner){
							noOfTokens++;
							if(noOfTokens == 4) return true;
						}else{
							possibleWinner = currPlayer;
							noOfTokens = 1;
						}
					}
			}
		
		noOfTokens = 0;
		possibleWinner = null;
		
		//Check for a horizontal line of 4 same-colour tokens
		rowloop:
			for(int row = 0; row < height; row++){
				columnloop:
					for(int col = 0; col < width; col++){
						if(width-col+noOfTokens < 4) continue rowloop;
						
						currToken = board.getToken(col, row);
						if (currToken == null){
							possibleWinner = null;
							noOfTokens = 0;
							continue columnloop;
						}
						currPlayer = currToken.getOwner();
						if(currPlayer == possibleWinner){
							noOfTokens++;
							if(noOfTokens == 4) return true;
						}else{
							possibleWinner = currPlayer;
							noOfTokens = 1;
						}
					}
			}
		
		//Check for a horizontal line of 4 same-colour tokens
		for(int col = 0; col < width-3; col++) {
			for(int row = 0; row < height; row++) {
				// Check diagonally downwards
				possibleWinner = null;
				noOfTokens = 0;
				if(row > 2) {
					for(int offset = 0; offset < 4; offset++) {
						currToken = board.getToken(col+offset, row-offset);
						if(currToken == null) break;
						currPlayer = currToken.getOwner();
						if(offset == 0) possibleWinner = currPlayer;
						if(currPlayer == possibleWinner){
							noOfTokens++;
							if(noOfTokens == 4) return true;
						}else{
							break;
						}
					}
				}
				// Check diagonally upwards
				possibleWinner = null;
				noOfTokens = 0;
				if(row < height-3) {
					for(int offset = 0; offset < 4; offset++) {
						currToken = board.getToken(col+offset, row+offset);
						if(currToken == null) break;
						currPlayer = currToken.getOwner();
						if(offset == 0) possibleWinner = currPlayer;
						if(currPlayer == possibleWinner){
							noOfTokens++;
							if(noOfTokens == 4) return true;
						}else{
							break;
						}
					}
				}
			}
		}

		return false;
	}
	
	public Player whoseTurnIsIt() {
		return players.get(turnNumber % players.size());
	}
	
	public void undo() {
		if(!moveHistory.isEmpty()) {
			Move lastMove = moveHistory.pop();
			board.removeToken(lastMove.getColumn());
			undoneMoves.add(lastMove);
		}
	}
	
	public void redo() {
		if(!undoneMoves.isEmpty()) {
			Move lastUndoneMove = undoneMoves.pop();
			board.placeToken(lastUndoneMove.getColumn(), lastUndoneMove.getToken());
			moveHistory.add(lastUndoneMove);
		}
	}
	
	public Board getBoardImage() {
		// TODO stub
		return null;
	}
	
}

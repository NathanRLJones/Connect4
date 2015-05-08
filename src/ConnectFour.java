import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConnectFour {
	
	private Board board;
	private int turnNumber;
	private List<Player> players;
	private List<Move> moveHistory;
	private List<Move> undoneMoves;
	
	public ConnectFour() {
		board = new Board(6, 7);
		turnNumber = 0;
		players = new ArrayList<Player>();
		moveHistory = new LinkedList<Move>();
		undoneMoves = new LinkedList<Move>();
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
		
		noOfTokens = 0;
		possibleWinner = null;
		
		// TODO check for diagonal lines

		return false;
	}
	
	public Player whoseTurnIsIt() {
		// TODO stub
		return null;
	}
	
	public void undo() {
		// TODO stub
	}
	
	public void redo() {
		// TODO stub
	}
	
	public Board getBoardImage() {
		// TODO stub
		return null;
	}
	
}

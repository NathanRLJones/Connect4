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
			for(int i = 0; i < width; i++){
				rowloop:
					for(int j = 0; j < height; j++){
						if(noOfTokens == 4) return true;
						if(height-j+noOfTokens < 4) continue columnloop;
						
						currToken = board.getToken(i, j);
						if(currToken == null) continue rowloop;
						currPlayer = currToken.getOwner();
						if(currPlayer == possibleWinner){
							noOfTokens++;
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
			for(int j = 0; j < height; j++){
				columnloop:
					for(int i = 0; i < width; i++){
						if(noOfTokens == 4) return true;
						if(width-i+noOfTokens < 4) continue rowloop;
						
						currToken = board.getToken(i, j);
						if (currToken == null){
							possibleWinner = null;
							noOfTokens = 0;
							continue columnloop;
						}
						currPlayer = currToken.getOwner();
						if(currPlayer == possibleWinner){
							noOfTokens++;
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

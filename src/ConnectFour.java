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
		// TODO stub
	}
	
	public boolean isGameOver() {
		int height = board.getHeight();
		int width = board.getWidth();
		
		for(int i = 0; i < height; i++){
			
		}
		return true;
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

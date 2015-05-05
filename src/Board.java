import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private List<ArrayList<Token>> columns;
	private int height;
	private int width;
	
	public Board(int height, int width) {
		columns = new ArrayList<ArrayList<Token>>();
		this.height = height;
		this.width = width;
	}
	
	public Token getToken(int column, int row) {
		// TODO stub
		return null;
	}
	
	public void placeToken(int column, Token token) {
		// TODO stub
	}
	
	public boolean isColumnFull(int column) {
		// TODO stub
		return true;
	}
	
	public void removeToken(int column) {
		// TODO stub
	}
	
}

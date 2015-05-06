import java.util.ArrayList;

public class Board {
	
	private ArrayList<ArrayList<Token>> columns;
	private int height;
	private int width;
	
	public Board(int height, int width) {
		columns = new ArrayList<ArrayList<Token>>();
		this.height = height;
		this.width = width;
	}
	
	//assume column < width
	public Token getToken(int column, int row) {
		ArrayList<Token> rows = columns.get(column);
		if(rows.size() > row) return rows.get(row);
		return null;
	}
	
	public void placeToken(int column, Token token) {
		ArrayList<Token> level = columns.get(column);
		if(!isColumnFull(column)){
			level.add(token);
		}
	}
	
	public boolean isColumnFull(int column) {
		ArrayList<Token> level = columns.get(column);
		return (level.size()==height);
	}
	
	public void removeToken(int column) {
		ArrayList<Token> levels = columns.get(column);
		if(!levels.isEmpty()) levels.remove(levels.size()-1);
	}
	
}

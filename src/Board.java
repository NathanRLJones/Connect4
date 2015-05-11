import java.util.ArrayList;

public class Board implements BoardInterface{
	
	private ArrayList<ArrayList<Token>> columns;
	private int height;
	private int width;
	
	public Board(int height, int width) {
		columns = new ArrayList<ArrayList<Token>>();
		this.height = height;
		this.width = width;
		
		for(int i = 0; i < width; i++){
			ArrayList<Token> column = new ArrayList<Token>();
			columns.add(column);
		}
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
	
	@Override
	public int getHeight(){
		return height;
	}
	
	@Override
	public int getWidth(){
		return width;
	}

	@Override
	public boolean isSpaceTaken(int column, int row) {
		return (columns.get(column).get(row) != null);
	}

	@Override
	public boolean isPlayersToken(Player player, int column, int row){
		if(isSpaceTaken(column, row)){
			return columns.get(column).get(row).getOwner() == player;
		}
		return false;
	}
}

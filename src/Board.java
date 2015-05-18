import java.util.ArrayList;

public class Board implements BoardInterface{
	
	private ArrayList<ArrayList<Token>> columns;
	private int height;
	private int width;

	/**
	 * Default constructor to create an empty board
	 * @param height the height of the board
	 * @param width the width of the board
	 */
	public Board(int width, int height) {
		columns = new ArrayList<ArrayList<Token>>();
		this.height = height;
		this.width = width;
		
		for(int i = 0; i < width; i++){
			ArrayList<Token> column = new ArrayList<Token>();
			columns.add(column);
		}
	}

	/**
	 * Method to get the token located in the given position
	 * @param column the column position on the board
	 * @param row the row position on the board
	 * @return the token in the given row and column. 
	 * 		   null if the given position is empty or outside the board size limit
	 */
	//assume column < width
	public Token getToken(int column, int row) {
		ArrayList<Token> rows = columns.get(column);
		if(rows.size() > row) return rows.get(row);
		return null;
	}

	/**
	 * Method to place token on the board if column is not full
	 * @param column the column position on the board
	 * @param token the player token to place on the board
	 */
	public void placeToken(int column, Token token) {
		ArrayList<Token> level = columns.get(column);
		if(!isColumnFull(column)){
			level.add(token);
		}
	}
	
	/**
	 * Method to remove token from the board
	 * @param column the column position on the board
	 */
	public void removeToken(int column) {
		ArrayList<Token> levels = columns.get(column);
		if(!levels.isEmpty()) levels.remove(levels.size()-1);
	}

	/**
	 * Method to check if the column is full
	 * @param column the column position on the board
	 * @return true if the level of the stacked token is
	 * 		   is equal to the height of the board
	 * 		   false otherwise
	 */
	public boolean isColumnFull(int column) {
		return (getColumnLevel(column) == height);
	}
	
	public int getColumnLevel(int column) {
		return (isValidColumn(column) ? columns.get(column).size() : 0);
	}

	public boolean isValidColumn(int column) {
		return (column >= 0 && column < width);
	}
	
	public boolean isValidSpace(int column, int row){
		return (isValidColumn(column) && row >= 0 && row < columns.get(column).size());
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
		return (row < columns.get(column).size());
	}

	@Override
	public boolean isPlayersToken(Player player, int column, int row){
		Player owner = null;
		if(row >= columns.get(column).size()) return false;
		if(isSpaceTaken(column, row)){
			return columns.get(column).get(row).getOwner() == player;
		}
		return false;
	}

	@Override
	public Player whoOwnsToken(int column, int row) {
		Token token = getToken(column, row);
		if(token == null) return null;
		return token.getOwner();
	}


}

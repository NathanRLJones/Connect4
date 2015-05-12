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
	public Board(int height, int width) {
		columns = new ArrayList<ArrayList<Token>>();
		this.height = height;
		this.width = width;
		
		for(int i = 0; i < width; i++){
			ArrayList<Token> column = new ArrayList<Token>();
			columns.add(column);
		}
	}

	/**
	 * Method to get token's position
	 * @param column the column position on the board
	 * @param row the row position on the board
	 * @return row position of the token if the size of the row
	 * 		   assigned from the token is within the range of the
	 * 		   board.
	 * 		   null otherwise
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
	 * Method to check if the column is full
	 * @param column the column position on the board
	 * @return true if the level of the stacked token is
	 * 		   is equal to the height of the board
	 * 		   false otherwise
	 */
	public boolean isColumnFull(int column) {
		ArrayList<Token> level = columns.get(column);
		return (level.size()==height);
	}

	/**
	 * Method to remove token from the board
	 * @param column the column position on the board
	 */
	public void removeToken(int column) {
		ArrayList<Token> levels = columns.get(column);
		if(!levels.isEmpty()) levels.remove(levels.size()-1);
	}
	
	@Override
	public int getRows(){
		return height;
	}
	
	@Override
	public int getColumns(){
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

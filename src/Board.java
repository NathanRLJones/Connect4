/**
 * Implements Board class
 * 
 * @author      Nathan Jones z5021296
 *              Minjee Son z3330687
 *              Lawrence z5018371
 *              Nicholas Yuwono z5016198
 *              Alen Bou-Haidar z5019028
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.util.ArrayList;

/**
 * The Class Board.
 * Creates empty board on the screen
 */
public class Board implements BoardInterface{
	
	/** The list of columns. */
	private ArrayList<ArrayList<Token>> columns;

	/** The height of the board. */
	private int height;
	
	/** The width of the board. */
	private int width;

	/**
	 * Default constructor to create an empty board.
	 *
	 * @param width the width of the board
	 * @param height the height of the board
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
	 * Method to get the token located in the given position.
	 *
	 * @param column the column position on the board
	 * @param row the row position on the board
	 * @return the token in the given row and column.
	 * 		   null if the given position is empty or outside the board size limit
	 */
	public Token getToken(int column, int row) {
		ArrayList<Token> rows = columns.get(column);
		if(rows.size() > row) return rows.get(row);
		return null;
	}

	/**
	 * Method to place token on the board if column is not full.
	 *
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
	 * Method to remove token from the board.
	 *
	 * @param column the column position on the board
	 */
	public void removeToken(int column) {
		ArrayList<Token> level = columns.get(column);
		if(!level.isEmpty()) level.remove(level.size()-1);
	}

	/**
	 * Method to check if the column is full.
	 *
	 * @param column the column position on the board
	 * @return true if the level of the stacked token is
	 * 		   is equal to the height of the board
	 * 		   false otherwise
	 */
	public boolean isColumnFull(int column) {
		return (getColumnLevel(column) == height);
	}
	
	/**
	 * Method to return the column level.
	 *
	 * @param column integer position on the board
	 * @return integer height of the column
	 */
	public int getColumnLevel(int column) {
		return (isValidColumn(column) ? columns.get(column).size() : 0);
	}
	
	/**
	 * Method to check if the column is valid.
	 *
	 * @param column the column position on the board
	 * @return true if the column number given is valid for the board and false otherwise
	 */
	public boolean isValidColumn(int column) {
		return (column >= 0 && column < width);
	}
	
	/**
	 * Method to check if the space at the given column and row is valid in the board.
	 *
	 * @param column integer position on the board
	 * @param row integer position on the board
	 * @return true if the space is valid and false otherwise
	 */
	public boolean isValidSpace(int column, int row){
		return (isValidColumn(column) && 
				row >= 0 && row < columns.get(column).size());
	}
	
	/**
	 * Method to return the height of the board.
	 *
	 * @return integer height of the board
	 */
	@Override
	public int getHeight(){
		return height;
	}
	
	/**
	 * Method to return the width of the board.
	 *
	 * @return integer width of the board
	 */
	@Override
	public int getWidth(){
		return width;
	}
	
	/**
	 * Method to check if the space at given column and row is already occupied.
	 *
	 * @param column the column
	 * @param row the row
	 * @return true if the space is occupied and false if free
	 */
	@Override
	public boolean isSpaceTaken(int column, int row) {
		return (row < columns.get(column).size());
	}
	
	/**
	 * Method to check if the token in the given column and row belongs to the player.
	 *
	 * @param player Player for checking the ownership of the token
	 * @param column integer column position of the token in the board
	 * @param row integer row position of the token in the board
	 * @return true if the token belongs to player and false otherwise
	 */
	@Override
	public boolean isPlayersToken(Player player, int column, int row){
		if(row >= columns.get(column).size()) return false;
		if(isSpaceTaken(column, row)){
			return columns.get(column).get(row).getOwner() == player;
		}
		return false;
	}
	
	/**
	 * Method to return the Player who is the owner of the token located in the given column and row.
	 *
	 * @param column integer column position of the token in the board
	 * @param row integer row position of the token in the board
	 * @return Player who owns the token and null if there are no tokens in the given position
	 */
	@Override
	public Player whoOwnsToken(int column, int row) {
		Token token = getToken(column, row);
		if(token == null) return null;
		return token.getOwner();
	}
	
	/**
	 * Method to check if the board is symmetric.
	 *
	 * @return true if the board is symmetric and false otherwise
	 */
	public boolean isSymmetric(){
		int mirrorCol;
		for(int col = 0; col < Math.ceil(width/2); col++){
			mirrorCol = width-1-col;
			if(!areColumnsEqual(col, mirrorCol)) return false;
		}
		return true;
	}
	
	/**
	 * Method to check if two given columns in a board are equal.
	 *
	 * @param col1 integer position of the column in the board
	 * @param col2 integer position of the column in the board
	 * @return true if all the corresponding tokens in the two columns are located in equal positions and false otherwise
	 */
	public boolean areColumnsEqual(int col1, int col2){
		ArrayList<Token> column1 = columns.get(col1);
		ArrayList<Token> column2 = columns.get(col2);
		
		if(column1.size()!=column2.size()) return false;
		for(int row = 0; row<column1.size(); row++){
			if(whoOwnsToken(col1, row) != whoOwnsToken(col2, row)) return false;
		}
		return true;
	}
}

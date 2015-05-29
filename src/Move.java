
/**
 * The Class Move.
 * Represents a move by a player
 */
public class Move {
	
	/** The column index. */
	private int column;
	
	/** The token. */
	private Token token;

	/**
	 * Default constructor to set a move.
	 *
	 * @param column the column position on the board
	 * @param token the player token to place on the board
	 */
	public Move(int column, Token token) {
		this.column = column;
		this.token = token;
	}
	
	/**
	 * Method to return the column for the Move.
	 *
	 * @return integer column position in the board for the Move
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Method to return the Token used in the Move.
	 *
	 * @return Token used in the Move
	 */
	public Token getToken() {
		return token;
	}
	
}

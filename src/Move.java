public class Move {
	
	private int column;
	private Token token;

	/**
	 * Default constructor to set a move
	 * @param column the column position on the board
	 * @param token the player token to place on the board
	 */
	public Move(int column, Token token) {
		this.column = column;
		this.token = token;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Token getToken() {
		return token;
	}
	


}

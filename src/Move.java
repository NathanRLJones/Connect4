public class Move {
	
	private int column;
	private Token token;
	
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

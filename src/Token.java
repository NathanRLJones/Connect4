import java.awt.Color;

public class Token {

	private Player owner;
	
	public Token(Player owner) {
		this.owner = owner;
	}

	public Color getColor() {
		return owner.getColor();
	}
	
	public Player getOwner() {
		return owner;
	}
	
}
import java.awt.Color;

/**
 * Piece to be placed on the connect four board.
 */
public class Token {

	/** Player who placed this token. */
	private Player owner;
	
	/**
	 * Creates a new token with a given owner.
	 *
	 * @param owner the player who placed this token
	 */
	public Token(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * Gets the color of this token.
	 *
	 * @return color of this token
	 */
	public Color getColor() {
		return owner.getColor();
	}
	
	/**
	 * Gets the owner of this token.
	 *
	 * @return owner of this token
	 */
	public Player getOwner() {
		return owner;
	}
	
}
import java.awt.Color;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface Player.
 */
public interface Player {

	/**
	 * Gets the move.
	 *
	 * @param board the board
	 * @param players the players
	 * @param tokensToWin the tokens to win
	 * @return the move
	 */
	public Move getMove(BoardInterface board, List<Player> players, int tokensToWin);
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor();
	
	/**
	 * Checks if is interactive.
	 *
	 * @return true, if is interactive
	 */
	public boolean isInteractive();
}

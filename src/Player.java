/**
 * Implements Player interface class
 * 
 * @author      Nathan Jones z5021296
 *              Lawrence z5018371
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.Color;
import java.util.List;

/**
 * The Interface Player.
 */
public interface Player {

	/**
	 * Gets the move.
	 *
	 * @param board the board of game
	 * @param players the players of game
	 * @param tokensToWin the tokens to win
	 * @return the move chosen
	 */
	public Move getMove(BoardInterface board, List<Player> players, int tokensToWin);
	
	/**
	 * Gets the name of player.
	 *
	 * @return the name of player
	 */
	public String getName();
	
	/**
	 * Gets the color of player.
	 *
	 * @return the color of player
	 */
	public Color getColor();
	
	/**
	 * Checks if player requires interaction
	 *
	 * @return true, if is interactive
	 */
	public boolean isInteractive();
}

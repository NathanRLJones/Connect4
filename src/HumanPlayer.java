/**
 * Implements HumanPlayer class
 * 
 * @author      Minjee Son z3330687
 *              Nathan Jones z5021296
 *              Lawrence z5018371
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.Color;
import java.util.List;

/**
 * The Class HumanPlayer.
 * Holds the player's name and token color.
 */
public class HumanPlayer implements Player {

	/** The color of player. */
	private Color color;
	
	/** The name of player. */
	private String name;
	
	/**
	 * Default constructor for players.
	 *
	 * @param humanName name for the player
	 * @param tokenColor colour indicator for the player
	 */
	public HumanPlayer(String humanName, Color tokenColor) {
		name = humanName;
		color = tokenColor;
	}

	/**
	 * Method to get the player's move.
	 *
	 * @param board the board
	 * @param players the players
	 * @param tokensToWin the tokens to win
	 * @return the move of the player
	 */
	public Move getMove(BoardInterface board, List<Player> players, int tokensToWin) {
		return null;
	}
	
	/**
	 * Method to get the name of the player.
	 *
	 * @return String name of the Player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the color of the player.
	 *
	 * @return Color of the player
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Method to check if the player is interactive.
	 *
	 * @return true if the player is human and false otherwise
	 */
	@Override
	public boolean isInteractive(){
		return true;
	}
	
}

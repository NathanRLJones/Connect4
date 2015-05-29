/**
 * Implements RandomAI class
 * 
 * @author      Nathan Jones z5021296
 *              Minjee Son z3330687
 *              Nicholas Yuwono z5016198
 *              Lawrence z5018371
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.Color;
import java.util.List;
import java.util.Random;


/**
 * The Class RandomAI.
 * When asked for a move returns a random move.
 */
public class RandomAI implements Player {

	/** The color of player. */
	Color color;
	
	/** The name of player. */
	String name;
	
	/** The random object to genrerate moves. */
	Random rand;
	
	/**
	 * Default constructor for players.
	 *
	 * @param aiName name for the player
	 * @param aiColor colour indicator for the player
	 */
	public RandomAI(String aiName, Color aiColor){
		name = aiName;
		color = aiColor;
		rand = new Random();
	}
	
	/**
	 * Method to get the player's move.
	 *
	 * @param board the board
	 * @param players the players
	 * @param tokensToWin the tokens to win
	 * @return the move of the player
	 */
	@Override
	public Move getMove(BoardInterface board, List<Player> players, int tokensToWin) {
		int col = rand.nextInt(board.getWidth());
		
		while(board.isColumnFull(col))
			col = rand.nextInt(board.getWidth());
		return new Move(col, new Token(this));
	}

	/**
	 * Method to get the name of the player.
	 *
	 * @return String name of the Player
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Method to get the color of the player.
	 *
	 * @return Color of the player
	 */
	@Override
	public Color getColor() {
		return color;
	}
	
	/**
	 * Method to check if the player is interactive.
	 *
	 * @return true if the player is human and false otherwise
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}

}

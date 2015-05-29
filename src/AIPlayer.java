/**
 * Implements AIPlayer class
 * @author      Minjee Son z3330687
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.Color;
import java.util.List;

/**
 * Represents a computer-controller player that has a difficulty level
 * and queries the moveGenie to get appropriate moves.
 */
public class AIPlayer implements Player {
	public static final int TOKENS_TO_WIN = 4;
	/** The name of the Player */
	String name;
	/** The Color of the Player Token */
	Color color;
	/** The level of AI smartness */
	int difficultyLevel; 
	
	/**
	 * Constructor for AIPlayer
	 * @param aiName String name for AIPlayer
	 * @param aiColor Color of AIPlayer tokens
	 * @param difficultyLevel integer level of AI smartness
	 */
	public AIPlayer(String aiName, Color aiColor, int difficultyLevel){
		name = aiName;
		color = aiColor;
		this.difficultyLevel = difficultyLevel; 
	}
	/**
	 * Method to get player's move
	 * @param currBoard BoardInterface of the current board
	 * @param players List<Player> of Players in the order of the play
	 * @param tokensToWin integer number of tokens in a line required to win
	 * @return AIPlayer's best Move
	 */
	@Override
	public Move getMove(BoardInterface currBoard, List<Player> players, int tokensToWin) {
		return MoveGenie.getMove(currBoard, difficultyLevel, players, this, tokensToWin);
	}
	/**
	 * Method to get the name of the player
	 * @return String name of the player
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * Method to get the token colour of the player 
	 * @return Color of the player
	 */
	@Override
	public Color getColor() {
		return color;
	}
	/**
	 * Method to check if the player is interactive
	 * @return true if the player is human and false otherwise
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}
}

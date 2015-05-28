import java.awt.Color;
import java.util.List;
import java.util.Random;


public class RandomAI implements Player {

	Color color;
	String name;
	Random rand;
	/**
	 * Default constructor for players
	 * @param aiNamee name for the player
	 * @param aiColor colour indicator for the player
	 */
	public RandomAI(String aiName, Color aiColor){
		name = aiName;
		color = aiColor;
		rand = new Random();
	}
	
	/**
	 * Method to get the player's move
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
	 * Method to get the name of the player
	 * @return String name of the Player
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Method to get the color of the player
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

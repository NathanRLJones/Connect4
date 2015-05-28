import java.awt.Color;
import java.util.List;


public class AIPlayer implements Player {
	public static final int TOKENS_TO_WIN = 4;
	
	String name;
	Color color;
	int difficultyLevel; //Depth of search 
	int depth;
	Board board;
	List<Player> allPlayers;
	int noOfPlayers;
	int aITurnInd;
	
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
	 * Returns AIPlayer's best Move 
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
	 * Returns name of the AIPlayer
	 * @return String name of the AIPlayer
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * Returns the color of the AIPlayer
	 * @return Color of the AIPlayer
	 */
	@Override
	public Color getColor() {
		return color;
	}
	/**
	 * Returns true if the Player is interactive and false otherwise
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}
}

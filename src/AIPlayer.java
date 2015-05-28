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
	
	public AIPlayer(String aiName, Color aiColor, int difficultyLevel){
		name = aiName;
		color = aiColor;
		this.difficultyLevel = difficultyLevel; 
	}
	
	@Override
	public Move getMove(BoardInterface currBoard, List<Player> players, int tokensToWin) {
		return MoveGenie.getMove(currBoard, difficultyLevel, players, this, tokensToWin);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public boolean isInteractive() {
		return false;
	}
}

import java.awt.Color;
import java.util.List;
import java.util.Random;


public class RandomAI implements Player {

	Color color;
	String name;
	Random rand;
	
	public RandomAI(String aiName, Color aiColor){
		name = aiName;
		color = aiColor;
		rand = new Random();
	}
	
	@Override
	public Move getMove(BoardInterface board, List<Player> players) {
		Move randomMove = null;
		
		int col = rand.nextInt(board.getWidth());
		
		while(board.isColumnFull(col)){
			col = rand.nextInt(board.getWidth());
		}
		
		return new Move(col, new Token(this));
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

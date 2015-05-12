import java.awt.Color;
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
	public Move getMove(BoardInterface board) {
		Move randomMove = null;
		
		int col = rand.nextInt(board.getColumns());
		
		while(board.isColumnFull(col)){
			col = rand.nextInt(board.getColumns());
		}
		
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

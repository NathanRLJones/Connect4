import java.awt.Color;

public class Human implements Player {

	private Color color;
	private String name;
	
	public Human(String humanName, Color tokenColor) {
		// TODO stub
		name = humanName;
		color = tokenColor;
	}
	
	public Move getMove() {
		// TODO stub
		
		int column = 0;
		
		/*
		 * Wait for GUI to trigger a MoveChosenEvent
		 * which should contain a column
		 * 
		 */
		
		return new Move(column, new Token(this));
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
}

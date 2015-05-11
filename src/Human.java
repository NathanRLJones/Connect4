import java.awt.Color;

public class Human implements Player {

	private Color color;
	private String name;

	/**
	 * Default constructor for players
	 * @param humanName name for the player
	 * @param tokenColor colour indicator for the player
	 */
	public Human(String humanName, Color tokenColor) {
		// TODO stub
		name = humanName;
		color = tokenColor;
	}

	/**
	 * Method to get player's move
	 * @return the move of the player
	 */
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

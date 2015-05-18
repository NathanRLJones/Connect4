import java.awt.Color;

public class Human implements Player {

	private Color color;
	private String name;
	private Gui gui;
	
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
	public Move getMove(BoardInterface board) {
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public boolean isInteractive(){
		return true;
	}
	
}
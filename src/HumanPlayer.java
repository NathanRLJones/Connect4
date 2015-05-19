import java.awt.Color;
import java.util.List;

public class HumanPlayer implements Player {

	private Color color;
	private String name;
	private Gui gui;
	
	/**
	 * Default constructor for players
	 * @param humanName name for the player
	 * @param tokenColor colour indicator for the player
	 */
	public HumanPlayer(String humanName, Color tokenColor) {
		// TODO stub
		name = humanName;
		color = tokenColor;
	}

	/**
	 * Method to get player's move
	 * @return the move of the player
	 */
	public Move getMove(BoardInterface board, List<Player> players) {
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

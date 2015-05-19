import java.awt.Color;
import java.util.List;

public interface Player {

	public Move getMove(BoardInterface board, List<Player> players);
	public String getName();
	public Color getColor();
	public boolean isInteractive();
}

import java.awt.Color;

public interface Player {

	public Move getMove(BoardInterface board);
	public String getName();
	public Color getColor();
	public boolean isInteractive();
}

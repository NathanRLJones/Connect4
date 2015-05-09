import javax.swing.SwingUtilities;

public class GameManager {

	private ConnectFour game;
	private Gui gui;
	
	public static void main(String[] args) {
		// Entry point
		ConnectFour game = new ConnectFour();
		Gui gui = new Gui();
		GameManager manager = new GameManager(game, gui);
	}
	
	public GameManager(ConnectFour game, Gui gui) {
		this.game = game;
		this.gui = gui;
		
		startGui();
	}
	
	public void startGui() {
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	gui.display();
	        }
	    });
	}
	
}

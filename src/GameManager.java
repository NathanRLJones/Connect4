import javax.swing.SwingUtilities;

public class GameManager {

	private ConnectFour game;
	private Gui gui;
	
	public static void main(String[] args) {
		// Entry point
		ConnectFour game = new ConnectFour();
		Gui gui = new Gui();
		GameManager manager = new GameManager(game, gui);
		manager.run();
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
	
	public void run(){
		//here is where we will run our game
		//loop while window is open
		//	-display gui
		//	-
		//	-see if current player has decided on a move
		//	-if move has been decided
		//		-make move
		//		-tell the gui the move
		//		-get next player
		//		-tell next player it is their turn
		//		-if it is a human player, tell the gui
	}
	
}

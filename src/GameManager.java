import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class GameManager {

	private ConnectFour game;
	private Gui gui;

	/**
	 * Main method for the ConnectFour
	 * @param args are ignored
	 */
	public static void main(String[] args) {
		// Entry point
		//ConnectFour game = new ConnectFour();
		ArrayList<Player> players = new ArrayList<Player>();
		Human p1 = new Human("p1", Color.RED);
		Human p2 = new Human("p2", Color.BLUE);
		players.add(p1);
		players.add(p2);
		ConnectFour game = new ConnectFour(7, 6, players);
		Gui gui = new Gui(game);
		p1.setGui(gui);
		p2.setGui(gui);
		GameManager manager = new GameManager(game, gui);
		manager.run();
	}

	/**
	 * Default constructor for the game manager
	 * @param game the game skeleton/mechanic
	 * @param gui the graphic user interface
	 */
	public GameManager(ConnectFour game, Gui gui) {
		this.game = game;
		this.gui = gui;
		
		startGui();
	}

	/**
	 * Method to display the window
	 */
	public void startGui() {
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	gui.display();
	        }
	    });
	}

	/**
	 * Method to run the game
	 */
	public void run(){
		//here is where we will run our game
		Player currPlayer = game.whoseTurnIsIt();
		
		if(currPlayer == null){
			//The game has been started with no players,
			//so abort.
			return;
		}
		
		gui.updateCurrentPlayer();
		
		//loop while window is open
		while(true){
			//sleep for a bit, we don't need to run as fast as possible
			//this could be changed over to hold at a max fps by storing
			//the time between frames.
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//repaint gui
			//hanlded by timer in the BoardPanel
			//gui.repaint();
			
			//get the player's move (returns null if undecided)
			Move currMove = currPlayer.getMove();
			if(currMove != null){
				game.doMove(currMove);
				gui.doMove(currMove);
				currPlayer = game.whoseTurnIsIt();
				
				//check if game is over
				if(game.isGameOver()){
					System.out.println("GAME IS OVER");
					break;
				}
				
				gui.updateCurrentPlayer();
				
				//The following won't be needed if
				//the gui can ask the game whose turn it is,
				//and then ask the player if they are human
				//if(currPlayer.isHuman()){
					//gui.interactiveTurn();
				//}else{
					//gui.noninteractiveTurn();
				//}
			}
		}

	}
	
}

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class BoardPanelDemo {
	
	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private JButton newGameButton;

	/**
	 * Method to bootstrap the main frame
	 * @param args
	 */
	public static void main(String[] args) {
		final BoardPanelDemo bpd = new BoardPanelDemo();
		
		// display the main window in a different thread.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	bpd.display();
            }
        });
	}
	
	/**
	 * Constructor for the main window.
	 */
	public BoardPanelDemo() {
		mainFrame = new JFrame("Connect 4 GUI Demo");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create the soduku panel
		boardPanel = new BoardPanel();
		
		// Create a new button and add the action listener.
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(boardPanel);
		
		// Or do it through an anonymous inner class
//		newGameButton = new JButton(new AbstractAction("New Game") {
//		    public void actionPerformed(ActionEvent e) {
//		        sodukuPanel.generateBoard();
//		        sodukuPanel.repaint();
//		    }
//		});
	}

	/**
	 * Method to display the main window
	 */
	private void display() {
		mainFrame.getContentPane().add(boardPanel,BorderLayout.CENTER);
		mainFrame.getContentPane().add(newGameButton,BorderLayout.SOUTH);
		mainFrame.pack();
        mainFrame.setVisible(true);
	}

}

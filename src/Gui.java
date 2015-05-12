import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Gui {

	private JFrame mainFrame;
	private BoardPanel boardPanel;
	
	public Gui() {
		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void display() {
		mainFrame.setPreferredSize(new Dimension(1024, 800));
		mainFrame.getContentPane().add(boardPanel,BorderLayout.CENTER);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public void repaint(){
		mainFrame.repaint();
	}
	
}

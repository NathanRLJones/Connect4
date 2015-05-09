import java.awt.Dimension;

import javax.swing.JFrame;

public class Gui {

	private JFrame mainFrame;
	
	public Gui() {
		mainFrame = new JFrame("Connect 4");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void display() {
		mainFrame.setPreferredSize(new Dimension(1024, 800));
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
	}
	
}

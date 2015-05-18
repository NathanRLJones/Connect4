import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class InfoPanel extends JPanel {

	private JLabel turnLabel;
	
	public InfoPanel() {
        super();
        setOpaque(true);
        setBackground(Color.WHITE);
        
        turnLabel = new JLabel();
        add(turnLabel, BorderLayout.NORTH);
    }

	public Dimension getPreferredSize() {
        return new Dimension(500,50);
    }
	
	public void setTurnLabel(String text) {
		turnLabel.setText(text);
	}
	
}

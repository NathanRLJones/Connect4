import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InfoPanel extends JPanel {

	private JLabel statusLabel;
	
	// TODO make it scale
	public InfoPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.WHITE);
        
        add(Box.createVerticalGlue());
        
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 32));
        topPanel.add(statusLabel);
        
        add(topPanel);
    }

	public Dimension getPreferredSize() {
        return new Dimension(500,50);
    }
	
	public void setStatusLabel(String text) {
		statusLabel.setText(text);
	}
	
	public void setStatusLabelColor(Color color) {
		statusLabel.setForeground(color);
	}
	
}

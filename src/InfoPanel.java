/**
 * Implements InfoPanel class
 * 
 * @author      Lawrence z5018371
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The class InfoPanel
 * Displays information about the game, either the active player's name
 * or the end result of the game.
 */
public class InfoPanel extends JPanel {
	
	/** Displays the current status of the game. */
	private JLabel statusLabel;
	
	/**
	 * Creates a new InfoPanel.
	 */
	public InfoPanel() {
		// Set panel properties
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.WHITE);
        
        // Set up layout
        add(Box.createVerticalGlue());
        
        // Panel for top
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        
        // Status label
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 32));
        topPanel.add(statusLabel);
        
        add(topPanel);
    }

	/**
	 * Sets the preferred size for this panel.
	 *
	 * @return the preferred size
	 */
	public Dimension getPreferredSize() {
        return new Dimension(500, 50);
    }
	
	/**
	 * Changes the text of the status label.
	 *
	 * @param text new text to display for the label
	 */
	public void setStatusLabel(String text) {
		statusLabel.setText(text);
	}
	
	/**
	 * Changes the foreground color of the status label.
	 *
	 * @param color new color of the label
	 */
	public void setStatusLabelColor(Color color) {
		statusLabel.setForeground(color);
	}
	
}

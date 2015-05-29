import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerOptionsPanel.
 */
class PlayerOptionsPanel extends JPanel implements ActionListener{

    /** The start y. */
    private int startY;
    
    /** The name box. */
    private JTextField nameBox;
    
    /** The player type. */
    private JComboBox<String> playerType;
    
    /** The color. */
    private Color color;
    
    /** The name. */
    private String name;

    /**
     * Instantiates a new player options panel.
     *
     * @param n the n
     * @param t the t
     * @param c the c
     */
    public PlayerOptionsPanel (String n, String t, Color c) {
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // player type
        String[] typeLabels = { "Human", "Easy", "Medium", "Hard" };
        playerType =  new JComboBox<String>(typeLabels);
        playerType.setSelectedItem(t);
        playerType.addActionListener(this);


        nameBox = new JTextField(n);
        // nameBox.setFont(nameBox.getFont().deriveFont(14f));
        
        gbc.insets = new Insets(1,55,0,5);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        add(nameBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        add(playerType, gbc);
        
        name = n;
        color = c;
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        int width = 280;
        int height = 10;
        height += nameBox.getPreferredSize().height;
        height += playerType.getPreferredSize().height;
        return new Dimension(width, height);
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String type = (String)cb.getSelectedItem();
    }


    /**
     * Gets the start y.
     *
     * @return the start y
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Sets the start y.
     *
     * @param startY the start y
     * @return the int
     */
    public int setStartY(int startY) {
        return this.startY = startY;
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    public Player getPlayer(){
    	Player player = null;
    	
    	switch((String) playerType.getSelectedItem()){
    	case "Human":
    		player = new HumanPlayer(nameBox.getText(), color);
    		break;
    	case "Easy":
    		player = new RandomAI(nameBox.getText(), color);
    		break;
    	case "Medium":
    		player = new AIPlayer(nameBox.getText(), color, 1);
    		break;
    	default:
    		player = new AIPlayer(nameBox.getText(), color, 2);
    	}
    	
    	return player;
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2;          // 2D graphics 
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Paint little token icon
        Ellipse2D circle = new Ellipse2D.Double();
        int y = (getHeight() - 40)/2;
        circle.setFrame(10, y, 40, 40);
        g2.setColor(color.darker());
        g2.fill(circle);
        circle.setFrame(12, y+2, 36, 36);
        g2.setColor(color);
        g2.fill(circle);
        
        // Paint some gripers

    } 
}




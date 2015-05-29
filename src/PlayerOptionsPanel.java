import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class PlayerOptionsPanel extends JPanel implements ActionListener{

    private int startY;
    private JTextField nameBox;
    private JComboBox<String> playerType;
    private Color color;
    private String name;

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

    public Dimension getPreferredSize() {
        int width = 280;
        int height = 10;
        height += nameBox.getPreferredSize().height;
        height += playerType.getPreferredSize().height;
        return new Dimension(width, height);
    }

    public Color getColor() {
        return color;
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String type = (String)cb.getSelectedItem();
    }


    public int getStartY() {
        return startY;
    }

    public int setStartY(int startY) {
        return this.startY = startY;
    }

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




import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class PlayerOptionsPanel extends JPanel implements ActionListener{

    private int startY;
    private JTextField nameBox;
    private JComboBox<String> playerType;
    private Color color;
    private String name;


    public PlayerOptionsPanel (String n, Color c) {
        super(new GridBagLayout());
        // setBackground(color);

        String[] typeLabels = { "Human", "Easy", "Medium", "Hard" };
        GridBagConstraints gbc = new GridBagConstraints();

        playerType =  new JComboBox<String>(typeLabels);
        playerType.setSelectedIndex(0);
        playerType.addActionListener(this);

        nameBox = new JTextField(n);
        //nameBox.setText(n);
        
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameBox, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        add(playerType, gbc);
        
        name = n;
        color = c;
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
}




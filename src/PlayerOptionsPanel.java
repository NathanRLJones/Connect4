import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class PlayerOptionsPanel extends JPanel implements ActionListener{

    private int startY;
    private JComboBox<String> playerType;


    public PlayerOptionsPanel (String name) {
        super(new GridBagLayout());
        // setBackground(color);

        String[] typeLabels = { "Human", "Easy", "Medium", "Hard" };
        GridBagConstraints gbc = new GridBagConstraints();

        playerType =  new JComboBox<String>(typeLabels);
        playerType.setSelectedIndex(0);
        playerType.addActionListener(this);

        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(name), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        add(playerType, gbc);

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


}




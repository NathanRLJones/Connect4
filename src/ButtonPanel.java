import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private JButton button;

    public ButtonPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.WHITE);

        add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(Color.WHITE);

        button = new JButton("UNDO");
        button.setActionCommand("Undo");

        add(bottomPanel);
        add(button);
    }

    public Dimension getPreferredSize() {
        return new Dimension(500,50);
    }

}

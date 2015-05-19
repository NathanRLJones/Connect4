import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private JLabel statusLabel;

    public ButtonPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.WHITE);

        add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(Color.WHITE);

        add(bottomPanel);
    }

    public Dimension getPreferredSize() {
        return new Dimension(500,50);
    }

    public void setStatusLabel(String text) {
        statusLabel.setText(text);
    }

}

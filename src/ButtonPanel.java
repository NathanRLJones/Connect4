import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {

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

        JButton undoButton = new JButton("UNDO");
        undoButton.addActionListener(this);
        undoButton.setActionCommand("Undo");

        JButton redoButton = new JButton("REDO");
        redoButton.addActionListener(this);
        redoButton.setActionCommand("Redo");

        add(bottomPanel);
        add(undoButton);
        add(redoButton);
    }

    public Dimension getPreferredSize() {
        return new Dimension(500,50);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Undo")) {
            System.out.println("Button pressed!");
        } else if (action.equals("Redo")) {

        }
    }

}

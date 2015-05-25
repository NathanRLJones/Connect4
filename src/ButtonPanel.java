import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {

	private ConnectFour game;
    private JButton button;

    public ButtonPanel(ConnectFour game) {
        super();
        this.game = game;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.WHITE);

        //add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(Color.WHITE);
        //bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JButton undoButton = new JButton("UNDO");           //Adding undo button
        undoButton.addActionListener(this);
        undoButton.setActionCommand("Undo");
        c.insets = new Insets(10,10,0,10);                  //Button extra space(top, left, bottom, right)

        JButton redoButton = new JButton("REDO");
        redoButton.addActionListener(this);
        redoButton.setActionCommand("Redo");
        c.insets = new Insets(10,10,0,10);

        JButton restartButton = new JButton("RESTART");
        restartButton.addActionListener(this);
        restartButton.setActionCommand("Restart");
        c.insets = new Insets(10,10,0,10);

        bottomPanel.add(undoButton, c);
        bottomPanel.add(redoButton, c);
        bottomPanel.add(restartButton, c);
        //bottomPanel.add(undoButton);
        //bottomPanel.add(redoButton);
        add(bottomPanel);
        //add(undoButton);
        //add(redoButton);
    }

    public Dimension getPreferredSize() {
        return new Dimension(100,60);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Undo")) {
            game.undo();
        } else if (action.equals("Redo")) {
        	game.redo();
        } else if (action.equals("Restart")) {
            game.restart();
        }
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel{

	private ConnectFour game;
	private JButton hintButton;
    private JButton redoButton;
    private JButton undoButton;
    private JButton restartButton;
    private JButton newGameButton;

    public ButtonPanel(ConnectFour game, ActionListener actionListener) {
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

        hintButton = new JButton("HINT");           //Adding hint button
        hintButton.addActionListener(actionListener);
        hintButton.setActionCommand("Hint");
        c.insets = new Insets(10,10,0,10);
        //c.weightx = 0.1;
        c.weighty = 0.5;
        
        undoButton = new JButton("UNDO");           //Adding undo button
        undoButton.addActionListener(actionListener);
        undoButton.setActionCommand("Undo");
        c.insets = new Insets(10,10,0,10);                  //Button extra space(top, left, bottom, right)

        redoButton = new JButton("REDO");
        redoButton.addActionListener(actionListener);
        redoButton.setActionCommand("Redo");
        c.insets = new Insets(10,10,0,10);

        restartButton = new JButton("RESTART");
        restartButton.addActionListener(actionListener);
        restartButton.setActionCommand("Restart");
        c.insets = new Insets(10,10,0,10);

        newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(actionListener);
        newGameButton.setActionCommand("NewGame");
        c.insets = new Insets(10,10,0,10);

        bottomPanel.add(hintButton, c);
        bottomPanel.add(undoButton, c);
        bottomPanel.add(redoButton, c);
        bottomPanel.add(restartButton, c);
        bottomPanel.add(newGameButton, c);
        //bottomPanel.add(undoButton);
        //bottomPanel.add(redoButton);
        add(bottomPanel);
        //add(undoButton);
        //add(redoButton);
        
        updateButtonStatus();
    }

    public Dimension getPreferredSize() {
        return new Dimension(100,60);
    }
    
    public void updateButtonStatus() {
    	if(game.getMoveHistory() == null || game.getMoveHistory().isEmpty()) {
    		undoButton.setEnabled(false);
        } else {
        	undoButton.setEnabled(true);
        }
        if(game.getUndoneMoves() == null || game.getUndoneMoves().isEmpty()) {
        	redoButton.setEnabled(false);
        } else {
        	redoButton.setEnabled(true);
        }
    }

}

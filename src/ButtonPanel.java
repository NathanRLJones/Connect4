/*
 * 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class ButtonPanel.
 * Panel at the bottom of the window for buttons.
 */
public class ButtonPanel extends JPanel{

	/** The main class used for reference. */
	private ConnectFour game;
	
	/** The hint button. */
	private JButton hintButton;
    
    /** The redo button. */
    private JButton redoButton;
    
    /** The undo button. */
    private JButton undoButton;
    
    /** The restart button. */
    private JButton restartButton;
    
    /** The new game button. */
    private JButton newGameButton;

    /**
     * Create button panel at the bottom of the window.
     *
     * @param game used to reference methods in ConnectFour to button commands
     * @param actionListener used for button commands
     */
    public ButtonPanel(ConnectFour game, ActionListener actionListener) {
        // Set panel properties
        super();
        this.game = game;
        setOpaque(true);
        setBackground(Color.WHITE);

        // Set up layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Construct new bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new GridBagLayout());

        //Add some gridbags restrictions
        GridBagConstraints c = new GridBagConstraints();

        //Setup Hint button
        hintButton = new JButton("HINT");
        hintButton.addActionListener(actionListener);   //Add command to the button
        hintButton.setActionCommand("Hint");
        c.insets = new Insets(10,10,0,10);              //Button extra space(top, left, bottom, right)
        c.weighty = 0.5;                                //Make buttons position scale from the bottom of the screen when resized

        //Setup Undo button
        undoButton = new JButton("UNDO");
        undoButton.addActionListener(actionListener);
        undoButton.setActionCommand("Undo");

        c.insets = new Insets(10,10,0,10);       //Button extra space(top, left, bottom, right)


        //Setup Redo button
        redoButton = new JButton("REDO");
        redoButton.addActionListener(actionListener);
        redoButton.setActionCommand("Redo");
        c.insets = new Insets(10,10,0,10);

        //Setup Restart button
        restartButton = new JButton("RESTART");
        restartButton.addActionListener(actionListener);
        restartButton.setActionCommand("Restart");
        c.insets = new Insets(10,10,0,10);

        //Setup New Game button
        newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(actionListener);
        newGameButton.setActionCommand("NewGame");
        c.insets = new Insets(10,10,0,10);

        //Add components to the bottom panel
        bottomPanel.add(hintButton, c);
        bottomPanel.add(undoButton, c);
        bottomPanel.add(redoButton, c);
        bottomPanel.add(restartButton, c);
        bottomPanel.add(newGameButton, c);
        add(bottomPanel);

        updateMoveButtons();
    }

    /**
     * Set default size of the panel.
     *
     * @return size of the panel
     */
    public Dimension getPreferredSize() {
        return new Dimension(100,60);
    }

    /**
     * Enable/disable buttons based on events.
     */
    public void updateMoveButtons() {
        //Disable undo, redo and hint button when the game is over
    	if(game.isGameOver()) {
    		undoButton.setEnabled(false);
    		redoButton.setEnabled(false);
    		hintButton.setEnabled(false);
    	} else {
            //If there is no previous move, disable undo button. Otherwise enable it
    		if(game.getMoveHistory() == null || game.getMoveHistory().isEmpty()) {
    			undoButton.setEnabled(false);
    		} else {
    			undoButton.setEnabled(true);
    		}
            //If there is move has been done, disable redo button. Otherwise, enable it
    		if(game.getUndoneMoves() == null || game.getUndoneMoves().isEmpty()) {
    			redoButton.setEnabled(false);
    		} else {
    			redoButton.setEnabled(true);
    		}
    		hintButton.setEnabled(true);
    	}
    }

    /**
     * Method to disable buttons.
     */
    public void disableMoveButtons() {
    	undoButton.setEnabled(false);
    	redoButton.setEnabled(false);
    	hintButton.setEnabled(false);
    }
    
}

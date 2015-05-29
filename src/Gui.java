import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Gui.
 */
public class Gui implements GameListener, BoardListener, ActionListener{

	/** The Constant DEFAULT_WIDTH. */
	public static final int DEFAULT_WIDTH = 600;
	
	/** The Constant DEFAULT_HEIGHT. */
	public static final int DEFAULT_HEIGHT = 700;
	
	/** The main frame. */
	private JFrame mainFrame;
	
	/** The board panel. */
	private BoardPanel boardPanel;
	
	/** The info panel. */
	private InfoPanel infoPanel;
    
    /** The button panel. */
    private ButtonPanel buttonPanel;
	
	/** The dialog panel. */
	private DialogPanel dialogPanel;
    
    /** The game. */
    private ConnectFour game;
    
    /** The pl panel. */
    private PlayerListPanel plPanel;
    
    /** The go panel. */
    private GameOptionsPanel goPanel;
    
    /** The paused. */
    private boolean paused;
	
	/**
	 * Instantiates a new gui.
	 *
	 * @param game the game
	 */
	public Gui(ConnectFour game) {

		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel(this);
		infoPanel = new InfoPanel();
        buttonPanel = new ButtonPanel(game, this);
        dialogPanel = new DialogPanel();
        plPanel = new PlayerListPanel();
        goPanel = new GameOptionsPanel(this);
        paused = false;
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(DEFAULT_WIDTH, 
                                               DEFAULT_HEIGHT/2));
		this.game = game;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                display();
            }
        });
	}

    /* (non-Javadoc)
     * @see GameListener#tokenPlaced(int, Token)
     */
    public void tokenPlaced(int column, Token token) {
        boardPanel.placeToken(column, token);
        buttonPanel.disableMoveButtons();
    }
    
    /* (non-Javadoc)
     * @see GameListener#tokenHinted(int, Token)
     */
    public void tokenHinted(int column, Token token) {
    	boardPanel.placeHint(column, token);
    }

    /* (non-Javadoc)
     * @see BoardListener#placedAnimationComplete()
     */
    public void placedAnimationComplete() {
        game.advance();
    }

    /* (non-Javadoc)
     * @see GameListener#tokenRemoved(int, Token)
     */
    public void tokenRemoved(int column, Token token) {
        boardPanel.removeToken(column, token);
        buttonPanel.disableMoveButtons();
    }
    
    /* (non-Javadoc)
     * @see BoardListener#animationQueueEmptied()
     */
    public void animationQueueEmptied() {
    	buttonPanel.updateMoveButtons();
    }

    /* (non-Javadoc)
     * @see GameListener#newTurn(Player)
     */
    public void newTurn(Player player) {
        if (paused) 
            return;
    	infoPanel.setStatusLabel(player.getName() + "'s turn!");
    	infoPanel.setStatusLabelColor(player.getColor().darker());
        infoPanel.paintImmediately(infoPanel.getBounds());
        if (player.isInteractive()){
            boardPanel.setInput(new Token(player));
        } else {
            game.queryPlayers();
        }
    }

    /* (non-Javadoc)
     * @see GameListener#gameStarted()
     */
    public void gameStarted() {
        BoardInterface board = game.getBoard();
        boardPanel.setBoardSize(board.getWidth(), board.getHeight());
    }

    /* (non-Javadoc)
     * @see GameListener#gameWon(Player, int, int, int, int, int)
     */
    public void gameWon(Player player, 
                        int col1, int row1, int col2, int row2, int numTokens) {
        infoPanel.setStatusLabel(player.getName() + " has won!");
        boardPanel.highlightConnected(col1, row1, col2, row2, numTokens);
    }

    /* (non-Javadoc)
     * @see GameListener#gameDrawn()
     */
    public void gameDrawn() {
    	infoPanel.setStatusLabelColor(Color.BLACK);
        infoPanel.setStatusLabel("Game is drawn!");
    }

    /* (non-Javadoc)
     * @see BoardListener#columnSelected(int)
     */
    public void columnSelected(int column) {
        game.setInputSuggestion(column);
        game.queryPlayers();
    }
    
	/**
	 * Display.
	 */
	public void display() {
        JPanel basePanel = new JPanel(new BorderLayout());
        
        basePanel.add(infoPanel, BorderLayout.NORTH);
        basePanel.add(buttonPanel, BorderLayout.SOUTH);
        basePanel.add(boardPanel,BorderLayout.CENTER);
        
        JButton startGameButton = new JButton("START GAME");
        startGameButton.addActionListener(this);
        startGameButton.setActionCommand("StartGame");
        
        JButton cancelNewGameButton = new JButton("CANCEL");
        cancelNewGameButton.addActionListener(this);
        cancelNewGameButton.setActionCommand("CancelNewGame");
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(cancelNewGameButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(startGameButton);
        plPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JPanel newGamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        newGamePanel.add(plPanel, c);
        c.gridx = 1;
        c.gridy = 0;
        newGamePanel.add(goPanel,  c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        newGamePanel.add(new JSeparator(SwingConstants.HORIZONTAL),  c);
        c.gridx = 0;
        c.gridy = 2;
        newGamePanel.add(buttonPane,  c);

        dialogPanel.setPanels(basePanel, newGamePanel);
        mainFrame.add(dialogPanel);        
		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
        startGame();
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
        case "Undo":
        	game.undo();
        	break;
        case "Redo":
        	game.redo();
        	break;
        case "Hint":
        	game.getHint();
        	break;
        case "NewGame":
            paused = true;
        	dialogPanel.showDialog();
        	break;
        case "StartGame":
            paused = false;
            startGame();
        	dialogPanel.hideDialog();
            break;
        case "Restart":
        	startGame();
        	break;
        case "changeNumOfPlayers":
            plPanel.setNumberOfPlayers(goPanel.getNumberOfPlayers());
            break;
        case "CancelNewGame":
        	dialogPanel.hideDialog();
            paused = false;
            newTurn(game.getCurrentPlayer());

        	break;
        }
	}

    /**
     * Start game.
     */
    private void startGame() {
        int[] size = goPanel.getBoardSize();
        int toWin = goPanel.getTokensToWin();
        game.newGame(toWin, size[0], size[1], plPanel.getPlayers());
    }
	
}

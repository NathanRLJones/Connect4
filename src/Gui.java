import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Gui implements GameListener, BoardListener, ActionListener{

	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 700;
	
	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private InfoPanel infoPanel;
    private ButtonPanel buttonPanel;
	private DialogPanel dialogPanel;
    private ConnectFour game;
    private PlayerListPanel plPanel;
    private GameOptionsPanel goPanel;
	
	public Gui(ConnectFour game) {

		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel(this);
		infoPanel = new InfoPanel();
        buttonPanel = new ButtonPanel(game, this);
        dialogPanel = new DialogPanel();
        plPanel = new PlayerListPanel();
        goPanel = new GameOptionsPanel();
        
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game = game;




        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                display();
            }
        });
	}

    public void tokenPlaced(int column, Token token) {
        boardPanel.placeToken(column, token);
        buttonPanel.disableMoveButtons();
    }
    
    public void tokenHinted(int column, Token token) {
    	boardPanel.placeHint(column, token);
    }

    public void placedAnimationComplete() {
        game.advance();
    }

    public void tokenRemoved(int column, Token token) {
        boardPanel.removeToken(column, token);
        buttonPanel.disableMoveButtons();
    }
    
    public void animationQueueEmptied() {
    	buttonPanel.updateMoveButtons();
    }

    public void newTurn(Player player) {
    	infoPanel.setStatusLabel(player.getName() + "'s turn!");
    	infoPanel.setStatusLabelColor(player.getColor().darker());
        infoPanel.paintImmediately(infoPanel.getBounds());
        if (player.isInteractive()){
            boardPanel.setInput(new Token(player));
        } else {
            game.queryPlayers();
        }
    }

    public void gameStarted() {
        BoardInterface board = game.getBoard();
        boardPanel.setBoardSize(board.getWidth(), board.getHeight());
    }

    public void gameWon(Player player, 
                        int col1, int row1, int col2, int row2, int numTokens) {
        infoPanel.setStatusLabel(player.getName() + " has won!");
        boardPanel.highlightConnected(col1, row1, col2, row2, numTokens);
    }

    public void gameDrawn() {
    	infoPanel.setStatusLabelColor(Color.BLACK);
        infoPanel.setStatusLabel("Game is drawn!");
    }

    public void columnSelected(int column) {
        game.setInputSuggestion(column);
        game.queryPlayers();
    }
    
	public void display() {
        JPanel basePanel = new JPanel(new BorderLayout());
        JPanel newGamePanel = new JPanel(new BorderLayout());
        
        basePanel.add(infoPanel, BorderLayout.NORTH);
        basePanel.add(buttonPanel, BorderLayout.SOUTH);
        basePanel.add(boardPanel,BorderLayout.CENTER);
        
        JButton startGameButton = new JButton("START GAME");
        startGameButton.addActionListener(this);
        startGameButton.setActionCommand("StartGame");
        
        JButton cancelNewGameButton = new JButton("CANCEL");
        cancelNewGameButton.addActionListener(this);
        cancelNewGameButton.setActionCommand("CancelNewGame");
        
        newGamePanel.add(plPanel, BorderLayout.CENTER);
        newGamePanel.add(goPanel, BorderLayout.EAST);
        JPanel coolNewPanel = new JPanel(new BorderLayout());
        coolNewPanel.add(startGameButton, BorderLayout.NORTH);
        coolNewPanel.add(cancelNewGameButton, BorderLayout.SOUTH);
        newGamePanel.add(coolNewPanel, BorderLayout.SOUTH);
        
        dialogPanel.setPanels(basePanel, newGamePanel);
        
        //mainFrame.getContentPane().add(dialogPanel, BorderLayout.CENTER);
        mainFrame.add(dialogPanel);        
		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
        startGame();
	}

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
        	dialogPanel.showDialog();
        	break;
        case "StartGame":
        	dialogPanel.hideDialog();
        case "Restart":        	
        	startGame();
        	break;
        case "CancelNewGame":
        	dialogPanel.hideDialog();
        	break;
        }
	}

    private void startGame() {
        int[] size = goPanel.getBoardSize();
        int toWin = goPanel.getTokensToWin();
        game.newGame(toWin, size[0], size[1], plPanel.getPlayers());
    }
	
}

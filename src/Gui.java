import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Gui implements GameListener, BoardListener, ActionListener{

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 800;
	
	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private InfoPanel infoPanel;
    private ButtonPanel buttonPanel;
	private DialogPanel dialogPanel;
    private ConnectFour game;
    private PlayerListPanel plPanel;
	
	public Gui(ConnectFour game) {

		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel(this);
		infoPanel = new InfoPanel();
        buttonPanel = new ButtonPanel(game, this);
        dialogPanel = new DialogPanel();
        plPanel = new PlayerListPanel();
        
        
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
        buttonPanel.updateButtonStatus();
    }
    
    public void tokenHinted(int column, Token token) {
    	boardPanel.placeHint(column, token);
    }

    public void placedAnimationComplete() {
        game.advance();
    }

    public void tokenRemoved(int column, Token token) {
        boardPanel.removeToken(column, token);
        buttonPanel.updateButtonStatus();
    }

    public void newTurn(Player player) {
    	infoPanel.setStatusLabel(player.getName() + "'s turn!");
    	infoPanel.setStatusLabelColor(player.getColor().darker());
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
                        int col1, int row1, int col2, int row2) {
        infoPanel.setStatusLabel(player.getName() + " has won!");
        boardPanel.highlightConnected(col1, row1, col2, row2);
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
        
        JButton startGameButton = new JButton("START GAME");           //Adding hint button
        startGameButton.addActionListener(this);
        startGameButton.setActionCommand("StartGame");
        
        newGamePanel.add(plPanel, BorderLayout.CENTER);
        newGamePanel.add(startGameButton, BorderLayout.SOUTH);
        
        dialogPanel.setPanels(basePanel, newGamePanel);
        mainFrame.getContentPane().add(dialogPanel, BorderLayout.CENTER);

		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Undo")) {
            game.undo();
        } else if (action.equals("Redo")) {
        	game.redo();
        } else if (action.equals("Restart")) {
            game.restart();
        } else if (action.equals("Hint")) {
        	game.getHint();
        } else if (action.equals("NewGame")) {
        	//~~hide the board/game screen
        	
        	//System.out.println("NEW GAME");
        	
        	//show the game menu
        	dialogPanel.showDialog();
        } else if(action.equals("StartGame")) {
        	dialogPanel.hideDialog();
        	
        	game.newGame(7, 6, plPanel.getPlayers());
        }
	}
	
}

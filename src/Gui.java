import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Gui implements GameListener, BoardListener{

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 800;
	
	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private InfoPanel infoPanel;
    private ButtonPanel buttonPanel;
	private ConnectFour game;
	
	public Gui(ConnectFour game) {

		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel(this);
		infoPanel = new InfoPanel();
        buttonPanel = new ButtonPanel();
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
    }

    public void placedAnimationComplete() {
        game.advance();
    }

    public void tokenRemoved(int column) {
        boardPanel.removeToken(column);
    }

    public void newTurn(Player player) {
        //TODO set label for whose turn
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
    }

    public void gameDrawn() {
        infoPanel.setStatusLabel("Game is drawn!");
    }

    public void columnSelected(int column) {
        game.setInputSuggestion(column);
        game.queryPlayers();
    }
	
	public void display() {
		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		mainFrame.getContentPane().add(infoPanel, BorderLayout.NORTH);
        mainFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		mainFrame.getContentPane().add(boardPanel,BorderLayout.CENTER);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
}

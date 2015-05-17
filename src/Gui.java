import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;

import javax.swing.SwingUtilities;

public class Gui implements GameListener, BoardListener{

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 800;
	
	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private ConnectFour game;
	
	public Gui(ConnectFour game) {

		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel(this);
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

    public void tokenRemoved(int column) {
        boardPanel.removeToken(column);
    }

    public void newTurn(Player player) {
        //TODO set label for whose turn
        if (player.isInteractive()){
            boardPanel.setInput(new Token(player));
        } else {
            game.advance();
        }
    }

    public void gameStarted() {
        BoardInterface board = game.getBoard();
        boardPanel.setBoardSize(board.getWidth(), board.getHeight());
    }

    public void gameWon(Player player, 
                        int col1, int row1, int col2, int row2) {
        System.out.println(player.getName() + " has won!");
        //TODO
    }

    public void gameDrawn() {
        System.out.println("Game is drawn!");
        //TODO
    }

    public void columnSelected(int column) {
        game.setInputSuggestion(column);
        game.advance();
    }
	
	public void display() {
		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		mainFrame.getContentPane().add(boardPanel,BorderLayout.CENTER);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
}

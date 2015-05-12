import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;

public class Gui {

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 800;
	
	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private ConnectFour game;
	private int lastMove;
	
	public Gui(ConnectFour newGame) {
		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game = newGame;
		lastMove = -1;
		boardPanel.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	int colNum;
		    	colNum = boardPanel.getColumnNumber(e.getX(), e.getY());
		    	if (!boardPanel.isProcessing() && colNum != -1) {
		    		lastMove = colNum;
		    	}
		    }
		});
	}
	
	public void doMove(Move move){
		boardPanel.addToken(move.getToken(), move.getColumn());
	}
	
	public void updateCurrentPlayer(){
		if(game.whoseTurnIsIt().isInteractive()){
			boardPanel.setPlayer(game.whoseTurnIsIt());
		}
	}
	
	public Move getMove(){
		Move newMove = null;
		
		if(game.whoseTurnIsIt().isInteractive()){
			//col = boardPanel.getCol();
			if(lastMove != -1){
				newMove = new Move(lastMove, new Token(game.whoseTurnIsIt()));
				lastMove = -1;
			}
			
		}
		
		if(game.isLegal(newMove)){
			return newMove;
		}
		return null;
	}
	
	public void display() {
		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		mainFrame.getContentPane().add(boardPanel,BorderLayout.CENTER);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public void repaint(){
		mainFrame.repaint();
	}
	
}

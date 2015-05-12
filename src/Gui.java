import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Gui {

	private JFrame mainFrame;
	private BoardPanel boardPanel;
	private ConnectFour game;
	
	public Gui(ConnectFour newGame) {
		mainFrame = new JFrame("Connect 4");
		boardPanel = new BoardPanel();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game = newGame;
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
		int col = -1;
		
		if(game.whoseTurnIsIt().isInteractive()){
			//col = boardPanel.getCol();
			if(col >= 0){
				newMove = new Move(col, new Token(game.whoseTurnIsIt()));
			}
			
		}
		
		if(game.isLegal(newMove)){
			return newMove;
		}
		return null;
	}
	
	public void display() {
		mainFrame.setPreferredSize(new Dimension(1024, 800));
		mainFrame.getContentPane().add(boardPanel,BorderLayout.CENTER);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public void repaint(){
		mainFrame.repaint();
	}
	
}

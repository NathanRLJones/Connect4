import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameOptionsPanel extends JPanel{

	private JComboBox<String> boardSizes;
	private JComboBox<Integer> winInARows;

	/**
	 * Create game option panel for new game
	 */
	public GameOptionsPanel(){
		// Set panel properties and layout
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(true);
        setBackground(Color.WHITE);

		//Add options to boardsizes
        String[] sizeLabels = {"4 x 3", "7 x 6", "10 x 9", "14 x 12"};
        boardSizes = new JComboBox<String>(sizeLabels);
        boardSizes.setSelectedIndex(1);

		//Add options to how many tokens to win
        Integer[] winInARowLabels = {3, 4, 5, 6};
        winInARows = new JComboBox<Integer>(winInARowLabels);
        winInARows.setSelectedIndex(1);

		//Add indicator labels  for the menu
        this.add(new JLabel("Board Size:"));
        this.add(boardSizes);

        this.add(new JLabel("Pieces to Win:"));
        this.add(winInARows);
	}

	/**
	 * Get number of tokens to win from the user
	 * @return number of tokens to win
	 */
	public int getTokensToWin(){
		return (int) winInARows.getSelectedItem();
	}

	/**
	 * Get board size select by the user
	 * @return selected board size by the user
	 */
	public int[] getBoardSize(){
		int[] size = new int[2];
		
		System.out.println("\"" + boardSizes.getSelectedItem() + "\"");
		
		switch((String) boardSizes.getSelectedItem()){
		case "4 x 3":
			size[0] = 4;
			size[1] = 3;
			break;
		case "7 x 6":
			size[0] = 7;
			size[1] = 6;
			break;
		case "10 x 9":
			size[0] = 10;
			size[1] = 9;
			break;
		default:
			size[0] = 14;
			size[1] = 12;
			break;
		}
		return size;
	}
	
}

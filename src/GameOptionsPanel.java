/**
 * Implements GameOptionsPanel class
 * 
 * @author      Nathan Jones z5021296
 *              Alen Bou-Haidar z5019028
 *              Nicholas Yuwono z5016198
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class GameOptionsPanel.
 * Menu panel for user when they pressed new game button.
 */
public class GameOptionsPanel extends JPanel{

	/** The size of the board. */
	private JComboBox<String> boardSizes;
	
	/** The number of tokens in a row needed to win. */
	private JComboBox<Integer> winInARows;
    
    /** The number of players. */
    private JComboBox<Integer> numOfPlayers;

	/**
	 * Create game option panel for new game.
	 *
	 * @param actionListener the gui action listener
	 */
    public GameOptionsPanel(ActionListener actionListener){
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Add options for board sizes
        String[] sizeLabels = {"4 x 3", "7 x 6", "10 x 9", "14 x 12"};
        boardSizes = new JComboBox<String>(sizeLabels);
        boardSizes.setSelectedIndex(1);

		//Add options to how many tokens to win
        Integer[] winInARowLabels = {3, 4, 5, 6};
        winInARows = new JComboBox<Integer>(winInARowLabels);
        winInARows.setSelectedIndex(1);

        //Add options for number of player
        Integer[] numOfPlayersLabels = {2, 3, 4};
        numOfPlayers = new JComboBox<Integer>(numOfPlayersLabels);
        numOfPlayers.setSelectedIndex(0);
        numOfPlayers.addActionListener(actionListener);
        numOfPlayers.setActionCommand("changeNumOfPlayers");

        //Place using gridbag layout
        gbc.insets = new Insets(0, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(boardSizes, gbc);
        gbc.gridy = 3;
        add(winInARows, gbc);
        gbc.gridy = 5;
        add(numOfPlayers, gbc);
        gbc.insets = new Insets(5, 5, 0, 5);

        //Add indicator labels
        gbc.gridy = 0;
        add(new JLabel("Board Size:"), gbc);
        gbc.gridy = 2;
        add(new JLabel("Pieces to Win:"), gbc);
        gbc.gridy = 4;
        add(new JLabel("Players:"), gbc);
        gbc.gridy = 7;
        add(new JLabel("<html>Drag player <br>token to <br>rearrage." +
                        "<br> &nbsp;<br> &nbsp;</html>"), gbc);
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        add(new JLabel(), gbc);

}

    /**
     * Get the number of players.
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return (int) numOfPlayers.getSelectedItem();
    }

	/**
	 * Get number of tokens to win from the user.
	 *
	 * @return number of tokens to win
	 */
	public int getTokensToWin(){
		return (int) winInARows.getSelectedItem();
	}

	/**
	 * Get board size select by the user.
	 *
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

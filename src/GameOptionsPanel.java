import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOptionsPanel extends JPanel{

	private JComboBox<String> boardSizes;
	private JComboBox<Integer> winInARows;
    private JComboBox<Integer> numOfPlayers;

	public GameOptionsPanel(ActionListener actionListener){
		super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        String[] sizeLabels = {"4 x 3", "7 x 6", "10 x 9", "14 x 12"};
        boardSizes = new JComboBox<String>(sizeLabels);
        boardSizes.setSelectedIndex(1);

        Integer[] winInARowLabels = {3, 4, 5, 6};
        winInARows = new JComboBox<Integer>(winInARowLabels);
        winInARows.setSelectedIndex(1);

        Integer[] numOfPlayersLabels = {2, 3, 4};
        numOfPlayers = new JComboBox<Integer>(numOfPlayersLabels);
        numOfPlayers.setSelectedIndex(0);
        numOfPlayers.addActionListener(actionListener);
        numOfPlayers.setActionCommand("changeNumOfPlayers");


        // gbc.insets = new Insets(int top, int left, int bottom, int right)
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

    public int getNumberOfPlayers() {
        return (int) numOfPlayers.getSelectedItem();
    }

	public int getTokensToWin(){
		return (int) winInARows.getSelectedItem();
	}
	
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

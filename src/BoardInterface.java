/**
 * Implements BoardInterface interface class
 * 
 * @author      Nathan Jones z5021296
 *              Alen Bou-Haidar z5019028
 *
 * @version     0.1
 * @since       2015-05-29
 */

/**
 * The Interface BoardInterface acts as a restricted board limiting a 
 * board to methods related to querying information.
 */
public interface BoardInterface {
	
	/**
	 * Gets the board height.
	 *
	 * @return the board height
	 */
	public int getHeight();
	
	/**
	 * Gets the board width.
	 *
	 * @return the board width
	 */
	public int getWidth();
	
	/**
	 * Who owns token at a position
	 *
	 * @param column the column
	 * @param row the row
	 * @return the player who owns the position
	 */
	public Player whoOwnsToken(int column, int row);
	
	/**
	 * Checks if is space taken.
	 *
	 * @param column the column
	 * @param row the row
	 * @return true, if is space taken
	 */
	public boolean isSpaceTaken(int column, int row);
	
	/**
	 * Checks if it is a players token.
	 *
	 * @param player the player
	 * @param column the column
	 * @param row the row
	 * @return true, if it is the players token
	 */
	public boolean isPlayersToken(Player player, int column, int row);
	
	/**
	 * Checks if is column full.
	 *
	 * @param column the column
	 * @return true, if is column full
	 */
	public boolean isColumnFull(int column);
}

// TODO: Auto-generated Javadoc
/**
 * The Interface BoardInterface.
 */
public interface BoardInterface {
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight();
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth();
	
	/**
	 * Who owns token.
	 *
	 * @param column the column
	 * @param row the row
	 * @return the player
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
	 * Checks if is players token.
	 *
	 * @param player the player
	 * @param column the column
	 * @param row the row
	 * @return true, if is players token
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

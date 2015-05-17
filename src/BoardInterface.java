public interface BoardInterface {
	
	public int getHeight();
	public int getWidth();
	
	public boolean isSpaceTaken(int column, int row);
	public boolean isPlayersToken(Player player, int column, int row);
	public boolean isColumnFull(int column);
}

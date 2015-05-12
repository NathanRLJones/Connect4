public interface BoardInterface {
	
	public int getRows();
	public int getColumns();
	
	public boolean isSpaceTaken(int column, int row);
	//public Player whoOwnsToken(int column, int row);
	public boolean isPlayersToken(Player player, int column, int row);
}

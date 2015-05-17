import java.awt.Color;
import java.util.ArrayList;


public class AIPlayer implements Player {
	public static final int TOKENS_TO_WIN = 4;
	
	String name;
	Color color;
	int difficultyLevel; //Depth of search 
	Board board;
	boolean isGameOver;
	
	public AIPlayer(String aiName, Color aiColor, int difficultyLevel){
		name = aiName;
		color = aiColor;
		this.difficultyLevel = difficultyLevel;
		isGameOver = false;
	}
	
	@Override
	public Move getMove(BoardInterface newBoard) {
		this.board = (Board)newBoard;
		int columns = board.getWidth();
		Token token = new Token(this);
		int maxScoreColumn = -1;
		int maxScore = Integer.MIN_VALUE;
		Player nextPlayer = null; // TODO: need to get the order of players
		
		for(int i = 0; i < columns; i++){
			if(!board.isColumnFull(i)){
				board.placeToken(i, token);
				int newScore = alphaBetaSearch(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, nextPlayer);
				if(newScore > maxScore){
					maxScore = newScore;
					maxScoreColumn = i;
				}
				board.removeToken(i);
			}
		}
		return new Move(maxScoreColumn, token);
	}

	private int calculateScore(){
		// 1 points for 1 aiPlayer token line with enough space for 4
		// 4 points for 2 aiPlayer token lines with enough space for 4
		// 6 points for 3 aiPlayer token lines with enough space for 4
		// 100 points for 4 aiPlayer token lines 
		// negative points for above if tokens belong to another player
		
		int score = 0;
		int height = board.getHeight();
		int width = board.getWidth();
		int noOfTokens;
		Player prevTokenOwner = null;
		Player currTokenOwner = null;
		//int emptySpaces = 0;

		// Check for scores vertically
		for (int col = 0; col < width; col++) {
			noOfTokens = 0;
			prevTokenOwner = null;
			currTokenOwner = null;
			for (int row = 0; row < height; row++) {
				if (height - row + noOfTokens < TOKENS_TO_WIN)
					break;
				currTokenOwner = board.whoOwnsToken(col, row);
				if(currTokenOwner == null){
					if (prevTokenOwner == this){
						switch(noOfTokens){
						case 1: score+=1;
						break;
						case 2: score+=4;
						break;
						case 3: score+=6;
						break;
						default: break;
						}
					}
					else if (prevTokenOwner != null){
						switch(noOfTokens){
						case 1: score-=1;
						break;
						case 2: score-=4;
						break;
						case 3: score-=6;
						break;
						default: break;
						}
					}
					break;
				}
				if (currTokenOwner == prevTokenOwner) {
					noOfTokens++;
					if(noOfTokens == TOKENS_TO_WIN){
						if(currTokenOwner == this) score+=100;
						else score-=100;
						isGameOver = true;
						break;
					}
				}
				else{
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}
		}
		//TODO: Horizontal and diagonal scores
		return score;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public boolean isInteractive() {
		return false;
	}
	
	public int alphaBetaSearch(int alpha, int beta, int depth, Player currTurn){
		//TODO
		ArrayList<Move> availableMoves = new ArrayList<>();
		Token token;
		int columns = board.getWidth();
		
		if(depth == difficultyLevel || isGameOver){
			isGameOver = false;
			return calculateScore();
		}
		
		token = new Token(currTurn);
		for(int i = 0; i < columns; i++){
			if(!board.isColumnFull(i)){
				availableMoves.add(new Move(i, token));
			}
		}
		
		return 0;
	}

}

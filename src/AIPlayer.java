import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class AIPlayer implements Player {
	public static final int TOKENS_TO_WIN = 4;
	
	String name;
	Color color;
	int difficultyLevel; //Depth of search 
	int depth;
	Board board;
	List<Player> allPlayers;
	int noOfPlayers;
	int aITurnInd;
	
	public AIPlayer(String aiName, Color aiColor, int difficultyLevel){
		name = aiName;
		color = aiColor;
		this.difficultyLevel = difficultyLevel; 
	}
	
	@Override
	public Move getMove(BoardInterface currBoard, List<Player> players) {
		board = getBoardCopy(currBoard);
		allPlayers = players;
		noOfPlayers = players.size();
		depth = difficultyLevel*noOfPlayers;
		int columns = board.getWidth();
		Token token = new Token(this);
		int maxScoreColumn = -1;
		int maxScore = Integer.MIN_VALUE;
		aITurnInd = players.indexOf(this);
		int nextTurnInd = (aITurnInd+1)%noOfPlayers;
		Player nextPlayer = players.get(nextTurnInd);
		int newScore;
		
		
		
		for(int i = 0; i < columns; i++){
			if(!board.isColumnFull(i)){
				board.placeToken(i, token);
				if(noOfPlayers == 2)
					newScore = alphaBetaSearch(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, nextPlayer);
				else
					newScore = minMaxSearch(0, nextPlayer).get(aITurnInd);
				if(newScore > maxScore){
					maxScore = newScore;
					maxScoreColumn = i;
				}
				board.removeToken(i);
			}
		}
		return new Move(maxScoreColumn, token);
	}

	private ArrayList<Integer> calculateScore(){
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for(Player p : allPlayers){
			scores.add(0);
		}
		
		// 1 points for 1 aiPlayer token line with enough space for 4
		// 4 points for 2 aiPlayer token lines with enough space for 4
		// 6 points for 3 aiPlayer token lines with enough space for 4
		// 100 points for 4 aiPlayer token lines 
		// negative points for above if tokens belong to another player
		
		int height = board.getHeight();
		int width = board.getWidth();
		int noOfTokens = 0;;
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
					int scoreChange = 0;
					switch(noOfTokens){
					case 1: scoreChange = 1;
					break;
					case 2: scoreChange = 4;
					break;
					case 3: scoreChange = 6;
					break;
					default: break;
					}
					for(int i = 0; i < allPlayers.size(); i++){	
						if(prevTokenOwner == allPlayers.get(i)){
							scores.set(i, scores.get(i) + scoreChange);
						}else{
							scores.set(i, scores.get(i) - scoreChange);
						}
					}
					break;
				}
				if (currTokenOwner == prevTokenOwner) {
					noOfTokens++;
					if(noOfTokens == TOKENS_TO_WIN){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
								scores.set(i, scores.get(i) + 100);
							}else{
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
					}
				}
				else{
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}
		}
		
		//check for scores horizontally
		for (int row = 0; row < width; row++) {
			noOfTokens = 0;
			prevTokenOwner = null;
			currTokenOwner = null;
			for (int col = 0; col < height; col++) {
				if (width - col + noOfTokens < TOKENS_TO_WIN)
					break;
				currTokenOwner = board.whoOwnsToken(col, row);
				if(currTokenOwner == null){
					int scoreChange = 0;
					switch(noOfTokens){
					case 1: scoreChange = 1;
					break;
					case 2: scoreChange = 4;
					break;
					case 3: scoreChange = 6;
					break;
					default: break;
					}
					for(int i = 0; i < allPlayers.size(); i++){	
						if(prevTokenOwner == allPlayers.get(i)){
							scores.set(i, scores.get(i) + scoreChange);
						}else{
							scores.set(i, scores.get(i) - scoreChange);
						}
					}
					break;
				}
				if (currTokenOwner == prevTokenOwner) {
					noOfTokens++;
					if(noOfTokens == TOKENS_TO_WIN){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
								scores.set(i, scores.get(i) + 100);
							}else{
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
					}
				}
				else{
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}
		}
		
		//TODO: diagonal scores
		
		//check for scores NW/SE diagonally
		int startCol = 0;
		int col = startCol;
		int startRow = TOKENS_TO_WIN - 1;
		int row = startRow;
		noOfTokens = 0;
		
		while(height + width - startCol - startRow - 2 < TOKENS_TO_WIN){
			
			//do scoring if we are in a workable positon
			if ((Math.min(row, (width - 1) - col) + 1) + noOfTokens >= TOKENS_TO_WIN){
				currTokenOwner = board.whoOwnsToken(col, row);
				if(currTokenOwner == null){
					int scoreChange = 0;
					switch(noOfTokens){
					case 1: scoreChange = 1;
					break;
					case 2: scoreChange = 4;
					break;
					case 3: scoreChange = 6;
					break;
					default: break;
					}
					for(int i = 0; i < allPlayers.size(); i++){	
						if(prevTokenOwner == allPlayers.get(i)){
							scores.set(i, scores.get(i) + scoreChange);
						}else{
							scores.set(i, scores.get(i) - scoreChange);
						}
					}
					break;
				}
				if (currTokenOwner == prevTokenOwner) {
					noOfTokens++;
					if(noOfTokens == TOKENS_TO_WIN){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
								scores.set(i, scores.get(i) + 100);
							}else{
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
					}
				}
				else{
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}
			
			//move to next space
			if(row > 0){
				//continue down the current diagonal
				col++;
				row--;
			}else{
				//go to the next diagonal
				if(startRow + 1 == height){
					row = startRow;
					startCol++;
					col = startCol;
				}else{
					startRow++;
					row = startRow;
					col = startCol;
				}
				
				//reset counters
				noOfTokens = 0;
				prevTokenOwner = null;
				currTokenOwner = null;
			}
		}
		
		//check for scores NE/SW diagonally
		startCol = 0;
		col = startCol;
		startRow = (height - 1) - (TOKENS_TO_WIN - 1);
		row = startRow;
		noOfTokens = 0;
		
		while(!(row == 0 && col + TOKENS_TO_WIN == width)){
			
			//do scoring if we are in a workable positon
			if ((Math.min((height - 1) -row, (width - 1) - col) + 1) + noOfTokens >= TOKENS_TO_WIN){
				currTokenOwner = board.whoOwnsToken(col, row);
				if(currTokenOwner == null){
					int scoreChange = 0;
					switch(noOfTokens){
					case 1: scoreChange = 1;
					break;
					case 2: scoreChange = 4;
					break;
					case 3: scoreChange = 6;
					break;
					default: break;
					}
					for(int i = 0; i < allPlayers.size(); i++){	
						if(prevTokenOwner == allPlayers.get(i)){
							scores.set(i, scores.get(i) + scoreChange);
						}else{
							scores.set(i, scores.get(i) - scoreChange);
						}
					}
					break;
				}
				if (currTokenOwner == prevTokenOwner) {
					noOfTokens++;
					if(noOfTokens == TOKENS_TO_WIN){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
								scores.set(i, scores.get(i) + 100);
							}else{
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
					}
				}
				else{
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}
			
			//move to next space
			if(row < height - 1){
				//continue down the current diagonal
				col++;
				row++;
			}else{
				//go to the next diagonal
				if(startRow == 0){
					row = startRow;
					startCol++;
					col = startCol;
				}else{
					startRow--;
					row = startRow;
					col = startCol;
				}
				
				//reset counters
				noOfTokens = 0;
				prevTokenOwner = null;
				currTokenOwner = null;
			}
		}
		
		return scores;
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
	
	private boolean isGameOver(){
		int height = board.getHeight();
		int width = board.getWidth();
		Player possibleWinner;
		int noOfTokens;
		Player currPlayer;
		Token currToken;
		
		// Check for a vertical line of TOKENS_TO_WIN same-colour tokens
		for(int col = 0; col < width; col++){
		 	noOfTokens = 0;
		 	possibleWinner = null;
		 	for(int row = 0; row < height; row++){
		 		if(height-row+noOfTokens < TOKENS_TO_WIN) break;
				
		 		currToken = board.getToken(col, row);
		 		if(currToken == null) break;
		 		currPlayer = currToken.getOwner();
		 		if(currPlayer == possibleWinner){
		 			noOfTokens++;
		 			if(noOfTokens == TOKENS_TO_WIN) {
		 				return true;
		 			}
		 		}else{
		 			possibleWinner = currPlayer;
		 			noOfTokens = 1;
		 		}
		 	}
		 }

		 //Check for a horizontal line of TOKENS_TO_WIN same-colour tokens
		 for(int row = 0; row < height; row++){
		 	noOfTokens = 0;
		 	possibleWinner = null;
		 	for(int col = 0; col < width; col++){
		 		if(width-col+noOfTokens < TOKENS_TO_WIN) break;
				
				currToken = board.getToken(col, row);
		 		if (currToken == null){
		 			possibleWinner = null;
		 			noOfTokens = 0;
		 			continue;
		 		}
		 		currPlayer = currToken.getOwner();
		 		if(currPlayer == possibleWinner){
		 			noOfTokens++;
		 			if(noOfTokens == TOKENS_TO_WIN) {
		 				return true;
		 			}
		 		}else{
		 			possibleWinner = currPlayer;
		 			noOfTokens = 1;
		 		}
		 	}
		 }
		
		 //Check for a diagonal line of TOKENS_TO_WIN same-colour tokens
		 for(int col = 0; col < width - TOKENS_TO_WIN+1; col++) {
		 	for(int row = 0; row < height; row++) {
		 		// Check diagonally upwards
		 		possibleWinner = null;
		 		noOfTokens = 0;
		 		if(row < height - TOKENS_TO_WIN+1) {
		 			for(int offset = 0; offset < TOKENS_TO_WIN; offset++) {
		 				currToken = board.getToken(col+offset, row+offset);
		 				if(currToken == null) break;
		 				currPlayer = currToken.getOwner();
		 				if(offset == 0) possibleWinner = currPlayer;
		 				if(currPlayer == possibleWinner){
		 					noOfTokens++;
		 					if(noOfTokens == TOKENS_TO_WIN) {
		 						return true;
		 					}
		 				}else{
		 					break;
		 				}
		 			}
		 		}
		 		// Check diagonally downwards
		 		possibleWinner = null;
		 		noOfTokens = 0;
		 		if(row > TOKENS_TO_WIN-2) {
		 			for(int offset = 0; offset < TOKENS_TO_WIN; offset++) {
		 				currToken = board.getToken(col+offset, row-offset);
		 				if(currToken == null) break;
		 				currPlayer = currToken.getOwner();
		 				if(offset == 0) possibleWinner = currPlayer;
		 				if(currPlayer == possibleWinner){
		 					noOfTokens++;
		 					if(noOfTokens == TOKENS_TO_WIN) {
		 						return true;
		 					}
		 				}else{
		 					break;
		 				}
		 			}
		 		}
		 	}
		 }
		 return false;
	}
	/*
	private int minMaxSearch(int depth, Player currTurn){
		ArrayList<Integer> availableColumns = new ArrayList<>();
		Token token = new Token(currTurn);
		int score;
		int nextTurnInd = allPlayers.indexOf(currTurn) + 1;
		Player nextTurn = allPlayers.get(nextTurnInd%allPlayers.size());
		
		if(depth == difficultyLevel || isGameOver())
			return calculateScore();
		for(int i = 0; i < board.getWidth(); i++){
			if(!board.isColumnFull(i))
				availableColumns.add(i);
		}		
		if(availableColumns.size()==0) 
			return 0;
		if(currTurn == this){
			score = Integer.MIN_VALUE;
			for(int column:availableColumns){
				board.placeToken(column, token);
				score = Math.max(score, minMaxSearch(depth+1, nextTurn));
				board.removeToken(column);
			}
		}else{
			score = Integer.MAX_VALUE;
			for(int column: availableColumns){
				board.placeToken(column, token);
				score = Math.min(score, minMaxSearch(depth+1, nextTurn));
				board.removeToken(column);
			}
		}		
		return score;
	}*/

	private ArrayList<Integer> minMaxSearch(int depth, Player currTurn){
		ArrayList<Integer> availableColumns = new ArrayList<>();
		Token token = new Token(currTurn);
		int currTurnInd = allPlayers.indexOf(currTurn);
		int nextTurnInd = (currTurnInd + 1)%noOfPlayers;
		Player nextTurn = allPlayers.get(nextTurnInd);
		int maxScore = Integer.MIN_VALUE;
		ArrayList<Integer> bestScoreList = null;
		ArrayList<Integer> currScoreList;
		int currScore;
		
		if(depth == this.depth || isGameOver())
			return calculateScore();
		for(int i = 0; i < board.getWidth(); i++){
			if(!board.isColumnFull(i))
				availableColumns.add(i);
		}		
		if(availableColumns.size()==0){
			bestScoreList = new ArrayList<>();
			for(int i = 0; i < allPlayers.size(); i++)
				bestScoreList.add(0);
			return bestScoreList;
		}
		for(int column:availableColumns){
			board.placeToken(column, token);
			currScoreList = minMaxSearch(depth+1, nextTurn);
			currScore = currScoreList.get(currTurnInd);
			if(currScore > maxScore){
				maxScore = currScore;
				bestScoreList = currScoreList;
			}
			board.removeToken(column);
		}	
		return bestScoreList;
	}
	
	//For 2-player option
	private int alphaBetaSearch(int alpha, int beta, int depth, Player currTurn){
		ArrayList<Integer> availableColumns = new ArrayList<>();
		Token token = new Token(currTurn);
		int score;
		int nextTurnInd = (allPlayers.indexOf(currTurn) + 1)%noOfPlayers;
		Player nextTurn = allPlayers.get(nextTurnInd);
		if(beta<=alpha){
			return currTurn==this ? Integer.MAX_VALUE: Integer.MIN_VALUE;
		}
		if(depth == this.depth || isGameOver()){
			//System.out.println(calculateScore());
			//board.printBoard();
			return calculateScore().get(aITurnInd);
		}
		for(int i = 0; i < board.getWidth(); i++){
			if(!board.isColumnFull(i))
				availableColumns.add(i);
		}		
		if(availableColumns.size()==0) 
			return 0;
		if(currTurn == this){
			score = Integer.MIN_VALUE;
			for(int column:availableColumns){
				board.placeToken(column, token);
				score = Math.max(score, alphaBetaSearch(alpha, beta, depth+1, nextTurn));
				alpha = Math.max(score, alpha);
				board.removeToken(column);
				if(score==Integer.MAX_VALUE || score == Integer.MIN_VALUE) break;
			}
		}else{
			score = Integer.MAX_VALUE;
			for(int column: availableColumns){
				board.placeToken(column, token);
				score = Math.min(score, alphaBetaSearch(alpha, beta, depth+1, nextTurn));
				beta = Math.min(score, beta);
				board.removeToken(column);
				if(score==Integer.MAX_VALUE || score == Integer.MIN_VALUE) break;
			}
		}		
		return score;
	}
	
	private Board getBoardCopy(BoardInterface currBoard){
		int width = currBoard.getWidth();
		int height = currBoard.getHeight();
		Board newBoard = new Board(width, height);
		Player tokenOwner;
		for(int column = 0; column < width; column++){
			for(int row = 0; row < height; row++){
				tokenOwner = currBoard.whoOwnsToken(column, row);
				if(tokenOwner == null) 
					break;
				newBoard.placeToken(column, new Token(tokenOwner));
			}
		}
		return newBoard;
	}
}

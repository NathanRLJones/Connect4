import java.util.ArrayList;
import java.util.List;

public class MoveGenie {

	static Board board;
	static List<Player> allPlayers;
	static int noOfPlayers;
	static int aITurnInd;
	static int tokensToWin;
	static int gameEndScore;

	public static Move getMove(BoardInterface currBoard, int maxDepth,
			List<Player> players, Player target, int toWin) {
		System.out.println("Getting move, depth of: " + maxDepth);

		board = getBoardCopy(currBoard);
		allPlayers = players;
		noOfPlayers = players.size();
		tokensToWin = toWin;
		gameEndScore = (int)Math.pow(toWin, 2)*100;
		int depth = noOfPlayers * maxDepth;
		int columns = board.getWidth();
		Token token = new Token(target);
		int maxScoreColumn = -1;
		int maxScore = Integer.MIN_VALUE;
		aITurnInd = players.indexOf(target);
		int nextTurnInd = (aITurnInd + 1) % noOfPlayers;
		Player nextPlayer = players.get(nextTurnInd);
		int newScore;

		for (int i = 0; i < columns; i++) {
			if (!board.isColumnFull(i)) {
				board.placeToken(i, token);
				if (noOfPlayers == 2)
					newScore = alphaBetaSearch(Integer.MIN_VALUE,
							Integer.MAX_VALUE, depth, nextPlayer, target);
				else
					newScore = minMaxSearch(depth, nextPlayer).get(aITurnInd);
				if (newScore > maxScore) {
					maxScore = newScore;
					maxScoreColumn = i;
				}
				board.removeToken(i);
			}
			if (i == Math.ceil(columns / 2) - 1 && board.isSymmetric())
				break;
		}
		return new Move(maxScoreColumn, token);
	}

	private static ArrayList<Integer> calculateScore() {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		for (Player p : allPlayers) {
			scores.add(0);
		}

		// 1 points for 1 aiPlayer token line with enough space for 4
		// 4 points for 2 aiPlayer token lines with enough space for 4
		// 6 points for 3 aiPlayer token lines with enough space for 4
		// 100 points for 4 aiPlayer token lines
		// negative points for above if tokens belong to another player

		int height = board.getHeight();
		int width = board.getWidth();
		int noOfTokens = 0;
		;
		Player prevTokenOwner = null;
		Player currTokenOwner = null;
		Player prevSpace = null;
		int prevEmptySpaces = 0;
		int currEmptySpaces = 0;

		// Check for scores vertically
		for (int col = 0; col < width; col++) {
			noOfTokens = 0;
			prevTokenOwner = null;
			currTokenOwner = null;
			for (int row = 0; row < height; row++) {
				if (height - row + noOfTokens < tokensToWin)
					break;
				currTokenOwner = board.whoOwnsToken(col, row);
				if (currTokenOwner == null) {
					int scoreChange = getTokensScore(noOfTokens);
					for (int i = 0; i < allPlayers.size(); i++) {
						if (prevTokenOwner == allPlayers.get(i)) {
							scores.set(i, scores.get(i) + scoreChange);
						} else {
							scores.set(i, scores.get(i) - scoreChange);
						}
					}
					break;
				}
				if (currTokenOwner == prevTokenOwner) {
					noOfTokens++;
					if(noOfTokens == tokensToWin){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
								scores.set(i, gameEndScore);
							} else {
								scores.set(i, -gameEndScore);
							}
						}
						return scores;
					}
				} else {
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}
		}

		// check for scores horizontally
		for (int row = 0; row < height; row++) {
			noOfTokens = 0;
			prevTokenOwner = null;
			currTokenOwner = null;
			prevSpace = null;
			prevEmptySpaces = 0;
			currEmptySpaces = 0;
			for (int col = 0; col < width; col++){
				if(width-col+noOfTokens+prevEmptySpaces+currEmptySpaces < tokensToWin) break;
				currTokenOwner = board.whoOwnsToken(col, row);

				if (currTokenOwner == null) {
					currEmptySpaces++;
				} else if (currTokenOwner == prevSpace) {
					noOfTokens++;
					if(noOfTokens == tokensToWin){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i))
								scores.set(i, gameEndScore);
							else
								scores.set(i, - gameEndScore);
						}
						return scores;
					}
				}else{
					if(currEmptySpaces+prevEmptySpaces+noOfTokens >= tokensToWin){
						int scoreChange = getTokensScore(noOfTokens);
						if(currEmptySpaces + noOfTokens >= tokensToWin 
								&& prevEmptySpaces + noOfTokens >= tokensToWin )
							scoreChange*=2;
						for (int i = 0; i < allPlayers.size(); i++) {
							if (prevTokenOwner == allPlayers.get(i)) {
								scores.set(i, scores.get(i) + scoreChange);
							} else {
								scores.set(i, scores.get(i) - scoreChange);
							}
						}
					}
					prevEmptySpaces = prevSpace == null ? currEmptySpaces : 0;
					currEmptySpaces = 0;
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
				prevSpace = currTokenOwner;
				if(col==width-1 && currEmptySpaces+prevEmptySpaces+noOfTokens >= tokensToWin){
					int scoreChange = getTokensScore(noOfTokens);
					if(currEmptySpaces + noOfTokens >= tokensToWin 
							&& prevEmptySpaces + noOfTokens >= tokensToWin )
						scoreChange*=2;
					for (int i = 0; i < allPlayers.size(); i++) {
						if (prevTokenOwner == allPlayers.get(i)) {
							scores.set(i, scores.get(i) + scoreChange);
						} else {
							scores.set(i, scores.get(i) - scoreChange);
						}
					}
				}
			}

		}

	
		// diagonal upward
		
	
		for (int col = 0; col <= width - tokensToWin; col++) {
			int maxRow = 0;
			if(col == 0) { 
				maxRow = height-tokensToWin-1;
			}
			for(int row = 0; row <= maxRow; row++){
				currEmptySpaces = 0;
				prevEmptySpaces = 0;
				prevSpace = null;
				noOfTokens = 0;
				int maxOffset = Math.min(height - row, width - col);
				for (int offset = 0; offset < maxOffset; offset++) {
					currTokenOwner = board.whoOwnsToken(col + offset, row + offset);
					if (currTokenOwner == null) {
						currEmptySpaces++;
					} else if (currTokenOwner == prevSpace) {
						noOfTokens++;
						if (noOfTokens == tokensToWin) {
							for (int i = 0; i < allPlayers.size(); i++) {
								if (prevTokenOwner == allPlayers.get(i))
									scores.set(i, gameEndScore);
								else
									scores.set(i,- gameEndScore);
							}
							return scores;
						}
					} else {
						if (currEmptySpaces + prevEmptySpaces + noOfTokens >= tokensToWin) {
							int scoreChange = getTokensScore(noOfTokens);
							if(currEmptySpaces + noOfTokens >= tokensToWin 
									&& prevEmptySpaces + noOfTokens >= tokensToWin )
								scoreChange*=2;
							for (int i = 0; i < allPlayers.size(); i++) {
								if (prevTokenOwner == allPlayers.get(i)) {
									scores.set(i, scores.get(i) + scoreChange);
								} else {
									scores.set(i, scores.get(i) - scoreChange);
								}
							}
						}
						prevEmptySpaces = prevSpace == null ? currEmptySpaces
								: 0;
						currEmptySpaces = 0;
						prevTokenOwner = currTokenOwner;
						noOfTokens = 1;
					}
					prevSpace = currTokenOwner;
					if (offset == maxOffset-1
							&& currEmptySpaces + prevEmptySpaces + noOfTokens >= tokensToWin) {
						int scoreChange = getTokensScore(noOfTokens);
						if(currEmptySpaces + noOfTokens >= tokensToWin 
								&& prevEmptySpaces + noOfTokens >= tokensToWin )
							scoreChange*=2;
						for (int i = 0; i < allPlayers.size(); i++) {
							if (prevTokenOwner == allPlayers.get(i)) {
								scores.set(i, scores.get(i) + scoreChange);
							} else {
								scores.set(i, scores.get(i) - scoreChange);
							}
						}
					}
				}

			}
		}
	
		//diagonal downwards
		for (int col = 0; col <= width - tokensToWin ; col++) {
			int minRow = height-1;
			if(col == 0) { 
				minRow = tokensToWin-1;
			}
			for(int row = height-1; row >= minRow; row--){
				currEmptySpaces = 0;
				prevEmptySpaces = 0;
				prevSpace = null;
				noOfTokens = 0;
				int maxOffset = Math.min(row+1, width - col);
				for (int offset = 0; offset < maxOffset; offset++) {
					currTokenOwner = board.whoOwnsToken(col + offset, row - offset);
					if (currTokenOwner == null) {
						currEmptySpaces++;
					} else if (currTokenOwner == prevSpace) {
						noOfTokens++;
						if (noOfTokens == tokensToWin) {
							for (int i = 0; i < allPlayers.size(); i++) {
								if (prevTokenOwner == allPlayers.get(i))
									scores.set(i, scores.get(i) + gameEndScore);
								else
									scores.set(i, scores.get(i) - gameEndScore);
							}
							return scores;
						}
					} else {
						if (currEmptySpaces + prevEmptySpaces + noOfTokens >= tokensToWin) {
							int scoreChange = getTokensScore(noOfTokens);
							if(currEmptySpaces + noOfTokens >= tokensToWin 
									&& prevEmptySpaces + noOfTokens >= tokensToWin )
								scoreChange*=2;
							for (int i = 0; i < allPlayers.size(); i++) {
								if (prevTokenOwner == allPlayers.get(i)) {
									scores.set(i, scores.get(i) + scoreChange);
								} else {
									scores.set(i, scores.get(i) - scoreChange);
								}
							}
						}
						prevEmptySpaces = prevSpace == null ? currEmptySpaces
								: 0;
						currEmptySpaces = 0;
						prevTokenOwner = currTokenOwner;
						noOfTokens = 1;
					}
					prevSpace = currTokenOwner;
					if (offset == maxOffset-1
							&& currEmptySpaces + prevEmptySpaces + noOfTokens >= tokensToWin) {
						int scoreChange = getTokensScore(noOfTokens);
						if(currEmptySpaces + noOfTokens >= tokensToWin 
								&& prevEmptySpaces + noOfTokens >= tokensToWin )
							scoreChange*=2;
						for (int i = 0; i < allPlayers.size(); i++) {
							if (prevTokenOwner == allPlayers.get(i)) {
								scores.set(i, scores.get(i) + scoreChange);
							} else {
								scores.set(i, scores.get(i) - scoreChange);
							}
						}
					}
				}

			}
		}
		
		return scores;
	}

	private static ArrayList<Integer> minMaxSearch(int depth, Player currTurn) {

		ArrayList<Integer> availableColumns = new ArrayList<>();
		Token token = new Token(currTurn);
		int currTurnInd = allPlayers.indexOf(currTurn);
		int nextTurnInd = (currTurnInd + 1) % noOfPlayers;
		Player nextTurn = allPlayers.get(nextTurnInd);
		int maxScore = Integer.MIN_VALUE;
		ArrayList<Integer> bestScoreList = null;
		ArrayList<Integer> currScoreList;
		int currScore;
		int boardWidth = board.getWidth();

		ArrayList<Integer> scores = calculateScore();
		if(depth == 0) 
			return scores;
		if(scores.get(currTurnInd) == gameEndScore){
			int newEndScore = gameEndScore+depth;
			for(int i = 0; i < scores.size(); i++) {
				if(i!=currTurnInd) 
					scores.set(i, -newEndScore);
				else 
					scores.set(i, newEndScore);
			}
			return scores;		
		}
		if(scores.get(currTurnInd) == -gameEndScore){
			int newEndScore = gameEndScore+depth;
			for(int i = 0; i < scores.size(); i++) {
				if(i!=currTurnInd) 
					scores.set(i, newEndScore);
				else 
					scores.set(i, -newEndScore);
			}
			return scores;		
		
		}
		
		for (int i = 0; i < board.getWidth(); i++) {
			if (!board.isColumnFull(i))
				availableColumns.add(i);
		}
		if (availableColumns.size() == 0) {
			bestScoreList = new ArrayList<>();
			for (int i = 0; i < allPlayers.size(); i++)
				bestScoreList.add(0);
			return bestScoreList;
		}
		for (int column : availableColumns) {
			board.placeToken(column, token);
			currScoreList = minMaxSearch(depth - 1, nextTurn);
			currScore = currScoreList.get(currTurnInd);
			if (currScore > maxScore) {
				maxScore = currScore;
				bestScoreList = currScoreList;
			}
			board.removeToken(column);
			if (column == Math.ceil(boardWidth / 2) - 1 && board.isSymmetric())
				break;
		}
		return bestScoreList;
	}

	// For 2-player option
	private static int alphaBetaSearch(int alpha, int beta, int depth,
			Player currTurn, Player target) {

		ArrayList<Integer> availableColumns = new ArrayList<>();
		Token token = new Token(currTurn);
		int score;
		int nextTurnInd = (allPlayers.indexOf(currTurn) + 1) % noOfPlayers;
		Player nextTurn = allPlayers.get(nextTurnInd);
		if (beta <= alpha) {
			return currTurn == target ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		}
		ArrayList<Integer> scores = calculateScore();
		if(depth == 0) 
			return scores.get(aITurnInd);
		if(scores.get(aITurnInd) == gameEndScore){
			int newEndScore = gameEndScore+depth;
			for(int i = 0; i < scores.size(); i++) {
				if(i!=aITurnInd) 
					scores.set(i, -newEndScore);
				else 
					scores.set(i, newEndScore);
			}
			return scores.get(aITurnInd);		
		}
		if(scores.get(aITurnInd) == -gameEndScore){
			int newEndScore = gameEndScore+depth;
			for(int i = 0; i < scores.size(); i++) {
				if(i!=aITurnInd) 
					scores.set(i, newEndScore);
				else 
					scores.set(i, -newEndScore);
			}
			return scores.get(aITurnInd);		
		
		}
		for (int i = 0; i < board.getWidth(); i++) {
			if (!board.isColumnFull(i))
				availableColumns.add(i);
		}
		if (availableColumns.size() == 0)
			return 0;
		if (currTurn == target) {
			score = Integer.MIN_VALUE;
			for (int column : availableColumns) {
				board.placeToken(column, token);
				score = Math.max(
						score,
						alphaBetaSearch(alpha, beta, depth - 1, nextTurn,
								target));
				alpha = Math.max(score, alpha);
				board.removeToken(column);
				if (score == Integer.MAX_VALUE || score == Integer.MIN_VALUE)
					break;
			}
		} else {
			score = Integer.MAX_VALUE;
			for (int column : availableColumns) {
				board.placeToken(column, token);
				score = Math.min(
						score,
						alphaBetaSearch(alpha, beta, depth - 1, nextTurn,
								target));
				beta = Math.min(score, beta);
				board.removeToken(column);
				if (score == Integer.MAX_VALUE || score == Integer.MIN_VALUE)
					break;
			}
		}
		return score;
	}

	private static int getTokensScore(int noOfTokens){
		return (int)Math.pow(noOfTokens, 2);
	}
	
	private static Board getBoardCopy(BoardInterface currBoard) {
		int width = currBoard.getWidth();
		int height = currBoard.getHeight();
		Board newBoard = new Board(width, height);
		Player tokenOwner;
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				tokenOwner = currBoard.whoOwnsToken(column, row);
				if (tokenOwner == null)
					break;
				newBoard.placeToken(column, new Token(tokenOwner));
			}
		}
		return newBoard;
	}
}

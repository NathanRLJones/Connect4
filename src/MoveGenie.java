import java.util.ArrayList;
import java.util.List;

public class MoveGenie {

	static Board board;
	static List<Player> allPlayers;
	static int noOfPlayers;
	static int aITurnInd;
	static int tokensToWin;

	public static Move getMove(BoardInterface currBoard, int maxDepth,
			List<Player> players, Player target, int toWin) {
		System.out.println("Getting move, depth of: " + maxDepth);

		board = getBoardCopy(currBoard);
		allPlayers = players;
		noOfPlayers = players.size();
		tokensToWin = toWin;
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
					int scoreChange = 0;
					switch (noOfTokens) {
					case 1:
						scoreChange = 1;
						break;
					case 2:
						scoreChange = 4;
						break;
					case 3:
						scoreChange = 6;
						break;
					default:
						break;
					}
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
								scores.set(i, scores.get(i) + 100);
							} else {
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
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
								scores.set(i, scores.get(i) + 100);
							else
								scores.set(i, scores.get(i) - 100);
						}
						noOfTokens = 0;
						prevEmptySpaces = 0;
						prevTokenOwner = null;
					}
				}else{
					if(currEmptySpaces+prevEmptySpaces+noOfTokens >= tokensToWin){
						int scoreChange = 0;
						switch (noOfTokens) {
						case 1:
							scoreChange = 1;
							break;
						case 2:
							scoreChange = 4;
							break;
						case 3:
							scoreChange = 6;
							break;
						default:
							break;
						}
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
					int scoreChange = 0;
					switch (noOfTokens) {
					case 1:
						scoreChange = 1;
						break;
					case 2:
						scoreChange = 4;
						break;
					case 3:
						scoreChange = 6;
						break;
					default:
						break;
					}
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
									scores.set(i, scores.get(i) + 100);
								else
									scores.set(i, scores.get(i) - 100);
							}
							noOfTokens = 0;
							prevEmptySpaces = 0;
							prevTokenOwner = null;
						}
					} else {
						if (currEmptySpaces + prevEmptySpaces + noOfTokens >= tokensToWin) {
							int scoreChange = 0;
							switch (noOfTokens) {
							case 1:
								scoreChange = 1;
								break;
							case 2:
								scoreChange = 4;
								break;
							case 3:
								scoreChange = 6;
								break;
							default:
								break;
							}
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
						int scoreChange = 0;
						switch (noOfTokens) {
						case 1:
							scoreChange = 1;
							break;
						case 2:
							scoreChange = 4;
							break;
						case 3:
							scoreChange = 6;
							break;
						default:
							break;
						}
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
									scores.set(i, scores.get(i) + 100);
								else
									scores.set(i, scores.get(i) - 100);
							}
							noOfTokens = 0;
							prevEmptySpaces = 0;
							prevTokenOwner = null;
						}
					} else {
						if (currEmptySpaces + prevEmptySpaces + noOfTokens >= tokensToWin) {
							int scoreChange = 0;
							switch (noOfTokens) {
							case 1:
								scoreChange = 1;
								break;
							case 2:
								scoreChange = 4;
								break;
							case 3:
								scoreChange = 6;
								break;
							default:
								break;
							}
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
						int scoreChange = 0;
						switch (noOfTokens) {
						case 1:
							scoreChange = 1;
							break;
						case 2:
							scoreChange = 4;
							break;
						case 3:
							scoreChange = 6;
							break;
						default:
							break;
						}
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
		
	
/*
		// check for scores NW/SE diagonally
		int startCol = 0;
		int col = startCol;
		int startRow = tokensToWin - 1;
		int row = startRow;
		noOfTokens = 0;
<<<<<<< HEAD
		
		while(height + width - startCol - startRow - 2 < tokensToWin){
			
			//do scoring if we are in a workable positon
			if ((Math.min(row, (width - 1) - col) + 1) + noOfTokens >= tokensToWin){
=======
		prevEmptySpaces = 0;
		currEmptySpaces = 0;

		while (height + width - startCol - startRow - 2 < tokensToWin) {

			// do scoring if we are in a workable positon
			if ((Math.min(row, (width - 1) - col) + 1) + noOfTokens >= tokensToWin) {
>>>>>>> ab14ca16ebc7b7c4f5acbb96c614f3eefaef58fd
				currTokenOwner = board.whoOwnsToken(col, row);
				if (currTokenOwner == null) {
					int scoreChange = 0;
					switch (noOfTokens) {
					case 1:
						scoreChange = 1;
						break;
					case 2:
						scoreChange = 4;
						break;
					case 3:
						scoreChange = 6;
						break;
					default:
						break;
					}
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
<<<<<<< HEAD
					if(noOfTokens == tokensToWin){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
=======
					if (noOfTokens == tokensToWin) {
						for (int i = 0; i < allPlayers.size(); i++) {
							if (prevTokenOwner == allPlayers.get(i)) {
>>>>>>> ab14ca16ebc7b7c4f5acbb96c614f3eefaef58fd
								scores.set(i, scores.get(i) + 100);
							} else {
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
					}
				} else {
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}

			// move to next space
			if (row > 0) {
				// continue down the current diagonal
				col++;
				row--;
			} else {
				// go to the next diagonal
				if (startRow + 1 == height) {
					row = startRow;
					startCol++;
					col = startCol;
				} else {
					startRow++;
					row = startRow;
					col = startCol;
				}

				// reset counters
				noOfTokens = 0;
				prevTokenOwner = null;
				currTokenOwner = null;
			}
		}

		// check for scores NE/SW diagonally
		startCol = 0;
		col = startCol;
		startRow = (height - 1) - (tokensToWin - 1);
		row = startRow;
		noOfTokens = 0;
<<<<<<< HEAD
		
		while(!(row == 0 && col + tokensToWin == width)){
			
			//do scoring if we are in a workable positon
			if ((Math.min((height - 1) -row, (width - 1) - col) + 1) + noOfTokens >= tokensToWin){
=======

		while (!(row == 0 && col + tokensToWin == width)) {

			// do scoring if we are in a workable positon
			if ((Math.min((height - 1) - row, (width - 1) - col) + 1)
					+ noOfTokens >= tokensToWin) {
>>>>>>> ab14ca16ebc7b7c4f5acbb96c614f3eefaef58fd
				currTokenOwner = board.whoOwnsToken(col, row);
				if (currTokenOwner == null) {
					int scoreChange = 0;
					switch (noOfTokens) {
					case 1:
						scoreChange = 1;
						break;
					case 2:
						scoreChange = 4;
						break;
					case 3:
						scoreChange = 6;
						break;
					default:
						break;
					}
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
<<<<<<< HEAD
					if(noOfTokens == tokensToWin){
						for(int i = 0; i < allPlayers.size(); i++){	
							if(prevTokenOwner == allPlayers.get(i)){
=======
					if (noOfTokens == tokensToWin) {
						for (int i = 0; i < allPlayers.size(); i++) {
							if (prevTokenOwner == allPlayers.get(i)) {
>>>>>>> ab14ca16ebc7b7c4f5acbb96c614f3eefaef58fd
								scores.set(i, scores.get(i) + 100);
							} else {
								scores.set(i, scores.get(i) - 100);
							}
						}
						break;
					}
				} else {
					prevTokenOwner = currTokenOwner;
					noOfTokens = 1;
				}
			}

			// move to next space
			if (row < height - 1) {
				// continue down the current diagonal
				col++;
				row++;
			} else {
				// go to the next diagonal
				if (startRow == 0) {
					row = startRow;
					startCol++;
					col = startCol;
				} else {
					startRow--;
					row = startRow;
					col = startCol;
				}

				// reset counters
				noOfTokens = 0;
				prevTokenOwner = null;
				currTokenOwner = null;
			}
		}
*/
		return scores;
	}

	private static boolean isGameOver() {

		int height = board.getHeight();
		int width = board.getWidth();
		Player possibleWinner;
		int noOfTokens;
		Player currPlayer;
		Token currToken;
		
		// Check for a vertical line of tokensToWin same-colour tokens

		for(int col = 0; col < width; col++){
		 	noOfTokens = 0;
		 	possibleWinner = null;
		 	for(int row = 0; row < height; row++){
		 		if(height-row+noOfTokens < tokensToWin) break;
				
		 		currToken = board.getToken(col, row);
		 		if(currToken == null) break;
		 		currPlayer = currToken.getOwner();
		 		if(currPlayer == possibleWinner){
		 			noOfTokens++;
		 			if(noOfTokens == tokensToWin) {
		 				return true;
		 			}
		 		}else{
		 			possibleWinner = currPlayer;
		 			noOfTokens = 1;
		 		}
		 	}
		 }

		 //Check for a horizontal line of tokensToWin same-colour tokens
		 for(int row = 0; row < height; row++){
		 	noOfTokens = 0;
		 	possibleWinner = null;
		 	for(int col = 0; col < width; col++){
		 		if(width-col+noOfTokens < tokensToWin) break;
				
				currToken = board.getToken(col, row);
		 		if (currToken == null){
		 			possibleWinner = null;
		 			noOfTokens = 0;
		 			continue;
		 		}
		 		currPlayer = currToken.getOwner();
		 		if(currPlayer == possibleWinner){
		 			noOfTokens++;
		 			if(noOfTokens == tokensToWin) {
		 				return true;
		 			}
		 		}else{
		 			possibleWinner = currPlayer;
		 			noOfTokens = 1;
		 		}
		 	}
		 }
	
		 //Check for a diagonal line of tokensToWin same-colour tokens
		 for(int col = 0; col < width - tokensToWin+1; col++) {
		 	for(int row = 0; row < height; row++) {
		 		// Check diagonally upwards
		 		possibleWinner = null;
		 		noOfTokens = 0;
		 		if(row < height - tokensToWin+1) {
		 			for(int offset = 0; offset < tokensToWin; offset++) {
		 				currToken = board.getToken(col+offset, row+offset);
		 				if(currToken == null) break;
		 				currPlayer = currToken.getOwner();
		 				if(offset == 0) possibleWinner = currPlayer;
		 				if(currPlayer == possibleWinner){
		 					noOfTokens++;
		 					if(noOfTokens == tokensToWin) {
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
		 		if(row > tokensToWin-2) {
		 			for(int offset = 0; offset < tokensToWin; offset++) {
		 				currToken = board.getToken(col+offset, row-offset);
		 				if(currToken == null) break;
		 				currPlayer = currToken.getOwner();
		 				if(offset == 0) possibleWinner = currPlayer;
		 				if(currPlayer == possibleWinner){
		 					noOfTokens++;
		 					if(noOfTokens == tokensToWin) {
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

		if (depth == 0 || isGameOver()) {
			ArrayList<Integer> scores = calculateScore();
			/*
			if (isGameOver()) {
				for (int i = 0; i < scores.size(); i++) {
					System.out.println(allPlayers.get(i).getName() + " = "
							+ scores.get(i));
				}
				board.printBoard();
				System.out.println("\n\n");
			}*/
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
		if (depth == 0 || isGameOver()) {
			/*
			ArrayList<Integer> scores = calculateScore();
			
			if (isGameOver()) {
				for (int i = 0; i < scores.size(); i++) {
					System.out.println(allPlayers.get(i).getName() + " = "
							+ scores.get(i));
				}
				board.printBoard();
				System.out.println("\n\n");
			}*/
			return calculateScore().get(aITurnInd);
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

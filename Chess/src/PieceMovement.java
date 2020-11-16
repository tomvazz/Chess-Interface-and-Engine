
public class PieceMovement {
	

	public int[][] pawn(String[][] piecetxt, String playerTurn, int indexX, int indexY) {
		
		int [][] pawnPossibilities = new int[8][8];
		
		if (playerTurn == "white") {
			// move up two on first move, otherwise 1
			if (indexY == 1 && piecetxt[indexX][indexY+1].equals(". ") && piecetxt[indexX][indexY+2].equals(". ")) {
				pawnPossibilities[indexX][indexY+1] = 1;
				pawnPossibilities[indexX][indexY+2] = 1;
			} else {
				if (indexY != 7 && piecetxt[indexX][indexY+1].equals(". ")) {
					pawnPossibilities[indexX][indexY+1] = 1;
				}
			}
			// attack diagonal, capture piece
			if (indexX != 7 && indexY != 7) {
				if (piecetxt[indexX+1][indexY+1].substring(1).equals("b")) {
					pawnPossibilities[indexX+1][indexY+1] = 1;
				}
			}
			if (indexX != 0 && indexY != 7) {
				if (piecetxt[indexX-1][indexY+1].substring(1).equals("b")) {
					pawnPossibilities[indexX-1][indexY+1] = 1;
				}
			}
		} else if (playerTurn == "black") {
			// move up two on first move, otherwise 1
			if (indexY == 6 && piecetxt[indexX][indexY-1].equals(". ") && piecetxt[indexX][indexY-2].equals(". ")) {
				pawnPossibilities[indexX][indexY-1] = 1;
				pawnPossibilities[indexX][indexY-2] = 1;
			} else {
				if (indexY != 0 && piecetxt[indexX][indexY-1].equals(". ")) {
					pawnPossibilities[indexX][indexY-1] = 1;
				}
			}
			// attack diagonal, capture piece
			if (indexX != 0 && indexY != 0) {
				if (piecetxt[indexX-1][indexY-1].substring(1).equals("w")) {
					pawnPossibilities[indexX-1][indexY-1] = 1;
				}
			}
			if (indexX != 7 && indexY != 0) {
				if (piecetxt[indexX+1][indexY-1].substring(1).equals("w")) {
					pawnPossibilities[indexX+1][indexY-1] = 1;
				}
			}
		}
		
		return pawnPossibilities;
	}
	
	public int[][] bishop(String[][] piecetxt, String playerTurn, int indexX, int indexY) {
			
		int [][] bishopPossibilities = new int[8][8];
		
		String opponentColor = "w";
		if (playerTurn == "white") {
			opponentColor = "b";
		} else if (playerTurn == "black") {
			opponentColor = "w";
		}
			
		
		// scans northeast
		int a = indexX;
		for (int b = indexY; b < 7; b++) {
			if (a != 7) {
				if (piecetxt[a+1][b+1].equals(". ") || piecetxt[a+1][b+1].substring(1).equals(opponentColor)) {
					bishopPossibilities[a+1][b+1] = 1;
					if (piecetxt[a+1][b+1].substring(1).equals(opponentColor)) {
						break;
					}
					a++;
					} else {
						break;
					}
			}
		}
		// scans northwest
		a = indexX;
		for (int b = indexY; b < 7; b++) {
			if (a != 0) {
				if (piecetxt[a-1][b+1].equals(". ") || piecetxt[a-1][b+1].substring(1).equals(opponentColor)) {
					bishopPossibilities[a-1][b+1] = 1;
					if (piecetxt[a-1][b+1].substring(1).equals(opponentColor)) {
						break;
					}
					a--;
					} else {
						break;
					}
			}
		}
		// scans southeast
		a = indexX;
		for (int b = indexY; b > 0; b--) {
			if (a != 7) {
				if (piecetxt[a+1][b-1].equals(". ") || piecetxt[a+1][b-1].substring(1).equals(opponentColor)) {
					bishopPossibilities[a+1][b-1] = 1;
					if (piecetxt[a+1][b-1].substring(1).equals(opponentColor)) {
						break;
					}
					a++;
					} else {
						break;
					}
			}
		}
		//scans southwest
		a = indexX;
		for (int b = indexY; b > 0; b--) {
			if (a != 0) {
				if (piecetxt[a-1][b-1].equals(". ") || piecetxt[a-1][b-1].substring(1).equals(opponentColor)) {
					bishopPossibilities[a-1][b-1] = 1;
					if (piecetxt[a-1][b-1].substring(1).equals(opponentColor)) {
						break;
					}
					a--;
					} else {
						break;
					}
			}
		}
		
			
		return bishopPossibilities;
	}
	
	public int[][] rook(String[][] piecetxt, String playerTurn, int indexX, int indexY) {
		
		int [][] rookPossibilities = new int[8][8];
		
		String opponentColor = "w";
		if (playerTurn == "white") {
			opponentColor = "b";
		} else if (playerTurn == "black") {
			opponentColor = "w";
		}
			
		// scans north/south
		for (int b = indexY; b < 7; b++) {
			if (piecetxt[indexX][b+1].equals(". ") || piecetxt[indexX][b+1].substring(1).equals(opponentColor)) {
				rookPossibilities[indexX][b+1] = 1;
				if (piecetxt[indexX][b+1].substring(1).equals(opponentColor)) {
					break;
				}
			} else {
				break;
			}
		}
		for (int b = indexY; b > 0; b--) {
			if (piecetxt[indexX][b-1].equals(". ") || piecetxt[indexX][b-1].substring(1).equals(opponentColor)) {
				rookPossibilities[indexX][b-1] = 1;
				if (piecetxt[indexX][b-1].substring(1).equals(opponentColor)) {
					break;
				}
			} else {
				break;
			}
		}
		// scans east/west
		for (int a = indexX; a < 7; a++) {
			if (piecetxt[a+1][indexY].equals(". ") || piecetxt[a+1][indexY].substring(1).equals(opponentColor)) {
				rookPossibilities[a+1][indexY] = 1;
				if (piecetxt[a+1][indexY].substring(1).equals(opponentColor)) {
					break;
				}
			} else {
				break;
			}
		}
		for (int a = indexX; a > 0; a--) {
			if (piecetxt[a-1][indexY].equals(". ") || piecetxt[a-1][indexY].substring(1).equals(opponentColor)) {
				rookPossibilities[a-1][indexY] = 1;
				if (piecetxt[a-1][indexY].substring(1).equals(opponentColor)) {
					break;
				}
			} else {
				break;
			}
		}
		
			
		return rookPossibilities;
	}
	
	public int[][] knight(String[][] piecetxt, String playerTurn, int indexX, int indexY) {
		
		int [][] nightPossibilities = new int[8][8];
		
		String opponentColor = "w";
		if (playerTurn == "white") {
			opponentColor = "b";
		} else if (playerTurn == "black") {
			opponentColor = "w";
		}
		
		if (indexX < 6 && indexY != 7) {
			if (piecetxt[indexX+2][indexY+1].equals(". ") || piecetxt[indexX+2][indexY+1].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX+2][indexY+1] = 1;
			}
		}
		if (indexX != 7 && indexY < 6) {
			if (piecetxt[indexX+1][indexY+2].equals(". ") || piecetxt[indexX+1][indexY+2].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX+1][indexY+2] = 1;
			}
		}
		if (indexX != 0 && indexY < 6) {
			if (piecetxt[indexX-1][indexY+2].equals(". ") || piecetxt[indexX-1][indexY+2].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX-1][indexY+2] = 1;
			}
		}
		if (indexX > 1 && indexY != 7) {
			if (piecetxt[indexX-2][indexY+1].equals(". ") || piecetxt[indexX-2][indexY+1].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX-2][indexY+1] = 1;
			}
		}
		if (indexX < 6 && indexY != 0) {
			if (piecetxt[indexX+2][indexY-1].equals(". ") || piecetxt[indexX+2][indexY-1].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX+2][indexY-1] = 1;
			}
		}
		if (indexX != 7 && indexY > 1) {
			if (piecetxt[indexX+1][indexY-2].equals(". ") || piecetxt[indexX+1][indexY-2].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX+1][indexY-2] = 1;
			}
		}
		if (indexX != 0 && indexY > 1) {
			if (piecetxt[indexX-1][indexY-2].equals(". ") || piecetxt[indexX-1][indexY-2].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX-1][indexY-2] = 1;
			}
		}
		if (indexX > 1 && indexY != 0) {
			if (piecetxt[indexX-2][indexY-1].equals(". ") || piecetxt[indexX-2][indexY-1].substring(1).equals(opponentColor)) {
				nightPossibilities[indexX-2][indexY-1] = 1;
			}
		}
		
			
		return nightPossibilities;
	}
	
	public int[][] queen(String[][] piecetxt, String playerTurn, int indexX, int indexY){
		
		int [][] bishopPossibilities = new int[8][8];
		int [][] rookPossibilities = new int[8][8];
		int [][] queenPossibilities = new int[8][8];
		
		bishopPossibilities = bishop(piecetxt, playerTurn, indexX, indexY);
		rookPossibilities = rook(piecetxt, playerTurn, indexX, indexY);
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (bishopPossibilities[a][b] == 1 || rookPossibilities[a][b] == 1) {
					queenPossibilities[a][b] = 1;
				} else {
					queenPossibilities[a][b] = 0;
				}
			}
		}
		
		return queenPossibilities;
	}
	
	public int[][] king(String[][] piecetxt, String playerTurn, int indexX, int indexY, int[] validCastle){
		
		int [][] kingPossibilities = new int[8][8];
		
		String opponentColor = "w";
		if (playerTurn == "white") {
			opponentColor = "b";
		} else if (playerTurn == "black") {
			opponentColor = "w";
		}
		
		// scans north
		if (indexY != 7) {
			if (piecetxt[indexX][indexY+1].equals(". ") || piecetxt[indexX][indexY+1].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX][indexY+1] = 1;
			}
		}
		// scans northeast
		if (indexX != 7 && indexY != 7) {
			if (piecetxt[indexX+1][indexY+1].equals(". ") || piecetxt[indexX+1][indexY+1].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX+1][indexY+1] = 1;
			}
		}
		// scans northwest
		if (indexX != 0 && indexY != 7) {
			if (piecetxt[indexX-1][indexY+1].equals(". ") || piecetxt[indexX-1][indexY+1].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX-1][indexY+1] = 1;
			}
		}
		// scans east
		if (indexX != 7) {
			if (piecetxt[indexX+1][indexY].equals(". ") || piecetxt[indexX+1][indexY].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX+1][indexY] = 1;
			}
		}
		// scans west
		if (indexX != 0) {
			if (piecetxt[indexX-1][indexY].equals(". ") || piecetxt[indexX-1][indexY].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX-1][indexY] = 1;
			}
		}
		// scans southeast
		if (indexX != 7 && indexY != 0) {
			if (piecetxt[indexX+1][indexY-1].equals(". ") || piecetxt[indexX+1][indexY-1].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX+1][indexY-1] = 1;
			}
		}
		// scans south
		if (indexY != 0) {
			if (piecetxt[indexX][indexY-1].equals(". ") || piecetxt[indexX][indexY-1].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX][indexY-1] = 1;
			}
		}
		// scans southwest
		if (indexX != 0 && indexY != 0) {
			if (piecetxt[indexX-1][indexY-1].equals(". ") || piecetxt[indexX-1][indexY-1].substring(1).equals(opponentColor)) {
				kingPossibilities[indexX-1][indexY-1] = 1;
			}
		}
		
		// castle checker
		// valid castle, 1 for true, 0 for false
		// validcastle[0] white right castle, validcastle[1] white left castle, validcastle[2] white right castle, validcastle[3] white left castle
		if (playerTurn == "white") {
			if (validCastle[0] == 1 && piecetxt[5][0] == ". " && piecetxt[6][0] == ". ") {
				kingPossibilities[6][0] = 1;
			}
			if (validCastle[1] == 1 && piecetxt[3][0] == ". " && piecetxt[2][0] == ". " && piecetxt[1][0] == ". ") {
				kingPossibilities[2][0] = 1;
			}
		}
		if (playerTurn == "black") {
			if (validCastle[2] == 1 && piecetxt[5][7] == ". " && piecetxt[6][7] == ". ") {
				kingPossibilities[6][7] = 1;
			}
			if (validCastle[3] == 1 && piecetxt[3][7] == ". " && piecetxt[2][7] == ". " && piecetxt[1][7] == ". ") {
				kingPossibilities[2][7] = 1;
			}
		}
		
		return kingPossibilities;
	}
		
}

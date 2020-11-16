import java.awt.Color;

import javax.swing.JOptionPane;

public class AiPlayer {
	
	public int pawn = 100;
	public int knight = 320;
	public int bishop = 325;
	public int rook = 500;
	public int queen = 975;
	public int king = 32767;

	public int[] maximizer(String[][] piecetxt, int[] validCastle) {
		
		int[] returnMove = new int[4]; // tempX, tempY, parentX, parentY
		
		int[][] possibleMovesCopy = new int[8][8];
		int[][] spaceScore = new int[8][8];
		int[][] totalMoveScore = new int[8][8];
		
		String[][] piecetxtCopy = new String[8][8];
		
		int max = -100000;
		
		PieceMovement p = new PieceMovement();
		Scoring s = new Scoring();
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				
				if (piecetxt[a][b].substring(1).equals("b")) {
					if (piecetxt[a][b].substring(0,1).equals("p")){
						possibleMovesCopy = p.pawn(piecetxt, "black", a, b);
						spaceScore = s.pieceSquareTables("p", "black", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("k")){
						possibleMovesCopy = p.king(piecetxt, "black", a, b, validCastle);
						spaceScore = s.pieceSquareTables("k", "black", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("q")){
						possibleMovesCopy = p.queen(piecetxt, "black", a, b);
						spaceScore = s.pieceSquareTables("q", "black", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("r")){
						possibleMovesCopy = p.rook(piecetxt, "black", a, b);
						spaceScore = s.pieceSquareTables("r", "black", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("n")){
						possibleMovesCopy = p.knight(piecetxt, "black", a, b);
						spaceScore = s.pieceSquareTables("n", "black", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("b")){
						possibleMovesCopy = p.bishop(piecetxt, "black", a, b);
						spaceScore = s.pieceSquareTables("b", "black", piecetxt);
					}
					
					for (int t = 0; t < 8; t++) {
						for (int v = 0; v < 8; v++) {
							if (possibleMovesCopy[t][v] == 1) {
								
								if (checkChecker(piecetxt, validCastle, t, v, a, b, "w", false) == "b") {
									possibleMovesCopy[t][v] = 0;
								}
								
								// gives extra points if this move will cause a check on opponent
								// if move will cause checkmate, 10000 points will be added to ensure move is made
								if (checkChecker(piecetxt, validCastle, t, v, a, b, "b", false) == "w") {
									spaceScore[t][v] = spaceScore[t][v] + 10;
									
									// gives a copy of piecetxt to checkmate checker to see if move made will be a checkmate
									for (int g = 0; g < 8; g++) {
										for (int h = 0; h < 8; h++) {
											piecetxtCopy[g][h] = piecetxt[g][h];
										}
									}
									piecetxtCopy[t][v] = piecetxtCopy[a][b];
									piecetxtCopy[a][b] = ". ";
									if (checkmateChecker(piecetxtCopy, "w", validCastle) == "w"){
										spaceScore[t][v] = 10000;
									}
								}
								
							}
						}
					}
					
					// cant castle through check or in check
					if (a == 4 && b == 7 && piecetxt[4][7] == "kb" && possibleMovesCopy[6][7] == 1) {
						if (checkChecker(piecetxt, validCastle, 0, 0, 0, 0, "w", true) == "b") {
							possibleMovesCopy[6][7] = 0;
						}
						if (checkChecker(piecetxt, validCastle, 5, 7, a, b, "w", false) == "b") {
							possibleMovesCopy[6][7] = 0;
						}
					} else if (a == 4 && b == 7 && piecetxt[4][7] == "kb" && possibleMovesCopy[2][7] == 1) {
						if (checkChecker(piecetxt, validCastle, 0, 0, 0, 0, "w", true) == "b") {
							possibleMovesCopy[2][7] = 0;
						}
						if (checkChecker(piecetxt, validCastle, 3, 7, a, b, "w", false) == "b") {
							possibleMovesCopy[2][7] = 0;
						}
					}
					
					
					totalMoveScore = moveScore(piecetxt, "b", possibleMovesCopy, a, b);
					for (int i = 0; i < 8; i++) {
						for (int e = 0; e < 8; e++) {
							if (possibleMovesCopy[i][e] == 1) {
								
								//totalMoveScore[i][e] = totalMoveScore[i][e] + spaceScore[i][e];
								
								for (int g = 0; g < 8; g++) {
									for (int h = 0; h < 8; h++) {
										piecetxtCopy[g][h] = piecetxt[g][h];
									}
								}
								piecetxtCopy[i][e] = piecetxtCopy[a][b];
								piecetxtCopy[a][b] = ". ";
								
								totalMoveScore[i][e] = minimizer(piecetxtCopy, validCastle) + totalMoveScore[i][e] + spaceScore[i][e];
								System.out.println(piecetxt[a][b] + " to " + i + "," + b + " (score): " + totalMoveScore[i][e]);
								if (totalMoveScore[i][e] > max) {
									max = totalMoveScore[i][e];
									
									returnMove[0] = a;
									returnMove[1] = b;
									returnMove[2] = i;
									returnMove[3] = e;
									
								}
								
							}
						}
					}
					
				}
				
				
			}
		}
		
		// check for stalemate
		if (max == -100000 && checkChecker(piecetxt, validCastle, 0, 0, 0, 0, "w", true) == "") {
			JOptionPane.showMessageDialog(null, "STALEMATE");
		}
		
		System.out.println("AI MOVE MADE -> " + piecetxt[returnMove[0]][returnMove[1]] + " to " + returnMove[2] + ", " + returnMove[3]);
		System.out.println("MOVE SCORE: " + max);
		return returnMove;
	}
	
	public int minimizer(String[][] piecetxt, int[] validCastle) {
		
		int[][] possibleMovesCopy = new int[8][8];
		int[][] spaceScore = new int[8][8];
		int[][] totalMoveScore = new int[8][8];
		
		String[][] piecetxtCopy = new String[8][8];
		
		int min = 100000;
		
		PieceMovement p = new PieceMovement();
		Scoring s = new Scoring();
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
	
				if (piecetxt[a][b].substring(1).equals("w")) {
					if (piecetxt[a][b].substring(0,1).equals("p")){
						possibleMovesCopy = p.pawn(piecetxt, "white", a, b);
						spaceScore = s.pieceSquareTables("p", "white", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("k")){
						possibleMovesCopy = p.king(piecetxt, "white", a, b, validCastle);
						spaceScore = s.pieceSquareTables("k", "white", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("q")){
						possibleMovesCopy = p.queen(piecetxt, "white", a, b);
						spaceScore = s.pieceSquareTables("q", "white", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("r")){
						possibleMovesCopy = p.rook(piecetxt, "white", a, b);
						spaceScore = s.pieceSquareTables("r", "white", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("n")){
						possibleMovesCopy = p.knight(piecetxt, "white", a, b);
						spaceScore = s.pieceSquareTables("n", "white", piecetxt);
					} else if (piecetxt[a][b].substring(0,1).equals("b")){
						possibleMovesCopy = p.bishop(piecetxt, "white", a, b);
						spaceScore = s.pieceSquareTables("b", "white", piecetxt);
					}
					
					for (int t = 0; t < 8; t++) {
						for (int v = 0; v < 8; v++) {
							if (possibleMovesCopy[t][v] == 1) {
								
								if (checkChecker(piecetxt, validCastle, t, v, a, b, "b", false) == "w") {
									possibleMovesCopy[t][v] = 0;
								}
								if (checkChecker(piecetxt, validCastle, t, v, a, b, "w", false) == "b") {
									
									// gives a copy of piecetxt to checkmate checker to see if move made will be a checkmate
									for (int g = 0; g < 8; g++) {
										for (int h = 0; h < 8; h++) {
											piecetxtCopy[g][h] = piecetxt[g][h];
										}
									}
									piecetxtCopy[t][v] = piecetxtCopy[a][b];
									piecetxtCopy[a][b] = ". ";
									if (checkmateChecker(piecetxtCopy, "b", validCastle) == "b"){
										spaceScore[t][v] = 10000;
									}
								}
								
							}
						}
					}
					
					
					totalMoveScore = moveScore(piecetxt, "w", possibleMovesCopy, a, b);
					for (int i = 0; i < 8; i++) {
						for (int e = 0; e < 8; e++) {
							if (possibleMovesCopy[i][e] == 1) {
								
								// take negative of total score for white
								totalMoveScore[i][e] = ((totalMoveScore[i][e] + spaceScore[i][e]) * -1);
								
								if (totalMoveScore[i][e] < min) {
									min = totalMoveScore[i][e];
									
								}
								
							}
						}
					}
					
				}
				
				
			}
		}
		
		return min;
	}
	
	
	public int[][] moveScore(String[][] piecetxt, String playerColor, int[][] possibleMoves, int parentX, int parentY) {
		
		int[][] moveScore = new int[8][8];
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				moveScore[a][b] = 0;
			}
		}
		
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				
				if (playerColor == "b") {
					if (piecetxt[a][b].substring(1).equals("w") && possibleMoves[a][b] == 1) {
						if (piecetxt[a][b].substring(0,1).equals("p")){
							moveScore[a][b] = moveScore[a][b] + pawn;
						} else if (piecetxt[a][b].substring(0,1).equals("k")){
							moveScore[a][b] = moveScore[a][b] + king;
						} else if (piecetxt[a][b].substring(0,1).equals("q")){
							moveScore[a][b] = moveScore[a][b] + queen;
						} else if (piecetxt[a][b].substring(0,1).equals("r")){
							moveScore[a][b] = moveScore[a][b] + rook;
						} else if (piecetxt[a][b].substring(0,1).equals("n")){
							moveScore[a][b] = moveScore[a][b] + knight;
						} else if (piecetxt[a][b].substring(0,1).equals("b")){
							moveScore[a][b] = moveScore[a][b] + bishop;
						}
					}
				} else if (playerColor == "w") {
					if (piecetxt[a][b].substring(1).equals("b") && possibleMoves[a][b] == 1) {
						if (piecetxt[a][b].substring(0,1).equals("p")){
							moveScore[a][b] = moveScore[a][b] + pawn;
						} else if (piecetxt[a][b].substring(0,1).equals("k")){
							moveScore[a][b] = moveScore[a][b] + king;
						} else if (piecetxt[a][b].substring(0,1).equals("q")){
							moveScore[a][b] = moveScore[a][b] + queen;
						} else if (piecetxt[a][b].substring(0,1).equals("r")){
							moveScore[a][b] = moveScore[a][b] + rook;
						} else if (piecetxt[a][b].substring(0,1).equals("n")){
							moveScore[a][b] = moveScore[a][b] + knight;
						} else if (piecetxt[a][b].substring(0,1).equals("b")){
							moveScore[a][b] = moveScore[a][b] + bishop;
						}
					}
				}
				
				
			}
		}
		
		
		return moveScore;
	}
	
	public String checkChecker(String[][] piecetxt, int[] validCastle, int x, int y, int tempX, int tempY, String attackingColor, boolean moveComplete) {
		
		String playerInCheck = "";
		String pieceColor = "";
		String targetColor = "";
		if (attackingColor == "b") {
			pieceColor = "black";
			targetColor = "w";
		} else if (attackingColor == "w") {
			pieceColor = "white";
			targetColor = "b";
		}
		
		PieceMovement p = new PieceMovement();
		int[][] possibleMovesCopy = new int[8][8];
		
		int count = 0;
		String pieceHolder = piecetxt[x][y];
		if (moveComplete == false) {
			piecetxt[x][y] = piecetxt[tempX][tempY];
			piecetxt[tempX][tempY] = ". ";
		}
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				
				if (piecetxt[a][b].substring(1).equals(attackingColor)) {
					if (piecetxt[a][b].substring(0,1).equals("p")){
						possibleMovesCopy = p.pawn(piecetxt, pieceColor, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("k")){
						possibleMovesCopy = p.king(piecetxt, pieceColor, a, b, validCastle);
					} else if (piecetxt[a][b].substring(0,1).equals("q")){
						possibleMovesCopy = p.queen(piecetxt, pieceColor, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("r")){
						possibleMovesCopy = p.rook(piecetxt, pieceColor, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("n")){
						possibleMovesCopy = p.knight(piecetxt, pieceColor, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("b")){
						possibleMovesCopy = p.bishop(piecetxt, pieceColor, a, b);
					}
					
					for (int i = 0; i < 8; i++) {
						for (int e = 0; e < 8; e++) {
							
							if (attackingColor == "b") {
								if (possibleMovesCopy[i][e] == 1 && piecetxt[i][e] == ("kw")) {
									playerInCheck = targetColor;
									count++;
								}
							}
							if (attackingColor == "w") {
								if (possibleMovesCopy[i][e] == 1 && piecetxt[i][e] == ("kb")) {
									playerInCheck = targetColor;
									count++;
								}
							}
							
						}
					}
					
				}
				
			}
		}
		if (count == 0) {
			playerInCheck = "";
		} else {
			playerInCheck = targetColor;
		}
		if (moveComplete == false) {
			piecetxt[tempX][tempY] = piecetxt[x][y];
			piecetxt[x][y] = pieceHolder;
		}
		
		return playerInCheck;
	} 
	
	public String checkmateChecker(String[][] piecetxt, String playerInCheck, int[] validCastle) {
		
		String checkMateColor = "";
		
		String defendingPlayer = "";
		String defendingPlayerInitial = "";
		String attackingPlayerInitial = "";
		if (playerInCheck == "w") {
			defendingPlayer = "white";
			defendingPlayerInitial = "w";
			attackingPlayerInitial = "b";
		} else if (playerInCheck == "b") {
			defendingPlayer = "black";
			defendingPlayerInitial = "b";
			attackingPlayerInitial = "w";
		}
		
		PieceMovement p = new PieceMovement();
		int[][] possibleMovesCopy = new int[8][8];
		String[][] piecetxtCopy = new String[8][8];
		int count = 0;
		
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				
				for (int t = 0; t < 8; t++) {
					for (int v = 0; v < 8; v++) {
						piecetxtCopy[t][v] = piecetxt[t][v];
					}
				}
				
				if (piecetxt[a][b].substring(1).equals(defendingPlayerInitial)) {
					if (piecetxt[a][b].substring(0,1).equals("p")){
						possibleMovesCopy = p.pawn(piecetxt, defendingPlayer, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("k")){
						possibleMovesCopy = p.king(piecetxt, defendingPlayer, a, b, validCastle);
					} else if (piecetxt[a][b].substring(0,1).equals("q")){
						possibleMovesCopy = p.queen(piecetxt, defendingPlayer, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("r")){
						possibleMovesCopy = p.rook(piecetxt, defendingPlayer, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("n")){
						possibleMovesCopy = p.knight(piecetxt, defendingPlayer, a, b);
					} else if (piecetxt[a][b].substring(0,1).equals("b")){
						possibleMovesCopy = p.bishop(piecetxt, defendingPlayer, a, b);
					}
					
					for (int i = 0; i < 8; i++) {
						for (int e = 0; e < 8; e++) {
							
							for (int t = 0; t < 8; t++) {
								for (int v = 0; v < 8; v++) {
									piecetxtCopy[t][v] = piecetxt[t][v];
								}
							}
							if (possibleMovesCopy[i][e] == 1) {
								piecetxtCopy[i][e] = piecetxtCopy[a][b];
								piecetxtCopy[a][b] = ". ";
								if (checkChecker(piecetxt, validCastle, i, e, a, b, attackingPlayerInitial, false) == "") {
									count = 1;
								}
							}
							
						}
					}
					
					
				}
				
			}
		}
		
		if (count == 0 && playerInCheck == "w") {
			checkMateColor = "w";
		}
		if (count == 0 && playerInCheck == "b") {
			checkMateColor = "b";
		}
		
		
		return checkMateColor;
	}
	
	
	
}

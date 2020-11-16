import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Gameplay {
	
	public JLabel[][] boardtile = new JLabel[8][8];
	public JLabel[][] piece = new JLabel[8][8];
	public JButton[][] tilebtn = new JButton[8][8];
	public JButton[] theme = new JButton[6];
	public JLabel[] themecolor = new JLabel[6];
	public JLabel bardesign1;
	public JLabel bardesign2;
	public JLabel checkAlertw;
	public JLabel checkAlertb;
	public JLabel winner;
	public JLabel displayKing;
	
	public String[][] piecetxt = new String[8][8];
	
	public String playerTurn = "white";
	// white right castle, white left castle, black right castle, black left castle
	public int[] validCastle = new int[]{1, 1, 1, 1};
	public String promotionpicked = "p"; // pawn promotion
	public int pawnpromo = 0;
	public String playerInCheck = ""; // stores b or w if player in check
	public int playercount = 1;
	public int timeSetting = 0;
	public int themenum = 0;
	public int parentX = 0;
	public int parentY = 0;
	
	//timers
	public JLabel timerones_w;
	public JLabel colon_w;
	public JLabel timerminutes_w;
	public int onespassed_w = 0;
	public int minutespassed_w = 0;
	public JLabel timerones_b;
	public JLabel colon_b;
	public JLabel timerminutes_b;
	public int onespassed_b = 0;
	public int minutespassed_b = 0;
	Timer t = new Timer();
	TimerTask tt = new TimerTask() {
		@Override
		public void run() {
			if (playerTurn == "white") {
				
				if (timeSetting == 0) {
					onespassed_w++;
					if (onespassed_w == 60) {
						onespassed_w = 0;
						minutespassed_w++;
					}
				} else if (timeSetting == 5 || timeSetting == 10 || timeSetting == 30) {
					onespassed_w--;
					if (onespassed_w < 0) {
						onespassed_w = 59;
						minutespassed_w--;
					}
					
					if (onespassed_w == 0 && minutespassed_w == 0) {
						playerTurn = "game over";
						winnerWindow();
						winner.setText("black wins!");
						winner.setForeground(Color.BLACK);
						displayKing.setForeground(Color.BLACK);
					}
				} 
				
				timerminutes_w.setText(String.valueOf(String.format("%02d", minutespassed_w)));
				timerones_w.setText(String.valueOf(String.format("%02d", onespassed_w)));
				
			} else if (playerTurn == "black"){
				
				if (timeSetting == 0) {
					onespassed_b++;
					if (onespassed_b == 60) {
						onespassed_b = 0;
						minutespassed_b++;
					}
				} else if (timeSetting == 5 || timeSetting == 10 || timeSetting == 30) {
					onespassed_b--;
					if (onespassed_b < 0) {
						onespassed_b = 59;
						minutespassed_b--;
					}
					
					if (onespassed_b == 0 && minutespassed_b == 0) {
						playerTurn = "game over";
						winnerWindow();
						winner.setText("white wins!");
						winner.setForeground(Color.WHITE);
						displayKing.setForeground(Color.WHITE);
					}
				} 
				
				timerminutes_b.setText(String.valueOf(String.format("%02d", minutespassed_b)));
				timerones_b.setText(String.valueOf(String.format("%02d", onespassed_b)));
				
			}
			
		}
	};

	public void appComponants(JFrame f) {
		
		// string array that stores the values of each piece
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				piecetxt[a][b] = ". ";
			}
		}
		piecetxt[0][7] = "rb"; piecetxt[1][7] = "nb"; piecetxt[2][7] = "bb"; piecetxt[3][7] = "qb"; piecetxt[4][7] = "kb"; piecetxt[5][7] = "bb"; piecetxt[6][7] = "nb"; piecetxt[7][7] = "rb";
		piecetxt[0][6] = "pb"; piecetxt[1][6] = "pb"; piecetxt[2][6] = "pb"; piecetxt[3][6] = "pb"; piecetxt[4][6] = "pb"; piecetxt[5][6] = "pb"; piecetxt[6][6] = "pb"; piecetxt[7][6] = "pb";
		
		piecetxt[0][1] = "pw"; piecetxt[1][1] = "pw"; piecetxt[2][1] = "pw"; piecetxt[3][1] = "pw"; piecetxt[4][1] = "pw"; piecetxt[5][1] = "pw"; piecetxt[6][1] = "pw"; piecetxt[7][1] = "pw";
		piecetxt[0][0] = "rw"; piecetxt[1][0] = "nw"; piecetxt[2][0] = "bw"; piecetxt[3][0] = "qw"; piecetxt[4][0] = "kw"; piecetxt[5][0] = "bw"; piecetxt[6][0] = "nw"; piecetxt[7][0] = "rw";
		
		// JLabel array on top of tiles that holds the piece text
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				piece[a][b] = new JLabel(" ");
				piece[a][b].setFont(new Font("Lucida Grande", Font.PLAIN, 73));
				piece[a][b].setForeground(Color.WHITE);
				piece[a][b].setHorizontalAlignment(SwingConstants.CENTER);
				piece[a][b].setBounds(93+(a*75), 547-(b*75), 75, 75);
				f.getContentPane().add(piece[a][b]);
			}
		}
		drawPieces();
		outputBoard();
		
		// checkered board
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				boardtile[a][b] = new JLabel("g");
				if (((a+b)%2) == 0) {
					boardtile[a][b].setForeground(new Color(205, 133, 63));
				} else {
					boardtile[a][b].setForeground(new Color(222, 184, 135));
				}
				boardtile[a][b].setFont(new Font("Webdings", Font.PLAIN, 75));
				boardtile[a][b].setBounds(100+(75*a), 555-(75*b), 75, 75);
				f.getContentPane().add(boardtile[a][b]);
			}
		}
		
		// button array below visable tiles to allow clicking/action
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				tilebtn[a][b] = new JButton();
				tilebtn[a][b].setBounds(100+(75*a), 555-(75*b), 75, 75);
				f.getContentPane().add(tilebtn[a][b]);
			}
		}
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				int x = a; int y = b;
				tilebtn[a][b].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onePlayerGameplay(x, y);
					}
				});
			}
		}
		
		// theme circle JLabel array over JButton theme array
		for (int a = 0; a < 6; a++) {
			themecolor[a] = new JLabel("n");
			switch (a) {
				case 0: themecolor[a].setForeground(new Color(205, 133, 63));
					break;
				case 1: themecolor[a].setForeground(new Color(165, 42, 42));
					break;
				case 2: themecolor[a].setForeground(new Color(32, 178, 170));
					break;
				case 3: themecolor[a].setForeground(new Color(46, 139, 87));
					break;
				case 4: themecolor[a].setForeground(new Color(128, 128, 128));
					break;
				case 5: themecolor[a].setForeground(new Color(219, 112, 147));
					break;
			}
			themecolor[a].setHorizontalAlignment(SwingConstants.CENTER);
			themecolor[a].setFont(new Font("Webdings", Font.PLAIN, 13));
			themecolor[a].setBounds(305+(a*35), 722, 14, 14);
			f.getContentPane().add(themecolor[a]);
		}
		ImageIcon darkblock;
		darkblock = new ImageIcon(this.getClass().getResource("/darkblock.png"));
		for (int a = 0; a < 6; a++) {
			theme[a] = new JButton(darkblock);
			theme[a].setBounds(305+(a*35), 722, 14, 14);
			f.getContentPane().add(theme[a]);
		}
		for (int a = 0; a < 6; a++) {
			int num = a;
			theme[a].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					themenum = num;
					colorTheme();
				}
			});
		}
		
		JLabel menutxt = new JLabel("---- M E N U ----");
		menutxt.setHorizontalAlignment(SwingConstants.CENTER);
		menutxt.setFont(new Font("Futura", Font.PLAIN, 16));
		menutxt.setForeground(Color.GRAY);
		menutxt.setBounds(350, 690, 100, 30);
		f.getContentPane().add(menutxt);
		JButton openMenu = new JButton(darkblock);
		openMenu.setBounds(350, 690, 100, 30);
		openMenu.setFocusable(false);
		f.getContentPane().add(openMenu);
		openMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuWindow();
			}
		});
		
		t.schedule(tt, 1000, 1000); // timer starts
		timerones_w = new JLabel(String.valueOf(String.format("%02d", onespassed_w)));
		timerones_w.setFont(new Font("Futura", Font.PLAIN, 35));
		timerones_w.setHorizontalAlignment(SwingConstants.CENTER);
		timerones_w.setForeground(Color.LIGHT_GRAY);
		timerones_w.setBounds(198, 695, 70, 35);
		f.getContentPane().add(timerones_w);
		colon_w = new JLabel(String.valueOf(":"));
		colon_w.setFont(new Font("Futura", Font.PLAIN, 35));
		colon_w.setHorizontalAlignment(SwingConstants.CENTER);
		colon_w.setForeground(Color.LIGHT_GRAY);
		colon_w.setBounds(183, 693, 35, 35);
		f.getContentPane().add(colon_w);
		timerminutes_w = new JLabel(String.valueOf(String.format("%02d", minutespassed_w)));
		timerminutes_w.setFont(new Font("Futura", Font.PLAIN, 35));
		timerminutes_w.setHorizontalAlignment(SwingConstants.RIGHT);
		timerminutes_w.setForeground(Color.LIGHT_GRAY);
		timerminutes_w.setBounds(86, 695, 105, 35);
		f.getContentPane().add(timerminutes_w);
		timerones_b = new JLabel(String.valueOf(String.format("%02d", onespassed_w)));
		timerones_b.setFont(new Font("Futura", Font.PLAIN, 35));
		timerones_b.setHorizontalAlignment(SwingConstants.CENTER);
		timerones_b.setForeground(Color.LIGHT_GRAY);
		timerones_b.setBounds(598, 695, 70, 35);
		f.getContentPane().add(timerones_b);
		colon_b = new JLabel(String.valueOf(":"));
		colon_b.setFont(new Font("Futura", Font.PLAIN, 35));
		colon_b.setHorizontalAlignment(SwingConstants.CENTER);
		colon_b.setForeground(Color.LIGHT_GRAY);
		colon_b.setBounds(583, 693, 35, 35);
		f.getContentPane().add(colon_b);
		timerminutes_b = new JLabel(String.valueOf(String.format("%02d", minutespassed_w)));
		timerminutes_b.setFont(new Font("Futura", Font.PLAIN, 35));
		timerminutes_b.setHorizontalAlignment(SwingConstants.RIGHT);
		timerminutes_b.setForeground(Color.LIGHT_GRAY);
		timerminutes_b.setBounds(486, 695, 105, 35);
		f.getContentPane().add(timerminutes_b);
		
		JLabel txtwhite = new JLabel("w h i t e");
		txtwhite.setFont(new Font("Futura", Font.PLAIN, 12));
		txtwhite.setHorizontalAlignment(SwingConstants.CENTER);
		txtwhite.setForeground(Color.GRAY);
		txtwhite.setBounds(163, 720, 70, 35);
		f.getContentPane().add(txtwhite);
		
		JLabel txtblack = new JLabel("b l a c k");
		txtblack.setFont(new Font("Futura", Font.PLAIN, 12));
		txtblack.setHorizontalAlignment(SwingConstants.CENTER);
		txtblack.setForeground(Color.GRAY);
		txtblack.setBounds(565, 720, 70, 35);
		f.getContentPane().add(txtblack);
		
		checkAlertw = new JLabel("");
		checkAlertw.setFont(new Font("Futura", Font.PLAIN, 16));
		checkAlertw.setHorizontalAlignment(SwingConstants.CENTER);
		checkAlertw.setForeground(new Color(221, 160, 221));
		checkAlertw.setBounds(163, 665, 70, 35);
		f.getContentPane().add(checkAlertw);
		
		checkAlertb = new JLabel("");
		checkAlertb.setFont(new Font("Futura", Font.PLAIN, 16));
		checkAlertb.setHorizontalAlignment(SwingConstants.CENTER);
		checkAlertb.setForeground(new Color(221, 160, 221));
		checkAlertb.setBounds(565, 665, 70, 35);
		f.getContentPane().add(checkAlertb);
		
		bardesign1 = new JLabel("v");
		bardesign1.setForeground(Color.DARK_GRAY);
		bardesign1.setHorizontalAlignment(SwingConstants.CENTER);
		bardesign1.setFont(new Font("Wingdings", Font.PLAIN, 75));
		bardesign1.setBounds(50, 677, 75, 75);
		f.getContentPane().add(bardesign1);
		
		bardesign2 = new JLabel("v");
		bardesign2.setForeground(Color.DARK_GRAY);
		bardesign2.setHorizontalAlignment(SwingConstants.CENTER);
		bardesign2.setFont(new Font("Wingdings", Font.PLAIN, 75));
		bardesign2.setBounds(675, 677, 75, 75);
		f.getContentPane().add(bardesign2);
		
		ImageIcon background;
		background = new ImageIcon(this.getClass().getResource("/background.png"));
		JLabel backgroundlbl = new JLabel(background);
		backgroundlbl.setBounds(0, 0, 800, 800);
		f.getContentPane().add(backgroundlbl);
		
	}
	
	public void outputBoard(){
		
		System.out.println("------------------------------");
		for (int b = 7; b >= 0; b--) {
			for (int a = 0; a < 8; a++) {
				System.out.print(piecetxt[a][b] + "  ");
				if (a == 7) {
					System.out.println("\n");
				}
			}
		}
		System.out.println("------------------------------");
		
	}
	
	// when this function is called, board tiles will change respective to current themenum value
	public void colorTheme() {
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				
				if (((a+b)%2) == 0) {
					
					switch (themenum) {
					case 0: boardtile[a][b].setForeground(new Color(205, 133, 63));
						break;
					case 1: boardtile[a][b].setForeground(new Color(165, 42, 42));
						break;
					case 2: boardtile[a][b].setForeground(new Color(32, 178, 170));
						break;
					case 3: boardtile[a][b].setForeground(new Color(46, 139, 87));
						break;
					case 4: boardtile[a][b].setForeground(new Color(128, 128, 128));
						break;
					case 5: boardtile[a][b].setForeground(new Color(219, 112, 147));
						break;
					}
					
				} else {
					
					switch (themenum) {
					case 0: boardtile[a][b].setForeground(new Color(222, 184, 135));
						break;
					case 1: boardtile[a][b].setForeground(new Color(240, 128, 128));
						break;
					case 2: boardtile[a][b].setForeground(new Color(64, 224, 208));
						break;
					case 3: boardtile[a][b].setForeground(new Color(222, 184, 135));
						break;
					case 4: boardtile[a][b].setForeground(new Color(192, 192, 192));
						break;
					case 5: boardtile[a][b].setForeground(new Color(216, 191, 216));
						break;
					}
				
				}
				
			}
		}
	}
	
	// changes visable piece array on checkered board according to piecetxt array
	public void drawPieces() {
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				
				if (piecetxt[a][b].substring(1).equals("w")) {
					piece[a][b].setForeground(new Color(255, 250, 240));
				} else if (piecetxt[a][b].substring(1).equals("b")) {
					piece[a][b].setForeground(Color.BLACK);
				}
				
				if (piecetxt[a][b].substring(0, 1).equals("p")) {
					piece[a][b].setFont(new Font("Lucida Grande", Font.PLAIN, 65));
				} else {
					piece[a][b].setFont(new Font("Lucida Grande", Font.PLAIN, 73));
				}
				
				if (piecetxt[a][b].substring(0, 1).equals("p")) {
					piece[a][b].setText("♟");
				} else if (piecetxt[a][b].substring(0, 1).equals("k")) {
					piece[a][b].setText("♚");
				} else if (piecetxt[a][b].substring(0, 1).equals("q")) {
					piece[a][b].setText("♛");
				} else if (piecetxt[a][b].substring(0, 1).equals("r")) {
					piece[a][b].setText("♜");
				} else if (piecetxt[a][b].substring(0, 1).equals("n")) {
					piece[a][b].setText("♞");
				} else if (piecetxt[a][b].substring(0, 1).equals("b")) {
					piece[a][b].setText("♝");
				} else if (piecetxt[a][b].substring(0, 1).equals(".")) {
					piece[a][b].setText(" ");
				}
				
			}
		}
	}
	
	// method called when piece on board is clicked
	/*
	 * board tile is adjusted to different color when clicked
	 * the possible moves for the piece clicked is scanned and stored into possibleMoves
	 * possibleMoves is updated after taking into account checkChecker
	 * if castle move is requested, legality is checked
	 * if pawn reaches end, promotePawn method is called
	 * move selected is implemented if move is possible and does not cause check
	 * if playercount is 1, ai implementation is called
	 * king and rooks are checked if they moved at all to see if castling is legal
	 */
	public void onePlayerGameplay(int x, int y) {
		
		// resets board to appropriate theme then highlights new focused tile
		colorTheme();
		boardtile[x][y].setForeground(new Color(221, 160, 221));
		int tempX = parentX; int tempY = parentY;
		parentX = x;
		parentY = y;
		
		PieceMovement p = new PieceMovement();
		
		// checks what moves are possible for piece in space tempX, tempY
		int[][] possibleMoves = new int[8][8]; // 1 for true, 0 for false
		if (piecetxt[tempX][tempY].substring(0, 1).equals("p")) {
			possibleMoves = p.pawn(piecetxt, playerTurn, tempX, tempY);
		} else if (piecetxt[tempX][tempY].substring(0, 1).equals("k")) {
			possibleMoves = p.king(piecetxt, playerTurn, tempX, tempY, validCastle);
		} else if (piecetxt[tempX][tempY].substring(0, 1).equals("q")) {
			possibleMoves = p.queen(piecetxt, playerTurn, tempX, tempY);
		} else if (piecetxt[tempX][tempY].substring(0, 1).equals("r")) {
			possibleMoves = p.rook(piecetxt, playerTurn, tempX, tempY);
		} else if (piecetxt[tempX][tempY].substring(0, 1).equals("n")) {
			possibleMoves = p.knight(piecetxt, playerTurn, tempX, tempY);
		} else if (piecetxt[tempX][tempY].substring(0, 1).equals("b")) {
			possibleMoves = p.bishop(piecetxt, playerTurn, tempX, tempY);
		} 
		// updates possibleMoves, taking into account checkChecker
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (possibleMoves[a][b] == 1) {
					
					if (playerTurn == "white") {
						if (checkChecker(a, b, tempX, tempY, "b", false) == "w") {
							possibleMoves[a][b] = 0;
						}
					}
					if (playerTurn == "black") {
						if (checkChecker(a, b, tempX, tempY, "w", false) == "b") {
							possibleMoves[a][b] = 0;
						}
					}
					
				}
			}
		}
		
		// castle implementation
		// checks to ensure the king does not move through check
		if (piecetxt[tempX][tempY].substring(0, 1).equals("k") && possibleMoves[x][y] == 1) {
			if (x == 6 && y == 0 && validCastle[0] == 1) {
				if (checkChecker(5, 0, tempX, tempY, "b", false) != "w") {
					piecetxt[7][0] = ". ";
					piecetxt[5][0] = "rw";
				} else {
					possibleMoves[x][y] = 0;
				}
			}
			if (x == 2 && y == 0 && validCastle[1] == 1) {
				if (checkChecker(3, 0, tempX, tempY, "b", false) != "w") {
					piecetxt[0][0] = ". ";
					piecetxt[3][0] = "rw";
				} else {
					possibleMoves[x][y] = 0;
				}
			}
			if (x == 6 && y == 7 && validCastle[2] == 1) {
				if (checkChecker(5, 7, tempX, tempY, "w", false) != "b") {
					piecetxt[7][7] = ". ";
					piecetxt[5][7] = "rb";
				} else {
					possibleMoves[x][y] = 0;
				}
			}
			if (x == 2 && y == 7 && validCastle[3] == 1) {
				if (checkChecker(3, 7, tempX, tempY, "w", false) != "b") {
					piecetxt[0][7] = ". ";
					piecetxt[3][7] = "rb";
				} else {
					possibleMoves[x][y] = 0;
				}
			}
		}
		
		
		// pawn promotion
		if (piecetxt[tempX][tempY].substring(0, 1).equals("p") && possibleMoves[x][y] == 1) {
			if (y == 7) {
				promotePawn();
				// pauses play when playing computer
				if (playercount == 1) {
					pawnpromo = 1;
				}
			}
			if (y == 0) {
				promotePawn();
			}
		}
		
		// implement move
		if (playerTurn == "white") {
			// if previous parent tile is a white piece and current parent tile is either black or empty
			if (piecetxt[tempX][tempY].substring(1).equals("w") && 
			   (piecetxt[x][y].substring(1).equals("b") || piecetxt[x][y].equals(". "))) {
				
				//print possible moves in terminal
				System.out.println("possible moves for: " + piecetxt[tempX][tempY]);
				for (int b = 7; b >= 0; b--) {
					for (int a = 0; a < 8; a++) {
						System.out.print(possibleMoves[a][b]);
						if (a == 7) {
							System.out.print("\n");
						}
					}
				}
				
				int inCheck = 0;
				checkChecker(x, y, tempX, tempY, "b", false);
				if (playerInCheck == "w") {
					inCheck = 1;
				}
				checkChecker(x, y, tempX, tempY, "w", false);
				if (inCheck == 1) {
					playerInCheck = "w";
				}
				
				int moveMade = 0;
				if (possibleMoves[x][y] == 1 && playerInCheck != "w") {
					moveMade = 1;
					piecetxt[x][y] = piecetxt[tempX][tempY];
					piecetxt[tempX][tempY] = ". ";
					checkAlertw.setText("");
					bardesign1.setForeground(Color.DARK_GRAY);
					boardtile[tempX][tempY].setForeground(new Color(255, 182, 193));
					playerTurn = "black";
				}
				
				if (moveMade == 1) {
					if (playerInCheck == "b") {
						checkmateChecker();
						checkAlertb.setText("c h e c k");
						bardesign2.setForeground(new Color(221, 160, 221));
					} else if (playerInCheck == "w") {
						checkmateChecker();
						checkAlertw.setText("c h e c k");
						bardesign1.setForeground(new Color(221, 160, 221));
					}
				}
				
			}
		}
		if (playerTurn == "black" && playercount == 2) {
			// if previous parent tile is a white piece and current parent tile is either white or empty
			if (piecetxt[tempX][tempY].substring(1).equals("b") && 
			   (piecetxt[x][y].substring(1).equals("w") || piecetxt[x][y].equals(". "))) {
				
				//print possible moves in terminal
				System.out.println("possible moves for: " + piecetxt[tempX][tempY]);
				for (int b = 7; b >= 0; b--) {
					for (int a = 0; a < 8; a++) {
						System.out.print(possibleMoves[a][b]);
						if (a == 7) {
							System.out.print("\n");
						}
					}
				}
				
				int inCheck = 0;
				checkChecker(x, y, tempX, tempY, "w", false);
				if (playerInCheck == "b") {
					inCheck = 1;
				}
				checkChecker(x, y, tempX, tempY, "b", false);
				if (inCheck == 1) {
					playerInCheck = "b";
				}
				
				int moveMade = 0;
				if (possibleMoves[x][y] == 1 && playerInCheck != "b") {
					moveMade = 1;
					piecetxt[x][y] = piecetxt[tempX][tempY];
					piecetxt[tempX][tempY] = ". ";
					checkAlertb.setText("");
					bardesign2.setForeground(Color.DARK_GRAY);
					boardtile[tempX][tempY].setForeground(new Color(255, 182, 193));
					playerTurn = "white";
				}
				
				if (moveMade == 1) {
					if (playerInCheck == "b") {
						checkmateChecker();
						checkAlertb.setText("c h e c k");
						bardesign2.setForeground(new Color(221, 160, 221));
					} else if (playerInCheck == "w") {
						checkmateChecker();
						checkAlertw.setText("c h e c k");
						bardesign1.setForeground(new Color(221, 160, 221));
					}
				}
				
			}
		} else if (playerTurn == "black" && playercount == 1 && pawnpromo == 0) {
			// AI call
			aiImplementation();
		}
		
		// checks if the king or rook has moved (castle validation)
		//white
		if (piecetxt[4][0] != "kw") {
			validCastle[0] = 0;
			validCastle[1] = 0;
		}
		if (piecetxt[7][0] != "rw") {
			validCastle[0] = 0;
		}
		if (piecetxt[0][0] != "rw") {
			validCastle[1] = 0;
		}
		//black
		if (piecetxt[4][7] != "kb") {
			validCastle[2] = 0;
			validCastle[3] = 0;
		}
		if (piecetxt[7][7] != "rb") {
			validCastle[2] = 0;
		}
		if (piecetxt[0][7] != "rb") {
			validCastle[3] = 0;
		}
		
		drawPieces();
		outputBoard();
		
	}
	
	public void aiImplementation() {
		
		int[] aiMove = new int[4];
		
		// AI CALCULATIONS
		AiPlayer ai = new AiPlayer();
		aiMove = ai.maximizer(piecetxt, validCastle);
		
		int tempX = aiMove[0];
		int tempY = aiMove[1];
		int x = aiMove[2];
		int y = aiMove[3];
		
		// move rook when king castles from its origin
		if (piecetxt[tempX][tempY].substring(0, 1).equals("k") && tempX == 4 && tempY == 7) {
			if (x == 6 && y == 7 && piecetxt[7][7] == "rb" && validCastle[2] == 1) {
				piecetxt[7][7] = ". ";
				piecetxt[5][7] = "rb";
			}
			if (x == 2 && y == 7 && piecetxt[0][7] == "rb" && validCastle[3] == 1) {
				piecetxt[0][7] = ". ";
				piecetxt[3][7] = "rb";
			}
		}
		
		// pawn promotes to a queen
		int pawnpromo = 0;
		if (piecetxt[tempX][tempY].substring(0, 1).equals("p") && y == 0){
			piecetxt[x][y] = "qb";
			piecetxt[tempX][tempY] = ". ";
			pawnpromo = 1;
		}
		
		// move implementation
		if (pawnpromo == 0) {
			piecetxt[x][y] = piecetxt[tempX][tempY];
			piecetxt[tempX][tempY] = ". ";
		}
		playerTurn = "white";
		
		
		// checks if move puts white in check
		checkAlertb.setText("");
		bardesign2.setForeground(Color.DARK_GRAY);
		checkChecker(x, y, tempX, tempY, "b", true);
		
		if (playerInCheck == "w") {
			checkmateChecker();
			checkAlertw.setText("c h e c k");
			bardesign1.setForeground(new Color(221, 160, 221));
		}
		
		colorTheme();
		boardtile[x][y].setForeground(new Color(221, 160, 221));
		boardtile[tempX][tempY].setForeground(new Color(255, 182, 193));
		
	}
	
	public String checkChecker(int x, int y, int tempX, int tempY, String attackingColor, boolean moveComplete) {
		
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
		if(moveComplete == false) {
			piecetxt[tempX][tempY] = piecetxt[x][y];
			piecetxt[x][y] = pieceHolder;
		}
		
		return playerInCheck;
	}
	
	public void checkmateChecker() {
		
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
								if (checkChecker(i, e, a, b, attackingPlayerInitial, false) == "") {
									System.out.println("PIECE THAT CAN GET YOU OUT OF CHECK " + piecetxtCopy[i][e]);
									count = 1;
								}
							}
							
						}
					}
					
					
				}
				
			}
		}
		
		if (count == 0 && playerInCheck == "w") {
			winnerWindow();
			winner.setText("black wins!");
			winner.setForeground(Color.BLACK);
			displayKing.setForeground(Color.BLACK);
		}
		if (count == 0 && playerInCheck == "b") {
			winnerWindow();
			winner.setText("white wins!");
			winner.setForeground(Color.WHITE);
			displayKing.setForeground(Color.WHITE);
		}
		
		
	}
	
	// resets the game and its components
	public void resetGame() {
		
		colorTheme();
		
		parentX = 0;
		parentY = 0;
		
		
		playerTurn = "white";
		playerInCheck = "";
		promotionpicked = "p";
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				piecetxt[a][b] = ". ";
			}
		}
		piecetxt[0][7] = "rb"; piecetxt[1][7] = "nb"; piecetxt[2][7] = "bb"; piecetxt[3][7] = "qb"; piecetxt[4][7] = "kb"; piecetxt[5][7] = "bb"; piecetxt[6][7] = "nb"; piecetxt[7][7] = "rb";
		piecetxt[0][6] = "pb"; piecetxt[1][6] = "pb"; piecetxt[2][6] = "pb"; piecetxt[3][6] = "pb"; piecetxt[4][6] = "pb"; piecetxt[5][6] = "pb"; piecetxt[6][6] = "pb"; piecetxt[7][6] = "pb";
		
		piecetxt[0][1] = "pw"; piecetxt[1][1] = "pw"; piecetxt[2][1] = "pw"; piecetxt[3][1] = "pw"; piecetxt[4][1] = "pw"; piecetxt[5][1] = "pw"; piecetxt[6][1] = "pw"; piecetxt[7][1] = "pw";
		piecetxt[0][0] = "rw"; piecetxt[1][0] = "nw"; piecetxt[2][0] = "bw"; piecetxt[3][0] = "qw"; piecetxt[4][0] = "kw"; piecetxt[5][0] = "bw"; piecetxt[6][0] = "nw"; piecetxt[7][0] = "rw";
		drawPieces();
		
		if (timeSetting == 0) {
			onespassed_w = 0;
			minutespassed_w = 0;
			onespassed_b = 0;
			minutespassed_b = 0;
		} else if (timeSetting == 5) {
			onespassed_w = 1;
			minutespassed_w = 5;
			onespassed_b = 1;
			minutespassed_b = 5;
		} else if (timeSetting == 10) {
			onespassed_w = 1;
			minutespassed_w = 10;
			onespassed_b = 1;
			minutespassed_b = 10;
		} else if (timeSetting == 30) {
			onespassed_w = 1;
			minutespassed_w = 30;
			onespassed_b = 1;
			minutespassed_b = 30;
		}
		
		timerminutes_w.setText(String.valueOf(String.format("%02d", minutespassed_w)));
		timerones_w.setText(String.valueOf(String.format("%02d", 0)));
		timerminutes_b.setText(String.valueOf(String.format("%02d", minutespassed_b)));
		timerones_b.setText(String.valueOf(String.format("%02d", 0)));
		
		checkAlertw.setText("");
		bardesign1.setForeground(Color.DARK_GRAY);
		checkAlertb.setText("");
		bardesign2.setForeground(Color.DARK_GRAY);
		
		for (int a = 0; a < 4; a++) {
			validCastle[a] = 1;
		}
		
	}
	
	public void promotePawn() {
		promotionpicked = "p";
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JFrame frame = new JFrame();
		frame.setBounds(570, 307, 300, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel);

		JButton queen = new JButton("♛");
		queen.setFont(new Font("Futura", Font.PLAIN, 50));
		queen.setForeground(new Color(25, 25, 112));
		queen.setBounds(30, 30, 60, 60);
		frame.add(queen);
		queen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				promotionpicked = "q";
				if (playerTurn == "black") { // player turn change already made in onePlayerGameplay method
					piecetxt[parentX][parentY] = promotionpicked + "w";
				} else if (playerTurn == "white") { // player turn change already made in onePlayerGameplay method
					piecetxt[parentX][parentY] = promotionpicked + "b";
				}
				
				// in 1 player version this method can only be called by white
				if (playercount == 1) {
					piecetxt[parentX][parentY] = promotionpicked + "w";
					pawnpromo = 0;
					aiImplementation();
				}
				drawPieces();
				outputBoard();
				frame.dispose();
			}
		});
		JButton rook = new JButton("♜");
		rook.setFont(new Font("Futura", Font.PLAIN, 50));
		rook.setForeground(new Color(25, 25, 112));
		rook.setBounds(90, 30, 60, 60);
		frame.add(rook);
		rook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				promotionpicked = "r";
				if (playerTurn == "black") {
					piecetxt[parentX][parentY] = promotionpicked + "w";
				} else if (playerTurn == "white") {
					piecetxt[parentX][parentY] = promotionpicked + "b";
				}
				
				// in 1 player version this method can only be called by white
				if (playercount == 1) {
					piecetxt[parentX][parentY] = promotionpicked + "w";
					pawnpromo = 0;
					aiImplementation();
				}
				drawPieces();
				outputBoard();
				frame.dispose();
			}
		});
		JButton knight = new JButton("♞");
		knight.setFont(new Font("Futura", Font.PLAIN, 50));
		knight.setForeground(new Color(25, 25, 112));
		knight.setBounds(150, 30, 60, 60);
		frame.add(knight);
		knight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				promotionpicked = "n";
				if (playerTurn == "black") {
					piecetxt[parentX][parentY] = promotionpicked + "w";
				} else if (playerTurn == "white") {
					piecetxt[parentX][parentY] = promotionpicked + "b";
				}
				
				// in 1 player version this method can only be called by white
				if (playercount == 1) {
					piecetxt[parentX][parentY] = promotionpicked + "w";
					pawnpromo = 0;
					aiImplementation();
				}
				drawPieces();
				outputBoard();
				frame.dispose();
			}
		});
		JButton bishop = new JButton("♝");
		bishop.setFont(new Font("Futura", Font.PLAIN, 50));
		bishop.setForeground(new Color(25, 25, 112));
		bishop.setBounds(210, 30, 60, 60);
		frame.add(bishop);
		bishop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				promotionpicked = "b";
				if (playerTurn == "black") {
					piecetxt[parentX][parentY] = promotionpicked + "w";
				} else if (playerTurn == "white") {
					piecetxt[parentX][parentY] = promotionpicked + "b";
				}
				
				// in 1 player version this method can only be called by white
				if (playercount == 1) {
					piecetxt[parentX][parentY] = promotionpicked + "w";
					pawnpromo = 0;
					aiImplementation();
				}
				drawPieces();
				outputBoard();
				frame.dispose();
			}
		});
		
		ImageIcon background;
		background = new ImageIcon(this.getClass().getResource("/background.png"));
		JLabel menubackground = new JLabel(background);
		menubackground.setBounds(0, 0, 300, 300);
		frame.add(menubackground);
		
		frame.setVisible(true);
	}
	
	// menu window pops up when menu button is clicked
	public void menuWindow() {
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JFrame frame = new JFrame();
		frame.setBounds(570, 232, 300, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel);
		
		ImageIcon background;
		background = new ImageIcon(this.getClass().getResource("/background.png"));
		
		JLabel twoplayertxt = new JLabel("2 PLAYER");
		twoplayertxt.setForeground(new Color(255, 250, 240));
		twoplayertxt.setHorizontalAlignment(SwingConstants.CENTER);
		twoplayertxt.setFont(new Font("Futura", Font.PLAIN, 18));
		twoplayertxt.setBounds(40, 20, 100, 100);
		frame.add(twoplayertxt);
		JLabel twoplayerlbl = new JLabel("n");
		twoplayerlbl.setForeground(Color.BLACK);
		twoplayerlbl.setHorizontalAlignment(SwingConstants.CENTER);
		twoplayerlbl.setFont(new Font("Webdings", Font.PLAIN, 100));
		twoplayerlbl.setBounds(40, 20, 100, 100);
		frame.add(twoplayerlbl);
		JButton twoplayerbtn = new JButton(background);
		twoplayerbtn.setBounds(40, 20, 100, 100);
		frame.add(twoplayerbtn);
		twoplayerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playercount = 2;
				resetGame();
				System.out.println("clickedtwoplayer");
			}
		});
		
		JLabel oneplayertxt = new JLabel("1 PLAYER");
		oneplayertxt.setForeground(Color.BLACK);
		oneplayertxt.setHorizontalAlignment(SwingConstants.CENTER);
		oneplayertxt.setFont(new Font("Futura", Font.PLAIN, 18));
		oneplayertxt.setBounds(160, 20, 100, 100);
		frame.add(oneplayertxt);
		JLabel oneplayerlbl = new JLabel("n");
		oneplayerlbl.setForeground(new Color(255, 250, 240));
		oneplayerlbl.setHorizontalAlignment(SwingConstants.CENTER);
		oneplayerlbl.setFont(new Font("Webdings", Font.PLAIN, 100));
		oneplayerlbl.setBounds(160, 20, 100, 100);
		frame.add(oneplayerlbl);
		JButton oneplayerbtn = new JButton(background);
		oneplayerbtn.setBounds(160, 20, 100, 100);
		frame.add(oneplayerbtn);
		oneplayerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playercount = 1;
				resetGame();
				System.out.println("clickedoneplayer");
			}
		});
		
		JButton noBlitz = new JButton("- -|- -");
		noBlitz.setFont(new Font("Futura", Font.PLAIN, 15));
		noBlitz.setForeground(Color.DARK_GRAY);
		noBlitz.setBounds(30, 140, 60, 50);
		frame.add(noBlitz);
		noBlitz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeSetting = 0;
				resetGame();
				System.out.println("noblitz");
			}
		});
		JButton min5 = new JButton("05|05");
		min5.setFont(new Font("Futura", Font.PLAIN, 15));
		min5.setForeground(Color.DARK_GRAY);
		min5.setBounds(90, 140, 60, 50);
		frame.add(min5);
		min5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeSetting = 5;
				resetGame();
				System.out.println("5 min");
			}
		});
		JButton min10 = new JButton("10|10");
		min10.setFont(new Font("Futura", Font.PLAIN, 15));
		min10.setForeground(Color.DARK_GRAY);
		min10.setBounds(150, 140, 60, 50);
		frame.add(min10);
		min10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeSetting = 10;
				resetGame();
				System.out.println("10 min");
			}
		});
		JButton min30 = new JButton("30|30");
		min30.setFont(new Font("Futura", Font.PLAIN, 15));
		min30.setForeground(Color.DARK_GRAY);
		min30.setBounds(210, 140, 60, 50);
		frame.add(min30);
		min30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeSetting = 30;
				resetGame();
				System.out.println("30 min");
			}
		});
		
		JButton savebtn = new JButton("S A V E");
		savebtn.setFont(new Font("Futura", Font.PLAIN, 15));
		savebtn.setForeground(Color.DARK_GRAY);
		savebtn.setBounds(30, 210, 240, 50);
		frame.add(savebtn);
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		
		JLabel menubackground = new JLabel(background);
		menubackground.setBounds(0, 0, 300, 300);
		frame.add(menubackground);
		
		frame.setVisible(true);
	}
	
	public void winnerWindow() {
		
		playerTurn = "game over";
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JFrame frame = new JFrame();
		frame.setBounds(570, 307, 300, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel);

		winner = new JLabel("");
		winner.setForeground(Color.WHITE);
		winner.setHorizontalAlignment(SwingConstants.CENTER);
		winner.setFont(new Font("Futura", Font.PLAIN, 30));
		winner.setBounds(0, 0, 300, 55);
		frame.add(winner);
		
		displayKing = new JLabel("♚");
		displayKing.setForeground(Color.WHITE);
		displayKing.setHorizontalAlignment(SwingConstants.CENTER);
		displayKing.setFont(new Font("Futura", Font.PLAIN, 80));
		displayKing.setBounds(0, 0, 285, 145);
		frame.add(displayKing);
		
		ImageIcon background;
		background = new ImageIcon(this.getClass().getResource("/background.png"));
		JLabel menubackground = new JLabel(background);
		menubackground.setBounds(0, 0, 300, 300);
		frame.add(menubackground);
		
		frame.setVisible(true);
	}
	
}

/*
 * STILL NEED TO DO -
 * 
 * stalemate for 2 player and 1 player white
 * make ai avoid making a stalemate
 * 3rd gen ai
 * checkmate check after pawn promo
 */

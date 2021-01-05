package mygame;

import javax.swing.Timer;
import mygame.Shape.MyShape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board implements ActionListener, MyColor {

	public static int DELAY = 1000;

	private static final int BORDER_WIDTH = 5;

	// The number of columns on the board.
	public static final int BOARD_WIDTH = 10;

	// The number of visible rows on the board.
	private static final int VISIBLE_HEIGHT_COUNT = 20;

	// The number of hidden rows on the board
	private static int HIDDEN_HEIGHT_COUNT = 3;

	public static final int BOARD_HEIGHT = VISIBLE_HEIGHT_COUNT + HIDDEN_HEIGHT_COUNT;

	// size of square
	public static final int TILE_SIZE = 24;

	// The width of the shading on the tiles.
	public static final int SHADE_WIDTH = 4;

	// The central x coordinate on the game board.
	private static final int CENTER_X = BOARD_WIDTH * TILE_SIZE / 2;

	// The central y coordinate on the game board.
	private static final int CENTER_Y = VISIBLE_HEIGHT_COUNT * TILE_SIZE / 2;

	// The total width of the board
	public static final int PANEL_WIDTH = BOARD_WIDTH * TILE_SIZE + BORDER_WIDTH * 2;

	// The total height of the panel.
	public static final int PANEL_HEIGHT = VISIBLE_HEIGHT_COUNT * TILE_SIZE + BORDER_WIDTH * 2;

	// The larger font to display.
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 16);

	// The smaller font to display.
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);

	public static final int INIT_DELAY = 1000;

	public Timer timer;

	public boolean isFallingFinished = false;

	private boolean isPaused = false;

	public int numLinesRemoved = 0;

	public int curX = 0;

	public int curY = 0;

	public Shape nextPiece;

	public Shape curPiece;

	private Shape ghostPiece;

	private int ghostCurY;

	private int ghostCurX;

	private MyShape[] board;

	private Tetris tetris;

	public static int level = 0;

	private long curTime;
	private long beforeTime;

	public Board(Tetris parent) {
		initBoard(parent);
	}

	private void initBoard(Tetris parent) {
		tetris = parent;
	}

	private MyShape shapeAt(int x, int y) {

		return board[(y * BOARD_WIDTH) + x];
	}

	void start() {

		nextPiece = new Shape();
		curPiece = new Shape();
		nextPiece.setRandomShape();
		board = new MyShape[BOARD_WIDTH * BOARD_HEIGHT];
		clearBoard();
		newPiece();
		curTime = 0;
		beforeTime = 0;
	}

	public void startTimer() {
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void pause() {
		isPaused = !isPaused;
	}

	public void setChangeUpLevel() {
		this.curTime = 0;
		this.beforeTime = 0;
		if (Board.DELAY > 500 && level >= 0) {
			Board.DELAY -= 50;
			level += 1;
			timer.setDelay(DELAY);
		}
	}

	public void setChangeDownLevel() {
		this.curTime = 0;
		this.beforeTime = 0;
		if (Board.DELAY <= 1000 && level >= 0) {
			Board.DELAY += 50;
			if (Board.DELAY == 1050)
				Board.DELAY = 1000;

			level -= 1;
			if (level == -1)
				level = 0;
			timer.setDelay(DELAY);
		}
	}

	public void doDrawing(Graphics g) {

		g.translate(BORDER_WIDTH, BORDER_WIDTH);
		int boardTop = PANEL_HEIGHT - BOARD_HEIGHT * TILE_SIZE;
		if (this.isPaused) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.white);
			String msg = "PAUSED";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, CENTER_Y);
		} else {

			g.setColor(Color.DARK_GRAY);
			for (int x = 0; x < BOARD_WIDTH; x++) {
				for (int y = 0; y < VISIBLE_HEIGHT_COUNT; y++) {
					g.drawLine(0, y * TILE_SIZE + 10, BOARD_WIDTH * TILE_SIZE, y * TILE_SIZE + 10);
					g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, VISIBLE_HEIGHT_COUNT * TILE_SIZE + 10);
				}
			}

			for (int i = 0; i < BOARD_HEIGHT; i++) {

				for (int j = 0; j < BOARD_WIDTH; j++) {

					MyShape shape = shapeAt(j, BOARD_HEIGHT - i - 1);

					if (shape != MyShape.NoShape) {

						drawSquare(g, j * TILE_SIZE, boardTop + i * TILE_SIZE, shape);
					}
				}
			}
			// draw ghostPiece
			ghostPiece = new Shape();
			ghostPiece = curPiece;
			Color base = colors[curPiece.getShape().ordinal()];
			base = new Color(base.getRed(), base.getGreen(), base.getBlue(), 30);
			ghostCurY = curY;
			ghostCurX = curX;
			int newGhostY = ghostCurY;

			while (newGhostY > 0) {

				if (!tryMoveGhost(ghostPiece, ghostCurX, newGhostY - 1)) {

					break;
				}

				newGhostY--;
			}
			// draw ghostPiece
			if (ghostPiece.getShape() != MyShape.NoShape) {

				for (int i = 0; i < 4; i++) {

					int x = ghostCurX + ghostPiece.x(i);
					int y = newGhostY - ghostPiece.y(i);
					drawSquareGhost(g, x * TILE_SIZE, boardTop + (BOARD_HEIGHT - y - 1) * TILE_SIZE,
							ghostPiece.getShape(), base);
				}
			}

			// draw curPiece
			if (curPiece.getShape() != MyShape.NoShape) {

				for (int i = 0; i < 4; i++) {

					int x = curX + curPiece.x(i);
					int y = curY - curPiece.y(i);
					drawSquare(g, x * TILE_SIZE, boardTop + (BOARD_HEIGHT - y - 1) * TILE_SIZE, curPiece.getShape());
				}
			}

		}

		g.setColor(Color.WHITE);
		g.drawRect(0, 0, TILE_SIZE * BOARD_WIDTH, TILE_SIZE * VISIBLE_HEIGHT_COUNT + 10);
		g.setColor(Color.black);
		g.fillRect(-5, -5, PANEL_WIDTH, 4);
		g.translate(Board.PANEL_WIDTH + 1, 0);
		tetris.side.doDrawing(g);

	}

	public void dropDown() {

		int newY = curY;

		while (newY > 0) {

			if (!tryMove(curPiece, curX, newY - 1)) {

				break;
			}

			newY--;
		}

		pieceDropped();
	}

	public void oneLineDown() {

		if (!tryMove(curPiece, curX, curY - 1)) {

			pieceDropped();
		}
	}

	public void clearBoard() {

		for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {

			board[i] = MyShape.NoShape;
		}

		Board.level = 0;
		this.numLinesRemoved = 0;
		this.beforeTime = 0;
		this.curTime = 0;
		if (timer != null) {
			DELAY = 1000;
			this.timer.setInitialDelay(INIT_DELAY);
			this.timer.setDelay(DELAY);
		}
	}

	public void pieceDropped() {

		for (int i = 0; i < 4; i++) {

			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			try {
				board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
			} catch (Exception e) {
				System.out.println("Exception");
			}
		}
		// check after dropped have remove line ?
		removeFullLines();

		if (!isFallingFinished) {
			newPiece();

		}
	}

	private void newPiece() {

		curPiece.setShape(nextPiece.getShape());
		curX = BOARD_WIDTH / 2 + 1;
		curY = BOARD_HEIGHT - 1 + curPiece.minY();

		if (!tryMove(curPiece, curX, curY)) {

			curPiece.setShape(MyShape.NoShape);
			timer.stop();
			Tetris.isGameOver = true;
		}
		for (int x = 0; x < BOARD_WIDTH; x++) {
			if (board[(VISIBLE_HEIGHT_COUNT * BOARD_WIDTH) + x] != Shape.MyShape.NoShape)
				Tetris.isGameOver = true;
		}
		nextPiece.setRandomShape();

	}

	private boolean tryMoveGhost(Shape newGhostPiece, int newGhostX, int newGhostY) {
		for (int i = 0; i < 4; i++) {

			int x = newGhostX + newGhostPiece.x(i);
			int y = newGhostY - newGhostPiece.y(i);

			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {

				return false;
			}

			if (shapeAt(x, y) != MyShape.NoShape) {

				return false;
			}
		}
		ghostPiece = newGhostPiece;
		this.ghostCurX = newGhostX;
		this.ghostCurY = newGhostY;

		return true;
	}

	public boolean tryMove(Shape newPiece2, int newX, int newY) {

		for (int i = 0; i < 4; i++) {

			int x = newX + newPiece2.x(i);
			int y = newY - newPiece2.y(i);

			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {

				return false;
			}

			if (shapeAt(x, y) != MyShape.NoShape) {

				return false;
			}
		}

		curPiece = newPiece2;
		curX = newX;
		curY = newY;

		return true;
	}

	private void removeFullLines() {

		int numFullLines = 0;

		for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {

			boolean lineIsFull = true;

			for (int j = 0; j < BOARD_WIDTH; j++) {

				if (shapeAt(j, i) == MyShape.NoShape) {

					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {

				numFullLines++;

				for (int k = i; k < BOARD_HEIGHT - 1; k++) {
					for (int j = 0; j < BOARD_WIDTH; j++) {
						board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
					}
				}
			}
		}

		if (numFullLines > 0) {

			numLinesRemoved += numFullLines;

			isFallingFinished = true;
			curPiece.setShape(MyShape.NoShape);
		}
	}

	private void drawSquare(Graphics g, int x, int y, MyShape shape) {

		var color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

		// draw Shade darker
		g.setColor(color.darker());
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);

		// draw Shade brighter
		g.setColor(color.brighter());
		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}

	private void drawSquareGhost(Graphics g, int x, int y, MyShape shape, Color base) {

		var color = base;

		g.setColor(color);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

		// draw Shade darker
		g.setColor(color.darker());
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);

		// draw Shade brighter
		g.setColor(color.brighter());
		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		doGameCycle();
	}

	public void doGameCycle() {
		checkLevel();
		update();
	}

	private void checkLevel() {

		if (curTime - beforeTime > 15 && !this.isPaused) {
			if (Board.DELAY > 500) {
				Board.DELAY -= 50;
				Board.HIDDEN_HEIGHT_COUNT += 2;
				level += 1;
				beforeTime = curTime;
				timer.setDelay(DELAY);
			}
		} else
			curTime++;

	}

	private void update() {

		if (isPaused) {
			return;
		}

		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
			nextPiece.setRandomShape();
		} else {

			oneLineDown();
		}
	}

	public boolean isGameOver() {
		return Tetris.isGameOver;
	}

	public int getNumLinesRemoved() {
		return numLinesRemoved;
	}

	public void setNumLinesRemoved(int numLinesRemoved) {
		this.numLinesRemoved = numLinesRemoved;
	}

	public int getLevel() {
		return level;

	}

	public boolean getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

}

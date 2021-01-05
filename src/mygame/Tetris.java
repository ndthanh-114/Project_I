package mygame;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import background.BallBg;
import endgame.Effect2;
import endgame.EffectEndGame;
import endgame.EndGame;
import intro.Intro;
import mygame.Shape.MyShape;
import side.ChangeLevel;
import side.MyClick;
import side.MyMouseMove;
import side.Side;

public class Tetris extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int PANEL_WIDTH_GAME = Board.PANEL_WIDTH * 2;
	public static final int PANEL_HEIGHt_GAME = Board.PANEL_HEIGHT + 10;
	public static boolean isNewGame;
	public Side side;
	private Intro intro;
	public static boolean isGameOver;
	public Board board;
	public BallBg ballBg;
	public EndGame endGame;
	ChangeLevel changeLevel;
	public static boolean isIntro = true;

	public Tetris() {
		initUI();
	}

	private void initUI() {

		this.setBackground(Color.black);
		isNewGame = true;
		isGameOver = false;
		this.setPreferredSize(new Dimension(PANEL_WIDTH_GAME, PANEL_HEIGHt_GAME));
		intro = new Intro();
		ballBg = new BallBg();
		board = new Board(this);
		board.start();
		side = new Side(board);
		changeLevel = new ChangeLevel(this);
		endGame = new EndGame();
		this.setFocusable(true);
		this.addMouseListener(new EffectEndGame(this.endGame, this));
		this.addMouseMotionListener(new Effect2(this.endGame));
		this.addKeyListener(new TAdapter());
		this.addMouseMotionListener(new MyMouseMove(this.changeLevel));
		this.addMouseListener(new MyClick(this.changeLevel));
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if (intro.getRectNewGame().contains(x, y)) {
					isIntro = false;
				}
				if (intro.getRectQuitGame().contains(x, y)) {
					System.exit(0);
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				if (intro.getRectNewGame().contains(x, y)) {
					intro.color1 = Color.red;
					intro.effect.setClick(true);
				} else {
					intro.color1 = Color.DARK_GRAY;
					intro.effect.setClick(false);
				}
				if (intro.getRectQuitGame().contains(x, y)) {
					intro.color2 = Color.red;
					intro.effect.setClick(true);
				} else {
					intro.color2 = Color.DARK_GRAY;
					if (!intro.getRectNewGame().contains(x, y))
						intro.effect.setClick(false);
				}

			}

		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isIntro) {
			intro.doDrawing(g);

		} else if (Tetris.isGameOver) {
			if (this.board.timer != null) {
				this.board.timer.stop();
				this.ballBg.timer.stop();
			}
			endGame.timer.start();
			endGame.doDrawing(g);
		} else {
			if (endGame.timer != null)
				endGame.timer.stop();
			if (this.board.timer != null)
				this.board.timer.start();
			else
				board.startTimer();

			ballBg.timer.start();
			Graphics2D g2d = (Graphics2D) g.create();
			ballBg.doDrawing(g2d);
			g2d.dispose();
			changeLevel.doDrawing(g);
			board.doDrawing(g);

		}
		repaint();
	}

	public void setGame() {
		if (!isIntro) {
			isNewGame = false;
//        	board.startTimer();
		}

	}

	class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (board.curPiece.getShape() == MyShape.NoShape) {

				return;
			}

			int keycode = e.getKeyCode();

			// Java 12 switch expressions
			if (!isIntro) {
				switch (keycode) {

				case KeyEvent.VK_P -> board.pause();
				case KeyEvent.VK_LEFT -> board.tryMove(board.curPiece, board.curX - 1, board.curY);
				case KeyEvent.VK_RIGHT -> board.tryMove(board.curPiece, board.curX + 1, board.curY);
				case KeyEvent.VK_DOWN -> board.tryMove(board.curPiece.rotateRight(), board.curX, board.curY);
				case KeyEvent.VK_UP -> board.tryMove(board.curPiece.rotateLeft(), board.curX, board.curY);
				case KeyEvent.VK_SPACE -> board.dropDown();
				case KeyEvent.VK_D -> board.oneLineDown();
				case KeyEvent.VK_ENTER -> setGame();

				}
			}
		}
	}

	public static void main(String[] args) {

		var game = new Tetris();
		JFrame j = new JFrame("Hello");
		j.add(game);

		j.pack();
		j.setLocationRelativeTo(null);
		j.setVisible(true);

		j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				game.board.setIsPaused(true);
				int confirm = JOptionPane.showOptionDialog(j, "Are You Sure to Close this Application?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == JOptionPane.YES_OPTION) {
					System.exit(1);
				} else {
					game.board.setIsPaused(false);
					j.setVisible(true);
				}
			}
		};

		j.addWindowListener(exitListener);
	}
}

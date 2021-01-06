package side;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import mygame.Board;
import mygame.Tetris;

public class ChangeLevel implements ActionListener {
	Timer timer;
	private static final int STAR_Y = Tetris.PANEL_HEIGHt_GAME - 50;
	private static final int STAR_X = Tetris.PANEL_WIDTH_GAME - 120;
	private String up = "UP";
	private String down = "DOWN";
	private int leftDir = -15;
	private int downDir = 15;
	public RoundRectangle2D rectUp, rectDown;
	public static Color colorUp = new Color(128, 128, 128);
	public static Color colorDown = new Color(128, 128, 128);
	private Ellipse2D selectElipBg, selectedBg, selectElipGhost, selectedGhost;
	private Color colorElipBg, colorElipSlBg;
	private Color colorElipGhost, colorElipSlGhost;
	Tetris tetris;
	public static boolean startChange = false;

	public ChangeLevel(Tetris tetris) {
		this.tetris = tetris;
		timer = new Timer(20, this);
		colorElipBg = new Color(255, 255, 200);
		colorElipGhost = new Color(255, 255, 200);
		colorElipSlBg = new Color(0, 0, 0, 250);
		colorElipSlGhost = new Color(0, 0, 0, 250);
		this.rectDown = new RoundRectangle2D.Float(STAR_X - 70 + leftDir, STAR_Y + downDir, 50, 20, 10, 10);
		this.rectUp = new RoundRectangle2D.Float(STAR_X + 40 + leftDir, STAR_Y + downDir, 50, 20, 10, 10);

		this.selectElipGhost = new Ellipse2D.Double(STAR_X + 50, 102, 12, 12);

		this.selectedGhost = new Ellipse2D.Double(STAR_X + 50 + 2, 104, 8, 8);

		this.selectElipBg = new Ellipse2D.Double(STAR_X + 50, 102 + 20, 12, 12);

		this.selectedBg = new Ellipse2D.Double(STAR_X + 50 + 2, 104 + 20, 8, 8);
	}

	public void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		g2d.setColor(colorDown);
		g2d.fill(rectDown);
		g2d.setColor(colorUp);
		g2d.fill(rectUp);

		g2d.setColor(new Color(75, 20, 255));
		g2d.setFont(new Font("Arial", Font.BOLD, 12));
		g2d.drawString(down, STAR_X - 65 + leftDir, STAR_Y + 15 + downDir);
		g2d.drawString(up, STAR_X + 58 + leftDir, STAR_Y + 15 + downDir);
		g2d.setColor(Side.DRAW_COLOR);
		g2d.setFont(Side.LARGE_FONT);
		g2d.drawString("Change level:", STAR_X - 65 + leftDir - 20, STAR_Y - 15 + downDir);
		g2d.drawString("Display", STAR_X - 65 + leftDir - 20 - 2, 90);
		g2d.setFont(Side.SMALL_FONT);
		g2d.drawString("Shape shadow:", STAR_X - 85, 90 + 20);
		g2d.drawString("Background:", STAR_X - 85, 90 + 42);
		g2d.setColor(colorElipBg);
		g2d.fill(selectElipBg);
		g2d.setColor(colorElipGhost);
		g2d.fill(selectElipGhost);
		g2d.setColor(colorElipSlBg);
		g2d.fill(selectedBg);
		g2d.setColor(colorElipSlGhost);
		g2d.fill(selectedGhost);
		g2d.dispose();
	}

	public RoundRectangle2D getRectUp() {
		return rectUp;
	}

	public void setRectUp(RoundRectangle2D rectUp) {
		this.rectUp = rectUp;
	}

	public RoundRectangle2D getRectDown() {
		return rectDown;
	}

	public void setRectDown(RoundRectangle2D rectDown) {
		this.rectDown = rectDown;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Tetris.isNewGame || Tetris.isIntro) {
			timer.stop();
		} else
			timer.start();

	}

	public Ellipse2D getSelectElipBg() {
		return selectElipBg;
	}

	public void setSelectElipBg(Ellipse2D selectElipBg) {
		this.selectElipBg = selectElipBg;
	}

	public Ellipse2D getSelectElipGhost() {
		return selectElipGhost;
	}

	public void setSelectElipGhost(Ellipse2D selectElipGhost) {
		this.selectElipGhost = selectElipGhost;
	}

	public Color getColorElipBg() {
		return colorElipBg;
	}

	public void setColorElipBg(Color colorElipBg) {
		this.colorElipBg = colorElipBg;
	}

	public Color getColorElipSlBg() {
		return colorElipSlBg;
	}

	public void setColorElipSlBg(Color colorElipSlBg) {
		this.colorElipSlBg = colorElipSlBg;
	}

	public Color getColorElipGhost() {
		return colorElipGhost;
	}

	public void setColorElipGhost(Color colorElipGhost) {
		this.colorElipGhost = colorElipGhost;
	}

	public Color getColorElipSlGhost() {
		return colorElipSlGhost;
	}

	public void setColorElipSlGhost(Color colorElipSlGhost) {
		this.colorElipSlGhost = colorElipSlGhost;
	}

}

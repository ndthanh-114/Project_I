package background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.Timer;

import mygame.Tetris;

public class BallBg implements ActionListener {
	private double x = 50;
	private double y = 100;
	private double dx = 1;
	private double dy = 1;
	private double R = 200;
	public boolean change = false;
	private Color color;
	private int alpha;
	private boolean isBg = true;
	private Ellipse2D ellip;
	Tetris tetris;
	public Timer timer;
	private int DELAY = 10;

	public BallBg(Tetris t) {
		tetris = t;
		ellip = new Ellipse2D.Double();
		alpha = 40;
		color = new Color(255, 255, 254, alpha);
		timer = new Timer(DELAY, this);

	}

	public void doDrawing(Graphics2D g) {
		if (isBg) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.setColor(color.brighter());

			ellip.setFrame(x, y, R, R);
			g.fill(ellip);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (isBg) {
			reset();
			checkChange();
		}
	}

	private void reset() {
		if (x < 0) {
			dx = Math.random();
			change = true;
		} else if (x > Tetris.PANEL_WIDTH_GAME - R) {
			dx = -Math.random();
			change = true;
		}

		if (y < 0) {
			dy = Math.random();
			change = true;
		} else if (y > Tetris.PANEL_HEIGHt_GAME - R) {
			change = true;
			dy = -Math.random();

		}
		x += dx;
		y += dy;
	}

	private void checkChange() {
		if (change) {
			color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
					alpha);
			change = false;
		}
	}

	public boolean getIsBg() {
		return isBg;
	}

	public void setIsBg(boolean isBg) {
		this.isBg = isBg;
	}

}

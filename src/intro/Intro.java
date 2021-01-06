package intro;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.Timer;

import mygame.Tetris;

public class Intro implements ActionListener {

	Timer timer;

	BufferedImage bufBackground;
	public EffectSimple effect;
	private String str = "Tetris";
	private int size;
	Rectangle2D rectNewGame;
	Rectangle2D rectQuitGame;
	public Color color1;
	public Color color2;

	public Intro() {
		start();
	}

	private void start() {
//		this.setPreferredSize(new Dimension(Tetris.PANEL_WIDTH_GAME, Tetris.PANEL_HEIGHt_GAME));
		rectNewGame = new Rectangle2D.Float();
		rectQuitGame = new Rectangle2D.Float();
		size = 20;
		loadImage();
		timer = new Timer(50, this);
		timer.start();
		color1 = Color.DARK_GRAY;
		color2 = Color.DARK_GRAY;
		effect = new EffectSimple();
//		this.setFocusable(true);
//		this.addMouseMotionListener(new MouseMotionAdapter() {
//			public void mouseMoved(MouseEvent e) {
//				int x = e.getX(); 
//	            int y = e.getY();
//	           
//	            if (rectNewGame.contains(x, y)) { 
//	                color1=Color.red;
//	            }else color1=Color.DARK_GRAY;
//	            if(rectQuitGame.contains(x, y)) {
//	            	color2=Color.red;
//	            }else color2=Color.DARK_GRAY;
//	            
//			}
//			
//		});
	}

	private void loadImage() {
		bufBackground = new BufferedImage(Tetris.PANEL_WIDTH_GAME, Tetris.PANEL_HEIGHt_GAME,
				BufferedImage.TYPE_INT_ARGB);
		try {
			bufBackground = ImageIO.read(new File("data/TetrisBgIntro.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		doDrawing(g);
//	}
	public void doDrawing(Graphics g) {

		g.drawImage(bufBackground, 0, 0, null);
		if (effect.isClick())
			effect.doDrawing(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(255, 255, 0));
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		Font font = new Font("Tahoma", Font.BOLD, size);
		g2d.setFont(font);

		FontMetrics fm = g2d.getFontMetrics();

		int stringWidth = fm.stringWidth(str);

		g2d.drawString(str, (Tetris.PANEL_WIDTH_GAME - stringWidth) / 2, Tetris.PANEL_HEIGHt_GAME / 4 - 20);

		g2d.dispose();

		Graphics2D gd = (Graphics2D) g.create();

		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh2.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		gd.setRenderingHints(rh);

		String str2 = "New Game";
		Font font2 = new Font("Tahoma", Font.BOLD, 20);
		gd.setFont(font2);
		gd.setColor(color1);
		FontMetrics fm2 = gd.getFontMetrics();
		int stringWidth2 = fm2.stringWidth(str2);
		gd.drawString(str2, (Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 - 20);

		gd.setColor(new Color(0, 0, 0, 0));
		rectNewGame.setFrame((Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 - 40,
				stringWidth2, 30);
		gd.fill(rectNewGame);

		gd.setColor(color2);
		String str3 = "Quit Game";
		stringWidth2 = fm2.stringWidth(str3);
		gd.drawString(str3, (Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 + 50);
		gd.setColor(new Color(0, 0, 0, 0));
		rectQuitGame.setFrame((Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 + 25,
				stringWidth2, 30);
		gd.fill(rectQuitGame);
		gd.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkIntro();
		step();

	}

	private void checkIntro() {
		if (!Tetris.isIntro) {
			timer.stop();
			effect.timer.stop();
		}
	}

	private void step() {
		size += 1;

		if (size > 50)
			size = 20;
	}

	public Rectangle2D getRectNewGame() {
		return rectNewGame;
	}

	public void setRectNewGame(Rectangle2D rectNewGame) {
		this.rectNewGame = rectNewGame;
	}

	public Rectangle2D getRectQuitGame() {
		return rectQuitGame;
	}

	public void setRectQuitGame(Rectangle2D rectQuitGame) {
		this.rectQuitGame = rectQuitGame;
	}

}

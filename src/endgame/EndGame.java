package endgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.imageio.ImageIO;

import javax.swing.Timer;

import mygame.Tetris;

public class EndGame implements ActionListener {
	public Timer timer;
	private String gameOver = "Game Over";
	int size;
	int x = 0;
	int y = 0;
	double radius = 0;
	Rectangle2D rectNewGame;
	Rectangle2D rectQuitGame;
	int alpha = 30;
	float alpha2 = 0.2f;
	Font font = new Font("Algerian", Font.BOLD, 60);
	Color color1, color2, color3, color4;
	GradientPaint gp;
	BufferedImage bufBackground;
	BufferedImage bluri;
	Tetris tetris;

	public EndGame(Tetris t) {
		tetris = t;
		initStart();
		loadImage();
		createBlurredImage();
	}

	public void initStart() {
//		this.setFocusable(true);
//		this.addMouseListener(new EffectEndGame(this));
//		this.addMouseMotionListener(new Effect2(this));
		timer = new Timer(100, this);

		color1 = new Color(0, 0, 0, alpha2);
		color2 = new Color(240, 240, 240, alpha);
		color3 = new Color(240, 240, 240, 180);
		color4 = new Color(240, 240, 240, 180);
		gp = new GradientPaint(5, 25, 
	            new Color(0,255,0,alpha), 2, 2, new Color(255,0,0,alpha), true);
		rectNewGame = new Rectangle2D.Float();
		rectQuitGame = new Rectangle2D.Float();
//		this.setPreferredSize(new Dimension(Tetris.PANEL_WIDTH_GAME, Tetris.PANEL_HEIGHt_GAME));
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

	private void createBlurredImage() {

		float[] blurKernel = { 1 / 11f, 1 / 11f, 1 / 11f, 1 / 11f, 1 / 11f, 1 / 11f, 1 / 11f, 1 / 11f, 1 / 11f };

		BufferedImageOp blur = new ConvolveOp(new Kernel(3, 3, blurKernel));
		bluri = blur.filter(bufBackground,
				new BufferedImage(bufBackground.getWidth(), bufBackground.getHeight(), bufBackground.getType()));

		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-bluri.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		bluri = op.filter(bluri, null);
	}

	public void initEndGame() {
		this.alpha = 30;
		alpha2 = 0f;
		y = 0;
	}
	
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		doDrawing(g);
//		repaint();
//	}
	public void doDrawing(Graphics g) {

		Graphics2D g3d = (Graphics2D) g.create();
		g3d.drawImage(bluri, null, 0, 0);
		g3d.dispose();

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setPaint(gp);
//		
		Map<TextAttribute, Object> map =
			    new Hashtable<TextAttribute, Object>();
		map.put(TextAttribute.KERNING,TextAttribute.KERNING_ON);
		 map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		font = font.deriveFont(map);
		g2d.setFont(font);
			
		FontMetrics fm = g2d.getFontMetrics();

		int stringWidth = fm.stringWidth(gameOver);

		g2d.drawString(gameOver, (Tetris.PANEL_WIDTH_GAME - stringWidth) / 2, Tetris.PANEL_HEIGHt_GAME / 2 - (120 - y));
		
       
		
		g2d.dispose();

		Graphics2D gd = (Graphics2D) g.create();

		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		rh2.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		gd.setRenderingHints(rh2);

		String str2 = "New Game";
		Font font2 = new Font("Tahoma", Font.BOLD, 20);
		gd.setFont(font2);
		gd.setColor(color3);
		FontMetrics fm2 = gd.getFontMetrics();
		int stringWidth2 = fm2.stringWidth(str2);
		gd.drawString(str2, (Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 - 20 + 20);
		gd.setColor(new Color(0, 0, 0, 0));
		rectNewGame.setFrame((Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 - 40 + 20,
				stringWidth2, 30);
		gd.fill(rectNewGame);

		gd.setColor(color4);
		String str3 = "Quit Game";
		stringWidth2 = fm2.stringWidth(str3);
		gd.drawString(str3, (Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 + 50);
		gd.setColor(new Color(0, 0, 0, 0));
		rectQuitGame.setFrame((Tetris.PANEL_WIDTH_GAME - stringWidth2) / 2, Tetris.PANEL_HEIGHt_GAME / 2 + 25,
				stringWidth2, 30);
		gd.fill(rectQuitGame);
		gd.dispose();
		
	}
	int m=0;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		alpha += 10;
		alpha2 += 0.1f;
		if (alpha > 250)
			alpha = 250;
		if (alpha2 > 0.6f)
			alpha2 = 0.6f;
		color1 = new Color(0, 0, 0, alpha2);
		color2 = new Color(240, 240, 240, alpha);
		gp = new GradientPaint(5, 25, 
	            new Color(255,0,0,alpha).brighter(), 2, 2, new Color(255,255,0,alpha).brighter(), true);
		y++;
		if (y > 60) {
			y = 60;
			gp = new GradientPaint(5, 25+(int)(m%15), 
	            new Color(255,0,0,alpha).brighter(), 2, 2, new Color(255,255,0,alpha).brighter(), true);
			m++;
		}

	}

	public void setColor3InTimer() {
		color3 = new Color(0, 0, 255, alpha);
	}

	public void setColor4InTimer() {
		color4 = new Color(0, 0, 255, alpha);
	}

	public String getGameOver() {
		return gameOver;
	}

	public void setGameOver(String gameOver) {
		this.gameOver = gameOver;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
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

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public float getAlpha2() {
		return alpha2;
	}

	public void setAlpha2(float alpha2) {
		this.alpha2 = alpha2;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public Color getColor3() {
		return color3;
	}

	public void setColor3(Color color3) {
		this.color3 = color3;
	}

	public Color getColor4() {
		return color4;
	}

	public void setColor4(Color color4) {
		this.color4 = color4;
	}
//	public static void main(String args[]) {
//		JFrame f=new JFrame("a");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		EndGame endGame=new EndGame();
//		f.add(endGame);
//		f.pack();
//		f.setVisible(true);
//	}
}

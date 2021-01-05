package intro;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mygame.Tetris;

public class EffectSimple implements ActionListener{
	public Timer timer;
	private boolean isClick=false;
	private float alpha=0f;
	private Color color;
	public EffectSimple() {
		initStart();
	}
	private void initStart() {
		timer=new Timer(50, this);
		timer.start();
	}
	public void doDrawing(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, Tetris.PANEL_WIDTH_GAME, Tetris.PANEL_HEIGHt_GAME);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(isClick) {
			alpha+=0.1f;
			if(alpha>0.6f)
				alpha=0.6f;
		}else {
			alpha=0f;
		}
		color=new Color(0,0,0,alpha);
	}

	public boolean isClick() {
		return isClick;
	}
	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}	

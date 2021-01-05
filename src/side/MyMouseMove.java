package side;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import mygame.Tetris;


public class MyMouseMove extends MouseMotionAdapter{
	ChangeLevel change;
	Color color=new Color(128,128,128);
	int yk=0;
	public MyMouseMove(ChangeLevel end) {
		this.change=end;
	}
	public void mouseMoved(MouseEvent e) {
				double x=e.getX();
				double y=e.getY();
					
				if(change.getRectDown().contains(x, y)) {
					ChangeLevel.colorDown=color.brighter();

				}
				else {
					ChangeLevel.colorDown=new Color(128,128,128);
					
				}
				
				if(change.getRectUp().contains(x, y)) {
					ChangeLevel.colorUp=color.brighter();

				}
				else {
					ChangeLevel.colorUp=new Color(128,128,128);
					
				}
	}
}

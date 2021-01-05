package side;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mygame.Tetris;

public class MyClick extends MouseAdapter{
	ChangeLevel change;	
	Color color=new Color(128,128,128);
	int x=0;
	public MyClick(ChangeLevel change) {
		this.change=change;

	}
	public void mousePressed(MouseEvent e) {
				int x1=e.getX();
				int y=e.getY();
				if(change.getRectUp().contains(x1, y)) {
					ChangeLevel.colorUp=color.darker();

					change.tetris.board.setChangeUpLevel();
				}
				if(change.getRectDown().contains(x1, y)) {
					ChangeLevel.colorDown=color.darker();
	
					change.tetris.board.setChangeDownLevel();
				}
						
	}
	public void mouseReleased(MouseEvent e) {

			int x1=e.getX();
			int y=e.getY();
			if(change.getRectUp().contains(x1, y)) {
				
				ChangeLevel.colorUp=color;
			}
			if(change.getRectDown().contains(x1, y)) {

				ChangeLevel.colorDown=color;
		}
	}

}

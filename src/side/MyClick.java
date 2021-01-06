package side;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mygame.Tetris;

public class MyClick extends MouseAdapter {
	ChangeLevel change;
	Color color = new Color(128, 128, 128);
	int x = 0;

	public MyClick(ChangeLevel change) {
		this.change = change;

	}

	public void mousePressed(MouseEvent e) {
		int x1 = e.getX();
		int y = e.getY();
		if (change.getRectUp().contains(x1, y)) {
			ChangeLevel.colorUp = color.darker();

			change.tetris.board.setChangeUpLevel();
		} else if (change.getRectDown().contains(x1, y)) {
			ChangeLevel.colorDown = color.darker();

			change.tetris.board.setChangeDownLevel();
		} else if (change.getSelectElipGhost().contains(x1, y)) {
			if (change.tetris.board.getIsGhost()) {
				change.tetris.board.setIsGhost(false);
				change.setColorElipSlGhost(new Color(0, 0, 0, 0));
			} else {
				change.tetris.board.setIsGhost(true);
				change.setColorElipSlGhost(new Color(0, 0, 0, 250));
			}
		} else if (change.getSelectElipBg().contains(x1, y)) {
			if (change.tetris.ballBg.getIsBg()) {
				change.tetris.ballBg.setIsBg(false);
				change.setColorElipSlBg(new Color(0, 0, 0, 0));
			} else {
				change.tetris.ballBg.setIsBg(true);
				change.setColorElipSlBg(new Color(0, 0, 0, 250));
			}
		}

	}

	public void mouseReleased(MouseEvent e) {

		int x1 = e.getX();
		int y = e.getY();
		if (change.getRectUp().contains(x1, y)) {

			ChangeLevel.colorUp = color;
		}
		if (change.getRectDown().contains(x1, y)) {

			ChangeLevel.colorDown = color;
		}
	}

}

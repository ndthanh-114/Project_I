package endgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mygame.Tetris;

public class EffectEndGame extends MouseAdapter{
	
	EndGame endGame;
	Tetris tetris;
	public EffectEndGame(EndGame endGame, Tetris tetris) {
		this.endGame=endGame;
		this.tetris=tetris;
	}
	public void mousePressed(MouseEvent e) {
		double x=e.getX();
		double y=e.getY();
		if(endGame.getRectNewGame().contains(x, y)) {
			Tetris.isNewGame=true;
			Tetris.isGameOver=false;
			endGame.timer.stop();
			endGame.initEndGame();
			tetris.board.clearBoard();
		}
		if(endGame.getRectQuitGame().contains(x, y)) {
			System.exit(0);
		}
	}
	
}

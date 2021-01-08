package endgame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mygame.Tetris;

public class EffectEndGame extends MouseAdapter {

	EndGame endGame;
	Tetris tetris;

	public EffectEndGame(EndGame endGame, Tetris tetris) {
		this.endGame = endGame;
		this.tetris = tetris;
	}

	public void mousePressed(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		if (endGame.getRectNewGame().contains(x, y)&&Tetris.isGameOver) {
			Tetris.isNewGame = true;
			Tetris.isGameOver = false;
			endGame.timer.stop();
			endGame.initEndGame();
			tetris.board.clearBoard();
			endGame.tetris.changeLevel.setColorElipSlBg(new Color(0, 0, 0));
			endGame.tetris.changeLevel.setColorElipSlGhost(new Color(0, 0, 0));
			endGame.tetris.ballBg.setIsBg(true);
		}
		if (endGame.getRectQuitGame().contains(x, y)&&Tetris.isGameOver) {
			System.exit(0);
		}
	}

}

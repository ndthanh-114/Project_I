package endgame;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Effect2 extends MouseMotionAdapter{
	
		EndGame endGame2;
		public Effect2(EndGame end) {
			this.endGame2=end;
		}
		public void mouseMoved(MouseEvent e) {
			double x=e.getX();
			double y=e.getY();
				
			if(endGame2.getRectNewGame().contains(x, y)) {
				endGame2.setColor3(Color.yellow);
			}
			else {
				endGame2.setColor3(new Color(240, 240, 240,180));
				
			}
			if(endGame2.getRectQuitGame().contains(x, y)) {
				endGame2.setColor4(Color.yellow);
		
			}else {
				endGame2.setColor4(new Color(240, 240, 240,180));
			}
		}
	}
package side;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import mygame.Board;
import mygame.MyColor;
import mygame.Shape;
import side.*;

public class Side implements MyColor{
private static final int TILE_SIZE = Board.TILE_SIZE >> 1;
	
	
	//The width of the shading on each tile on the current piece preview.
	
	private static final int SHADE_WIDTH = Board.SHADE_WIDTH >> 1;
	//the center x of the current piece
	private static final int SQUARE_CENTER_X = 130;
	
	//the center y of the current piece
	private static final int SQUARE_CENTER_Y = 65;
	
	
	private static final int SMALL_INSET = 20;
	
	private static final int LARGE_INSET = 40;
	
	private static final int STATS_INSET = 150;
	
	
	//the y coordinate of the string controls
	private static final int CONTROLS_INSET = 250;
	
	//the number pixel each row
	private static final int TEXT_STRIDE = 25;
	
	
	//set the small font
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
	//large font
	public static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
	
	//color draw in the side
	public static final Color DRAW_COLOR = new Color(128, 192, 128);

	private Board board;
	

	public Side(Board board) {
		this.board = board;

	}
	public void doDrawing(Graphics g) {
		
		//Set the color for drawing.
		g.setColor(DRAW_COLOR);
		
		int offset;
		
		
		//Draw side
		g.setFont(LARGE_FONT);
		g.drawString("Status", SMALL_INSET, offset = STATS_INSET);
		g.setFont(SMALL_FONT);
		g.drawString("Level: "+String.valueOf(board.getLevel()), LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("Score: " + String.valueOf(board.getNumLinesRemoved()), LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("Delay:" +Board.DELAY, LARGE_INSET, offset += TEXT_STRIDE);
		
		//Draw the "Controls" category.
		
		g.setFont(LARGE_FONT);
		g.drawString("Controls", SMALL_INSET, offset = CONTROLS_INSET);
			
		g.setFont(SMALL_FONT);
		g.drawString("LEFT - Move Left", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("RIGHT - Move Right", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("DOWN - Rotate Left", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("UP - Rotate Right", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("SPACE - Drop", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("D - One Line Drop", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("PAUSE - Pause Game", LARGE_INSET, offset += TEXT_STRIDE);

		g.setFont(LARGE_FONT);
		g.drawString("Next Piece:", SMALL_INSET, 50);
		
		//draw current Piece
		if(board.isGameOver()) {
			
		}else {
			Shape.MyShape type = board.nextPiece.getShape();
			
			
			int startX = (SQUARE_CENTER_X - (3 * TILE_SIZE / 2));
			int startY = (SQUARE_CENTER_Y - (2 * TILE_SIZE / 2));
			
			for (int i = 0; i < 4; i++) {
				
	            int x =board.nextPiece.x(i);
	            int y =board.nextPiece.y(i);

	            drawTile(type, x * TILE_SIZE+startX+20,
	                    y*TILE_SIZE+startY-15,g);
	        }
		}
		
	}
	
	
	//Draws a tile
	private void drawTile(Shape.MyShape type, int x, int y, Graphics g) {
	
		var color = colors[type.ordinal()];
		g.setColor(color);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		
		//Fill the bottom and right edges of the tile with the dark shading color.
		
		g.setColor(color.darker());
		g.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);
		
	
		//Fill the top and left edges with the light shading.
		g.setColor(color.brighter());
		for(int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
	}
}

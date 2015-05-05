package com.invisi.snake;

import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Food implements Renderable {
	int x;
	int y;
	
	GRect rect;
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
		
		rect = new GRect(Main.TILE_SIZE, Main.TILE_SIZE);
		
		create(Main.instance());
		
		rect.setLocation(x * Main.TILE_SIZE, y * Main.TILE_SIZE);
		rect.setFilled(true);
		rect.setColor(Color.GREEN);
	}

	@Override
	public void create(GraphicsProgram prog) {
		prog.add(rect);
	}

	@Override
	public void destroy(GraphicsProgram prog) {
		prog.remove(rect);
	}
}

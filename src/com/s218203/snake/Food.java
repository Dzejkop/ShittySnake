package com.s218203.snake;

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
		
		rect = new GRect(20, 20);
		
		create(Main.instance());
		
		rect.setLocation(x * 20, y * 20);
		rect.setFilled(true);
		rect.setColor(Color.BLUE);
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

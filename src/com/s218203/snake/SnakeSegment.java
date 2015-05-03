package com.s218203.snake;

import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class SnakeSegment implements Renderable {
	
	GRect rect;
	
	int x, y;
	int lastX, lastY;
	
	float energy = 0; // <0, 1>
	
	Color dormantColor;
	Color spikedColor;
	
	public SnakeSegment(int x, int y) {
		this.x = x;
		this.y = y;
		lastX = x;
		lastY = y;
		
		dormantColor = Color.GRAY;
		spikedColor = Color.GREEN;
		
		rect = new GRect(20, 20);
		rect.setColor(dormantColor);
		rect.setFilled(true);
	}
	
	public SnakeSegment(int x, int y, Color dormantColor, Color spikedColor) {
		this.x = x;
		this.y = y;
		lastX = x;
		lastY = y;
		
		this.dormantColor = dormantColor;
		this.spikedColor = spikedColor;
		
		rect = new GRect(20, 20);
		rect.setColor(dormantColor);
		rect.setFilled(true);
	}

	@Override
	public void create(GraphicsProgram prog) {
		prog.add(rect);
	}

	@Override
	public void destroy(GraphicsProgram prog) {
		prog.remove(rect);
	}
	
	public void update() {
		
		
		float dRed = (float)(dormantColor.getRed() / 255.0f);
		float sRed = (float)(spikedColor.getRed() / 255.0f);
		
		float Red = (1-energy)*dRed + (energy*sRed);
		if(Red > 1) Red = 1;
		
		
		float dBlue = (float)(dormantColor.getBlue() / 255.0f);
		float sBlue = (float)(spikedColor.getBlue() / 255.0f);
		
		float Blue = (1-energy)*dBlue + (energy*sBlue);
		if(Blue > 1) Blue = 1;
		
		
		float dGreen = (float)(dormantColor.getGreen() / 255.0f);
		float sGreen = (float)(spikedColor.getGreen() / 255.0f);
		
		float Green = (1-energy)*dGreen + (energy*sGreen);
		if(Green > 1) Green = 1;
		
		// Actual color
		Color n = new Color(Red, Green, Blue);
		rect.setColor(n);
		
		// Energy decay
		energy *= 0.9f;
	}
	
	public void spike(float val) {
		energy += val;
		if(energy > 1) energy = 1;
	}
	
	public void follow(SnakeSegment s) {
		move(s.lastX, s.lastY);
	}
	
	public void move(int newX, int newY) {
		lastX = x;
		lastY = y;
		
		x = newX;
		y = newY;
		
		// Update the GObject
		rect.setLocation(x*20,  y*20);
	}
}

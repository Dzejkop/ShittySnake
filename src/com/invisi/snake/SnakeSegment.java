package com.invisi.snake;

import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class SnakeSegment implements Renderable {
	
	GRect rect;
	
	int x, y;
	int lastX, lastY;
	
	float energy = 0; // <0, 1>
	float energyDecay = 0.5f;
	
	Color dormantColor;
	Color spikedColor;
	
	public SnakeSegment(int x, int y) {
		this.x = x;
		this.y = y;
		lastX = x;
		lastY = y;
		
		dormantColor = new Color(1, 1, 1, 0.0f);
		spikedColor = new Color(1, 1, 1, 1);
		
		rect = new GRect(Main.TILE_SIZE, Main.TILE_SIZE);
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
		
		rect = new GRect(Main.TILE_SIZE, Main.TILE_SIZE);
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
		
		// Red
		float dRed = (float)(dormantColor.getRed() / 255.0f);
		float sRed = (float)(spikedColor.getRed() / 255.0f);
		
		float Red = (1-energy)*dRed + (energy*sRed);
		if(Red > 1) Red = 1;
		
		// Blue
		float dBlue = (float)(dormantColor.getBlue() / 255.0f);
		float sBlue = (float)(spikedColor.getBlue() / 255.0f);
		
		float Blue = (1-energy)*dBlue + (energy*sBlue);
		if(Blue > 1) Blue = 1;
		
		// Green
		float dGreen = (float)(dormantColor.getGreen() / 255.0f);
		float sGreen = (float)(spikedColor.getGreen() / 255.0f);
		
		float Green = (1-energy)*dGreen + (energy*sGreen);
		if(Green > 1) Green = 1;
		
		// Alpha
		float dAlpha = (float)(dormantColor.getAlpha() / 255.0f);
		float sAlpha = (float)(spikedColor.getAlpha() / 255.0f);
		
		float Alpha = (1-energy)*dAlpha + (energy*sAlpha);
		if(Alpha > 1) Alpha = 1;
		
		
		
		// Actual color
		Color n = new Color(Red, Green, Blue, Alpha);
		rect.setColor(n);
		
		// Energy decay
		energy *= energyDecay;
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
		rect.setLocation(x*Main.TILE_SIZE,  y*Main.TILE_SIZE);
	}
}

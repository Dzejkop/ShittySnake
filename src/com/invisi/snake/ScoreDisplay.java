package com.invisi.snake;

import java.awt.Color;

import acm.graphics.GLabel;

public class ScoreDisplay {

	GLabel scoreDisplay;
	
	public ScoreDisplay() {
		
		scoreDisplay = new GLabel("Score: ");
		
		scoreDisplay.setFont(Main.instance().bulkyFont.deriveFont(100.0f));
		scoreDisplay.setColor(Color.WHITE);
		
		scoreDisplay.setLocation(Main.RESX/2 - (scoreDisplay.getWidth()/2), Main.RESY - Main.TILE_SIZE);
		
		Main.instance().add(scoreDisplay);
		
	}
	
	public void setScore(int score) {
		scoreDisplay.setLabel("Score: " + score);
		scoreDisplay.setLocation(Main.RESX/2 - (scoreDisplay.getWidth()/2), Main.RESY - Main.TILE_SIZE);
	}
	
	public void resetScore() {
		scoreDisplay.setLabel("Score: " + "0");
		scoreDisplay.setLocation(Main.RESX/2 - (scoreDisplay.getWidth()/2), Main.RESY - Main.TILE_SIZE);
	}

	public void bringToFront() {
		scoreDisplay.sendToFront();
	}
	
	public void bringToBack() {
		scoreDisplay.sendToBack();
	}
	
}

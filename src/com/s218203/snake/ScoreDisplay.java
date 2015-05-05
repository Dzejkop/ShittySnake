package com.s218203.snake;

import java.awt.Color;
import java.awt.Font;

import acm.graphics.GLabel;

public class ScoreDisplay {

	GLabel scoreDisplay;
	
	public ScoreDisplay() {
		
		scoreDisplay = new GLabel("Score: ");
		
		scoreDisplay.setFont(Font.SANS_SERIF);
		scoreDisplay.setFont(scoreDisplay.getFont().deriveFont(120.0f));
		scoreDisplay.setColor(new Color(1,1,1,0.5f));
		
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
	
}

package com.s218203.snake;

import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GRect;

public class Button {

	public static final double BUTTON_WIDTH = 200;
	public static final double BUTTON_HEIGHT = 50;
	
	double x = 0;
	double y = 0;
	
	GRect background;
	GRect highlight;
	GLabel text;
	
	public Button(String label) {
		text = new GLabel(label);
		text.setColor(Color.BLACK);
		text.setFont(Main.instance().bulkyFont.deriveFont(24.0f));
		
		background = new GRect(BUTTON_WIDTH, BUTTON_HEIGHT);
		background.setFilled(true);
		background.setColor(Color.WHITE);
		highlight = new GRect(BUTTON_WIDTH, BUTTON_HEIGHT);
		highlight.setFilled(true);
		highlight.setColor(new Color(1, 0, 0, 0.7f));
		
		unHighlight();
		
		Main.instance().add(background);
		Main.instance().add(highlight);
		Main.instance().add(text);
	}
	
	public void setPos(int x, int y) {
		setPos((double)x, (double)y);
	}
	
	public void setPos(double x, double y) {
		background.setLocation(x, y);
		highlight.setLocation(x, y);
		
		text.setLocation(x + (BUTTON_WIDTH/2) - (text.getWidth()/2), y + (BUTTON_HEIGHT/2) + (text.getHeight()/4));
		
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public double getWidth() {
		return BUTTON_WIDTH;
	}
	
	public double getHeight() {
		return BUTTON_HEIGHT;
	}
	
	public void highlight() {
		highlight.setVisible(true);
	}
	
	public void unHighlight() {
		highlight.setVisible(false);
	}
	
	OnClickListener listener;
	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}
	
	public void onClick() {
		listener.onClick();
	}
	
	public static Button getExitButton() {
		Button b = new Button("Exit");
		
		// Create a listener
		b.setListener(new OnClickListener() {

			@Override
			public void onClick() {
				Main.instance().quitGame();
			}
			
		});
		return b;
	}

	public static Button getPlayButton() {
		Button b = new Button("Play");
		
		// Create a listener
		b.setListener(new OnClickListener() {

			@Override
			public void onClick() {
				Main.instance().startGame();
			}
			
		});
		return b;
	}
	
	static interface OnClickListener {
		public void onClick();
	}

	public void sendToFront() {
		background.sendToFront();
		highlight.sendToFront();
		text.sendToFront();
	}
}

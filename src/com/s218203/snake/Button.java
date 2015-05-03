package com.s218203.snake;

import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GRect;

public class Button {

	double x = 0;
	double y = 0;
	
	GRect background;
	GRect highlight;
	GLabel text;
	
	public Button(String label) {
		text = new GLabel(label);
		text.setColor(Color.BLACK);
		
		background = new GRect(200, 50);
		background.setFilled(true);
		background.setColor(Color.GRAY);
		highlight = new GRect(200, 50);
		highlight.setFilled(true);
		highlight.setColor(new Color(0, 1, 0, 0.7f));
		
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
		text.setLocation(x + 20, y);
		
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
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
		text.sendToFront();
		highlight.sendToFront();
	}
}

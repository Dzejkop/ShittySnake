package com.s218203.snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GRect;

public class MainMenu {

	public static final float I_SP = 0.1f;
	
	// Buttons in the current context
	List<Button> activeButtons;
	
	// Buttons outside
	List<Button> buttons;
	
	// Background panel
	GRect backgroundPanel;
	
	
	// Which of the active buttons is in focus
	int buttonInFocus = 0;
	
	// State
	enum MenuState {
		Active,
		Inactive,
	}
	MenuState state = MenuState.Active;
	
	public MainMenu() {
		buttons = new ArrayList<>();
		activeButtons = new ArrayList<>();
		
		backgroundPanel = new GRect(0, 0, Main.RESX, Main.RESY);
		backgroundPanel.setColor(new Color(0, 0, 0, 0.9f));
		backgroundPanel.setFilled(true);
		
		Main.instance().add(backgroundPanel);
	}
	
	public void update() {	
		interpolateElements();
	}
	
	public void createButtons() {
		Button b = Button.getPlayButton();
		b.setPos(0, Main.RESY/4*1);
		buttons.add(b);
		
		b = Button.getExitButton();
		b.setPos(0, Main.RESY/4*2);
		buttons.add(b);
	}
	
	public void setActive(boolean state) {
		if(state) {
			this.state = MenuState.Active;
			
			backgroundPanel.sendToFront();
			
			for(Button b : buttons) {
				b.sendToFront();
			}
			
		} else {
			this.state = MenuState.Inactive;
		}
	}
	
	void interpolateElements() {
		// Interpolate the background panel
		if(state == MenuState.Inactive) {
			backgroundPanel.setLocation(
					backgroundPanel.getX() + ((-810 - backgroundPanel.getX()) * I_SP), 
					backgroundPanel.getY());
		} else {
			backgroundPanel.setLocation(
					backgroundPanel.getX() + ((0 - backgroundPanel.getX()) * I_SP), 
					backgroundPanel.getY());
		}
		
		// Interpolate buttons
		for(Button b : buttons) {
			if(state == MenuState.Active) {
				b.setPos(
						b.x() + (((Main.RESX/2 - (b.getWidth()/2)) - b.x()) * I_SP),
						b.y());
			} else {
				b.setPos(
						b.x() + ((-810 - b.x()) * I_SP),
						b.y());
			}
		}
	}
	
	// Input
	public void goDown() {
		buttons.get(buttonInFocus).unHighlight();
		if(buttonInFocus == buttons.size() - 1) {
			buttonInFocus = 0;
		} else {
			buttonInFocus++;
		}
		buttons.get(buttonInFocus).highlight();
	}
	
	public void goUp() {
		buttons.get(buttonInFocus).unHighlight();
		if(buttonInFocus == 0) {
			buttonInFocus = buttons.size()-1;
		} else {
			buttonInFocus--;
		}
		buttons.get(buttonInFocus).highlight();
	}
	
	public void confirm() {
		buttons.get(buttonInFocus).onClick();
	}
}

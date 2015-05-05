package com.s218203.snake;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class Snake {
	
	int directionX = 0;
	int directionY = 0;
	
	// The first segment is a head
	List<SnakeSegment> body;
	
	Color headDormantColor = Color.GREEN;
	Color headSpikedColor = Color.WHITE;
	Color bodyDormantColor = new Color(1, 1, 1, 0);
	Color bodySpikedColor = Color.WHITE;
	
	public Snake(int startX, int startY) {
		body = new LinkedList<SnakeSegment>();
		
		// Add the head
		body.add(new SnakeSegment(startX, startY, headDormantColor, headSpikedColor));
		body.get(0).create(Main.instance());
	}
	
	void update() {
		
		for(int i = 0 ; i < body.size(); i++) {
			
			body.get(i).update();
			
			if(i == 0) {
				// It's the head
				moveHead();
			} else {
				// It's not the head
				body.get(i).follow(body.get(i-1));
			}
		}
	}
	
	public void grow() {
		SnakeSegment last = body.get(body.size()-1);
		
		SnakeSegment n = new SnakeSegment(last.x, last.y, bodyDormantColor, bodySpikedColor);
		
		n.create(Main.instance());
		
		body.add(n);
	}
	
	public void eat() {
		grow();
		
		spike();
	}
	
	public void spike() {
		for(SnakeSegment s : body) {
			s.spike(1);
		}
	}
	
	void moveHead() {
		SnakeSegment head = body.get(0);
		
		int nx = head.x + directionX;
		int ny = head.y + directionY;
		
		head.move(nx,  ny);
	}
	
	public void setDirection(int x, int y) {
		if((x * directionX) + (y * directionY) != 0) return;
		
		directionX = x;
		directionY = y;
	}

	public boolean checkSelfCollisions() {
		
		SnakeSegment head = body.get(0);
		
		// Impossible to self collide
		if(body.size() < 3) return false;
		
		for(SnakeSegment s : body) {
			if(s != head && head.x == s.x && head.y == s.y) {
				spike();
				return true;
			}
		}
		
		return false;
	}

	public void reset(int startX, int startY) {
		for(SnakeSegment s : body) {
			s.destroy(Main.instance());
		}
		
		body.clear();
		
		// Add the head
		body.add(new SnakeSegment(startX, startY, headDormantColor, headSpikedColor));
		body.get(0).create(Main.instance());
		
		directionX = 0;
		directionY = 0;
	}
	
	
}

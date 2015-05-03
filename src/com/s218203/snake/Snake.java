package com.s218203.snake;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class Snake {
	
	int directionX = 0;
	int directionY = 0;
	
	// The first segment is a head
	List<SnakeSegment> body;
	
	public Snake() {
		body = new LinkedList<SnakeSegment>();
		
		// Add the head
		body.add(new SnakeSegment(0,0, Color.GRAY, Color.WHITE));
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
		
		SnakeSegment n = new SnakeSegment(last.x, last.y, Color.GRAY, Color.GREEN);
		
		n.create(Main.instance());
		
		body.add(n);
	}
	
	public void eat() {
		grow();
		
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
		directionX = x;
		directionY = y;
	}

	public boolean checkSelfCollisions() {
		
		SnakeSegment head = body.get(0);
		
		for(SnakeSegment s : body) {
			if(s != head && head.x == s.x && head.y == s.y) return true;
		}
		
		return false;
	}
	
	
}

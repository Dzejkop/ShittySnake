package com.s218203.snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acm.program.GraphicsProgram;

public class Main extends GraphicsProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1194672956535071872L;

	// Values
	public static final int RESX = 800;
	public static final int RESY = 600;
	public static final int TILE_SIZE = 40;
	
	// Lists
	List<Food> foods;
	List<Wall> walls;

	// Snake
	Snake snake;
	
	// Score displayer
	ScoreDisplay scoreDisplay;
	int score = 0;

	// Singleton instance
	private static Main instance;

	// Main menu
	MainMenu menu;

	// Game state stuff
	enum GameState {
		Gameplay, Menu
	}
	GameState gameState = GameState.Menu;

	public static Main instance() {
		return instance;
	}

	@Override
	public void run() {
		// Singleton
		instance = this;
		
		initialize();

		// Main game loop
		while (isRunning()) {
			update();
		}
	}

	void update() {
		if(gameState == GameState.Gameplay) gameLoop();
		menuLoop();
		
		pause(60 / 100000);
	}
	
	// Primitive turn management
	int sinceLastTurn = 0;
	void gameLoop() {
		
		sinceLastTurn++;
		if(sinceLastTurn < 100000) return;
		else {
			sinceLastTurn = 0;
		}
		
		snake.update();

		// Spawn foods
		if (foods.size() < 5) {
			spawnFood();
		}

		detectCollisions();
	}

	void menuLoop() {
		menu.update();
	}

	boolean isRunning() {
		return true;
	}
	
	void switchMode(GameState newState) {
		if(newState == GameState.Gameplay && gameState == GameState.Menu) {
			menu.setActive(false);
		}
		else if(newState == GameState.Menu && gameState == GameState.Gameplay) {
			menu.setActive(true);
		}
		
		gameState = newState;
	}

	void initialize() {
		addKeyListeners();

		// Set resolution
		this.setSize(RESX, RESY);
		
		// Initialize lists
		foods = new ArrayList<Food>();
		walls = new ArrayList<Wall>();

		// Initialize snake
		snake = new Snake();

		// Set background color
		setBackground(Color.BLACK);
		
		scoreDisplay = new ScoreDisplay();
		
		// Create the menu
		menu = new MainMenu();
		menu.createButtons();
	}

	void spawnFood() {
		Random r = new Random();

		int x = 1 + r.nextInt(20);
		int y = 1 + r.nextInt(20);

		foods.add(new Food(x, y));
	}

	void detectCollisions() {
		SnakeSegment head = snake.body.get(0);

		ArrayList<Food> foodsToRemove = new ArrayList<Food>();

		// Check if it collided with any food
		for (Food f : foods) {
			if (f.x == head.x && f.y == head.y) {
				snake.eat();
				foodsToRemove.add(f);
				score++;
				scoreDisplay.setScore(score);
			}
		}

		for (Food f : foodsToRemove) {
			f.destroy(this);
			foods.remove(f);
		}

		// Check for collisions with tail
		if (snake.checkSelfCollisions() == true) {
			switchMode(GameState.Menu);
		}
	}

	// Input management
	@Override
	public void keyPressed(KeyEvent e) {
		if (gameState == GameState.Gameplay) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				snake.setDirection(0, -1);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				snake.setDirection(0, 1);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				snake.setDirection(-1, 0);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				snake.setDirection(1, 0);
			} else if (e.getKeyChar() == KeyEvent.VK_G) {
				snake.grow();
			}
		} else if (gameState == GameState.Menu) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.goUp();
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.goDown();
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.confirm();
			}
		}
	}

	public void quitGame() {
		this.exit();
	}

	public void startGame() {
		switchMode(GameState.Gameplay);
	}
}

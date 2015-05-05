package com.invisi.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acm.program.GraphicsProgram;
import acmx.export.java.io.FileInputStream;

public class Main extends GraphicsProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1194672956535071872L;

	// Values
	public static final int RESX = 800;
	public static final int RESY = 600;
	public static final int TILE_SIZE = 40;
	
	// Framerate management
	public static final int FPS = 60;
    public static final int SKIP_TICKS = 1000 / FPS;
    int nextGameTick;
    int sleepTime = 0;
	
	int boardWidth;
	int boardHeight;
	int snakeStartingX;
	int snakeStartingY;
	
	// Font for the game
	Font bulkyFont;
	
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

		nextGameTick = getTickCount();
		
		// Main game loop
		while (isRunning()) {
			update();
		}
	}

	void update() {
		if(gameState == GameState.Gameplay) gameLoop();
		menuLoop();
		
		nextGameTick += SKIP_TICKS;
		sleepTime = nextGameTick - getTickCount();
		if(sleepTime >= 0) {
			pause(sleepTime);
		} else {
		}
	}
	
	// Primitive turn management
	int sinceLastTurn = 0;
	int framesPerTurn = 7;
	void gameLoop() {
		
		sinceLastTurn++;
		if(sinceLastTurn < framesPerTurn) return;
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
	
	int getTickCount() {
		return (int) (System.nanoTime() / 1000000);
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
			scoreDisplay.bringToBack();
		}
		else if(newState == GameState.Menu && gameState == GameState.Gameplay) {
			menu.setActive(true);
			scoreDisplay.bringToFront();
		}
		
		gameState = newState;
	}
	
	void resetGame() {
		snake.reset(snakeStartingX, snakeStartingY);
		score = 0;
		scoreDisplay.resetScore();
	}
	
	public void quitGame() {
		System.exit(0);
	}

	public void startGame() {
		switchMode(GameState.Gameplay);
		resetGame();
	}

	// Initialize all the objects, lists and values
	void initialize() {
		addKeyListeners();

		// Set resolution
		this.setSize(RESX, RESY);
		
		// Determine board size
		boardWidth = RESX/TILE_SIZE;
		boardHeight = RESY/TILE_SIZE;
		
		snakeStartingX = boardWidth/2;
		snakeStartingY = boardHeight/2;
		
		// Create the font
		try {
			bulkyFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("BULKYPIX.TTF"));
		} catch (FontFormatException | IOException e) {

		}		
		
		// Initialize lists
		foods = new ArrayList<Food>();
		walls = new ArrayList<Wall>();
		
		// Generate walls
		// Generate top and bottom
		for(int x = 0; x < boardWidth; x++) {
			walls.add(new Wall(x, 0));
			walls.add(new Wall(x, boardHeight-1));
		}
		// Genrate sides
		for(int y = 1; y < boardHeight-1; y++) {
			walls.add(new Wall(0, y));
			walls.add(new Wall(boardWidth-1, y));
		}
		
		// Initialize snake
		snake = new Snake(snakeStartingX, snakeStartingY);

		// Set background color
		setBackground(Color.BLACK);
		
		scoreDisplay = new ScoreDisplay();
		
		// Create the menu
		menu = new MainMenu();
		menu.createButtons();
		scoreDisplay.bringToFront();
	}

	void spawnFood() {
		Random r = new Random();

		int x = 1 + r.nextInt(boardWidth-2);
		int y = 1 + r.nextInt(boardHeight-2);

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

		// Remove foods if necessary
		for (Food f : foodsToRemove) {
			f.destroy(this);
			foods.remove(f);
		}
		
		// Chech for collisions with walls
		for(Wall w : walls) {
			if(w.x == head.x && w.y == head.y) {
				gameOver();
			}
		}

		// Check for collisions with tail
		if (snake.checkSelfCollisions() == true) {
			gameOver();
		}
	}

	private void gameOver() {
		switchMode(GameState.Menu);
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
}

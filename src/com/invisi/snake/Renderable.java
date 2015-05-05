package com.invisi.snake;

import acm.program.GraphicsProgram;

public interface Renderable {
	public void create(GraphicsProgram prog);
	public void destroy(GraphicsProgram prog);
}

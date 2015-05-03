package com.s218203.snake;

import acm.program.GraphicsProgram;

public interface Renderable {
	public void create(GraphicsProgram prog);
	public void destroy(GraphicsProgram prog);
}

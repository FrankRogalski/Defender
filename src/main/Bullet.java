package main;

import javafx.scene.canvas.GraphicsContext;

public class Bullet extends Ding {
	private final double height = 2, width = 10;
	
	public Bullet(GraphicsContext gc, double x, double y) {
		this.gc = gc;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		x -= 5;
	}
	
	@Override
	public void show() {
		gc.fillRect(x, y, width, height);
	}
	
	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public double getWidth() {
		return width;
	}
}
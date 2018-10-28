package main;

import javafx.scene.canvas.GraphicsContext;

public abstract class Ding {
	protected GraphicsContext gc;
	protected double x, y;
	public abstract void show();
	public abstract double getWidth();
	public abstract double getHeight();
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}

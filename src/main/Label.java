package main;

import javafx.scene.canvas.GraphicsContext;

public class Label extends Ding{
	private int points = 0;
	
	public Label(GraphicsContext gc, double x, double y) {
		this.gc = gc;
		this.x = x;
		this.y = y;
	}

	public void update(int change) {
		points += change;
	}

	@Override
	public void show() {
		gc.fillText("Punktzahl: " + points, x, y);
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getHeight() {
		return 0;
	}
	
	public int getPoints() {
		return points;
	}
}
package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Alien extends Ding {
	private Image img = new Image(Defender.class.getResourceAsStream("Bilder/Alien.png"));
	
	public Alien(GraphicsContext gc, double x, double y) {
		this.gc = gc;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		y--;
	}
	
	@Override
	public void show() {
		gc.drawImage(img, x, y);
	}
	
	@Override
	public double getWidth() {
		return img.getWidth();
	}
	
	@Override
	public double getHeight() {
		return img.getHeight();
	}
}

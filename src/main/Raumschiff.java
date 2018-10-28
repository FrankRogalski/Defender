package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Raumschiff extends Ding {
	private double width = 70, height = 50, easingAmount = 0.05;
	private Image img = new Image(Defender.class.getResourceAsStream("Bilder/Raumschiff.png"));
	
	public Raumschiff(GraphicsContext gc) {
		this.gc = gc;
		x = this.gc.getCanvas().getWidth() / 2 - width / 2;
		y = this.gc.getCanvas().getHeight() / 2 - height / 2;
	}
	
	public void update(double mouseX, double mouseY) {
		double xDistance = mouseX - x - width / 2;
		double yDistance = mouseY - y - height / 2;
		double distance;
		distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
		if (distance > 1) {
			x += xDistance * easingAmount;
			y += yDistance * easingAmount;
		}
	}
	
	@Override
	public void show() {
		//gc.fillOval(x, y, size, size);
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
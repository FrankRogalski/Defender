package main;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Defender extends Application{
	private Canvas can;
	private GraphicsContext gc;
	
	private Random r = new Random();
	
	private Label label;
	private Raumschiff rs;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Alien> aliens = new ArrayList<Alien>();
	private ArrayList<Opfer> opfer = new ArrayList<Opfer>();
	
	private double x, y;
	private int count = 0;
	private boolean lebt;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		Timeline tl_draw = new Timeline(new KeyFrame(Duration.millis(16.67), e -> {
			draw();
		}));
		tl_draw.setCycleCount(Timeline.INDEFINITE);
		tl_draw.play();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 700, 400);
		
		can = new Canvas(scene.getWidth(), scene.getHeight());
		gc = can.getGraphicsContext2D();
		
		label = new Label(gc, 10, 10);
		rs = new Raumschiff(gc);
		root.setCenter(can);
		root.setStyle("-fx-background-color: #000000");
		
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				x = (int) e.getX();
				y = (int) e.getY();
			}
		});
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (MouseButton.PRIMARY == e.getButton()) {
					Bullet bullet = new Bullet(gc, rs.getX(), rs.getY() + 25);
					bullets.add(bullet);
				}
			}
		});
		
		scene.widthProperty().addListener((obsv, oldVal, newVal) -> {
		   can.setWidth(newVal.doubleValue());
		});
		
		scene.heightProperty().addListener((obsv, oldVal, newVal) -> {
			can.setHeight(newVal.doubleValue());
		});
		
		stage.setScene(scene);
		stage.show();
		
		gc.setFill(Color.rgb(255, 255, 0));
	}
	
	private void draw() {
		gc.clearRect(0, 0, can.getWidth(), can.getHeight());
		
		count++;
		if (count == 30) {
			if (r.nextInt(2) == 0) {
				alienSpawner();
			} else {
				opferSpawner();
			}
			count = 0;
		}
		
		updateOpfer();
		updateAliens();
		updateBullets();
		
		rs.update(x, y);
		rs.show();
		
		label.show();
	}
	
	private void updateOpfer() {
		for (int i = opfer.size() - 1;i >= 0;i--) {
			opfer.get(i).update();
			if (opfer.get(i).getY() + opfer.get(i).getHeight() <= 0) {
				opfer.remove(i);
				opfer.trimToSize();
				label.update(-1);
			} else {
				lebt = true;
				for (int n = bullets.size() - 1; n >= 0;n--) {
					if (isTouching(opfer.get(i), bullets.get(n))) {
						opfer.remove(i);
						bullets.remove(n);
						opfer.trimToSize();
						bullets.trimToSize();
						label.update(-1);
						lebt = false;
						break;
					}
				}
				if (lebt) {
					if (isAbove(rs, opfer.get(i))) {
						opfer.remove(i);
						opfer.trimToSize();
						label.update(1);
						lebt = false;
					}
					if (lebt) {
						opfer.get(i).show();
					}
				}
			}
		}
	}

	private void updateBullets() {
		for (int i = bullets.size() - 1;i >= 0;i--) {
			bullets.get(i).update();
			if (bullets.get(i).getX() + bullets.get(i).getWidth() <= 0) {
				bullets.remove(i);
				bullets.trimToSize();
			} else {
				lebt = true;
				for (int n = aliens.size() - 1; n >= 0;n--) {
					if (isTouching(bullets.get(i), aliens.get(n))) {
						bullets.remove(i);
						aliens.remove(n);
						bullets.trimToSize();
						aliens.trimToSize();
						lebt = false;
						break;
					}
				}
				
				if (lebt) {
					for (int n = opfer.size() - 1;n >= 0;n--) {
						if (isTouching(bullets.get(i), opfer.get(n))) {
							bullets.remove(i);
							opfer.remove(n);
							bullets.trimToSize();
							opfer.trimToSize();
							label.update(-1);
							lebt = false;
							break;
						}
					}
					if (lebt) {
						bullets.get(i).show();
					}
				}
			}
		}
	}
	
	private void updateAliens() {
		for (int i = aliens.size() - 1; i >= 0;i--) {
			aliens.get(i).update();
			if (aliens.get(i).getY() + aliens.get(i).getHeight() <= 0) {
				aliens.remove(i);
				aliens.trimToSize();
			} else {
				lebt = true;
				for (int n = bullets.size() - 1;n >= 0;n--) {
					if (isTouching(aliens.get(i), bullets.get(n))) {
						aliens.remove(i);
						bullets.remove(n);
						aliens.trimToSize();
						bullets.trimToSize();
						lebt = false;
						break;
					}
				}
				if (lebt) {
					if (isTouching(rs, aliens.get(i))) {
						aliens.remove(i);
						aliens.trimToSize();
						label.update(-2);
						lebt = false;
					}
					if (lebt) {
						aliens.get(i).show();
					}
				}
			}
		}
	}
	
	private boolean isTouching(Ding obj1, Ding obj2) {
		return (obj1.getX() <= obj2.getX() + obj2.getWidth() 
			&& obj1.getY() <= obj2.getY() + obj2.getHeight() 
			&& obj1.getX() + obj1.getWidth() >= obj2.getX()
			&& obj1.getY() + obj1.getHeight() >= obj2.getY());
	}
	
	private boolean isAbove(Ding obj1, Ding obj2) {
		return (obj2.getX() >= obj1.getX()
			&& obj2.getY() >= obj1.getY()
			&& obj2.getX() + obj2.getWidth() <= obj1.getX() + obj1.getWidth()
			&& obj2.getY() + obj2.getHeight() <= obj1.getY() + obj1.getHeight()
			|| obj1.getX() >= obj2.getX()
			&& obj1.getY() >= obj2.getY()
			&& obj1.getX() + obj1.getWidth() <= obj2.getX() + obj2.getWidth()
			&& obj1.getY() + obj1.getHeight() <= obj2.getY() + obj2.getHeight());
	}
	
	private void opferSpawner() {
		int x = r.nextInt((int) (can.getWidth() / 3 * 2));
		Opfer opfer = new Opfer(gc, x, can.getHeight() + 100);
		this.opfer.add(opfer);
	}
	
	private void alienSpawner() {
		int x = r.nextInt((int) (can.getWidth() / 3 * 2));
		Alien alien = new Alien(gc, x, can.getHeight() + 100);
		aliens.add(alien);
	}
}
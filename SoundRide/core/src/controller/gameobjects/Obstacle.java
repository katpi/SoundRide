package controller.gameobjects;

import model.gameobjects.ObstacleModel;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle extends Scrollable {
	private int dy = 24;

	public Obstacle(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		scrollable = new ObstacleModel(x, y, width, height, scrollSpeed);
		scrollable.boundingRectangle = new Rectangle(x, y + dy, width, height);
	}

	@Override
	public void reset(float newX, float newY) {
		super.reset(newX, newY);
		// scrollable.setX(newX);
		scrollable.setY(newY);
		scrollable.boundingRectangle.set(scrollable.getX(), scrollable.getY()
				+ dy, scrollable.getWidth(), scrollable.getHeight());
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		scrollable.boundingRectangle.set(scrollable.getX(), scrollable.getY()
				+ dy, scrollable.getWidth(), scrollable.getHeight());
	}

	public boolean collides(Vehicle vehicle) {
		if (scrollable.getX() < vehicle.getX() + vehicle.getWidth()) {
			return (Intersector.overlaps(vehicle.getBoundingCircle(),
					scrollable.boundingRectangle));
		}
		return false;
	}

	public void onRestart(float x, float scrollSpeed) {
		scrollable.setVelocityX(scrollSpeed);
		reset(x);
	}

	public Rectangle getBoundingRectangle() {
		return scrollable.boundingRectangle;
	}
}

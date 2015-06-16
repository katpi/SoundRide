package controller.gameobjects;

import model.gameobjects.ScrollableModel;

public class Scrollable {
	protected ScrollableModel scrollable;

	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		scrollable = new ScrollableModel(x, y, width, height, scrollSpeed);
	}

	public void update(float delta) {
		scrollable.update(delta);
		// If the Scrollable object is no longer visible:
		if (scrollable.getX() + scrollable.getWidth() < 0) {
			scrollable.setScrolledLeft(true);
		}
	}

	public void reset(float newX) {
		scrollable.setX(newX);
		scrollable.setScrolledLeft(false);
	}

	public void reset(float newX, float newY) {
		scrollable.setX(newX);
		scrollable.setY(newY);
		scrollable.setScrolledLeft(false);
	}

	public void stop() {
		scrollable.setVelocityX(0);
	}

	public float getTailX() {
		return scrollable.getTailX();
	}

	public float getX() {
		return scrollable.getX();
	}

	public float getY() {
		return scrollable.getY();
	}

	public void setX(float x) {
		scrollable.setX(x);
	}

	public void setY(float y) {
		scrollable.setY(y);
	}

	public int getWidth() {
		return scrollable.getWidth();
	}

	public int getHeight() {
		return scrollable.getHeight();
	}

	public boolean isScrolledLeft() {
		return scrollable.isScrolledLeft();
	}
}

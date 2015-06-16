package model.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ScrollableModel {
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	protected boolean isScrolledLeft;
	public Rectangle boundingRectangle;

	public ScrollableModel(float x, float y, int width, int height,
			float scrollSpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
	}

	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}

	public void setScrolledLeft(boolean isScrolledLeft) {
		this.isScrolledLeft = isScrolledLeft;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public void setX(float x) {
		this.position.x = x;
	}

	public void setY(float y) {
		this.position.y = y;
	}

	public void setVelocityX(float velocityX) {
		this.velocity.x = velocityX;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}

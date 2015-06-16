package model.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class VehicleModel {
	// Vector2 is a libGDX class, that holds two values - x and y
	private Vector2 position;
	private Vector2 velocity;
	private int width;
	private int height;
	public Circle boundingCircle;
	public static boolean isAlive = true;

	public VehicleModel(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		boundingCircle = new Circle();
		isAlive = true;
	}

	public void update(float delta) {
		// I multiply the velocity vector by the delta, which is the amount of
		// time that has passed since the update method was previously called.
		// This has a normalizing effect - frame-rate independent movement.
		position.add(velocity.cpy().scl(delta));
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	public void setVelocity(float x, float y) {
		velocity.set(x, y);
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getVelocityX() {
		return velocity.x;
	}

	public float getVelocityY() {
		return velocity.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void onClick() {
	}

	public void setAlive(boolean isAlive) {
		VehicleModel.isAlive = isAlive;
	}
}

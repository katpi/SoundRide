package controller.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import controller.helpers.AssetLoader;

import java.util.Map;

import model.gameobjects.VehicleModel;

public class Vehicle {
	public static final int SOUND_BIAS = 20;
	private VehicleModel vehicle;
	private Vector2 positionToGetTo;

	public Vehicle(float x, float y, int width, int height) {
		vehicle = new VehicleModel(x, y, width, height);
		positionToGetTo = new Vector2(x, y);
		vehicle.boundingCircle.set(x, y, 12);
	}

	public void update(float delta) {
		// I multiply the velocity vector by the delta, which is the amount of
		// time that has passed since the update method was previously called.
		// This has a normalizing effect - frame-rate independent movement.
		vehicle.update(delta);
		if (vehicle.getVelocityY() > 0) {
			if (positionToGetTo.y < vehicle.getY()) {
				vehicle.setPosition(positionToGetTo.x, positionToGetTo.y);
				vehicle.setVelocity(vehicle.getVelocityX(), 0);
			}
		} else if (vehicle.getVelocityY() < 0) {
			if (positionToGetTo.y > vehicle.getY()) {
				vehicle.setPosition(positionToGetTo.x, positionToGetTo.y);
				vehicle.setVelocity(vehicle.getVelocityX(), 0);
			}
		}
		vehicle.boundingCircle.set(vehicle.getX(), vehicle.getY(), 12);
	}

	public void changePosition(int positionYToGetTo) {
		if (positionYToGetTo != -1) {
			positionToGetTo = new Vector2(vehicle.getX(), positionYToGetTo);
			if (positionYToGetTo > vehicle.getY()) {
				vehicle.setVelocity(vehicle.getVelocityX(),
						vehicle.getVelocityY() + 70);
				// velocity.add(0, 70);
			} else {
				vehicle.setVelocity(vehicle.getVelocityX(),
						vehicle.getVelocityY() - 70);
			}
		}
	}

	public void changePositionFromSound(double sound) {
		changePosition(getPositionYFromSound(sound));
	}

	public int getPositionYFromSound(double sound) {
		int positionY = -1;
		for (Map.Entry<Double, Integer> entry : AssetLoader.positionsBySound
				.entrySet()) {
			if (entry.getKey() > sound - SOUND_BIAS
					&& entry.getKey() < sound + 10) {
				positionY = AssetLoader.positionsOfObjects
						.get(entry.getValue());
			}
		}
		return positionY;
	}

	public void onRestart(int y) {
		vehicle.setPosition(AssetLoader.startPoint, y);
		vehicle.setVelocity(0, 0);
		vehicle.setAlive(true);
	}

	public void updateReady(float runTime) {
		vehicle.setPosition(vehicle.getX(),
				AssetLoader.positionsOfObjects.get(1));
	}

	public void die() {
		vehicle.setAlive(false);
		vehicle.setVelocity(0, 0);
	}

	public Circle getBoundingCircle() {
		return vehicle.boundingCircle;
	}

	public int getWidth() {
		return vehicle.getWidth();
	}

	public int getHeight() {
		return vehicle.getHeight();
	}

	public float getX() {
		return vehicle.getX();
	}

	public float getY() {
		return vehicle.getY();
	}

	public void onClick() {
		vehicle.onClick();
	}
}

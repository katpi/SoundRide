package controller.helpers;

import java.util.Random;

import controller.gameobjects.Background;
import controller.gameobjects.Obstacle;
import controller.gameobjects.Vehicle;
import model.gameworld.GameWorld;

public class ScrollHandler {
	public static final int SCROLL_SPEED = -40;
	public static final int OBSTACLE_SPEED = -80;
	public static final int OBSTACLE_GAP = 180;

	private Random r;
	private Background frontBackground, backBackground;
	private Obstacle obstacle1, obstacle2;

	public ScrollHandler(GameWorld gameWorld, int gameWidth, int gameHeight) {
		r = new Random();
		frontBackground = new Background(0, 0, gameWidth, gameHeight,
				SCROLL_SPEED);
		backBackground = new Background(frontBackground.getTailX(), 0,
				gameWidth, gameHeight, SCROLL_SPEED);
		obstacle1 = new Obstacle(AssetLoader.obstacleStartingPosition,
				AssetLoader.positionsOfObjects.get(r.nextInt(4)),
				AssetLoader.obstacleWidth, AssetLoader.obstacleHeight,
				OBSTACLE_SPEED);
		obstacle2 = new Obstacle(obstacle1.getTailX() + OBSTACLE_GAP,
				AssetLoader.positionsOfObjects.get(r.nextInt(4)),
				AssetLoader.obstacleWidth, AssetLoader.obstacleHeight,
				OBSTACLE_SPEED);
	}

	public void onRestart() {
		frontBackground.onRestart(0, SCROLL_SPEED);
		backBackground.onRestart(frontBackground.getTailX(), SCROLL_SPEED);
		obstacle1.onRestart(AssetLoader.obstacleStartingPosition,
				OBSTACLE_SPEED);
		obstacle2
				.onRestart(obstacle1.getTailX() + OBSTACLE_GAP, OBSTACLE_SPEED);
	}

	public void update(float delta) {
		// Update our objects
		frontBackground.update(delta);
		backBackground.update(delta);
		obstacle1.update(delta);
		obstacle2.update(delta);

		if (backBackground.isScrolledLeft()) {
			backBackground.reset(frontBackground.getTailX());
		} else if (frontBackground.isScrolledLeft()) {
			frontBackground.reset(backBackground.getTailX());
		}
		if (obstacle2.isScrolledLeft()) {
			obstacle2.reset(obstacle1.getTailX() + OBSTACLE_GAP,
					AssetLoader.positionsOfObjects.get(r.nextInt(4)));
		} else if (obstacle1.isScrolledLeft()) {
			obstacle1.reset(obstacle2.getTailX() + OBSTACLE_GAP,
					AssetLoader.positionsOfObjects.get(r.nextInt(4)));
		}
	}

	public void updateReady(float delta) {
		frontBackground.update(delta);
		backBackground.update(delta);
		if (backBackground.isScrolledLeft()) {
			backBackground.reset(frontBackground.getTailX());
		} else if (frontBackground.isScrolledLeft()) {
			frontBackground.reset(backBackground.getTailX());
		}
	}

	public void stop() {
		obstacle1.stop();
		obstacle2.stop();
		backBackground.stop();
		frontBackground.stop();
	}

	public boolean collides(Vehicle vehicle) {
		return (obstacle1.collides(vehicle) || obstacle2.collides(vehicle));
	}

	public controller.gameobjects.Background getFrontBackground() {
		return frontBackground;
	}

	public controller.gameobjects.Background getBackBackground() {
		return backBackground;
	}

	public Obstacle getObstacle1() {
		return obstacle1;
	}

	public Obstacle getObstacle2() {
		return obstacle2;
	}
}
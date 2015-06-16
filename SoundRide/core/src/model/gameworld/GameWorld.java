package model.gameworld;

import com.badlogic.gdx.Gdx;

import model.gameobjects.VehicleModel;
import controller.gameobjects.Vehicle;
import controller.helpers.AssetLoader;
import controller.helpers.AudioInputAsyncHelper;
import controller.helpers.ScrollHandler;

/**
 * Keeps and updates the game objects.
 */
public class GameWorld {
	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public static float runTime;
	private static Vehicle vehicle;
	private static ScrollHandler scroller;
	private AudioInputAsyncHelper audioInputAsyncHelper;
	private static GameState currentState;

	public GameWorld(int startPointX, int startPointY, int gameWidth,
			int gameHeight) {
		currentState = GameState.MENU;
		vehicle = new Vehicle(startPointX, startPointY,
				AssetLoader.vehicleHeight, AssetLoader.vehicleWidth);
		scroller = new ScrollHandler(this, gameWidth, gameHeight);
		audioInputAsyncHelper = new AudioInputAsyncHelper();
		audioInputAsyncHelper.startFftAnalysis();
	}

	public static void restart() {
		currentState = GameState.READY;
		VehicleModel.isAlive = true;
		vehicle.onRestart(AssetLoader.positionsOfObjects.get(2));
		scroller.onRestart();
		currentState = GameState.READY;
	}

	public void update(float delta) {
		if (VehicleModel.isAlive)
			runTime += delta;
		switch (currentState) {
		case READY:
		case MENU:
			updateReady(delta);
			break;
		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}
	}

	private void updateReady(float delta) {
		vehicle.updateReady(runTime);
		scroller.updateReady(delta);
	}

	public void updateRunning(float delta) {
		vehicle.update(delta);
		scroller.update(delta);

		if (VehicleModel.isAlive && scroller.collides(vehicle)) {
			// Clean up on game over
			scroller.stop();
			AssetLoader.dead.play();
			vehicle.die();
			currentState = GameState.GAMEOVER;

			if (runTime > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore((int) runTime);
				currentState = GameState.HIGHSCORE;
			}
		}

		if (VehicleModel.isAlive && audioInputAsyncHelper.isDone()) {
			Gdx.app.log("Game World AnalysisResult", audioInputAsyncHelper
					.getAnalysisResult().toString());
			vehicle.changePositionFromSound(audioInputAsyncHelper
					.getAnalysisResult());
			audioInputAsyncHelper.startFftAnalysis();
		}
	}

	public static void start() {
		currentState = GameState.RUNNING;
		runTime = 0;
	}

	public static void ready() {
		currentState = GameState.READY;
	}

	public static boolean isReady() {
		return currentState == GameState.READY;
	}

	public static boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public static boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public static boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public static boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public static Vehicle getVehicle() {
		return vehicle;
	}

	public static ScrollHandler getScrollHandler() {
		return scroller;
	}
}

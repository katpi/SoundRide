package view.screens;

import view.gameworld.GameRenderer;
import model.gameobjects.Vehicle;
import model.gameworld.GameWorld;
import model.helpers.AssetLoader;
import controller.helpers.TouchInputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;

	/**
	 * The constructor of the screen. It's being called once the game starts.
	 */
	public GameScreen() {
		Gdx.app.log("GameScreen", "constructor called");
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		int gameHeight = 200;
		int gameWidth = (int) (screenWidth / (screenHeight / gameHeight));
		int startPointX = AssetLoader.startPoint;
		int startPointY = AssetLoader.positionsOfObjects.get(2);

		// world = new GameWorld(startPointX, startPointY, gameWidth,
		// gameHeight);
		// renderer = new GameRenderer(world, gameWidth, gameHeight);
		//
		// Gdx.input.setInputProcessor(new TouchInputHandler(world));

		world = new GameWorld(startPointX, startPointY, gameWidth, gameHeight);
		Gdx.input.setInputProcessor(new TouchInputHandler(screenWidth
				/ gameWidth, screenHeight / gameHeight));
		renderer = new GameRenderer(gameWidth, gameHeight);
	}

	/**
	 * Renders the screen. Estimated FPS is 60, so it's called 60 times in a
	 * second.
	 * <p>
	 * It's performed in a loop (provided by the GDX).
	 * <p>
	 * Uses functions update and render from GameWorld and GameRenderer.
	 */
	public void render(float delta) {
		if (Vehicle.isAlive)
			GameWorld.runTime += delta;
		world.update(delta);
		renderer.render(delta, GameWorld.runTime);
		// Gdx.app.log("GameScreen FPS", (1 / delta) + "");
	}

	public void resize(int width, int height) {
		// Gdx.app.log("GameScreen", "resizing");
	}

	public void show() {
		// Gdx.app.log("GameScreen", "show called");
	}

	public void hide() {
		// Gdx.app.log("GameScreen", "hide called");
	}

	public void pause() {
		// Gdx.app.log("GameScreen", "pause called");
	}

	public void resume() {
		// Gdx.app.log("GameScreen", "resume called");
	}

	public void dispose() {
	}
}

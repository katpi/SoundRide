package controller.game;

import model.gameworld.GameWorld;
import view.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import controller.helpers.AssetLoader;

public class SRGame extends Game {
	private float gameTime;

	public void create() {
		Gdx.app.log("Sound Ride Game", "Created");
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	public void dispose() {
		gameTime = GameWorld.runTime;
		Gdx.app.log("Sound Ride Game Time", (gameTime) + "");
		super.dispose();
		AssetLoader.dispose();
		Gdx.app.log("Sound Ride Game", "disposed");
	}
}

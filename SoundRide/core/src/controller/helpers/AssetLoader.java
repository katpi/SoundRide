package controller.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;

import view.helpers.TextureLoader;

/**
 * Sets important variables, such as positions of objects.
 */
public class AssetLoader {
	private static final double[] DEFAULT_SOUND_HEIGHT = { (double) 522,
			(double) 1173, (double) 328, (double) 263, (double) 193 };// c2,g1,e1,c1,gm
	public static final int vehicleHeight = 32, vehicleWidth = 19,
			startPoint = 20, obstacleHeight = 20, obstacleWidth = 20,
			obstacleStartingPosition = 250, buttonX = 95, buttonY = 150;
	public static HashMap<Double, Integer> positionsBySound;
	public static HashMap<Integer, Integer> positionsOfObjects;
	public static Sound dead;
	public static BitmapFont font, shadow;
	public static Preferences prefs;

	/**
	 * Called once the game starts.
	 */
	public static void load() {
		TextureLoader.load();

		positionsBySound = new HashMap<Double, Integer>(4);
		positionsBySound.put(DEFAULT_SOUND_HEIGHT[0], 0);
		positionsBySound.put(DEFAULT_SOUND_HEIGHT[1], 1);
		positionsBySound.put(DEFAULT_SOUND_HEIGHT[2], 2);
		positionsBySound.put(DEFAULT_SOUND_HEIGHT[3], 3);
		positionsBySound.put(DEFAULT_SOUND_HEIGHT[4], 4);

		positionsOfObjects = new HashMap<Integer, Integer>(4);
		positionsOfObjects.put(0, 29);
		positionsOfObjects.put(1, 62);
		positionsOfObjects.put(2, 95);
		positionsOfObjects.put(3, 128);
		positionsOfObjects.put(4, 161);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("SoundRide");
		// Provide default high score of 0
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

		Gdx.app.log("Asset Loader", "loaded");
	}

	/**
	 * Receives an integer and maps it to the String highScore in prefs
	 */
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	/**
	 * Retrieves the current high score
	 */
	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	/**
	 * Called when the game finishes.
	 */
	public static void dispose() {
		Gdx.app.log("Asset Loader", "disposed");
		TextureLoader.dispose();
		font.dispose();
		shadow.dispose();
	}
}
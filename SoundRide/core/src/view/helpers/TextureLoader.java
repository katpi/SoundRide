package view.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Deals with the appearance of game objects. Crops the texture file into
 * appropriate parts.
 */
public class TextureLoader {
	public static Texture texture, logoTexture;
	public static TextureRegion logo, zbLogo, background, playButtonUp, playButtonDown;
	public static Animation vehicleAnimation;
	public static TextureRegion vehicle1, vehicle2;
	public static TextureRegion obstacle, bonusBlue, bonusRed;

	public static void load() {
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 492, 300);

        texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        playButtonUp = new TextureRegion(texture, 400, 300, 30, 16);
        playButtonDown = new TextureRegion(texture, 429, 300, 30, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        zbLogo = new TextureRegion(texture, 100,300, 235, 40);
        zbLogo.flip(false, true);
		
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		background = new TextureRegion(texture, 0, 0, 600, 270);
		background.flip(false, true);

		vehicle1 = new TextureRegion(texture, 0, 286, 52, 66);
		vehicle1.flip(false, true);

		vehicle2 = new TextureRegion(texture, 47, 286, 52, 66);
		vehicle2.flip(false, true);

		TextureRegion[] vehicles = { vehicle1, vehicle2 };
		// the pictures of a vehicle will change each 0.06 seconds
		vehicleAnimation = new Animation(0.06f, vehicles);
		vehicleAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		obstacle = new TextureRegion(texture, 334, 286, 56, 66);
		obstacle.flip(false, true);

		bonusBlue = new TextureRegion(texture, 212, 286, 56, 66);
		bonusBlue.flip(false, true);

		bonusRed = new TextureRegion(texture, 274, 286, 56, 66);
		bonusRed.flip(false, true);
		Gdx.app.log("TextureLoader", "loaded");
	}

	public static void dispose() {
		Gdx.app.log("TextureLoader", "disposed");
		texture.dispose();
	}
}
package view.gameworld;

import view.helpers.TextureLoader;
import view.tweenaccessors.Value;
import view.tweenaccessors.ValueAccessor;
import view.ui.Menu;
import view.ui.SimpleButton;
import controller.gameobjects.Background;
import controller.gameobjects.Obstacle;
import controller.gameobjects.Vehicle;
import controller.helpers.AssetLoader;
import controller.helpers.ScrollHandler;
import model.gameworld.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Renders the game objects.
 */
public class GameRenderer {
	private int gameWidth;
	private int gameHeight;

	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	// Game Objects
	private Vehicle vehicle;
	private ScrollHandler scroller;
	private Obstacle obstacle1, obstacle2;
	private Background frontBackground, backBackground;
	// Game Assets
	private TextureRegion background, obstacle;
	private Animation vehicleAnimation;
	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();
	// Buttons
	private List<SimpleButton> menuButtons;
	private Menu menu;

	public GameRenderer(int gameWidth, int gameHeight) {
		menu = new Menu();
		this.menuButtons = menu.getMenuButtons();
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;

		cam = new OrthographicCamera();

		// sets the size of the world (after that it's scaled in the launcher
		// class)
		cam.setToOrtho(true, gameWidth, gameHeight);

		// The SpriteBatch draws images for us using the indices provided.
		batcher = new SpriteBatch();

		// Attach batcher to camera
		batcher.setProjectionMatrix(cam.combined);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
		setupTweens();
	}

	private void setupTweens() {
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
				.start(manager);
	}

	public void render(float delta, float runTime) {
		// black background - prevents flickering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, gameWidth, gameHeight);
		shapeRenderer.end();

		batcher.begin();
		batcher.disableBlending();
		drawBackground();
		batcher.enableBlending();
		drawObstacles();

		if (GameWorld.isRunning()) {
			drawVehicle(runTime);
			drawScore();
		} else if (GameWorld.isReady()) {
			drawVehicle(runTime);
			drawReady();
		} else if (GameWorld.isMenu()) {
			drawVehicleCentered(runTime);
			drawMenuUI();
		} else if (GameWorld.isGameOver()) {
			drawVehicle(runTime);
			drawGameOver();
			drawScore();
		} else if (GameWorld.isHighScore()) {
			drawVehicle(runTime);
			drawHighScore();
			drawScore();
		}

		batcher.end();

		// drawVehicleCircle();
		// drawObstaclesRectangles();
		drawTransition(delta);
	}

	private void initGameObjects() {
		vehicle = GameWorld.getVehicle();
		scroller = GameWorld.getScrollHandler();
		frontBackground = scroller.getFrontBackground();
		backBackground = scroller.getBackBackground();
		obstacle1 = scroller.getObstacle1();
		obstacle2 = scroller.getObstacle2();
	}

	private void initAssets() {
		background = TextureLoader.background;
		vehicleAnimation = TextureLoader.vehicleAnimation;
		obstacle = TextureLoader.obstacle;
	}

	private void drawBackground() {
		batcher.draw(background, backBackground.getX(), backBackground.getY(),
				backBackground.getWidth(), backBackground.getHeight());
		batcher.draw(background, frontBackground.getX(),
				frontBackground.getY(), frontBackground.getWidth(),
				frontBackground.getHeight());
	}

	private void drawVehicleCentered(float runTime) {
		batcher.draw(vehicleAnimation.getKeyFrame(runTime), 110, 60,
				vehicle.getHeight() * 2, vehicle.getWidth() * 2);
	}

	private void drawVehicle(float runTime) {
		batcher.draw(vehicleAnimation.getKeyFrame(runTime), vehicle.getX() - 5,
				vehicle.getY() - 15, vehicle.getHeight(), vehicle.getWidth());
	}

	private void drawObstacles() {
		int dx, dy, dw, dh;
		dx = -6;
		dy = 0;
		dw = 9;
		dh = 9;
		batcher.draw(obstacle, obstacle1.getX() + dx, obstacle1.getY()
				+ obstacle1.getHeight() + dy, obstacle1.getWidth() + dw,
				obstacle1.getHeight() + dh);
		batcher.draw(obstacle, obstacle2.getX() + dx, obstacle2.getY()
				+ obstacle2.getHeight() + dy, obstacle2.getWidth() + dw,
				obstacle2.getHeight() + dh);
	}

	private void drawMenuUI() {
		batcher.draw(TextureLoader.zbLogo, 70, 40,
				TextureLoader.zbLogo.getRegionWidth() / 2,
				TextureLoader.zbLogo.getRegionHeight() / 2);

		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}

	}

	private void drawScore() {
		int length = ("" + (int) GameWorld.runTime).length();
		AssetLoader.shadow.draw(batcher, "" + (int) GameWorld.runTime,
				125 - (3 * length), 18);
		AssetLoader.font.draw(batcher, "" + (int) GameWorld.runTime,
				125 - (3 * length), 17);
	}

	private void drawHighScore() {
		AssetLoader.shadow.draw(batcher, "High Score!", 69, 56);
		AssetLoader.font.draw(batcher, "High Score!", 68, 55);
		AssetLoader.shadow.draw(batcher, "Try again?", 73, 76);
		AssetLoader.font.draw(batcher, "Try again?", 74, 75);
	}

	private void drawGameOver() {
		AssetLoader.shadow.draw(batcher, "Game Over", 75, 56);
		AssetLoader.font.draw(batcher, "Game Over", 74, 55);

		AssetLoader.shadow.draw(batcher, "High Score:", 53, 156);
		AssetLoader.font.draw(batcher, "High Score:", 52, 155);

		String highScore = AssetLoader.getHighScore() + "";
		AssetLoader.shadow.draw(batcher, highScore,
				180 - (3 * highScore.length()), 156);
		AssetLoader.font.draw(batcher, highScore,
				180 - (3 * highScore.length() - 1), 155);
		AssetLoader.shadow.draw(batcher, "Try again?", 75, 76);
		AssetLoader.font.draw(batcher, "Try again?", 74, 75);
	}

	private void drawReady() {
		AssetLoader.shadow.draw(batcher, "Click to start!", 101, 21);
		AssetLoader.font.draw(batcher, "Click to start!", 100, 20);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 1, alpha.getValue());
			shapeRenderer.rect(0, 0, 136, 300);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

	@SuppressWarnings("unused")
	private void drawVehicleCircle() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.circle(vehicle.getBoundingCircle().x,
				vehicle.getBoundingCircle().y,
				vehicle.getBoundingCircle().radius);
		shapeRenderer.end();
	}

	@SuppressWarnings("unused")
	private void drawObstaclesRectangles() {
		Gdx.app.log("obstacle1.getBoundingRectangle().x",
				obstacle1.getBoundingRectangle().x + "");
		Gdx.app.log("obstacle1.getBoundingRectangle().y",
				obstacle1.getBoundingRectangle().y + "");
		Gdx.app.log("obstacle1.getBoundingRectangle().width",
				obstacle1.getBoundingRectangle().width + "");
		Gdx.app.log("obstacle1.getBoundingRectangle().height",
				obstacle1.getBoundingRectangle().height + "");
		Gdx.app.log("obstacle2.getBoundingRectangle().x",
				obstacle2.getBoundingRectangle().x + "");
		Gdx.app.log("obstacle2.getBoundingRectangle().y",
				obstacle2.getBoundingRectangle().y + "");
		Gdx.app.log("obstacle2.getBoundingRectangle().width",
				obstacle2.getBoundingRectangle().width + "");
		Gdx.app.log("obstacle2.getBoundingRectangle().height",
				obstacle2.getBoundingRectangle().height + "");
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(obstacle1.getBoundingRectangle().x,
				obstacle1.getBoundingRectangle().y,
				obstacle1.getBoundingRectangle().width,
				obstacle1.getBoundingRectangle().height);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(obstacle2.getBoundingRectangle().x,
				obstacle2.getBoundingRectangle().y,
				obstacle2.getBoundingRectangle().width,
				obstacle2.getBoundingRectangle().height);
		shapeRenderer.end();
	}
}
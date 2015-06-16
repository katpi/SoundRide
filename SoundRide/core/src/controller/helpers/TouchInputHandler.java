package controller.helpers;

import model.gameobjects.Vehicle;
import model.gameworld.GameWorld;
import view.ui.Menu;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class TouchInputHandler implements InputProcessor {
	private Vehicle myVehicle;

	private float scaleFactorX;
	private float scaleFactorY;

	public TouchInputHandler(float scaleFactorX,
			float scaleFactorY) {
		// myVehicle now represents the gameWorld's Vehicle.
		myVehicle = GameWorld.getVehicle();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		System.out.println(screenX + " " + screenY);
		if (GameWorld.isMenu()) {
			Menu.playButton.isTouchDown(screenX, screenY);
		} else if (GameWorld.isReady()) {
			GameWorld.start();
		}

		myVehicle.onClick();

		if (GameWorld.isGameOver() || GameWorld.isHighScore()) {
			// Reset all variables, go to GameState.READ
			GameWorld.restart();
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (GameWorld.isMenu()) {
			if (Menu.playButton.isTouchUp(screenX, screenY)) {
				GameWorld.ready();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// Can now use Space Bar to play the game
		if (keycode == Keys.SPACE) {
			if (GameWorld.isMenu()) {
				GameWorld.ready();
			} else if (GameWorld.isReady()) {
				GameWorld.start();
			}
			myVehicle.onClick();
			if (GameWorld.isGameOver() || GameWorld.isHighScore()) {
				GameWorld.restart();
			}
		}
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

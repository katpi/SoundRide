package controller.gameobjects;

import model.gameobjects.BackgroundModel;

public class Background extends Scrollable {
	public Background(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		scrollable = new BackgroundModel(x, y, width, height, scrollSpeed);
	}

	public void onRestart(float x, float scrollSpeed) {
		scrollable.setX(x);
		scrollable.setVelocityX(scrollSpeed);
	}

	public float getTailX() {
		return scrollable.getTailX();
	}
}

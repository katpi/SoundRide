package model.gameobjects;

import com.badlogic.gdx.math.Rectangle;

public class ObstacleModel extends ScrollableModel {
	public Rectangle boundingRectangle;

	public ObstacleModel(float x, float y, int width, int height,
			float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		boundingRectangle = new Rectangle();
	}

	public void setBoundingRectangle(Rectangle boundingRectangle) {
		this.boundingRectangle = boundingRectangle;
	}
}

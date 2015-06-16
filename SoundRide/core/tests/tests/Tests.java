package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import controller.gameobjects.Background;
import controller.gameobjects.Obstacle;
import controller.gameobjects.Vehicle;

public class Tests {
	@Test
	public void testAdd() {
		String str = "Junit is working fine";
		assertEquals("Junit is working fine", str);
	}

	@Test
	public void VehicleConstructorTest() {
		int x, y, width, height;
		x = 10;
		y = 10;
		width = 20;
		height = 20;
		Vehicle vehicle = new Vehicle(x, y, width, height);
		assertEquals(vehicle.getWidth(), width);
		assertEquals(vehicle.getHeight(), height);
	}

	@Test
	public void BoundingCircleTest() {
		int x, y, width, height;
		x = 10;
		y = 10;
		width = 20;
		height = 20;
		Vehicle vehicle = new Vehicle(x, y, width, height);
		Circle boundingCircle = new Circle(vehicle.getX(), vehicle.getY(), 12);
		assertEquals(vehicle.getBoundingCircle(), boundingCircle);
	}

	@Test
	public void BoundingCircleUpdateTest() {
		int x, y, width, height;
		x = 10;
		y = 10;
		width = 20;
		height = 20;
		Vehicle vehicle = new Vehicle(x, y, width, height);
		Circle boundingCircle = new Circle(vehicle.getX(), vehicle.getY(), 12);
		vehicle.update(0);
		assertEquals(vehicle.getBoundingCircle(), boundingCircle);
		vehicle.update(100);
		assertEquals(vehicle.getBoundingCircle(), boundingCircle);
	}

	@Test
	public void BoundingRectangleTest() {
		int x, y, width, height;
		x = 10;
		y = 10;
		width = 20;
		height = 20;
		int dy = 24;
		Obstacle obstacle = new Obstacle(x, y, width, height, 0);
		Rectangle boundingRectangle = new Rectangle(obstacle.getX(),
				obstacle.getY() + dy, obstacle.getWidth(), obstacle.getHeight());
		assertEquals(obstacle.getBoundingRectangle(), boundingRectangle);
	}

	@Test
	public void BoundingRectangleUpdateTest() {
		int x, y, width, height;
		x = 10;
		y = 10;
		width = 20;
		height = 20;
		int dy = 24;
		Obstacle obstacle = new Obstacle(x, y, width, height, 0);
		Rectangle boundingRectangle = new Rectangle(obstacle.getX(),
				obstacle.getY() + dy, obstacle.getWidth(), obstacle.getHeight());
		obstacle.update(0);
		assertEquals(obstacle.getBoundingRectangle(), boundingRectangle);
		obstacle.update(100);
		assertEquals(obstacle.getBoundingRectangle(), boundingRectangle);
	}

	@Test
	public void CollisionTest() {
		int x, y, width, height;
		x = 15;
		y = 10;
		width = 20;
		height = 20;
		Vehicle vehicle = new Vehicle(x, y, width, height);
		Circle boundingCircle = new Circle(vehicle.getX(), vehicle.getY(), 12);
		assertEquals(vehicle.getBoundingCircle(), boundingCircle);
		int dy = 24;
		x = 20;
		y = 10-dy;
		width = 20;
		height = 20;
		
		Obstacle obstacle = new Obstacle(x, y, width, height, 0);
		Rectangle boundingRectangle = new Rectangle(obstacle.getX(),
				obstacle.getY() + dy, obstacle.getWidth(), obstacle.getHeight());
		assertEquals(obstacle.getBoundingRectangle(), boundingRectangle);

		assertTrue(obstacle.getX() < vehicle.getX() + vehicle.getWidth());
		assertTrue(Intersector.overlaps(boundingCircle, boundingRectangle));
		assertTrue(Intersector.overlaps(vehicle.getBoundingCircle(),
				obstacle.getBoundingRectangle()));

		assertTrue(obstacle.collides(vehicle));
	}

	@Test
	public void GetTailText() {
		int x, y, width, height;
		x = 10;
		y = 10;
		width = 20;
		height = 20;
		Background background = new Background(x, y, width, height, 0);
		assertSame((int) background.getTailX(), (int) (x + width));
		assertSame((int) background.getTailX(),
				(int) (background.getWidth() + background.getX()));
	}
}
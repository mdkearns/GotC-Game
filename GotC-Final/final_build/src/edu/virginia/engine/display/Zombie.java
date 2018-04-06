package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Zombie extends DisplayObject {
	
	private double x_position;
	private double y_position;
	private double x_velocity;
	private double y_velocity;

	public Zombie(String id) {
		super(id);
	}

	public Zombie(String id, String imageFileName) {
		super(id, imageFileName);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
	}
	
	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (this.getDisplayImage() != null && this.isVisible()) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(this.getDisplayImage(), 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(x_position, y_position);
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.translate(-x_position, -y_position);
	}

	@Override
	public double getPositionX() {
		return x_position;
	}

	public void setX_position(double x_position) {
		this.x_position = x_position;
	}

	@Override
	public double getPositionY() {
		return y_position;
	}

	public void setY_position(double y_position) {
		this.y_position = y_position;
	}

	public double getX_velocity() {
		return this.x_velocity;
	}

	public void setX_velocity(double d) {
		this.x_velocity = d;
	}

	public double getY_velocity() {
		return this.y_velocity;
	}

	public void setY_velocity(double y_velocity) {
		this.y_velocity = y_velocity;
	}
	
	public double getRandomSpeed() {
		Random r = new Random();
		
		if (r.nextInt(2) % 2 == 0) {
			if (r.nextInt(2) % 2 == 0) {
				return 8.0;
			}
			else {
				return 2.0;
			}
		}
		else {
			if (r.nextInt(2) % 2 == 0) {
				return -2.0;
			}
			else {
				return -8.0;
			}
		}
	}
}

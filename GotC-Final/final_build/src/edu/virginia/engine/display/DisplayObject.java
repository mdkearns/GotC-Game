package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.events.EventDispatcher;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher{

	private String id;

	private BufferedImage displayImage;
	private boolean visible=true;
	private double positionX = 0;
	private double positionY = 0;
	private double pivotPointX = 0;
	private double pivotPointY = 0;
	private double scaleX = 1;
	private double scaleY = 1;
	private double rotation = 0;
	private float alpha = 1;
	private DisplayObject parent;
	

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {
		
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(getDisplayImage(),(int) -pivotPointX, (int)-pivotPointY,
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
		g2d.translate(positionX, positionY);
		g2d.scale(scaleX, scaleY);
		g2d.rotate(rotation);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		if(!visible){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
		}
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2d.rotate(-rotation);
		g2d.scale(1/scaleX, 1/scaleY);
		g2d.translate(-positionX, -positionY);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public double getPositionX() {
		if(this.getPivotPointX() != 0){
			return this.positionX - 0.25*this.getPivotPointX();
		}
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		if(this.getPivotPointY() != 0){
			return this.positionY - 0.25*this.getPivotPointY();
		}
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getPivotPointX() {
		return pivotPointX;
	}

	public void setPivotPointX(double pivotPointX) {
		this.pivotPointX = pivotPointX;
	}

	public double getPivotPointY() {
		return pivotPointY;
	}

	public void setPivotPointY(double pivotPointY) {
		this.pivotPointY = pivotPointY;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double d) {
		this.scaleX = d;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double d) {
		this.scaleY = d;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public DisplayObject getParent() {
		return parent;
	}

	public void setParent(DisplayObject parent) {
		this.parent = parent;
	}
	
	public Rectangle getHitbox(){
		Rectangle hitBox = new Rectangle();
		hitBox.setBounds((int) this.getPositionX()+40,
				(int) this.getPositionY()+20,
				(int) (this.getUnscaledWidth()*this.getScaleX()/4),
				(int) (this.getUnscaledHeight()*this.getScaleY()*0.8));
		return hitBox;
	}
	
	public Rectangle getTopHitBox(){
		Rectangle hitBox = new Rectangle();
		hitBox.setBounds((int) this.getPositionX()+20,
				(int) this.getPositionY(),
				(int) (this.getUnscaledWidth()*this.getScaleX())-40,
				10);
		return hitBox;
	}
	
	public boolean bottomCollides(DisplayObject other){
		Rectangle hitBox = new Rectangle();
		hitBox.setBounds((int) this.getPositionX()+20,
				(int) (this.getPositionY() + this.getUnscaledHeight()*this.getScaleY())-20,
				(int) (this.getUnscaledWidth()*this.getScaleX())-40,
				20);
		return hitBox.intersects(other.getTopHitBox());
	}
	
	
	public boolean collidesWith(DisplayObject other){
		return other.getHitbox().intersects(this.getHitbox());
	}

}

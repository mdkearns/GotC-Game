package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.engine.util.GameClock;

public class AnimatedZombie extends Zombie {
	
	private int currentFrame=0;
	private int startIndex;
	private int endIndex;
	int rows;
	int cols;
	BufferedImage sprite = super.getDisplayImage();
	BufferedImage[] sprites;
	int width;
	int height;
	HashMap<String, int[]> animations = new HashMap<String, int[]>();
	boolean blownUp = false;
	boolean dead = false;
	int animationSpeed;
	GameClock c = new GameClock();
	
	public AnimatedZombie(String id, String imageFileName, int r, int c) {
		super(id, imageFileName);
		rows = r;
		cols = c;
		width = sprite.getWidth()/cols;
		height = sprite.getHeight()/rows;
		sprites = new BufferedImage[rows*cols];
		this.setSpriteSheet();
	}

	public void setSpriteSheet(){
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<cols; j++){
				sprites[(i*cols)+j] = sprite.getSubimage(j*width,  i*height,  width,  height);
			}	
		}
		
	}
	
	public void setAnimSpeed(int s){
		animationSpeed = s;
	}
	
	public void setAnimation(String s, int start, int end){
		int[] range = new int[2];
		range[0] = start;
		//System.out.println("start: " + start);
		range[1] = end;
		//System.out.println("end" + end);
		animations.put(s, range);
	}
	
	public void getAnimation(String s){
		int[] numss = animations.get(s);
		this.setStartIndex(numss[0]);
		this.setEndIndex(numss[1]);
	}

	public void animate(String action){
		if(action == "move forward"){
			getAnimation("move forward");
			if(c.getElapsedTime()>animationSpeed){
				if(getCurrentFrame() < getEndIndex()){
					currentFrame = currentFrame+1;
					c.resetGameClock();
				}
				else{
					currentFrame = startIndex;
					c.resetGameClock();
				}
			}
		}
		if(action == "move backward"){
			getAnimation("move backward");
			if(c.getElapsedTime()>animationSpeed){
				if(getCurrentFrame() < getEndIndex()){
					currentFrame = currentFrame+1;
					c.resetGameClock();
				}
				else{
					currentFrame = startIndex;
					c.resetGameClock();
				}
			}
		}
		if(action == "jump"){
			getAnimation("jump");
			currentFrame = startIndex;
		}
		if(action == "down"){
			currentFrame = 5;
			
		}
		if(action == "blow up"){
			getAnimation("blow up");
			this.setX_velocity(0.0);
			if(c.getElapsedTime()>animationSpeed){
				if(getCurrentFrame() < getEndIndex() ){
					currentFrame = currentFrame +1;
					c.resetGameClock();
				}
				if(currentFrame == 55){
					dead = true;
					this.setVisible(false);
					c.resetGameClock();
				}
			}
		}
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public boolean getBlownUp(){
		return blownUp;
	}
	
	@Override
	public BufferedImage getDisplayImage() {
//		if(currentFrame > 55){
//			currentFrame = 44;
//		}
		return sprites[currentFrame];
		
	}
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int i) {
		startIndex = i;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int i) {
		this.endIndex = i;
	}

	public void setCurrentFrame(int i) {
		currentFrame = i;
		// TODO Auto-generated method stub
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	@Override
	public int getUnscaledWidth() {
		return width;
	}
	@Override
	public int getUnscaledHeight() {
		return height;
	}

	public void setBlownUp(boolean b) {
		blownUp = b;
	}
}


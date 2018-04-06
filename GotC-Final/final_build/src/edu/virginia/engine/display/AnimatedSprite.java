package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.engine.util.GameClock;

public class AnimatedSprite extends Sprite{
	
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
	GameClock c = new GameClock();
	public int animationSpeed = 0;
	public boolean exploded = false;
	
	public AnimatedSprite(String id, String imageFileName, int r, int c) {
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
		range[1] = end;
		animations.put(s, range);
	}
	
	public void getAnimation(String s){
		int[] numss = animations.get(s);
		this.setStartIndex(numss[0]);
		this.setEndIndex(numss[1]);
	}

	public void animate(String action){
		
		if(action == "explode"){
			getAnimation("explode");
//			this.setVisible(true);
			this.setAnimSpeed(20);
//			if(!exploded && currentFrame == 11){
//				currentFrame = 0;
//			}
			if(c.getElapsedTime()>animationSpeed){
				if(currentFrame != endIndex){
					currentFrame++;
					c.resetGameClock();
				}
				else{
					exploded = true;
				}
			}
		}
		
		if(action == "move forward"){
			getAnimation("move forward");
			if(c.getElapsedTime()>animationSpeed){
				if(getCurrentFrame() > endIndex){
					setCurrentFrame(startIndex);
					c.resetGameClock();
				}
				if(getCurrentFrame() != getEndIndex()){
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
				if(getCurrentFrame() < startIndex){
					setCurrentFrame(startIndex);
					c.resetGameClock();
				}
				if(getCurrentFrame() > endIndex){
					setCurrentFrame(startIndex);
					c.resetGameClock();
				}
				if(getCurrentFrame() != getEndIndex()){
					currentFrame = currentFrame +1;
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
			currentFrame = endIndex;
		}
		if(action == "jump left"){
			getAnimation("jump left");
			currentFrame = endIndex;
		}
		if(action == "duck"){
			currentFrame = 4;
			
		}
	}
	
	
	
	@Override
	public BufferedImage getDisplayImage() {
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
}

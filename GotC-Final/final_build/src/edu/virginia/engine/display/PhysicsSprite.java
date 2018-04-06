package edu.virginia.engine.display;

import java.util.ArrayList;

public class PhysicsSprite extends AnimatedSprite{
	
	boolean inMotion = false;
	public boolean isInMotion() {
		return inMotion;
	}

	public void setInMotion(boolean inMotion) {
		this.inMotion = inMotion;
	}
	
	double gravity = 9.8;
	double velocityX = 0;
	double velocityY = 0;
	double accelerationX = 0;
	double accelerationY = 0;

	public PhysicsSprite(String id, String imageFileName, int r, int c) {
		super(id, imageFileName, r, c);
	}
	
	public void setVelocityX(double v){
		velocityX = v;
	}
	
	public void setVelocityY(double v){
		velocityY = v;
	}
	
	public double getVelocityX(){
		return velocityX;
	}
	
	public double getVelocityY(){
		return velocityY;
	}
	
	public void setAx(double a){
		accelerationX = a;
	}
	
	public double getAx(){
		return accelerationX;
	}
	
	public void setAy(double a){
		accelerationY = a;
	}
	
	public double getAy(){
		return accelerationY;
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys){
		super.update(pressedKeys);
		this.setVelocityY(this.getVelocityY() + this.getAy());
		this.setVelocityX(this.getVelocityX() + this.getAx());
		this.setPositionY(this.getPositionY() + this.getVelocityY());
		this.setPositionX(this.getPositionX() + this.getVelocityX());
	}
}

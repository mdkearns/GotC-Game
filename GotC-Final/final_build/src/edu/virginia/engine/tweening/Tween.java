package edu.virginia.engine.tweening;

import java.awt.List;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.util.GameClock;

public class Tween extends EventDispatcher{
	
	private DisplayObject thing;
	ArrayList<TweenParam> params = new ArrayList<TweenParam>();
	GameClock timer = new GameClock();
	TweenTransitions trans;
	
	public Tween(DisplayObject object){
		thing = object;
	}
	
	public Tween(DisplayObject object, TweenTransitions transition){
		thing = object;
		trans = transition;
	}
	
	public ArrayList<TweenParam> getParams(){
		return params;
	}
	
	public DisplayObject getTweeny(){
		return thing;
	}
	
	public void animate(TweenableParams fieldToAnimate, double startVal, double endVal, double time){
		TweenParam t = new TweenParam(fieldToAnimate, startVal, endVal, time);
		params.add(t);
	}
	
	public void setValue(TweenableParams param, double value){
		if(param == TweenableParams.positionX){
			this.thing.setPositionX(value);
		}
		if(param == TweenableParams.positionY){
			this.thing.setPositionY(value);
		}
		if(param == TweenableParams.alpha){
			this.thing.setAlpha((float)value);
		}
		if(param == TweenableParams.rotation){
			this.thing.setRotation(value);
		}
		if(param == TweenableParams.scaleX){
			this.thing.setScaleX(value);
		}
		if(param == TweenableParams.scaleY){
			this.thing.setScaleY(value);
		}
	}
	
	public boolean isComplete(){
		if(!params.isEmpty()){
		for(TweenParam t : params){
			if(t.isComplete(this.timer.getElapsedTime())){
				return true;
			}
		}
		}
		return false;
	}
	
	public void update(){
		for(TweenParam t : params){
			if(trans != null){
			t.update(this.timer.getElapsedTime());
			setValue(t.getP(), trans.applyTransition(t.getCurrent()));
			}
			else{
				t.update(this.timer.getElapsedTime());
				setValue(t.getP(), t.getCurrent());
			}
		}
	}
}

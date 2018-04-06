package edu.virginia.engine.tweening;

import java.util.ArrayList;

public class TweenJuggler {
	
	private static TweenJuggler instance;
	ArrayList<Tween> tweens = new ArrayList<Tween>();
	
	public TweenJuggler(){
		if(instance != null) System.out.println("ERROR: can't re-initialize Juggler class");
		instance = this;
	}
	
	public static TweenJuggler getInstance(){return instance;}
	
	public void add(Tween tween){
		tweens.add(tween);
	}
	
	public void nextFrame(){
		for(Tween t : tweens){
			if(!t.isComplete()){
				t.update();
			}
		}
	}

}

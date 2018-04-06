package edu.virginia.engine.tweening;

public class TweenTransitions {
	
	private String name;
	
	public TweenTransitions(String type){
		name = type;
	}
	
	public double applyTransition(double percent){
//		s=name
		if(this.getName() == "linear"){
			return percent;
		}
		if(this.getName() == "quadratic"){
			return Math.pow(percent, 2);
		}
		if(this.getName() == "cubic"){
			return Math.pow(percent, 3);
		}
		else return percent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

package edu.virginia.engine.tweening;

public class TweenParam {
	
	private TweenableParams p;
	private double start;
	private double end;
	private double t;
	private double current;
	
	TweenParam(TweenableParams paramToTween, double startVal, double endVal, double time){
		p = paramToTween;
		start = startVal;
		end = endVal;
		t = time;
	}
	
	public double getStartVal(){
		return start;
	}
	
	public double getEndVal(){
		return end;
	}
	
	public double getTweenTime(){
		return t;
	}
	
	public boolean isComplete(double timePassed){
		return timePassed > t;
	}
	
	public void update(double timePassed){
		double percent = timePassed/t;
//		if(percent <= 1){
		setCurrent((end-start)*percent +start);
//		}
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public TweenableParams getP() {
		return p;
	}
}

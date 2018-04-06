package edu.virginia.engine.tweening;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventDispatcher;

public class TweenEvent extends Event{
	
	Tween tw;
	DisplayObject d;
	String eventType;
		
	public TweenEvent(Tween tween, String eventT){
		super(tween, eventT);
		this.tw = tween;
		this.d = tween.getTweeny();
		this.eventType = eventT;
	}

	public Tween getTween(){
		return tw;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public void setTween(EventDispatcher source) {
		this.tw = (Tween) source;
	}
	
}

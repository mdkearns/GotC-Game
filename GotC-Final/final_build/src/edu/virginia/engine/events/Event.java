package edu.virginia.engine.events;

public class Event {

	private String eventType;
	private EventDispatcher source;
	
	public Event(EventDispatcher s, String e){
		eventType = e;
		source = s;
	}
		
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public IEventDispatcher getSource() {
		return source;
	}
	
	public void setSource(EventDispatcher source) {
		this.source = source;
	}
	
}

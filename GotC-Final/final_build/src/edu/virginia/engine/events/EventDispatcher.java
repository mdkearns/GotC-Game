package edu.virginia.engine.events;

import java.util.HashMap;

public class EventDispatcher implements IEventDispatcher {

	private HashMap<IEventListener, String> observers = new HashMap<IEventListener, String>();
	
	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		observers.put(listener, eventType);
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		observers.remove(listener, eventType);
	}

	@Override
	public void dispatchEvent(Event event) {
		for(IEventListener listener : observers.keySet()){
			listener.handleEvent(event);
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		return observers.containsKey(listener);	
	}

}

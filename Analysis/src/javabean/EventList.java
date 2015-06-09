package javabean;

import java.util.ArrayList;

import javabean.db.Event;

/**
 * 事件列表
 * @author hx
 */
public class EventList {

	ArrayList<Event> events = new ArrayList<Event>();

	public EventList(ArrayList<Event> events) {
		super();
		this.events = events;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	public void addEvent(Event e) {
		this.events.add(e);
	}
}

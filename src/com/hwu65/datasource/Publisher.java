package com.hwu65.datasource;

import java.util.Vector;

public class Publisher {
	Vector<Subscriber> subscribers = new Vector<Subscriber>();
	
	public void addSubscriber(Subscriber subscriberAdded) {
        subscribers.addElement(subscriberAdded);
    }
	
	public void notifySubscribers(HealthParameter healthParameter){
        for(int i=0; i<subscribers.size(); i++) {
            Subscriber s = (Subscriber) subscribers.elementAt(i);
            s.update(healthParameter);
        }
    }
	
	public void removeSubscriber(Subscriber subscriberRemoved){
        subscribers.removeElement(subscriberRemoved);
    }
}

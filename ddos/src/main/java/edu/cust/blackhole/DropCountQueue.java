package edu.cust.blackhole;

import java.util.Iterator;
import java.util.LinkedList;

public class DropCountQueue {
	
	class DropCount{
		long time;
		long count;
	}
	
	LinkedList<DropCount> queue = new LinkedList<>();
	
	public synchronized void plusOne() {
		long t = System.currentTimeMillis() / 1000;
		if(queue.isEmpty() || queue.getLast().time != t) {
			DropCount dc = new DropCount();
			dc.time = t;
			dc.count = 1;
			queue.add(dc);
			return;
		}
		queue.getLast().count++;
	}
	
	public synchronized long sum(long t) {
		if(queue.isEmpty())
			return 0;
		//long t = System.currentTimeMillis() / 1000 - 10;
		long c = 0;
		for (Iterator<DropCount> i = queue.iterator(); i.hasNext();) {
			DropCount dc = i.next();
			if(dc.time <= t) {
				i.remove();
			} else {
				c += dc.count;
			}
		}
		return c;
	}

}

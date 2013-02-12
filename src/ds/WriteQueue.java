package ds;

import io.DataStreamPair;

import java.util.LinkedList;
import java.util.Queue;

public class WriteQueue {
	private Queue<DataStreamPair> q; 
	
	public WriteQueue () {
		q = new LinkedList <DataStreamPair> ();
	}
	
	public synchronized void add (DataStreamPair bsp) {
		q.add (bsp);
	}
	
	public synchronized DataStreamPair remove () {
		if (!q.isEmpty()) {
			return q.remove();
		}
		else
			return null;
	}
	
	public int getSize () {
		return q.size();
	}
}
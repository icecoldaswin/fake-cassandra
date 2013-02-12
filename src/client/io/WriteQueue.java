package client.io;

import java.util.LinkedList;
import java.util.Queue;

public class WriteQueue {
	private Queue<BlockStreamPair> q; 
	
	public WriteQueue () {
		q = new LinkedList <BlockStreamPair> ();
	}
	
	public void add (BlockStreamPair bsp) {
		q.add (bsp);
	}
	
	public BlockStreamPair remove () {
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
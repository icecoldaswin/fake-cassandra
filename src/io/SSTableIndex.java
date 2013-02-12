package io;

import java.util.Set;
import java.util.TreeMap;

import ds.IndexEntry;

public class SSTableIndex implements Store1DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1413097385750575246L;
	private TreeMap <String, IndexEntry> keyOffsetMap;
	
	public SSTableIndex () {
		keyOffsetMap = new TreeMap <String, IndexEntry> ();
	}
	
	public long getSize () {
		return -1L;
	}
	
	public synchronized void addIndexEntry (String key, IndexEntry ie) {
		keyOffsetMap.put(key, ie);
	}
	
	public IndexEntry getIndexEntry (String key) {
		System.out.println (keyOffsetMap.get (key).toString());
		return keyOffsetMap.get (key);
	}
	
	public Set<String> keySet () {
		return keyOffsetMap.keySet();
	}
	
//	public long getClosestHigherKeyOffset (String key) {
//		return keyOffsetMap.get (keyOffsetMap.higherKey(key)).longValue();
//	}
//	
//	public long getClosestLowerKeyOffset (String key) {
//		return keyOffsetMap.get (keyOffsetMap.lowerKey(key)).longValue();
//	}
}

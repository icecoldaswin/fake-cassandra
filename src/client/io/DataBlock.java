package client.io;

import java.util.concurrent.ConcurrentHashMap;


public class DataBlock implements StorageBlock {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2113911639413500178L;
	
	ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String> ();
	int maxSize = 64 * 1024 * 1024;
	int size = 0;
	
	public void addStuff (String key, String value) throws Exception {
		if (size < (maxSize - maxSize/10)) {
			data.put(key, value);
			size += 128;
		}
		else
			throw new Exception ("BlockIsFull");
	}
	
	public String readStuff (String key) {
		return data.get (key);
	}
	
	public int getSize () {
		return data.size();
	}
}

package ds;

import java.io.Serializable;
import java.util.ArrayList;

public class CFMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274780882033924382L;
	String KeyName;
	long size;
	
	public CFMetadata () {
		
	}
	
	public void setKeyName (String KeyName) {
		this.KeyName = KeyName;
	}
	
	public void setCfSize (long size) {
		this.size = size;
	}
	
	public long getCfSize () {
		return size;
	}
}

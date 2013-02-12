package client.ds;

import java.io.Serializable;
import java.util.ArrayList;

public class CFMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3869196677171804483L;
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
	
}

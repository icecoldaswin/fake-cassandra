package ds;

import java.io.Serializable;

public class IndexEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4116918339108676705L;
	private Long location;
	private Long size;
	
	public IndexEntry (long location, long size) {
		this.location = location;
		this.size = size;
	}
	
	public IndexEntry () {
		location = new Long (-1L);
		size = new Long (-1L);
	}
	
	public void setSize (long size) {
		this.size = new Long (size);
	}
	
	public long getSize () {
		return size.longValue();
	}
	
	public long getLocation () {
		return location.longValue();
	}
	
	public void setLocation (long location) {
		this.location = new Long (location);
	}
}

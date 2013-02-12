package ds;

import java.io.Serializable;

public class Record  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 765248216990463626L;
	String key;
	ColumnVector columns;
	
	public Record (String key, ColumnVector cv) {
		this.key = key;
		this.columns = cv;
	}
	
	public String getKey () {
		return key;
	}
	
	public ColumnVector getColumnVector () {
		return columns;
	}
}

package ds.exceptions;

public class ColumnAlreadyExists extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6374302872541251422L;
	final String message = "Such column already exists in this column family";
	public ColumnAlreadyExists () {
		super ();
	}
}

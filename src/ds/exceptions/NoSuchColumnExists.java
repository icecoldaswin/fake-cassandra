package ds.exceptions;

public class NoSuchColumnExists extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6374302872541251422L;
	final String message = "No such column exists in this column family.";
	public NoSuchColumnExists () {
		super ();
	}
}

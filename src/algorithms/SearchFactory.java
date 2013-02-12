package algorithms;

public class SearchFactory {
	
	public ISearch getSearchAlgorithm (String type) {
		ISearch sa = null;
		if (type.equalsIgnoreCase ("LINEAR"))
			sa = (ISearch)new LinearSearchAlgorithm ();
		
		return sa;
	}
}

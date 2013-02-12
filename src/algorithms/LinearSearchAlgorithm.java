package algorithms;

import java.util.ArrayList;
import java.util.Iterator;

public class LinearSearchAlgorithm implements ISearch {

	@Override
	public int exists (ArrayList<String> searchableStringsList, String key) {
		// TODO Auto-generated method stub
		int index = -1;
		
		for (int i=0; i < searchableStringsList.size(); i++) {
			if (searchableStringsList.get (i).equalsIgnoreCase (key))
				index = i;
		}
		return index;
	}
	
}

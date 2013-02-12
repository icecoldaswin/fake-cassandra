package client.ds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class RecordSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7410571956085108241L;
	String key;
	ColumnVector columns;
	ConcurrentHashMap<String, ColumnVector> recordSet;
	long timestamp;
	
	public RecordSet () {
		columns = new ColumnVector ();
		recordSet = new ConcurrentHashMap <String, ColumnVector> ();
	}
	
	public void addRecord (String key, ColumnVector cv) {

		/*
		//	if (!columns.containsColumnName (column.name))
		//		columns.addColumn (column);
		//	else
		//		throw new ds.exceptions.ColumnAlreadyExists ();
		*/
		
		recordSet.put (key, cv);
	}

	public Iterator<String> keyIterator () {
		return recordSet.keySet().iterator();
	}
	
	public Iterator<Column> columnIterator (String key) {
		return recordSet.get(key).columnIterator();
	}
	
//	public void removeCOlumn (String ColumnName) throws ds.exceptions.NoSuchColumnExists {
//		if (!ColumnNames.remove (ColumnName))
//			throw new ds.exceptions.NoSuchColumnExists ();
//	}
}

package client.ds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.ISearch;
import algorithms.SearchFactory;

public class ColumnVector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9004348511204154025L;
	long size;
	ConcurrentHashMap <String, Column> columns;
	
	
	public ColumnVector () {
		columns = new ConcurrentHashMap<String, Column> ();
	}
	
	public void addColumn (Column c) {
		columns.put (c.name, c);
		size += c.size;
	}
	
//	public boolean containsColumnName (String columnName) {
//		ISearch searchAlgorithm = (new SearchFactory ()).getSearchAlgorithm ("LINEAR");
//		if (searchAlgorithm.exists (names, columnName) >= 0)
//			return true;
//		else
//			return false;
//	}
//	
//	public Iterator<String> columnNameIterator () {
//		return names.iterator();
//	}
//	
	public Iterator<Column> columnIterator () {
		return columns.values().iterator();
	}
	
	public Column get (String name) {
		return columns.get(name);
	}
}

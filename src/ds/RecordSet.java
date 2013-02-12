package ds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RecordSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7410571956085108241L;
	String key;
	ColumnVector columns;
	ConcurrentHashMap<String, ColumnVector> recordSet;
	TreeMap<String, ColumnVector> 			sortedRecordSet;
	long timestamp, waitForLock = 10L;
	Lock l;
	
	public RecordSet () {
		columns = new ColumnVector ();
		recordSet = new ConcurrentHashMap <String, ColumnVector> ();
		l = new ReentrantLock ();
	}
	
	public void addRecord (String key, ColumnVector cv) {
		recordSet.put (key, cv);
	}

	public ColumnVector getColumnVector (String key) {

		// Acquire lock
		while (!l.tryLock()) try {Thread.sleep (waitForLock);} catch (Exception e) {}
		
		if (recordSet != null) {
			l.unlock ();
			return recordSet.get (key);	
		}
		else {
			l.unlock ();
			return sortedRecordSet.get (key);
		}
	}

	public Iterator<String> keyIterator () {
		Iterator<String> keyIterator = null;

		// Acquire lock
		while (!l.tryLock()) try {Thread.sleep (waitForLock);} catch (Exception e) {}
		
		if (recordSet != null)  
			keyIterator = recordSet.keySet().iterator();
		else 
			keyIterator = sortedRecordSet.keySet().iterator();
		
		l.unlock ();
		return keyIterator;
	}
	
	public Iterator<Column> columnIterator (String key) {
		return recordSet.get(key).columnIterator();
	}
	
	public synchronized void sortByKeys () {
		
		// Acquire lock
		while (!l.tryLock()) try {Thread.sleep (waitForLock);} catch (Exception e) {}
		
		sortedRecordSet = new TreeMap<String, ColumnVector> (recordSet);
		recordSet = null;
		l.unlock();
	}
	
	public int getEntryCount () {
		return recordSet.size ();
	}
//	public void removeCOlumn (String ColumnName) throws ds.exceptions.NoSuchColumnExists {
//		if (!ColumnNames.remove (ColumnName))
//			throw new ds.exceptions.NoSuchColumnExists ();
//	}
}

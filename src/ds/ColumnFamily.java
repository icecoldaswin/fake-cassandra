package ds;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import util.ByteBufferUtil;

public class ColumnFamily implements Serializable {

		
		private static final long serialVersionUID = -4172053251375389830L;
		CFMetadata cfmd;
		RecordSet recordSet;
		
		public ColumnFamily () {
			cfmd = new CFMetadata();
			recordSet = new RecordSet ();
		}
		
		public void set (byte[] key, byte[] columnVector) {
			cfmd.size += columnVector.length + key.length;
			ColumnVector cv = null;
			try {
				cv = (ColumnVector) ByteBufferUtil.getObject(columnVector);
				recordSet.addRecord((String)ByteBufferUtil.getObject (key), cv);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		public CFMetadata getMetaData () {
			return cfmd;
		}
		
		public RecordSet getRecordSet () {
			return recordSet ;
		}
		
		public void sortByKeys () {
			
		}
		
		public void list () {
			
			Iterator<String> recIterator = recordSet.keyIterator();
			while (recIterator.hasNext()) {
				String key = recIterator.next();
				System.out.print (key);
				
				Iterator<Column> colIterator = recordSet.columnIterator (key);
				
				while (colIterator.hasNext()) {
					System.out.print ('-');
					Column col = (Column)colIterator.next ();
					System.out.print ("("+col.name+", "+col.value+")");
				}
				System.out.println ();
			}
		}
}

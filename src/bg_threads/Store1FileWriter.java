package bg_threads;

import io.DataStreamPair;
import io.SSTableIndex;

import java.io.*;
import java.util.Iterator;

import util.ByteBufferUtil;

import ds.ColumnFamily;
import ds.IndexEntry;
import ds.Record;
import ds.RecordSet;
import ds.WriteQueue;
import logging.Logger;





public class Store1FileWriter implements Runnable {
	ObjectOutputStream objectStream;
	ByteArrayOutputStream writeStream;
	WriteQueue writableQueue;
	Logger l;
	int ID;
	
	public Store1FileWriter (int ID) {
		this.ID = ID;
		
	}
	
	public void setQueue (WriteQueue writableQueue) {
		this.writableQueue = writableQueue;
	}
	
	public void setLogger (Logger l) {
		this.l = l;
	}
	
	public void run () {
		while (true) {
			try {
				DataStreamPair bsp = writableQueue.remove();
				SSTableIndex ind = new SSTableIndex ();
				
				if (bsp != null) {
	//				long filePosxxition = fileStream.getChannel().position();
					l.info (" Thread ["+ID+"] Found a block, dumping to file: "+bsp.getCanonicalFilePath());
					long start = System.currentTimeMillis();
					
					RecordSet rs = bsp.getColumnFamily().getRecordSet();
					rs.sortByKeys();
					Iterator<String> it = rs.keyIterator();
					
					while (it.hasNext()) {
						writeStream = new ByteArrayOutputStream ();
						objectStream = new ObjectOutputStream (writeStream);
						String key = (String) it.next();
						Record r = new Record (key, rs.getColumnVector(key));
						IndexEntry ie = new IndexEntry ();
						ie.setLocation (bsp.getOutputFile().getChannel().position());
						objectStream.writeObject (r);
						writeStream.flush ();
						objectStream.flush ();
						byte buffer[] = writeStream.toByteArray();
						
//						Record rrrr = (Record)new ObjectInputStream (new ByteArrayInputStream (buffer)).readObject(); 
//						System.out.println (rrrr.getKey() + "-" + writeStream.toByteArray().length);
								
						bsp.getOutputFile ().write (buffer);
						ie.setSize (buffer.length);
						ind.addIndexEntry (key, ie);
					}

					System.out.println (" Thread ["+ID+"] Dump complete in "+ (System.currentTimeMillis() - start)+ " ms.");
					File indexFile = new File (bsp.getCanonicalFilePath ().replace(".sst", ".idx"));
					ObjectOutputStream indexStream = new ObjectOutputStream (new FileOutputStream (indexFile));
					indexStream.writeObject (ind);
					indexStream.close ();

					
					try {
						Runtime.getRuntime().gc();
					}
					catch (Throwable e) {
						e.printStackTrace ();
					}
				}
				if (writableQueue.getSize() <= 0)
					try {
						System.out.println ("Sleeping..");
						Thread.sleep (3000);
					}
					catch (InterruptedException ie) {
						ie.printStackTrace();
					}
	
			}
			catch (Exception e) {
				e.printStackTrace ();
			}
		}
	}
}


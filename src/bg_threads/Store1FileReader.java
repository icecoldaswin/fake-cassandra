package bg_threads;

import io.SSTableIndex;

import java.io.*;
import java.util.Iterator;

import client.util.ByteBufferUtil;

import ds.Column;
import ds.IndexEntry;
import ds.Record;
import ds.WriteQueue;
import logging.Logger;


public class Store1FileReader implements Runnable {
	ObjectInputStream readStream;
	Logger l;
	int ID;
	String filePrefix = "/Users/aswini/SSTables/SSTableFile", indexSuffix = ".idx", fileSuffix = ".sst";
	int ssTableFileID = 1;
	boolean continue1 = true;
	int key = 0;
	
	public Store1FileReader (int ID) {
		this.ID = ID;
	}
	
	public void setLogger (Logger l) {
		this.l = l;
	}
	
	public void run () {
		Record r;
		File fileSSTable, indexFile;
		FileInputStream ssTableInputStream, indexInputStream;
		
		
		
		while (key < 10000) {
			key++;
			ssTableFileID = 1;
			while (continue1) {
	
				try {
					indexFile   = new File (filePrefix+""+ssTableFileID+""+indexSuffix);
					indexInputStream = new FileInputStream (indexFile);
					SSTableIndex ssTableIndex = (SSTableIndex) new ObjectInputStream (indexInputStream).readObject(); 
					indexInputStream.close();
					
					IndexEntry offset = ssTableIndex.getIndexEntry (key+"");
					
					if (offset != null) {
						fileSSTable = new File (filePrefix+""+ssTableFileID+""+fileSuffix);
						ssTableInputStream = new FileInputStream (fileSSTable);
						boolean continue2 = true;
						readStream = new ObjectInputStream (ssTableInputStream);
	//					while (continue2) {
							try {
				
				//				long start = System.currentTimeMillis();
				//				Long nextObjSizeInBytes = (Long)readStream.readLong();
				//				byte[] b = new byte [(int)nextObjSizeInBytes.longValue()];
//								readStream.skip (offset);
								r = (Record) readStream.readObject ();
								Iterator<Column> it = r.getColumnVector().columnIterator();
								
								System.out.println ("Probe key: "+key+" Record: "+r.getKey()+" is in file: "+ssTableFileID);
								
							}
							catch (Exception e) {
								e.printStackTrace ();
								continue2 = false;
								try {
									Thread.sleep (3000);
								}
								catch (InterruptedException ie) {
									
								}
							}
	//					}
					}

					ssTableFileID ++;
					if (ssTableFileID == 5) 
						break;
				}
				catch (IOException ioe) {
					ioe.printStackTrace ();
					continue1 = false;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
		}
	}
}


import io.SSTableIndex;

import java.io.*;
import java.util.Iterator;
import java.util.Map.Entry;

import client.util.ByteBufferUtil;

import ds.Column;
import ds.IndexEntry;
import ds.Record;
import ds.WriteQueue;
import logging.Logger;


public class SSTableReader {
	ObjectInputStream readStream;
	Logger l;
	int ID;
	String filePrefix, indexSuffix, fileSuffix;
	int ssTableFileID;
	boolean continue1 = true;
	int key;
	
	public SSTableReader (int ID) {
		this.ID = ID;
		filePrefix = "/Users/aswini/SSTables/SSTableFile";
		indexSuffix = ".idx";
		fileSuffix = ".sst";
		ssTableFileID = 1;
		key = 0;
		
		runIt ();
	}
	
	public void runIt () {
		Record r;
		RandomAccessFile ssTableDataFile;
		File indexFile;
		FileInputStream ssTableInputStream, indexInputStream;
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter (new FileWriter ("/Users/aswini/index-datafile-contents.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		while (key < 10000) {
			key++;
			ssTableFileID = 0;
			while (ssTableFileID++ < 7) {
				try {
					indexFile   = new File (filePrefix+""+ssTableFileID+""+indexSuffix);
					ssTableDataFile = new RandomAccessFile (filePrefix+""+ssTableFileID+""+fileSuffix, "r"); 
					indexInputStream = new FileInputStream (indexFile);
					
					SSTableIndex ssTableIndex = (SSTableIndex) new ObjectInputStream (indexInputStream).readObject(); 
					indexInputStream.close();
//					while (true) { // Keep reading until the EOF exception is hit
//							IndexEntry ie = ssTableIndex.getIndexEntry (key+"");
							Iterator <String> idxIterator = ssTableIndex.keySet ().iterator();
							while (idxIterator.hasNext() && ssTableFileID < 2) {
								long actualPositionInFile = ssTableDataFile.getChannel().position();
								String indexKey = idxIterator.next();
								IndexEntry iE = ssTableIndex.getIndexEntry (indexKey);
								System.out.println ("file# "+ssTableFileID+" Idx: "+indexKey+"-"+iE.getLocation()+" ["+iE.getSize()+"]");
								pw.println ("file# "+ssTableFileID+" Idx: "+indexKey+"-"+iE.getLocation()+" ["+iE.getSize()+"]");
								
								long positionAsPerIndex = iE.getLocation();

								ssTableDataFile.getChannel().position (positionAsPerIndex);
								byte[] buf = new byte [(int)iE.getSize()];
								ssTableDataFile.read (buf) ;
								Record rec = null;
								try {
									rec = (Record) new ObjectInputStream (new ByteArrayInputStream (buf)).readObject();
									String key = rec.getKey();
									
									System.out.println ("file# "+ssTableFileID+" SST: "+key+"-"+actualPositionInFile);
									System.out.println ("--------------------------------"+positionAsPerIndex+"-----------------------------------------");
									pw.println ("file# "+ssTableFileID+" SST: "+key+"-"+actualPositionInFile);
									pw.println ("-------------------------------------------------------------------------");
//									pos ++;
//									System.out.println ("Setting position: "+(ssTableInputStream.getChannel().position (currentPosition).position()));
								}
								catch (Exception e) {
									e.printStackTrace();
								}
								
							}
//					}
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
//	}
	
	public void setLogger (Logger l) {
		this.l = l;
	}
	
	public static void main (String ap[]) {
		SSTableReader sstr = new SSTableReader (1);
	}
}


import io.SSTableIndex;

import java.io.*;
import java.util.Iterator;
import java.util.Map.Entry;

import client.util.ByteBufferUtil;

import ds.Column;
import ds.Record;
import ds.WriteQueue;
import logging.Logger;


public class SSTableSingleKeyReader {
	ObjectInputStream readStream;
	ByteArrayInputStream bin;
	Logger l;
	int ID;
	String filePrefix, indexSuffix, fileSuffix;
	int ssTableFileID;
	boolean continue1 = true;
	int key;
	
	public SSTableSingleKeyReader (int ID) {
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
			ssTableFileID = 1;

				try {
					indexFile   = new File (filePrefix+""+ssTableFileID+""+indexSuffix);
					ssTableDataFile = new RandomAccessFile (filePrefix+""+ssTableFileID+""+fileSuffix, "r"); 

//					ssTableInputStream = new FileInputStream (fileSSTable.getFD());

//					readStream = new ObjectInputStream (ssTableInputStream);
					long skipLength = 12560654L;
//					long currentPosition = ssTableInputStream.getChannel().position ();
					long currentPosition = ssTableDataFile.getFilePointer();
					
//					System.out.println ("Asking to skip: "+(skipLength-currentPosition)+" Number of bytes skipped: "+ssTableInputStream.skip (skipLength-currentPosition));
					PrintWriter eW = new PrintWriter (new FileWriter (new File ("/Users/aswini/errors.txt")));
//					for (long range = skipLength - 1000; range < skipLength + 1000; range ++) {
					
						try {
							ssTableDataFile.getChannel().position (skipLength);
							
							currentPosition = ssTableDataFile.getChannel().position ();
							System.out.println ("Current position: "+(currentPosition));
							
							byte buffer[] = new byte [1403];
							ssTableDataFile.read (buffer);
							
							readStream = new ObjectInputStream (new ByteArrayInputStream (buffer));
							Record rec = (Record) readStream.readObject ();
							String key = rec.getKey();
							
							System.out.println ("file# "+ssTableFileID+" SST: "+key+"-"+currentPosition+":::"+rec.getColumnVector().toString());
							System.out.println ("-------------------------------------------------------------------------");
						}
						catch (Exception e) {
							eW.println (e.getMessage());
							e.printStackTrace ();
						}
//					}
					
					}
				catch (IOException ioe) {
					ioe.printStackTrace ();
					continue1 = false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//	}
	
	public void setLogger (Logger l) {
		this.l = l;
	}
	
	public static void main (String ap[]) {
		SSTableSingleKeyReader sstr = new SSTableSingleKeyReader (1);
	}
}

;
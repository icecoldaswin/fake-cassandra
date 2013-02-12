package bg_threads;
import io.DataStreamPair;
import io.SSTable;
import io.Store1DataObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;



import logging.Logger;
import util.ByteBufferUtil;
import algorithms.*;
import ds.*;
import ds.exceptions.*;

class Monitor implements Runnable {
	
	int[]    monitoredVals;
	String[] monitoredVars;
	
	public Monitor () {
		
	}
	
	public void run () {
		while (true) {
			for (int i = 0; i < monitoredVals.length; i++) {
				System.out.println (monitoredVars[i] + ": " + monitoredVals[i]);
			}
			try {
				System.out.println ("Memory used: "+( Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) );
				Thread.sleep (10000);
			}
			catch (InterruptedException ie) {
				
			}
		}
	}
}


public class Store1_Server {
	
	public static void main (String a[]) {
		try {
			
				/**
				 *  Generic declarations
				 */
			
				long MAX_IN_MEMORY_CF_SIZE = 16 * 1024 * 1024;
				java.util.Random randomGenerator = new java.util.Random ();
				Logger l = new Logger ();
				int[]    monitoredVals = new int [1];
				String[] monitoredVars = {"Records inserted so far"};
				
				/**
				 *  Data structures
				 */
		
				ColumnFamily cf = new ColumnFamily ();
				WriteQueue wq = new WriteQueue ();
				ColumnVector cv;
				
				/**
				 * 	File handlers
				 */
				String filePrefix = "/Users/aswini/SSTables/SSTableFile", fileSuffix = ".sst";
				File ssTableFile; 
//				ByteArrayOutputStream ssTableFileOutputStream;
				int ssTableFileID = 0;
				
				/**
				 *  Background threads
				 */
			
				Store1FileWriter writerThread1 = new Store1FileWriter (1);
				new Thread (writerThread1).start ();
				writerThread1.setQueue (wq);
				writerThread1.setLogger (l);
				
				Store1FileWriter writerThread2 = new Store1FileWriter (2);
				new Thread (writerThread2).start ();
				writerThread2.setQueue (wq);
				writerThread2.setLogger (l);
				
				Store1FileWriter writerThread3 = new Store1FileWriter (3);
				new Thread (writerThread3).start ();
				writerThread3.setQueue (wq);
				writerThread3.setLogger (l);
				
				Monitor monThread = new Monitor ();
				
				monThread.monitoredVals = monitoredVals;
				monThread.monitoredVars = monitoredVars;
				
				new Thread (monThread).start ();
				
				
				int i = 0;
				
				for (monitoredVals [0] = i = 0; i < 10000000; i++) {
					monitoredVals [0] = i;
					cv = new ColumnVector ();

					for (int j = 0; j < randomGenerator.nextInt(10); j++) {
						byte[] name = new byte[64], value = new byte[64];
						name = ByteBufferUtil.getBytes (randomGenerator.nextInt (100000)+"");
						name  = ByteBufferUtil.getBytes (randomGenerator.nextInt (50000)+"");
						value = ByteBufferUtil.getBytes (randomGenerator.nextInt (123829)+"");
						cv.addColumn (new Column (name, value, System.currentTimeMillis()));
					}
		
					if (cf.getMetaData().getCfSize() + cv.getSize() >= MAX_IN_MEMORY_CF_SIZE ) {
						ssTableFileID ++;
						try {
							ssTableFile = new File (filePrefix+""+ssTableFileID+""+fileSuffix);
//							ssTableFileOutputStream = new ByteArrayOutputStream ();
							
							wq.add (new DataStreamPair (cf, new RandomAccessFile (ssTableFile, "rw"), ssTableFile));
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println ("Queue size: "+wq.getSize());
						System.out.println ("Last ColumnFamily size: "+cf.getMetaData().getCfSize());
						
						cf = new ColumnFamily ();
					}
					cf.set (ByteBufferUtil.getBytes (randomGenerator.nextInt (100000)+""), ByteBufferUtil.getBytes(cv));
				}
				
				Store1FileReader readerThread0 = new Store1FileReader (0);
				new Thread (readerThread0).start ();
				
//				cf.list();
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

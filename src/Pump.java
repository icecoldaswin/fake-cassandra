import io.DataStreamPair;
import io.SSTable;
import logging.Logger;

import java.io.File;
import java.io.FileOutputStream;

import bg_threads.Store1FileWriter;


import ds.WriteQueue;

public class Pump {

	
	public static void main (String ap[]) {
		try {
			File dataFile; //, indexFile;
			dataFile  = new File ("c:/users/aswini/dataFile.java");
//			indexFile = new File ("c:/users/aswini/indexFile.java");
			FileOutputStream dataStream = new FileOutputStream (dataFile);
			Logger l = new Logger ();
			SSTable blk = new SSTable ();
			byte[] b = new byte[64];
			WriteQueue wq = new WriteQueue ();
			
			Store1FileWriter fw = new Store1FileWriter ();
			fw.setLogger (l);
			fw.setQueue (wq);
			new Thread (fw).start ();
			
			for (int i=0; i<1000000; i++) {
				new java.util.Random ().nextBytes(b);
				try {
					blk.addStuff (i+"", b.toString());
				}
				catch (Exception e) {
					DataStreamPair bsp = new DataStreamPair ();
					bsp.setBlock (blk);
					bsp.setStream (dataStream);
					wq.add (bsp);
					blk = new SSTable ();
				}
				if (i % 100000 == 0) {
					System.out.println ("Written: "+ i);
					System.out.println ("Queue size: "+ wq.getSize());
					System.out.println ("Mem used: "+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					try {
						Thread.sleep (5000); //new java.util.Random ().nextInt(3000));
					}
					catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}


package client.io;

import java.io.*;
import logging.Logger;





public class FileWriter implements Runnable {
	ObjectOutputStream writeStream;
	WriteQueue writableQueue;
	Logger l;
	
	public void setQueue (WriteQueue writableQueue) {
		this.writableQueue = writableQueue;
	}
	
	public void setLogger (Logger l) {
		this.l = l;
	}
	
	public void run () {
		while (true) {
			try {
				BlockStreamPair bsp = writableQueue.remove();
				
				if (bsp != null) {
	//				long filePosition = fileStream.getChannel().position();
					l.info ("Found a block, dumping ");
					long start = System.currentTimeMillis();
					writeStream = new ObjectOutputStream (bsp.getStream());
					writeStream.writeObject (bsp.getBlock());
					System.out.println ("Dump complete in "+ (System.currentTimeMillis() - start)+ " ms.");
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


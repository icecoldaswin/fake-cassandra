package logging;

import java.io.File;
import java.io.PrintWriter;


public class Logger {
	File logFile = new File ("/Users/aswini/logFile.java");
	PrintWriter logStream;
	
	public Logger () {
		try {
			logStream = new PrintWriter (logFile);
		}
		catch (Exception e) {
			
		}
	}
	
	public synchronized void info (String m) {
		try {
			System.out.println ("[I]"+m);
			logStream.println ("[I]"+m);
		}
		catch (Exception e) {
			e.printStackTrace ();
		}
	}
	
	public synchronized void debug (String m) {
		try {
			System.out.println ("[D]"+m);
			logStream.println ("[D]"+m);
		}
		catch (Exception e) {
			e.printStackTrace ();
		}
	}
	
	public synchronized void error (String m) {
		try {
			System.out.println ("[E]"+m);
			logStream.println ("[E]"+m);
		}
		catch (Exception e) {
			e.printStackTrace ();
		}
	}
}

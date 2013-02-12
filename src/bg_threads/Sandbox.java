package bg_threads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import ds.IndexEntry;
import ds.Record;

public class Sandbox {
	
	public Sandbox () {
		
	}
  public void sandsand (Record ir) {
    try {
      // convert object to bytes
//      IndexEntry ir = new IndexEntry ();
//      ir.setLocation(991292L);
//      ir.setSize(991292L);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(ir);
      oos.flush();
      byte[] buf = baos.toByteArray();

      // convert back from bytes to object
      ObjectInputStream ois = 
        new ObjectInputStream(new ByteArrayInputStream(buf));
      ir = null;
      ir = (Record)ois.readObject();
      ois.close();

      System.out.println (ir.getKey());
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }
}

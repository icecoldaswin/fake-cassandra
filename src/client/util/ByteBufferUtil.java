package client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteBufferUtil {
	public static Object getObject (byte[] bytes) {
		Object o = null;
		
		try {
			o = new ObjectInputStream (new ByteArrayInputStream (bytes)).readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}
	
	public static byte[] getBytes (Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream ();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream (bos);
			oos.writeObject(o);
			oos.flush ();
			oos.close ();
			bos.close ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bos.toByteArray ();
	}
}

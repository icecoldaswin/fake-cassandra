package client.ds;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

import util.ByteBufferUtil;

public class Column implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5593085138142568704L;
	String name;
	String value;
	long timestamp, size;
	
	public Column (byte[] name, byte[] value, long timestamp) {
		this.name = new String (name);
		this.value = new String (value);
		this.timestamp = timestamp;
		
		this.size = name.length + value.length + 8; // 8 bytes (64-bit) for 'long' time stamp
	}
	
	public void setColumn (byte[] byteArray) {
		this.size = byteArray.length;
		Column col = null;
		try {
			col = (Column) ByteBufferUtil.getObject (byteArray);
			setName (col.name);
			setValue (col.value);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void setValue (String value) {
		this.value = value;
	}
	
}

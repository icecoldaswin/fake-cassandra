package io;

import java.util.concurrent.ConcurrentHashMap;

import ds.ColumnFamily;


public class SSTable implements Store1DataObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2113911639413500178L;
	
	ColumnFamily cf;
	
	public void setColumnFamily (ColumnFamily cf) {
		this.cf = cf;
	}
	
	public long getSize () {
		return cf.getMetaData().getCfSize();
	}
}

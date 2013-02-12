package io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import ds.ColumnFamily;

public class DataStreamPair {
	private ColumnFamily columnFamily;
	private RandomAccessFile outputFile;
	private File fileSSTable;
	
	public DataStreamPair (ColumnFamily columnFamily, RandomAccessFile outputFile, File fileSSTable) {
		this.columnFamily = columnFamily;
		this.outputFile = outputFile;
		this.fileSSTable = fileSSTable;
	}
	
	public DataStreamPair () {
	}
	
	public ColumnFamily getColumnFamily () {
		return this.columnFamily;
	}
	
	public String getCanonicalFilePath () throws IOException {
		return this.fileSSTable.getCanonicalPath();
	}
	
	public RandomAccessFile getOutputFile () {
		return this.outputFile;
	}
	
	public void setBlock (ColumnFamily columnFamily) {
		this.columnFamily = columnFamily;
	}
	
	public void setOutputFile (RandomAccessFile outputFile) {
		this.outputFile = outputFile;
	}
}

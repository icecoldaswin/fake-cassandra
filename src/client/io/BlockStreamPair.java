package client.io;

import java.io.FileOutputStream;

public class BlockStreamPair {
	private StorageBlock block;
	private FileOutputStream stream;
	
	public BlockStreamPair (StorageBlock block, FileOutputStream stream) {
		this.block = block;
		this.stream = stream;
	}
	
	public BlockStreamPair () {
	}
	
	public StorageBlock getBlock () {
		return this.block;
	}
	
	public FileOutputStream getStream () {
		return this.stream;
	}
	
	public void setBlock (StorageBlock block) {
		this.block = block;
	}
	
	public void setStream (FileOutputStream stream) {
		this.stream = stream;
	}
}

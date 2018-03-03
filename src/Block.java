/*
 * Firstly, Create class Block that make up the block-chain
 */

import java.util.Date;



public class Block {
	public String hash;  //hash or digital signature of current block
	public String previousHash; //hash or signature of previous block
	private String data ; // our data will be a simple message
	private long timeStamp ; // as number of milliseconds since 1/1/1970
	
	private int nonce;
	
	public Block(String data, String previousHash){
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	//Apply applySHA256 to calculate the hash - Calculate the Hash from ALL PARTS of block (previousHash, data, timeStamp)
	public String calculateHash(){
		String calculatedhash = StringUtil.applySHA256(this.previousHash + 
				                                       Long.toString(this.timeStamp) + 
				                                       Integer.toString(nonce) +
 				                                       this.data);
		return calculatedhash;
	}
	
	//MineBlocks method : takes in an int called difficulty, this is the number of 0's they must solve for. Low difficuty like 1 or 2 can be solved nearly
	//instantly on most computers. 
	
	public void minBlock(int difficulty){
		//Create a string with difficulty * "0"
		String target = new String(new char[difficulty]).replace('\0', '0');
		
		while (!hash.substring(0,difficulty).equals(target)){
			nonce ++;
			hash = calculateHash();
		}
		
		System.out.println("Block mined !!! " + hash);
		
	}
}

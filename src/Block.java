/*
 * Firstly, Create class Block that make up the block-chain
 */

import java.util.ArrayList;
import java.util.Date;



public class Block {
	public String hash;  //hash or digital signature of current block
	public String previousHash; //hash or signature of previous block
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message
	private String data ; // our data will be a simple message
	private long timeStamp ; // as number of milliseconds since 1/1/1970
	
	private int nonce;
	
	public Block(String data, String previousHash){
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash();
	}
	
	//We update our Block constructor as we no longer need pass in string data and included the merkle root in the calculate HASH method
	public Block(String previousHash){
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = newCalculateHash();
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
	
	//Calculate new HASH based on blocks contents
	public String newCalculateHash(){
		String calculatedhash = StringUtil.applySHA256( 
															this.previousHash +
															Long.toString(this.timeStamp) +
															Integer.toString(nonce) + 
															this.merkleRoot
				);
		return calculatedhash;
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
	
	public void mineBlock(int difficulty){
		//Create a string with difficulty * "0"
		String target = new String(new char[difficulty]).replace('\0', '0');
		
		while (!hash.substring(0,difficulty).equals(target)){
			nonce ++;
			hash = calculateHash();
		}
		
		System.out.println("Block mined !!! " + hash);
		
	}
	
	//Increase nonce value until hash target in reached
	public void newMineBlock(int difficulty){
		this.merkleRoot = StringUtil.getMerkleRoot(transactions);
		//Create a string with difficulty * "0"
		String target = StringUtil.getDificultyString(difficulty); 
		while(!hash.substring(0,difficulty).equals(target)){
			nonce ++;
			hash = newCalculateHash();
		}
		System.out.println("Block Mined !! " + hash);
	}
	
	
	//Add transactions to this block : will only return true if the transaction has been successfully added
	public boolean addTransaction(Transaction transaction){
		//process transaction and check if valid, unless block is genesis block then ignore
		if (transaction == null) return false;
		if (previousHash != "0"){
			if (!transaction.processTransaction()){
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction successfully added to Block");
		
		return true;
		
	}
}

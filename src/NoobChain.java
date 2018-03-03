import java.util.ArrayList;

import com.google.gson.GsonBuilder;

/*
 * Block chain is just a chain/list of blocks. Each block in the block-chain will have it OWN digital signature, contain digital signature of the PREVIOUS block
 * and have some data (this data could be transactions for example)
 * 
 *  HASH == Digital Signature
 *  
 *  Each block does not just only contain the hash of the block before it, but its own hash is in part, calculated from the previous hash. If the previous block's 
 *  data is changed then the previous block's hash will be changed (since it is calculated in part, by the data) in turn affecting all the hashes of the blocks 
 *  there after. Calculating and Comparing the HASHES allow us to see if a blockchain is invalid or not.
 *  
 *  What does it mean ? Changing any data in this list, will change the signature of all blocks and break the chain 
 */


public class NoobChain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	
	public static void main(String[] args){
		//Block genesisBlock = new Block("Hi, I am the first Block - Genesis Block","0");
		//System.out.println("Hash for Block 1 : " + genesisBlock.hash);
		
		//Block secondBlock = new Block("Yo, I am the second block",genesisBlock.hash);
		//System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		//Block thirdBlock = new Block("Hey, I am the third block",secondBlock.hash);
		//System.out.println("Hash for block 3 : " + thirdBlock.hash);
		
		/**
		 * Each block now has its own digital signature based on its information and the signature of the previous block
		 */
		
		/**
		 * Lets store our blocks in an arraylist 
		 */
		blockchain.add( new Block("Hi, I am the first Block - Genesis Block","0"));
		System.out.println("Trying to Mine Block 1 ...");
		blockchain.get(0).minBlock(difficulty);
		
		blockchain.add( new Block("Yo, I am the second block", blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine Block 2 ...");
		blockchain.get(1).minBlock(difficulty);
		
		blockchain.add( new Block("Hey, I am the third block", blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine Block 3 ...");
		blockchain.get(2).minBlock(difficulty);
		
		System.out.println("\n BlockChain is Valud : " + isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\n The Block Chain : ");
		System.out.println(blockchainJson);
		
		/**
		 * Now we need a way to check the integrity of our blockchain
		 */
		
		/**
		 * On the bitcoin network, nodes share their blockchains and the longest valid chain is accepted.
		 * If some one ware to tamper with the data in your blockchain system
		 *    + Their blockchain would be invalid
		 *    + They would not be able to create a longer blockchain
		 *    + 
		 */
		
		
	}
	
	//Create an isChainValid() Boolean method that will loop through all blocks in the chain and compare the hashes. This method will need to check
	//the hash variable is actually equal to the calculated hash. AND the previous block's hash is equal to the previousHash variable
	
	//Any change to the blockchain's blocks will cause this method to FALSE
	public static boolean isChainValid(){
	    Block currentBlock;
	    Block previousBlock;
	    String hashTarget = new String(new char[difficulty]).replace('\0', '0');
	    
	    for(int i = 1 ; i < blockchain.size() ; i++){
	    	currentBlock = blockchain.get(i);
	    	previousBlock = blockchain.get(i-1);
	    	
	    	//Compare registered hash and calculated hash
	    	if (!currentBlock.hash.equals(currentBlock.calculateHash())){
	    		System.out.println("Current Hashes Not Equal");
	    		return false;
	    	}
	    	
	    	//Compare previous hash and registered hash
	    	if (!previousBlock.hash.equals(currentBlock.previousHash)){
	    		System.out.println("Previous Hashes not equals");
	    		return false;
	    	}
	    	
	    	//Check if hash is solved
	    	if (!currentBlock.hash.substring(0,difficulty).equals(hashTarget)){
	    		System.out.println("This block hasn't been mined");
				return false;
	    	}
	    }
	    return true;
	}

}

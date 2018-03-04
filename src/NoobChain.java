import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

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

/**
 * Blocks in the chain may receive many transactions and the block-chain might be very long, it could take eons to process a new transaction because we have to find and 
 * check its inputs. 
 *
 */

public class NoobChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();	
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	public static float minimumTransaction = 0.1f;
	public static int difficulty = 5;
	
	public static Wallet walletA;
	public static Wallet walletB;
	public static Wallet walletC;
	
	public static Transaction genesisTransaction;
	

	public static void playArround() {
		// Block genesisBlock = new Block("Hi, I am the first Block - Genesis
		// Block","0");
		// System.out.println("Hash for Block 1 : " + genesisBlock.hash);

		// Block secondBlock = new Block("Yo, I am the second
		// block",genesisBlock.hash);
		// System.out.println("Hash for block 2 : " + secondBlock.hash);

		// Block thirdBlock = new Block("Hey, I am the third
		// block",secondBlock.hash);
		// System.out.println("Hash for block 3 : " + thirdBlock.hash);

		/**
		 * Each block now has its own digital signature based on its information
		 * and the signature of the previous block
		 */

		/**
		 * Lets store our blocks in an arraylist
		 */
		blockchain.add(new Block("Hi, I am the first Block - Genesis Block", "0"));
		System.out.println("Trying to Mine Block 1 ...");
		blockchain.get(0).mineBlock(difficulty);

		blockchain.add(new Block("Yo, I am the second block", blockchain.get(blockchain.size() - 1).hash));
		System.out.println("Trying to Mine Block 2 ...");
		blockchain.get(1).mineBlock(difficulty);

		blockchain.add(new Block("Hey, I am the third block", blockchain.get(blockchain.size() - 1).hash));
		System.out.println("Trying to Mine Block 3 ...");
		blockchain.get(2).mineBlock(difficulty);

		System.out.println("\n BlockChain is Valud : " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\n The Block Chain : ");
		System.out.println(blockchainJson);

		/**
		 * Now we need a way to check the integrity of our blockchain
		 */

		/**
		 * On the bitcoin network, nodes share their blockchains and the longest
		 * valid chain is accepted. If some one ware to tamper with the data in
		 * your blockchain system + Their blockchain would be invalid + They
		 * would not be able to create a longer blockchain +
		 */
	}

	public static void main(String[] args) {
		//playArround();
		
		//playNoobCoins();
		
		play_NoobCoins_Transactions();
	}
	
	/*
	 * Create 2 walles A and B, generated transaction and signed it using A's private key
	 */
	public static void playNoobCoins(){
		//Setup Bouncy castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//Create new Wallets
		walletA = new Wallet();
		walletB = new Wallet();
		walletC = new Wallet();
		
		//Test Public key and private key
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		
		//Create a test transaction from WalletA to WalletB
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from public key
		System.out.println("Is Signature verified ??");
		System.out.println(transaction.verifiySignature());
		
		
		/**
		 * We test sending coins to and from wallets and update our block-chain validity check.
		 * (1) Introduce new coins into the mix
		 * (2) A genesis block which release 100 Noobcoins to WalletA
		 * (3) An updated chain validity check that takes into account transactions
		 * (4) Some test transactions to see
		 */
	}
	
	
	public static void play_NoobCoins_Transactions(){
		//Setup Bouncy castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//Create wallets
		walletA = new Wallet();
		walletB = new Wallet();		
		Wallet coinbase = new Wallet();
		
		//Create genesis transaction, which sends 100 Noobcoins to walletA
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(coinbase.privateKey); //manually sign the genesis transaction
		genesisTransaction.transactionID = "0"; //manually set the transaction id
		//manually add the transactions output
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionID));
		
		//its important to score our first transaction in the UTXOs list
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		System.out.println("Creating and Mining Genesis Block ... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);
		
		//testing 
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWalletA's balance is : " + walletA.getBalance());
		System.out.println("\nWalletA is attempting to send funds (40) to WalletB ...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey,40f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is : " + walletA.getBalance());
		System.out.println("\nWalletB's balance is : " + walletB.getBalance());
		
		
		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		
		
		Block block3 = new Block(block2.hash);
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds( walletA.publicKey, 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		addBlock(block3);
		
		isChainValid();
	}

	// Create an isChainValid() Boolean method that will loop through all blocks
	// in the chain and compare the hashes. This method will need to check
	// the hash variable is actually equal to the calculated hash. AND the
	// previous block's hash is equal to the previousHash variable

	// Any change to the blockchain's blocks will cause this method to FALSE
	public static boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//a temporary working list of unspent transactions at a given block state
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>();
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

		
		//loop through blockchain to check hashes
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			// Compare registered hash and calculated hash
			if (!currentBlock.hash.equals(currentBlock.newCalculateHash())) {
				System.out.println("Current Hashes Not Equal");
				return false;
			}

			// Compare previous hash and registered hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equals");
				return false;
			}

			// Check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
			//loop throught block chains transactions
			TransactionOutput tempOutput;
			for(int t = 0 ; t < currentBlock.transactions.size() ; t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);
				
				if (!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction(" + t + ") is IN-valid");
					return false;
				}
				if (currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are not equal to outputs on Transaction(" + t + ")");
					return false;
				}
				
				for(TransactionInput input : currentTransaction.inputs) {
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if (tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					
					if (input.UTXO.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is IN-valid");
						return false;
					}
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output : currentTransaction.outputs) {
					tempUTXOs.put(output.id,output);
				}
				
				if (currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				
				if (currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
				
				
			}
		}
		System.out.println("Block Chain is VALID");
		return true;
	}
	
	/**
	 * Now we have a working transaction system, we need  to implement it into our block-chain. We should replace the useless data we had in our blocks with an ArrayList of transactions
	 * However, there may be 1000s transactions in a single block, too many to include our hash calculation
	 */	
	public static void addBlock(Block newBlock){
		newBlock.newMineBlock(difficulty);
		blockchain.add(newBlock);
	}
}

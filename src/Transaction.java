import java.security.*;
import java.util.ArrayList;

/**
 * 
 * @author thanhnguyen
 *
 * Transactions and Signatures:
 * 
 * Each transaction will carry a certain amount of data:
 *  - Public Key (address) of the sender of funds
 *  - Public Key (address) of the receiver of funds
 *  - The value/amount of funds to be transferred
 *  - Inputs, which are references to previous transactions that prove the sender has funds to send
 *  - Outputs, which shows the amount relevant address received in the transaction. (These outputs are referenced as input in new transactions)
 *  - A cryptographic signature, that proves the owner of the address is the one sending this transaction and that the data has not been changed 
 */

/**
 * Theory :
 * What is the purpose of signatures and how do they works ?
 * 
 * Signature perform two very important tasks on our block-chain : (1) they allow only the owner to spend their coins, (2) they prevent others from tamperring with
 * their submitted transaction before a new block is mined
 * 
 * Send 2 Noobcoins 
 * Wallet software generates this transaction and submits it to miners to include in the next block
 * A miner attempts to change the recipient of the 2 coins to John. However, luckily, Bod had signed
 * the transaction data with his private key, allowing anybody to verify if the transaction data has been changed using Bob's public key
 *
 */

public class Transaction {
	
	public String transactionID ; //this is also the hash of the transaction
	public PublicKey sender; // sender address/public key
	public PublicKey reciepient; //reciepents address/public key
	public float value;
	public byte[] signature; //this is to prevent anybody else from sending funds in our wallet
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	
	private static int sequence = 0; // a rough count of how many transactions have been generated
	
	//Constructor :
	
	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs){
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	//This calculates the transaction hash (will be used ad its Id)
	private String calculateHash(){
		sequence++;
		return StringUtil.applySHA256(
							StringUtil.getStringFromKey(sender) +
							StringUtil.getStringFromKey(reciepient) +
							Float.toString(value) +
							sequence
				);
				
	}
	
	//Sign all the data we do not wish to tampered with.
	public void generateSignature(PrivateKey privateKey){
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
		signature = StringUtil.applyECDSASig(privateKey, data);
	}
	
	//Verify the data we signed hanot been tampered with
	//Signature will be verified by miners as a new transaction are added to a block
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

}

/**
 * How to cypto currency is owned
 * @author thanhnguyen
 * 
 * For you to own 1 bitcoin, you have to receive 1 Bitcoin. The ledger does not really add one bitcoin to you and minus one bitcoin from the sender
 * The sender referenced that he/she previously received one bitcoin, the a transaction ouput was created showing that 1 Bitcoin was sent to your address
 * 
 * Transaction inputs are references to previous transaction outputs
 * 
 * Your wallet balance is the sum of all unspent transaction output addressed to you === call UNSPENT transaction outpus : UTXO's
 *
 */

/**
 * 
 * The class will be used to reference TransactionOutputs that have not yet been spent. 
 * The transactionOutputId will be used to find the relevant TransactionOutput
 * 
 *
 */
public class TransactionInput {
	public String transactionOutputId ; // Reference to TransactionOutputs.transactionId
	public TransactionOutput UTXO; //Contains all UNSPENT transaction outputs
	
	public TransactionInput(String transactionOutputId){
		this.transactionOutputId = transactionOutputId;
	}

}

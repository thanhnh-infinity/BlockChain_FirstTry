import java.security.PublicKey;
/**
 * 
 * Transactions outputs will show the final amount sent to each party from the transaction. These, when referenced as inputs in new transactions, act as proof
 * that you have coins to send
 *
 */
public class TransactionOutput {
	public String id;
	public PublicKey reciepient; // also know as the new owner of these coins
	public float value; // the amount of coins they own
	public String parentTransactionId ; //the id of the transaction this output was created in
	
	//Constructor
	public TransactionOutput(PublicKey reciepient,float value, String parentTransactionId){
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = StringUtil.applySHA256(
											StringUtil.getStringFromKey(reciepient) +
											Float.toString(value) +
											parentTransactionId
				              );
	}
	
	//Check if coins are belongs to you
	public boolean isMine(PublicKey publicKey){
		return (publicKey == this.reciepient);
	}

}

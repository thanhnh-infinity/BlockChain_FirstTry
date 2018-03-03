/**
 * Coin ownership is transfered on the BlockChain as transactions, participants have an address which funds can be sent
 * to and from. In their basic Wallet, store the addresses, most wallets however, are also software able to make new
 * transactions on the BlockChain
 */

import java.security.*;
import java.security.spec.ECGenParameterSpec;


public class Wallet {
	
	//Wallet has to hold PUBLIC and PRIVATE keys
	
	/*
	 * For our NoobCoin, the PUBLIC KEY will act as OUR ADDRESS. It's ok to share this public key with others to receive
	 * the payment. Our private key is used to sign our transactions, so that nobody can spend our noobcoins other than the owner of private key
	 * 
	 * Users will have to keep their private key SECRET. We also send our public key along with the transaction and it can be used to verify 
	 * that our signature is valid and data has not been tempered with
	 * 
	 * PRIVATE KEY : Sign data
	 * PUBLIC KEY : Verify the signature
	 */
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	
	/**
	 * We generate our private key and public key in a KeyPair. We use generateKeyPair() method 
	 */
	
	public Wallet(){
		generateKeyPair();
	}
	
	
	public void generateKeyPair(){
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			
			//Initialize the key generator and generate a KeyPair
			//256 bytes provides an acceptable security level
			keyGen.initialize(ecSpec,random);
			KeyPair keyPair = keyGen.generateKeyPair();
			//Set the public key and private key
			
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			
		}catch(Exception e){
			throw new RuntimeException();
		}
	}

}

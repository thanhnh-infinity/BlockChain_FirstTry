/**
 * Secondly, there are many algorithms you can choose to encrypt and hash
 * SHA256 is fine
 */

import java.security.MessageDigest;




public class StringUtil {
	//Apply SHA256 to a string and return a result
	public static String applySHA256(String input){
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // contain hash as hexidecimal
			
			for(int i = 0 ; i < hash.length ; i++){
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			
			return hexString.toString();
			
		} catch (Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}

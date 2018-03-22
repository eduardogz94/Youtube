package eagz.org.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	private String hastext;
	
	public Encrypt(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] pDigest = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1,pDigest);
		hastext = number.toString(16);
	}
	public String returnEncript() {
		return hastext;	
	}
}
package api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class pwdHash {
	private static Logger log = LoggerFactory.getLogger(pwdHash.class);

	public byte[] sha256(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        byte[] hashPwd = new byte[32];
        int hashCnt = 4;
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] inputDigest = digest.digest(password.getBytes("UTF-8"));
	    digest.reset();
	    for(int i = 0; i < hashCnt; i++){
	        hashPwd = digest.digest(inputDigest);
	    }
	    return hashPwd;
	}

	public String bytesToHex(byte[] hash) {
        
	    StringBuilder builder = new StringBuilder();
	    for(byte b : hash) {
	        builder.append(String.format("%02x", b));    
	    }
	             
	    return builder.toString();
	}
}

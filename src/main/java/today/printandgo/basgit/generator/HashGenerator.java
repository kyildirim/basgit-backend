package today.printandgo.basgit.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HashGenerator {

	public static byte[] generateHash(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			return md.digest(data); 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	public static String encryptHash(byte[] data) {
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get("secret/private.der"));

		    PKCS8EncodedKeySpec spec =
		      new PKCS8EncodedKeySpec(keyBytes);
		    KeyFactory kf = KeyFactory.getInstance("RSA");
		    PrivateKey pk = kf.generatePrivate(spec);
		    Cipher encrypt=Cipher.getInstance("RSA");
		    encrypt.init(Cipher.ENCRYPT_MODE, pk);
		    byte[] encryptedMessage=encrypt.doFinal(data);
		    return bytesToHex(encryptedMessage);
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
    		System.err.println(e.getMessage());
			return null;
		}
	}
	
	public static String bytesToHex(byte[] bytes) {
        final  char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
	
}

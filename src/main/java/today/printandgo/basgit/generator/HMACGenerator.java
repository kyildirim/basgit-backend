package today.printandgo.basgit.generator;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACGenerator {

	public static String generateHMAC(byte[] data, String key) {
		Mac sha512_HMAC = null;
        String result = null;
        try{
            byte [] byteKey = key.getBytes("UTF-8");
            sha512_HMAC = Mac.getInstance("HmacSHA512");      
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA512");
            sha512_HMAC.init(keySpec);
            byte [] mac_data = sha512_HMAC.
             doFinal(data);
            result = bytesToHex(mac_data);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
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

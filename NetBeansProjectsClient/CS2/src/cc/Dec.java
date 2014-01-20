package cc;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Dec {
	public static void AES(byte[] encrypted, SecretKeySpec skeySpec) {
		try{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

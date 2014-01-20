package database;

import java.io.IOException;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class BASE{
	/**
	 * @param mingwen
	 * @return
	 */
	/**
	 * @param mingwen
	 * @return
	 */
	/**
	 * @param mingwen
	 * @return
	 */
	public static String encode(String s){
		if (s == null) return null;
		else return "&%)(*" + new BASE64Encoder().encode(("ABCD"+s).getBytes()); 
	}
	public static String decode(String s)throws IOException{
		if (s == null) return null;
		else return new String(new BASE64Decoder().decodeBuffer(s.substring(5))).substring(4);
	}
	public static void main(String[] args)throws IOException{
		String s = "ABCDABC"; 
		System.out.println(s);
		System.out.println(encode(s));
		System.out.print(decode(encode(s)));
	}
}

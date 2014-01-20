package database;

import java.io.UnsupportedEncodingException;

public class chTr {
	public static String toOracle(String str) throws UnsupportedEncodingException{
		return str;
		//return new String(str.getBytes("GB2312"),"GBK");
	}
	public static String fromOracle(String str)throws UnsupportedEncodingException{
		return str;
		//return new String(str.getBytes("GBK"), "GB2312");
	}
}

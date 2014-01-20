package gen;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import cc.*;
public class Permutation {
	int length;
	int[] keyA;
	byte[] keyB;
	int logLen;
	static Random ran = new Random();
 	public Permutation(int aLogLen){//?????^231å¤§å???ermutation
		if (aLogLen > 255) {
			System.out.println("Wrong Permutation");
			return;
		}
		length = 1;
		logLen = aLogLen;
		for (int i = 0; i < logLen; i++)
			length *= 2;
		keyA = new int[8];
		for (int i = 0; i < 8; i++) keyA[i] = ran.nextInt(length);
		keyB = randomBytePermutation((byte)logLen);
	}
 	public Permutation(int aLogLen, byte[] key){//?????^231å¤§å???ermutation
		if (aLogLen > 255) {
			System.out.println("Wrong Permutation");
			return;
		}
		length = 1;
		logLen = aLogLen;
		for (int i = 0; i < logLen; i++)
			length *= 2;
		keyA = new int[8];  
		for (int i = 0; i < 8; i++) keyA[i] = T.byte2int(key, i*4);
		keyB = new byte[logLen];
		System.arraycopy(key, 8*4, keyB, 0, logLen);
	}
 	public int bi(int num){
 		int t;
 		for (int i = 0; i < 2; i++){
 			num ^= keyA[i];
 			t = 0;
 			for (int j = 0; j < logLen; j++)if ((num&(1<<j)) > 0){
 				t += (1<<keyB[j]);
 			}
 			num = t;
 		}
 		return num;
 	}
 	public byte[] bi(byte[] num){
 		return T.int2byte(bi(T.byte2int(num)));
 	}
	public static byte[] randomBytePermutation(byte size){
		byte[] randomP = new byte[size];
		for (byte i = 0; i < size; i++) randomP[i] = i;
		byte j, t;
		for (int i = size-1; i >= 2; i--){
			j = (byte)ran.nextInt(i-1);
			t = randomP[j];
			randomP[j] = randomP[i];
			randomP[i] = t;
  		}
		return randomP;
	}
	public static int[] randomIntPermutation(int size){
		int[] randomP = new int[size];
		for (int i = 0; i < size; i++) randomP[i] = i;
		int j, t;
		for (int i = size-1; i >= 2; i--){
			j = ran.nextInt(i-1);
			t = randomP[j];
			randomP[j] = randomP[i];
			randomP[i] = t;
  		}
		return randomP;
	}
	public static void main(String[] args){
		Permutation p = new Permutation(30);
		System.out.println(""+p.bi(12)+ T.byte2int(p.bi(T.int2byte(12))));
		for (int i = 0; i < 8; i++){
			T.printHexString(T.int2byte(p.keyA[i]));
			System.out.println(p.keyA[i]);
		}
		T.printHexString(p.keyB);
	}
}

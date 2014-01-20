package cc;

import client.Main_Frame;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import java.io.*;
import java.security.MessageDigest;
public class Gen {
    static int H1_len = 12;
    static int P_len = 8;
    public final static String AES = "AES";
    public final static String DES = "DES";
    public final static String DESEDE = "DESede";
    public final static String BLOWFISH = "BlowFish";
    public final static String MD5 = "MD5";
    public String userName;
    public int totalNodes;
    public CS2Array cs2Array;
    public static final String freeStr = "__free__";
    public Random ran = new Random();
    public static String k1, k2, k3, k4, k5;
//	public static int k4_len = 30;//J;permutation's key length must be 30
//	public static byte[] k4 = new byte[4*8+k4_len];//J:4*30+24
//            for(int i = 0; i < k4.length; i++)
//                k4[i] = (byte)key.charAt(i);

    public static boolean generateKey(String key) {
        if (key.length() < 72) {
            return false;
        }
        k1 = key.substring(0, 8);
        k2 = key.substring(8, 16);//For DES should be 64 bits
        k3 = key.substring(16, 32); //For AES should be 128/192/256 bits, here, it is 128 bits 
        k4 = key.substring(32, 40); //DES
        k5 = key.substring(40, 72);
        return true;
    }

    private void setTotalNodes(Vector<LinkedList<Integer>> kwToFile) {
        totalNodes = 0;
        for (int i = 0; i < kwToFile.size(); i++) {
            totalNodes += kwToFile.elementAt(i).size();
        }
        totalNodes *= 2;
    }
    
    public Gen(Vector<LinkedList<Integer>> kwToFile, int fileNum, Vector<String> keywords, String _userName) {
        userName = _userName;
        if (!generateKey(Main_Frame.key)) {
            return;
        }
        
        setTotalNodes(kwToFile);
        cs2Array = new CS2Array(totalNodes, 16, kwToFile.size(), 16, fileNum, keywords);
        
        for (int i = 0; i < kwToFile.size(); i++)
            for ( int fileId:kwToFile.elementAt(i))
                cs2Array.addNode(i, fileId);
        cs2Array.generatePara();
        cs2Array.sendArrays(userName);
        System.out.println("OK");
        
        Set<Integer> idSet = cs2Array.searchWord(0);
        for (int i:idSet){
            System.out.println(i);
        }    
        //System.out.println(num.length);
        //for (int i = 0; i < num.length; i++)
        //	if (num[i] > 1) System.out.println(num[i]);
    }

    public static byte[] encrypt(byte[] src, byte[] key, String name) {
        try {
            SecretKeySpec securekey = new SecretKeySpec(key, name);
            Cipher cipher = Cipher.getInstance(name);
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] decrypt(byte[] src, byte[] key, String name) {
        try {
            SecretKeySpec securekey = new SecretKeySpec(key, name);
            Cipher cipher = Cipher.getInstance(name);
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] P(String mes) {//with k3
        byte[] res = encrypt(mes.getBytes(), k3.getBytes(), AES);
        return T.toLen(res, P_len);
    }

    public static byte[] G(String mes, int len) {
        return T.toLen(encrypt(mes.getBytes(), k2.getBytes(), DES), len);
    }
    public static byte[] H1(byte[] K, byte[] r) { //ServerHas
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(T.concat2(K, r));
            return T.toLen(sha.digest(), H1_len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String args[]) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);

        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1, 1, 1, 1, 1};
    }
}

//	public static byte[] hash(byte[] src, byte[]key, int length){
//		try {
//			MessageDigest sha = MessageDigest.getInstance("SHA");
//			sha.update((mes.concat(key)).getBytes());
//			return sha.digest();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//    public static void setKey(String file) {
//        DataInputStream is;
//        try {
//            is = new DataInputStream(new FileInputStream(file));
//            k1 = is.readLine();
//            k2 = is.readLine();
//            k3 = is.readLine();
//            k4_len = Integer.parseInt(is.readLine());
//            byte[] k5 = T.toHexBytes(is.readLine());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    public void output(String file) {
//        try {
//            FileWriter os = new FileWriter(file + "Key");
//            os.write(k1 + "\n");
//            os.write(k2 + "\n");
//            os.write(k3 + "\n");
//
//            os.write(k4_len + "\n");
//            os.write(T.toHexString(k4));
//            os.flush();
//
//            sArray.output(file + "_s");
//            dArray.output(file + "_d");
//            os.close();
//
//            os = new FileWriter(file + "client");
//            os.write(sArray.table.length + "\n");
//            os.write(sArray.table.entryBytes + "\n");
//            os.write(dArray.table.length + "\n");
//            os.write(dArray.table.entryBytes + "\n");
//
//            os.close();
//
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        }
//    }
//
//    public Gen(String file) {
//        setKey(file + "Key");
//    }

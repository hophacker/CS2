package cc;

import java.io.*;
import java.util.Vector;

public class T {

    public static final String delim = "womanisshit";
    private static char hexChar[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static int nextPrime(int n) {
        int j = 0;
        for (int i = n;; i++) {
            for (j = 2; j < (int) Math.sqrt(i); j++) {
                if (i % j == 0) {
                    break;
                }
            }
            if (i % j == 0) {
                continue;
            } else {
                return i;
            }
        }
    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// ???ä½?
        targets[1] = (byte) ((res >> 8) & 0xff);// æ¬¡ä?ä½?
        targets[2] = (byte) ((res >> 16) & 0xff);// æ¬¡é?ä½?
        targets[3] = (byte) (res >>> 24);// ???ä½?????·å?ç§»ã?
        return targets;
    }

    public static int byte2int(byte[] res) {

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static int byte2int(byte[] res, int pos) {

        int targets = (res[pos + 0] & 0xff) | ((res[pos + 1] << 8) & 0xff00)
                | ((res[pos + 2] << 24) >>> 8) | (res[pos + 3] << 24);
        return targets;
    }

    public static byte[] toLen(byte[] b, int len) {
        byte[] ret = new byte[len];
        System.arraycopy(b, 0, ret, 0, Math.min(ret.length, b.length));
        for (int i = b.length; i < len; i++) {
            ret[i] = 0;
        }
        return ret;
    }

    public static int getHex(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        } else if ('a' <= c && c <= 'z') {
            return c - 'a' + 10;
        } else if ('A' <= c && c <= 'Z') {
            return c - 'A' + 10;
        } else {
            System.out.println("getHex wrong");
            return 0;
        }
    }

    public static byte[] toHexBytes(String s) {
        byte[] b = new byte[s.length() / 2];
        int t;
        for (int i = 0; i < s.length() / 2; i++) {
            t = getHex(s.charAt(2 * i)) * 16 + getHex(s.charAt(2 * i + 1));
            b[i] = (byte) t;
        }
        return b;
    }
    public static String hexStringToStr(String s) {
        StringBuffer sb = new StringBuffer(s.length() /2);
        int t;
        for (int i = 0; i < s.length() / 2; i++) {
            t = getHex(s.charAt(2 * i)) * 16+getHex(s.charAt(2 * i + 1));
            sb.append((char)t);
        }
        return sb.toString();
    }
    public static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0xf]);
        }
        return sb.toString();
    }
    public static String intToHexString(int b[]) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) 
            sb.append(toHexString(int2byte(b[i])));
        return sb.toString();
        
    }
    public static int[] hexStringToInt(String s){
        byte[] b = toHexBytes(s);
        int[] a = new int[b.length/4];
        for (int i = 0; i < a.length; i++)
            a[i] = byte2int(b, i*4);
        return a;
    }
    public static String strToHexString(String s) {
        StringBuilder sb = new StringBuilder(s.length() * 2);
        for (int i = 0; i < s.length(); i++) {
            sb.append(hexChar[(s.charAt(i) & 0xf0) >>> 4]);
            sb.append(hexChar[s.charAt(i) & 0xf]);
        }
        return sb.toString();
    }
    public static void printHexString(byte b[]) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(", (byte)0x");
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0xf]);
        }

        System.out.println(sb);
    }

    public static byte[] concat2(byte[] a, byte[] b) {
        byte[] ret = new byte[a.length + b.length];

        System.arraycopy(a, 0, ret, 0, a.length);
        System.arraycopy(b, 0, ret, a.length, b.length);
        return ret;
    }

    public static byte[] concat2(int a, int b) {
        return concat2(int2byte(a), int2byte(b));
    }

    public static byte[] concat2(byte[] a, int b) {
        return concat2(a, int2byte(b));
    }
    public static byte[] concat3(int a, int b, int c) {
        return concat3(int2byte(a), int2byte(b), int2byte(c));
    }
    public static byte[] concat3(byte[] a, byte[] b, byte[] c) {
        byte[] ret = new byte[a.length + b.length + c.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        System.arraycopy(b, 0, ret, a.length, b.length);
        System.arraycopy(c, 0, ret, a.length + b.length, c.length);
        return ret;
    }
    public static byte[] concat4(int a, int b, int c, int d) {
        return concat4(int2byte(a), int2byte(b), int2byte(c), int2byte(d));
    }
    public static byte[] concat4(byte[] a, byte[] b, byte[] c, byte[] d) {
        byte[] ret = new byte[a.length + b.length + c.length + d.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        System.arraycopy(b, 0, ret, a.length, b.length);
        System.arraycopy(c, 0, ret, a.length + b.length, c.length);
        System.arraycopy(d, 0, ret, a.length + b.length+ c.length , d.length);
        return ret;
    }

    public static byte[] xor(byte[] a, byte[] b) {
        if (a.length != b.length) {
            System.out.println("byte[] length not match.");
            return null;
        }
        byte[] res = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = (byte) (a[i] ^ b[i]);
        }
        return res;
    }

    public static byte[] xor(int a, int b) {
        return T.int2byte(a ^ b);
    }

    public static byte[] sub(byte[] a, int from, int len) {
        byte[] ret = new byte[len];
        System.arraycopy(a, from, ret, 0, ret.length);
        return ret;
    }
    public  static int[] byteToInt(byte[] b){
        int[] a = new int[b.length/4];
        for (int i = 0; i < a.length; i++)
            a[i] = byte2int(b, i*4);
        return a;
    }
    public static void main(String[] args) {
//        byte[] s = toHexBytes(toHexString(new byte[]{12, 13}));
//        s[1] = 12;
//        Vector<Integer> a = new Vector<Integer>();
//        a.setSize(10);
//        a.set(9, 10);
//        System.out.println(a.elementAt(9));
        int[] a = new int[]{1, 2};
        
        int[] b = byteToInt(toHexBytes(intToHexString(a)));
        System.out.println(b[0] + b[1]);
    }
}

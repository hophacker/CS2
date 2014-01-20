package cc;


import java.io.*;
import java.security.*;

import javax.crypto.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypt {

    Key key;
    String schema;
    public Crypt(String _schema, String _keyStr) {
        schema = _schema;
        setKey(_keyStr);//生成密匙
    }


    public void setKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance(schema);
            _generator.init(new SecureRandom(strKey.getBytes()));
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 加密String明文输入,String密文输出
     */
    public String getEncString(StringBuffer strMing){
        return getEncString(strMing.toString());
    }
    public byte[] getEncBytes(int strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        try {
            byteMing = (""+strMing).getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
        return byteMi;
    }
    public byte[] getEncBytes(byte[] ming) {
        byte[] byteMi = null;
        try {
            byteMi = this.getEncCode(ming);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
        return byteMi;
    }
    public String getEncString(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }
    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String getDecString(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDecCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(schema);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDecCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance(schema);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }
    public static void main(String args[]) {
        Crypt des = new Crypt("AES", "1123123123");

        String str1 = "密文2";
        String str2 = des.getEncString(str1);
        String deStr = des.getDecString(str2);
        System.out.println("密文:" + str2);
        System.out.println("明文:" + deStr);
    }
}
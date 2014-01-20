package cc;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import gen.*;

public class Gen {

    static int H1_len = 12;
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
}

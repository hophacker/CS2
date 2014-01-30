package gen;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class FileHash {
private static char hexChar[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };
    public FileHash()
    {
    }
    public static String getFileMD5(String filename)
    {
        String str = "";
        try
        {
            str = getHash(filename, "MD5");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA1(String filename)
    {
        String str = "";
        try
        {
            str = getHash(filename, "SHA1");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA256(String filename)
    {
        String str = "";
        try
        {
            str = getHash(filename, "SHA-256");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA384(String filename)
    {
        String str = "";
        try
        {
            str = getHash(filename, "SHA-384");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA512(String filename)
    {
        String str = "";
        try
        {
            str = getHash(filename, "SHA-512");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    private static String getHash(String fileName, String hashType)
        throws Exception
    {
        InputStream fis = new FileInputStream(fileName);
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for(int numRead = 0; (numRead = fis.read(buffer)) > 0;)
        {
            md5.update(buffer, 0, numRead);
        }

        fis.close();
        return toHexString(md5.digest());
    }
    private static String toHexString(byte b[])
    {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for(int i = 0; i < b.length; i++)
        {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0xf]);
        }

        return sb.toString();
    }
    public static void main(String[] args) {
		System.out.println(getFileMD5("1").length()/2 + " bytes");
		System.out.println(getFileSHA1("1").length()/2 + " bytes");
		System.out.println(getFileSHA256("1").length()/2 + " bytes");
		System.out.println(getFileSHA384("1").length()/2 + " bytes");
		System.out.println(getFileSHA512("1").length()/2 + " bytes");
	}
}
package client;

import cc.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import java.util.Scanner;

public class FileRunner {

    private File fileDir = null;
    private File keywordF = null;
    private String userName = null;
    private Vector<String> keywords = new Vector<String>();
    private Vector<LinkedList<Integer>> kwToFile;

    public FileRunner(String _userName, File _fileDir, File _keywordF) {
        fileDir = _fileDir;
        keywordF = _keywordF;
        userName = _userName;
    }

    public void getKeyWords() {
        Scanner reader = null;
        try {
            reader = new Scanner(keywordF);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (reader.hasNextLine()) {
            keywords.add(reader.nextLine());
        }
        reader.close();
    }
    
    public static String getSummaryEnc(StringBuffer SB, Crypt cryptor) {
        StringBuffer summary = new StringBuffer();
        int lastI = 0, J, count = 0;
        while (lastI < SB.length()) {
            while (lastI < SB.length() && !Character.isLetterOrDigit(SB.charAt(lastI))) {
                lastI++;
            }
            J = lastI;
            while (J < SB.length() && Character.isLetterOrDigit(SB.charAt(J))) {
                J++;
            }
            summary.append(SB.subSequence(lastI, J) + " ");
            lastI = J;
            if (++count == 20) {
                break;
            }
        }
        return cryptor.getEncString(summary);
    }
    public void processTheFile(int id, File file) {
        StringBuffer SB = new StringBuffer();
        Scanner reader = null;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                SB.append(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
//        for (int i = 0; i < keywords.size(); i++) {
//            if (SB.indexOf(keywords.elementAt(i)) != -1) {
//                kwToFile.elementAt(i).add(i);
//            }
//        }

        Crypt cryptor = new Crypt("DES", Main_Frame.fileKey);
        String contentEnc = cryptor.getEncString(SB);
        String summaryEnc = getSummaryEnc(SB, cryptor);
        CS2Client client = new CS2Client(1);
        client.send(userName + T.delim + id + T.delim + T.strToHexString(summaryEnc) + T.delim + T.strToHexString(contentEnc) + T.delim);
        client.close();
    }
    public void processTheFile(int id, File file, CS2Client client) {
        StringBuffer SB = new StringBuffer();
        Scanner reader = null;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                SB.append(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Crypt cryptor = new Crypt("DES", Main_Frame.fileKey);
        String contentEnc = cryptor.getEncString(SB);
        String summaryEnc = getSummaryEnc(SB, cryptor);
        client.send(userName + T.delim + id + T.delim + T.strToHexString(summaryEnc) + T.delim + T.strToHexString(contentEnc) + T.delim);
        
        for (int i = 0; i < keywords.size(); i++) {
            if (SB.indexOf(keywords.elementAt(i)) != -1) {
                kwToFile.elementAt(i).add(id);
            }
        }

    }
    public void process() {
        
        getKeyWords();
        kwToFile = new Vector<LinkedList<Integer>>();
        kwToFile.setSize(keywords.size());
        for (int i = 0; i < kwToFile.size(); i++) {
            kwToFile.setElementAt(new LinkedList(), i);
        }
        File[] files = fileDir.listFiles();
        CS2Client client = new  CS2Client(1);
        for (int i = 0; i < files.length; i++) {
            processTheFile(i, files[i], client);//client);
            System.out.println(i + "is ok");
        }
        client.print("END");
        client.close();
        Gen gen = new Gen(kwToFile, files.length, keywords, userName);
        int total = 0;
        for (int i = 0; i < keywords.size(); i++){
            total += keywords.elementAt(i).length();
        }
//        System.out.println(total);
//        client.send("fuWZck" + T.delim);
//        if (client.recvLine().equals("OK")){
//            System.out.println("File encryption and transformation is ok!");
//        }
    }


    public static void main(String[] args) {
       // String file = getFile("fengjie", 0);
        //Crypt c = new Crypt("DES", Main_Frame.fileKey);
        //System.out.println(file);
        //System.out.println(c.getDecString(file));
          FileRunner f = new FileRunner("fengjie", 
                new File("C:/Users/J/NetBeansProjects/files"),
                new File("C:/Users/J/NetBeansProjects/keywords.txt"));
          f.process();
    }
}
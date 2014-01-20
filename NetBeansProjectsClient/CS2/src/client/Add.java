/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import cc.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J
 */
public class Add {
    public static Vector<String> getKeyWords(File kwFile) {
        Scanner reader = null;
        Vector<String> keywords = new Vector();
        try {
            reader = new Scanner(kwFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (reader.hasNextLine()) {
            keywords.add(reader.nextLine());
        }
        reader.close();
        return keywords;
    }
    public static void addFiles(File fileDir, File kwFile){
        Gen.generateKey(Main_Frame.key);
        ClientPara para = ClientParaInOut.in();
        File files[] = fileDir.listFiles();
        
        
        
        StringBuffer[] SBs = new StringBuffer[files.length];
        
        for (int i = 0; i < files.length; i++){
            SBs[i] = new StringBuffer();
            Scanner reader = null;
            try {
                reader = new Scanner(files[i]);
                while (reader.hasNextLine()) {
                    SBs[i].append(reader.nextLine());
                }
                reader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        CS2Client clientAddFile = new CS2Client(1);
        for (int i = 0; i < files.length; i++){
                Crypt cryptor = new Crypt("DES", Main_Frame.fileKey);
                StringBuffer SB = SBs[i];
                String contentEnc = cryptor.getEncString(SB);
                String summaryEnc = FileRunner.getSummaryEnc(SB, cryptor);
                clientAddFile.send(Main_Frame.user + T.delim + (para.dTableV.size()+i) + T.delim + 
                        T.strToHexString(summaryEnc) + T.delim + T.strToHexString(contentEnc) + T.delim);
        }
        clientAddFile.close();
        
        CS2Client client = new CS2Client(8);
        client.print(Main_Frame.user);
        for (int i = 0; i < files.length; i++){
            para.dTableV.add(-1);
            int fId = para.dTableV.size()-1;
            addFile(fId, files[i], kwFile, para, client, SBs[i]);
            System.out.println("" + i);
        }
        client.print("END");
        client.close();
        
        ClientParaInOut.out(para);
    }
    public static void addFile(int fId, File file, File kwFile, ClientPara para, CS2Client client, StringBuffer SB){
        
       
        Vector<String> keywords = getKeyWords(kwFile);
        Random ran = new Random();
        for (int i = 0; i < keywords.size(); i++) {  
            String kw = keywords.elementAt(i);
            if (para.wordToId.containsKey(kw) && SB.indexOf(kw) != -1) {
                client.addWord(fId,  kw, para, ran);
                System.out.println(kw);
            }
        }
    }
    public static void main(String args[]){
        addFiles(new File("C:/Users/J/NetBeansProjects/addFiles"),
                new File("C:/Users/J/NetBeansProjects/keywords.txt"));
    }
}

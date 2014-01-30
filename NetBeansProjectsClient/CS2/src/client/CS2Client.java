package client;

import cc.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import cc.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Config;

public class CS2Client extends Thread {

    public Socket link;
    public Scanner reader = null;
    public PrintStream writer = null;
    private int mode;
    private static String server_address = Config.getValue("server_address");
    /*
     * 1: intert file
     */

    public CS2Client(int _mode) {
        mode = _mode;
        try {
            link = new Socket(InetAddress.getByName(server_address), 1314);
            reader = new Scanner(link.getInputStream());
            writer = new PrintStream(link.getOutputStream());
            reader.useDelimiter(T.delim);
            writer.print(mode + T.delim);
        } catch (IOException ex) {
            Logger.getLogger(CS2Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CS2Client() {
        try {
            link = new Socket(InetAddress.getByName(server_address), 1314);
            reader = new Scanner(link.getInputStream());
            reader.useDelimiter(T.delim);
            writer = new PrintStream(link.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CS2Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean sendArrays(String userName, ArrayX sArray, ArrayX dArray, int sTable[]) {
        print(userName); 
        print(sArray.array.length+"");
        print(T.toHexString(sArray.array));
        print(sArray.array.length+"");
        print(T.toHexString(dArray.array));
        print(T.intToHexString(sTable));
        writer.flush();
        if (reader.nextLine().equals("OK")) {
            return true;
        } else {
            return false;
        }
    }
    public Vector searchToken_ID(String user, int Id, byte[] P_k3){
        Crypt cryptor = new Crypt("DES", Main_Frame.fileKey);
        writer.print(user +T.delim);
        writer.print(Id+T.delim);
        writer.print(T.toHexString(P_k3) + T.delim);
        Vector<Integer> idSet = new Vector();
        while(reader.hasNext()){
            String id = reader.next();
                if (id.equals("OK")) break;
            idSet.add(Integer.parseInt(id));
            //idSet.add(new SearchItem(Integer.parseInt(id), cryptor.getDecString(T.hexStringToStr(summary))));
        }
        return idSet;
//        for (int i:idSet)//test
//            System.out.println(i);
        
    }
    public Vector<SearchItem> getSummary(String name, Vector<Integer> fileId){
        Crypt cryptor = new Crypt("DES", Main_Frame.fileKey);
        writer.print(name + T.delim);
        for (int id:fileId) writer.print(id + T.delim);
        writer.print("FINISH" + T.delim);
        
        Vector<SearchItem> itemSet = new Vector();
        for (int id:fileId){
            String summary = reader.next();
            itemSet.add(new SearchItem(id, cryptor.getDecString(T.hexStringToStr(summary))));
        }
        return itemSet;
    }
    public String getContent(String name, Vector<Integer> fileId ){
        Crypt cryptor = new Crypt("DES", Main_Frame.fileKey);
        
        writer.print(name + T.delim);
        for (int id:fileId) writer.print(id + T.delim);
        writer.print("FINISH" + T.delim);
        
        StringBuffer content = new StringBuffer();
        int num = 0;
        while(true){
            String c = reader.next();
            if (c.equals("OK")) break;
            content.append("File ID:" + fileId.elementAt(num++)+ "\n");
            content.append(cryptor.getDecString(T.hexStringToStr(c)));
            content.append("\n\n");
        }
        return content.toString();
    }
    public void send(String content) {
        writer.print(content);
        writer.flush();
    }
    public void addWord(int fileId, String word, ClientPara para, Random ran){
            byte[] r = new byte[4]; 
            ran.nextBytes(r);
            byte[] w_H1_12 = Gen.H1(Gen.P("word" + word), r);
        
            int sNewAddr = para.freeHead;//get
        print(para.freeHead+"");//1
            
            int id = para.wordToId.get(word);//get
        print(id+"");        //4
        print(fileId+""); //5
        print(T.toHexString(CS2Array.Q(para.freeLen)));//2
        
            //byte[] sNewNode = T.concat2(T.xor(T.concat3(fileId, -1, sNextAddr), w_H1_12), r);
            byte[] sNewNode_ = T.concat2(w_H1_12, r);
        print(T.toHexString(sNewNode_));//3

            ran.nextBytes(r);
            byte[] f_H1_12 = Gen.H1(Gen.P("file" + fileId), r);
            int dNextAddr = para.dTableV.elementAt(fileId);
            byte[] dNewNode = T.concat2(T.xor(T.concat3(-1, dNextAddr, sNewAddr), f_H1_12), r); //problem

        print(T.toHexString(dNewNode));
        para.freeHead = reader.nextInt();
        para.dTableV.set(fileId, reader.nextInt());
        para.freeLen--;
        writer.flush();
    }
    public void print(String s){
        writer.print(s+T.delim);
    }
    public void sendWithoutFlush(String content) {
        writer.print(content);
    }

    public String recvDeli() {
        return reader.next();
    }

    public String recvLine() {
        return reader.nextLine();
    }

    public void close() {
        reader.close();
        writer.close();
        try {
            link.close();
        } catch (IOException ex) {
            Logger.getLogger(CS2Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMode(int _mode) {
        mode = _mode;
    }

    public static void main(String[] args) throws Exception {
        CS2Client m = new CS2Client(1);
        m.run();
    }
}

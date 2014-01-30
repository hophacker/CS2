package server;

import database.ArrayX;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import cc.*;
import database.CS2Array;
import database.DBConn;
import database.file;
import database.user;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CS2Server {

    private static ServerSocket serverSocket;
    private static final int PORT = 1314;

    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ioEx) {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
        do {
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n" + (++ClientHandler.num) + client.getInetAddress());
            ClientHandler handler = new ClientHandler(client);
            handler.start();// As usual, method calls run.
        } while (true);
    }
}

class ClientHandler extends Thread {

    private Socket client = null;
    private Scanner input = null;
    private PrintStream output = null;
    public static int num = 0;
    private DBConn con = new DBConn();

    public ClientHandler(Socket socket) {
        // Set up reference to associated socket...
        client = socket;
        try {
            input = new Scanner(client.getInputStream());
            input.useDelimiter(T.delim);
            output = new PrintStream(client.getOutputStream());
        } catch (IOException ioEx) {
        }
        String mode = input.next();
//                if (mode == null || !Character.isDigit(mode.charAt(0))) continue;
        switch (Integer.parseInt(mode)) {
            case 0:
                con.close();
            case 1:
                insertFile();
                break;
            case 2:
                createUser();
                break;
            case 3:
                LoginUser();
                break;
            case 4:
                sendContent();
                break;
            case 5:
                recvArrays();
                break;
            case 6:
                searchWord();
                break;
            case 7:
                sendSummary();
                break;
            case 8:
                addFiles();
                break;
            case 9:
                deleteFiles();
            case 10:
                sendPara();
        }

    }
    public void sendPara(){
        String user = input.next();
    }
    public void deleteFiles(){
//        String userName = input.next();
//        Vector<Integer> ids = new Vector();
//        while(true){
//            String idStr = input.next();
//            if (idStr.equals("END")) break;
//            ids.add(Integer.parseInt(idStr));
//        }
//        file.delete(userName, ids);
    }
    public void addFiles(){
        String user = input.next();
        ArrayX sArray = new ArrayX();
        sArray.array = CS2Array.getSArray(user); 
        sArray.bytes = 16;
        sArray.length = sArray.array.length / sArray.bytes;
        
        ArrayX dArray = new ArrayX();
        dArray.array = CS2Array.getDArray(user); 
        dArray.bytes = 16;
        dArray.length = dArray.array.length / dArray.bytes;
        
        int[] sTable = CS2Array.getSTable(user);
        
        while(true){
            String addrStr = input.next();
            if (addrStr.equals("END")) break;
            int sNewAddr = Integer.parseInt(addrStr);//1
            
            int id = input.nextInt();//4
                int sNextAddr = sTable[id];
                sTable[id] = sNewAddr;
                
            int fileId = input.nextInt();
            byte[] QFreeLen = T.toHexBytes(input.next());//2;
                byte[] node =T.xor(sArray.get(sNewAddr, 12), QFreeLen);
                int freeHead = T.byte2int(node, 4); //get sFi_1
                int dNewAddr = T.byte2int(node, 8);
                
            byte[] sNewNode_ = T.toHexBytes(input.next());//3
                int b[] = new int[]{fileId,-1,sNextAddr,0};
                
                sArray.set(sNewAddr, T.xor(T.concat4(fileId,-1,sNextAddr,0), sNewNode_));
           
                if (sNextAddr != -1){
                    byte[] hey = T.xor(T.xor(-1, sNewAddr), sArray.get(sNextAddr, 4, 4));
                    sArray.set(sNextAddr, 4, hey);
                }
            byte[] dNewNode = T.toHexBytes(input.next());
                dArray.set(dNewAddr, dNewNode);

            print(freeHead+"");
            print(dNewAddr+"");
        }
        CS2Array.setArray(user, sArray.array, dArray.array, sTable);
        
    }
    public void print(String s){
        output.print(s+T.delim);
    }
    public void sendSummary(){
        String userName = input.next();
        String idStr, summary;
        while(true){
            idStr = input.next();
            if (idStr.equals("FINISH")) break;
            summary = file.getSummary(con, userName, Integer.parseInt(idStr));
            output.print(summary + T.delim);
        }
        output.print("OK" + T.delim);
        output.flush();
    }
    public void searchWord(){
        String user = input.next();
        int Id = input.nextInt();
        byte[] P_k3 = T.toHexBytes(input.next());
        
        ArrayX sArray = new ArrayX();
        sArray.array = CS2Array.getSArray(user); 
        sArray.bytes = 16;
        sArray.length = sArray.array.length / sArray.bytes;
        
        int[] sTable = CS2Array.getSTable(user);
        int addr = sTable[Id];
        
        Set<Integer> idSet = new TreeSet();
        while (addr != -1) {
            byte[] H1_12 = Gen.H1(P_k3, sArray.get(addr, 12, 4));
            byte[] node = T.xor(H1_12, sArray.get(addr, 0, 12));
            int id = T.byte2int(node, 0);
            //int pre = T.byte2int(node, 4);//test
            addr = T.byte2int(node, 8);
            idSet.add(id);
            // System.out.println(pre + " " + now);//test
        }
        for(int i:idSet){
            output.print(i+T.delim);
            //output.print(file.getSummary(con, user, i)+T.delim);
        }
        output.print("OK"+T.delim);
        output.flush();
    }
    public void recvArrays() {
        String userName = input.next();
        int sLen = input.nextInt();
        byte[] sArray = T.toHexBytes(input.next());
        int dLen = input.nextInt();
        byte[] dArray = T.toHexBytes(input.next());
        byte [] sTable_byte = T.toHexBytes(input.next());
        CS2Array.insert(userName, sArray, sLen, dArray, dLen, sTable_byte);
        output.println("OK");
        output.flush();
    }

    public void sendContent() {
        String userName = input.next();
        String idStr, content;
        while(true){
            idStr = input.next();
            if (idStr.equals("FINISH")) break;
            content = file.getFile(con, userName, Integer.parseInt(idStr));
            output.print(content + T.delim);
        }
        output.print("OK" + T.delim);
        output.flush();
    }

    public void LoginUser() {
        String name = input.next();
        String password = input.next();
        if (user.check(con, name, password)) {
            output.println("YES");
        } else {  
            output.println("NO");
        }
        output.flush();
    }

    public void createUser() {
        String name = input.next();
        String password = input.next();
        if (user.register(con, name, password)) {
            output.println("Registration Succeed!");
        } else {
            output.println("Your username is existed!");
        }
        output.flush();
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertFile() {
        String userName = input.next();
        file.deleteAll(userName);
        while (!userName.equals("END")) {
            int id = Integer.parseInt(input.next());
            String summaryEnc = input.next();
            String contentEnc = input.next();
            file.insert(con, userName, id, summaryEnc, contentEnc);
            System.out.println(id + " is ok");
            
            userName = input.next();
        }
        output.println("OK");
        output.flush();
    }
//	public void search(){
//		int F_k1 = Integer.parseInt(input.nextLine());//c->s 1
//		SOutput.println("Table address[F_k1(word)] is " + F_k1);
//		output.println(Tool.toHexString(sArray.table.get(F_k1)));//s->c 2
//		//SOutput.println(input.nextLine());
//		int sAddrHead = Integer.parseInt(input.nextLine());//c->s 3
//		byte[] P_k3 = Tool.toHexBytes(input.nextLine());//c->s 4
//		Set iSet = CS2Array.search(sAddrHead, sArray, F_k1, P_k3);
//		output.println(iSet.toString());
//	}
}
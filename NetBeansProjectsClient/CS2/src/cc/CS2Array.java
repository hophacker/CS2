package cc;

import java.security.MessageDigest;
import java.io.*;
import java.util.*;
import client.*;
public class CS2Array {
    ArrayX sArray, dArray;
    public static Random ran = new Random();
    private boolean nothing = true;
    private int freeLen = 0;
    private int freeHead = 0;
    public int sTable[], dTable[]; 
    Vector<String> keywords;
    public static Crypt QCrypt = new Crypt("DES", Gen.k4);

    public CS2Array(int _arrayLength, int _sArrayBytes, int _sTableLength, int _dArrayBytes, int _dTableLength, Vector<String> _keywords) {
        keywords = _keywords;
        sArray = new ArrayX(_arrayLength, _sArrayBytes);
        sTable = new int[_sTableLength];
        for (int i = 0; i < sTable.length; i++) {
            sTable[i] = -1;
        }

        dArray = new ArrayX(_arrayLength, _dArrayBytes);
        dTable = new int[_dTableLength];
        for (int i = 0; i < dTable.length; i++) {
            dTable[i] = -1;
        }

        int[] permuS = Permutation.randomIntPermutation(_arrayLength);
        int[] permuD = Permutation.randomIntPermutation(_arrayLength);
        int sFi_1 = -1;
        for (int i = 0; i < _arrayLength; i++) {
            addFreeNode(permuS[i], sFi_1, permuD[i]);
            sFi_1 = permuS[i];
        }
    }
    public static byte[] Q(int i) {
        byte[] ret = QCrypt.getEncBytes(i);
        while (ret.length < 12) {
            ret = QCrypt.getEncBytes(ret);
        }
        return T.toLen(ret, 12);
    }
    
    public void sendArrays(String userName){
        CS2Client client = new CS2Client(5);
        client.sendArrays(userName, sArray, dArray, sTable);
    }
    public void generatePara() {
        //client

        TreeMap wordToId = new TreeMap<String, Integer>();
        for (int i = 0; i < sTable.length; i++) {
            wordToId.put(keywords.elementAt(i), i);
        }
        Vector<Integer> dTableV = new Vector<Integer>();
        for (int i = 0; i < dTable.length; i++) dTableV.add(dTable[i]);
        ClientPara para = new ClientPara(wordToId, freeLen, freeHead, dTableV);
        ClientParaInOut.out(para);
    }
    public void addFreeNode(int addr, int sFi_1, int dFi) {
        // System.out.println(addr);//test
        freeLen++;
        freeHead = addr;
        sArray.set(addr,
                T.concat2(
                T.xor(T.concat3(0, sFi_1, dFi), Q(freeLen)),
                T.int2byte(ran.nextInt())));
    }
    private int sNewAddr, dNewAddr;

    public void getFreeNode() {
        // System.out.println(freeHead);//test
        byte[] node = T.xor(sArray.get(freeHead, 12), Q(freeLen));
        sNewAddr = freeHead;
        freeHead = T.byte2int(node, 4); //get sFi_1
        dNewAddr = T.byte2int(node, 8);
        freeLen--;
    }

    public void addNode(int wordId, int fileId) {
        String word = keywords.elementAt(wordId);
        getFreeNode();
        byte[] r = new byte[4];
        ran.nextBytes(r);//bug -1
        byte[] H1_12 = Gen.H1(Gen.P("word" + word), r);
        int sNextAddr = sTable[wordId];
        byte[] sNewNode = T.concat2(T.xor(T.concat3(fileId, -1, sNextAddr), H1_12), r);
        sArray.set(sNewAddr, sNewNode);
        if (sNextAddr != -1) {
            byte[] hey = T.xor(T.xor(-1, sNewAddr), sArray.get(sNextAddr, 4, 4));
            sArray.set(sNextAddr, 4, hey);
        }
        sTable[wordId] = sNewAddr;//get
        //if (wordId == 1) System.out.println(sNewNode[0] + " " + sNewNode[1] + " " + H1_12[0]);//test
        //System.out.println("Table " + wordId +" is " + sNewAddr);//test
        ran.nextBytes(r);
        H1_12 = Gen.H1(Gen.P("file" + fileId), r);
        int dNextAddr = dTable[fileId];
        byte[] dNewNode = T.concat2(T.xor(T.concat3(wordId, dNextAddr, sNewAddr), H1_12), r);
        dArray.set(dNewAddr, dNewNode);
        dTable[fileId] = dNewAddr;//!!!!!
    }

    public Set searchWord(int wordId) {
        String word = keywords.elementAt(wordId);
        Set<Integer> idSet = new TreeSet();
        int now = sTable[wordId];
        byte[] P_k3 = Gen.P("word" + word);
        while (now != -1) {
            System.out.println(now);//test
            byte[] H1_12 = Gen.H1(P_k3, sArray.get(now, 12, 4));
            byte[] node = T.xor(H1_12, sArray.get(now, 0, 12));
            int id = T.byte2int(node, 0);
            //int pre = T.byte2int(node, 4);//test
            now = T.byte2int(node, 8);
            idSet.add(id);
            // System.out.println(pre + " " + now);//test
        }
        return idSet;
    }
    public Set searchFile(int fileId) {
//        ran.nextBytes(r);
//        H1_12 = Gen.H1(Gen.P("file" + fileId), r);
//        int dNextAddr = dTable[fileId];
//        byte[] dNewNode = T.concat2(T.xor(T.concat3(wordId, dNextAddr, sNewAddr), H1_12), r);
        Set<Integer> idSet = new TreeSet();
        int now = dTable[fileId];
        byte[] fuck = dArray.get(now);
        while (now != -1) {
            System.out.println(now);//test
            byte[] H1_12 = Gen.H1(Gen.P("file" + fileId), dArray.get(now, 12, 4));
            byte[] node = T.xor(H1_12, dArray.get(now, 0, 12));
            int id = T.byte2int(node, 0);
            //int pre = T.byte2int(node, 4);//test
            now = T.byte2int(node, 4);
            idSet.add(id);
            // System.out.println(pre + " " + now);//test
        }
        return idSet;
    }
}
//    public Set search(int wordId) {
//        Set<Integer> idSet = new TreeSet();
//        if (T.byte2int(table.get(F(word)), 4) != notUsed) {								// , pre, next
//            int now = getNodeAddrOfTable(word);
//            if (nothing) {
//                return null;
//            }
//            while (now != -1) {
//                byte[] node = get(now);
//                byte[] idPreNext = T.xor(T.sub(node, 0, 12), Gen.H1("" + F(word), T.sub(node, 12, 4), 12));
//                // T.concat2(T.xor(T.concat3(id, -getNum(word), -1), Gen.H1(word , r, 12)), r)
//                int id = T.byte2int(idPreNext, 0);
//                int next = T.byte2int(idPreNext, 8);
//                idSet.add(id);
//                now = next;
//            }
//            return idSet;
//        } else {
//            return null;
//        }
//    }
//
//    public static Set search(int sAddrHead, CS2Array sArray, int F_k1, byte[] P_k3) {
//        Set<Integer> idSet = new TreeSet();
//        int now = sAddrHead;
//        while (now != -1) {
//            byte[] node = sArray.get(now);
//            byte[] idPreNext = T.xor(T.sub(node, 0, 12), Gen.H1("" + F_k1, T.sub(node, 12, 4), 12, P_k3));
//            // T.concat2(T.xor(T.concat3(id, -getNum(word), -1), Gen.H1(word , r, 12)), r)
//            int id = T.byte2int(idPreNext, 0);
//            int next = T.byte2int(idPreNext, 8);
//            idSet.add(id);
//            now = next;
//        }
//        return idSet;
//    }
//if (files != -1){
//	mapTable.put(freeStr, 0);
//	for (int i = 0; i < files; i++)
//		mapTable.put(""+i, i+1);
//}
//public int getNum(String word){
//if (!mapTable.containsKey(word))
//	mapTable.put(word, wordNum++);
//return mapTable.get(word);
//}
//    public void output(String file) {
//        super.output(file + "Array");
//        table.output(file + "ArrayTable");
//    }
//
//    public CS2Array(String file) {
//        super(file);
//        table = new CS2Table(file + "Table");
//    }
//
//
//    public void printStr(String word) {
//        int next = getNodeAddrOfTable(word);
//        while (next != -1) {
//            System.out.println(next);
//            next = getNextOfNode(next);
//        }
//    }
//
//    public int getNextOfNode(int now) {
//        byte[] ele = T.xor(get(now, 0, 4), Q.bi(get(now, 4, 4)));
//        return T.byte2int(ele);
//    }
//    //talbe [byte1, byte2] use byte2 to distinguish used from notused.
//
//    public void setTableLink(String word, int linkAddr) {
//        int r;
//        while (true) {
//            r = ran.nextInt();
//            if (r != notUsed) {
//                break;
//            }
//        }
//        table.set(F(word), T.concat2(T.xor(Q.bi(r), linkAddr), r));
//    }
//    /*
//     * function: get return: -1, entry is not been used; return: other
//     */
//
//    public int getNodeAddrOfTable(String word) {
//        byte[] entry = table.get(F(word));
//        int r = T.byte2int(entry, 4);
//        if (r == notUsed) {
//            nothing = true;
//            return -1;
//        } else {
//            nothing = false;
//            return T.byte2int(entry, 0) ^ Q.bi(r);
//        }
//    }
//
//    public static int getNodeAddrOfTable_client(byte[] entry, Permutation Q) {
//
//        int r = T.byte2int(entry, 4);
//        if (r == notUsed) {
//            return -1;
//        } else {
//            return T.byte2int(entry, 0) ^ Q.bi(r);
//        }
//    }
//
//    public int addDNode(String idStr, int dual) {
//        if (idStr.equals("")) {
//            System.out.println("added word wrong");
//            return -1;
//        }
//        int nowFree = getNodeAddrOfTable(Com.freeStr);
//        int nextFree = getNextOfNode(nowFree);
//        setTableLink(Com.freeStr, nextFree);
//
//        byte[] tableEntry = table.get(F(idStr));
//        //node : array     entry: table
//
//        byte[] r = new byte[4];
//        if (T.byte2int(tableEntry, 4) == notUsed) {
//            ran.nextBytes(r);								// , dual, next
//            byte[] node = T.concat2(T.xor(T.concat3(dual, -1, -1), Gen.H1(idStr, r, 12)), r); // idi, addrs(Ni-1),
//            set(nowFree, node);
//            setTableLink(idStr, nowFree);
//        } else {
//            int headNodeAddr = getNodeAddrOfTable(idStr);
//            byte[] node1 = get(headNodeAddr);
//            byte[] Gen_H1 = Gen.H1(idStr, T.sub(node1, 12, 4), 12);
//
//            byte[] idPreNext = T.xor(T.sub(node1, 0, 12), Gen_H1);
//            System.arraycopy(T.int2byte(nowFree), 0, idPreNext, 4, 4);
//            System.arraycopy(T.xor(idPreNext, Gen_H1), 0, node1, 0, 12);
//            set(headNodeAddr, node1);
//
//            byte[] node = T.concat2(T.xor(T.concat3(dual, -1, headNodeAddr), Gen.H1(idStr, r, 12)), r); // idi, addrs(Ni-1),
//            set(nowFree, node);
//            //System.out.println(nowFree);
//            setTableLink(idStr, nowFree);
//        }
//        return nowFree;
//    }
//    public void setTalbe(int size, int mul, int bytesOfEntry) {//! 
//        table = new CS2Table(size, mul, bytesOfEntry);
//        for (int i = 0; i < table.length; i++) {
//            table.set(i, T.concat2(ran.nextInt(), notUsed));// 8 bytes
//        }
//    }
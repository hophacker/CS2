/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import cc.*;
import java.util.*;
/**
 *
 * @author J
 */
public class Search {
    public static Vector<Integer> goMulWords(String user, String[] words){
        Gen.generateKey(Main_Frame.key);
        ClientPara para = ClientParaInOut.in();
        
        Vector<Vector<Integer>> idSets = new Vector();
        for (int i = 0; i < words.length; i++)
            idSets.add(go(user, words[i], para));
        for (int i = 1; i < idSets.size(); i++)
            idSets.elementAt(0).retainAll(idSets.elementAt(i));
        for (int i = 0; i < para.delTable.size(); i++)
            idSets.elementAt(0).remove(para.delTable.elementAt(i));
        return idSets.elementAt(0);
    }
    public static Vector<Integer> go(String user, String word, ClientPara para){
        Vector<Integer> idSet = new Vector();
        if (para.wordToId.containsKey(word)){
            int Id = para.wordToId.get(word);
            byte[] p_k3 = Gen.P("word"+word);
            
            CS2Client client = new CS2Client(6);
            idSet = client.searchToken_ID(user, Id, p_k3);
            client.close();
            return idSet;
        }else
            return idSet;
    }
    public static void main(String args[]){
        Vector<Integer> set = goMulWords("fengjie", new String[]{"the", "our"});
        for (int i:set){
            System.out.println(i);
        }
    }
}

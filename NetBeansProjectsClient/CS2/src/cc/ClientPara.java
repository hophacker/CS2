/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc;

import java.io.*;
import java.util.*;

/**
 *
 * @author J
 */
public class ClientPara  implements Serializable{
    public TreeMap<String, Integer> wordToId;
    public int freeLen, freeHead;
    public Vector<Integer> dTableV;
    public Vector<Integer> delTable;
    public ClientPara(TreeMap<String, Integer> _wordToId, int _freeLen, int _freeHead, Vector<Integer> _dTableV) {
        wordToId = _wordToId;
        freeLen = _freeLen;
        freeHead = _freeHead;
        dTableV = _dTableV;
        delTable = new Vector();
    }
}

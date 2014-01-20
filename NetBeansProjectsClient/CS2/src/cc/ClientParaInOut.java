/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author J
 */
public class ClientParaInOut {
    static String paraFile = "clientPara";
    public static void out(ClientPara para){
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(paraFile));
            
            os.writeObject(para);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ClientPara in(){
        ObjectInputStream ois;  
        try {
            ois = new ObjectInputStream(new FileInputStream(paraFile));
            ClientPara para = (ClientPara)ois.readObject();
            ois.close();
            return para;
        } catch (Exception ex) {
            Logger.getLogger(ClientParaInOut.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

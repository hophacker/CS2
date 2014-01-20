/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import cc.T;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class User{
    public static String doIt(int d, String name, String password){
        CS2Client client = new CS2Client(d);
        client.writer.println(name+T.delim+password+T.delim);
        String ret = client.reader.nextLine();
        client.close();
        return ret;
    }
    public static String register(String name, String password){
        return doIt(2, name, password);
        
    }
    public static String login(String name, String password){
        return doIt(3, name, password);
    }
    public static void main(String args[]){
        System.out.println(login("Wanghui", "ddd"));
    }
}

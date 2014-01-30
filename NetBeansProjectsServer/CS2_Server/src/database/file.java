/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J
 */
public class file {
    public static String cev(String col, String val){
        return col+"='"+val+"' ";
    }
    public static void insert(DBConn con, String userName, int id, String summary, String content){
        String str = "insert into file(userName,id,summary,content) values("
                + "'" + userName + "'" + ", "
                + "'" + id + "'" + ","
                + "'" + summary + "'" + ","
                + "'" + content + "'" + ")" ;
        con.executeUpdate(str);
    }
    public static void delete(String userName, Vector<Integer> ids){
       
//        for(int i = 0; i < ids.size(); i++){
//            int dAddr = addr.elementAt(i);
//        }
    }
    public static void deleteAll(String userName){
        String str = "delete from file where userName='" + userName + "'";
        DBConn con = new DBConn();
        con.executeUpdate(str);
    }
    public static String getSummary(DBConn con, String usreName, int id){
        String str = "select summary from file where " + cev("userName",usreName)
                + "and " + cev("id",""+id);
        ResultSet rs = con.executeQuery(str);
        String summary=null;
        try {
            rs.next();
            summary = rs.getNString(1);
        } catch (SQLException ex) {
            Logger.getLogger(file.class.getName()).log(Level.SEVERE, null, ex);
        }
        return summary;
    }
    public static String getFile(DBConn con, String userName, int id){
        String str = "select content from file where " + cev("userName",userName)
                + "and " + cev("id",""+id);
        ResultSet rs = con.executeQuery(str);
        String content=null;
        try {
            rs.next();
            content = rs.getNString(1);
        } catch (SQLException ex) {
            Logger.getLogger(file.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }
}

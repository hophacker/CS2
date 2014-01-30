/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author j
 */
public class user {
    static String qStr = "select * from user where ";
    public static String cev(String col, String val){
        return col+"='"+val+"' ";
    }
    public static boolean check(DBConn con, String name, String password){
        String str = qStr + cev("name",name) + "and " + cev("password", password);
        ResultSet rs = con.executeQuery(str);
        try {
            if (rs == null || !rs.next())
                return false;
            else return true;
        } catch (SQLException ex) {
            Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private static boolean check(DBConn con, String name){
        String str = qStr + cev("name",name);
        ResultSet rs = con.executeQuery(str);
        try {
            if (rs == null || !rs.next())
                return false;
            else return true;
        } catch (SQLException ex) {
            Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean register(DBConn con, String name, String password){
        if (!check(con, name)){
            String str = "insert into user values("
                    + "'" + name + "'" + ","
                    + "'" + password + "'" + ")" ;
            con.executeUpdate(str);
            return true;
        }else
            return false;
    }
    public static void main(String args[]){
        DBConn con = new DBConn();
        System.out.println(user.check(con, "fengjie"));
    }
}

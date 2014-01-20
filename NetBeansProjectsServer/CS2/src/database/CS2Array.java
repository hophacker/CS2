/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import cc.T;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WangZhen
 */
public class CS2Array {
    public static int[] getSTable(String userName){
        DBConn con = new DBConn();
        int[] sTable = null;
        try {
            ResultSet rs = con.executeQuery("select sTable from CSArray where userName='"+userName+"'");
            rs.next();
            sTable = T.byteToInt(rs.getBytes("sTable"));
        } catch (SQLException ex) {
            Logger.getLogger(CS2Array.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.close();
        return sTable;
    }
    public static void insert(String userName, byte[] sArray, int sLen, byte[] dArray, int dLen, byte[] sTable_byte) {
        DBConn con = new DBConn();
        con.call("{call checkCS2Array('"+ userName +"')}");
        PreparedStatement pstmt;
        try {
            pstmt = con.conn.prepareStatement(
                    "insert into CSArray(userName,sArray,sLen,dArray,dLen,sTable) values(?,?,?,?,?,?)");
            pstmt.setString(1, userName);
            pstmt.setBytes(2, sArray);
            pstmt.setInt(3, sLen);
            pstmt.setBytes(4, dArray);
            pstmt.setInt(5, dLen);
            pstmt.setBytes(6, sTable_byte);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CS2Array.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.close();
    }
    public static void get(String userName, byte[] sArray, byte[] dArray) {
        DBConn con = new DBConn();
        int sLen, dLen;
        PreparedStatement pstmt;
        try {
            ResultSet rs = con.executeQuery("select sArray,sLen,dArray,dLen from CSArray where userName='"+userName+"'");
            rs.next();
            sArray = rs.getBytes("sArray");
            dArray = rs.getBytes("dArray");
            sLen = rs.getInt("sLen");
            dLen = rs.getInt("dLen"); 
        } catch (SQLException ex) {
            Logger.getLogger(CS2Array.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.close();
    }
    public static byte[] getSArray(String userName) {
        DBConn con = new DBConn();
        byte[] sArray = null;
        int sLen, dLen;
        PreparedStatement pstmt;
        try {
            ResultSet rs = con.executeQuery("select sArray from CSArray where userName='"+userName+"'");
            rs.next();
            sArray = rs.getBytes("sArray");
        } catch (SQLException ex) {
            Logger.getLogger(CS2Array.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.close();
        return sArray;
    }
    
     public static byte[] getDArray(String userName) {
        DBConn con = new DBConn();
        byte[] dArray = null;
        int sLen, dLen;
        PreparedStatement pstmt;
        try {
            ResultSet rs = con.executeQuery("select dArray from CSArray where userName='"+userName+"'");
            rs.next();
            dArray = rs.getBytes("dArray");
        } catch (SQLException ex) {
            Logger.getLogger(CS2Array.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.close();
        return dArray;
    }
     public static void setArray(String userName, byte[] sArray, byte[] dArray, int[] sTable) {
        DBConn con = new DBConn();
        PreparedStatement pstmt;
        try {
            pstmt = con.conn.prepareStatement(
                    "update CSArray set sArray=?,dArray=?,sTable=? where userName='" +userName+"'");
            pstmt.setBytes(1, sArray);
            pstmt.setBytes(2, dArray);
            pstmt.setBytes(3, T.intToByte(sTable));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CS2Array.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.close();
    }
    public static void main(String args[]){
        byte[] sArray=getSArray("fengjie");
        System.out.println("ok");
        //insert("d", new byte[]{(byte)1}, 1, new byte[]{(byte)1}, 2);
    }
}

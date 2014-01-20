package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConn {
    //public DataSource pool;


    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    static String url = "jdbc:mysql://127.0.0.1:3306/cloud?useUnicode=true&characterEncoding=gb2312"; //oral为数据库的SID
    static String user = "fengjie";
    static String password = "fengjie";
    public Connection conn = null;
    public ResultSet rs = null;
    public Statement stmt = null;
    public boolean autoCommit = true;
    /*
     * <Resource name="jdbc/DBPool" auth="Container" type="javax.sql.DataSource"
     * driverClassName="oracle.jdbc.driver.OracleDriver"
     *
     * url="jdbc:oracle:thin:@58.192.141.200:1521:oral"
     *
     * username="system" password="system" maxActive="10000" maxIdle="1000"
     * maxWait="9000" minIdle="100" timeBetweenEvictionRunsMillis="1000"
     * minEvictableIdleTimeMillis="10000" />
     */

    public DBConn() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
        }
    }
    public void call(String str){
        try {
            CallableStatement  cstmt  =  conn.prepareCall(str);
            cstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public ResultSet executeQuery(String sql) {
        try {
            rs = (ResultSet) stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void close() {
        try {
            if (!stmt.isClosed()) {
                stmt.close();
            }
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int executeUpdate(String sql) {
        int result = 0;
        try {
            result = stmt.executeUpdate(sql);
            if (!autoCommit) {
                conn.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return result;
    }

    public void commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setAutoCommit(boolean b) {
        try {
            conn.setAutoCommit(b);
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String[] args) {
        DBConn conn = new DBConn();
        conn.executeUpdate("insert into user values('创建数据库指定数据库的字符集','fengjie')");
        conn.close();
    }
}

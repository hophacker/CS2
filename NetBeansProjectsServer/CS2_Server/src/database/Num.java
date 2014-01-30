package database;
import java.sql.*;

public class Num {
	public static int getNum(String cla, DBConn con){
		try{
			String queryS = "select num from numClass where cla='" + cla + "'";
			ResultSet rs = con.executeQuery(queryS);
			rs.next();
			return rs.getInt("num");
		}
		catch(Exception ex){
			ex.printStackTrace();
			return -1;
		}
	}
	public static int chgNum(String cla, int value, DBConn con){
		try{
			return con.executeUpdate("update numClass set num=" + value + "where cla='" + cla + "'");
		}
		catch(Exception ex){
			return -1;
		}
	}
}

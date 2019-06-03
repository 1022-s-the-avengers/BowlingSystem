package top.arron206.model;


import java.sql.*;
import java.util.Map;

public class DBConnection {
    protected static Connection getConn(){
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Map<String, String> env = System.getenv();
            conn = DriverManager.getConnection("jdbc:mysql://120.79.58.147:3306/BowlingSystemDev", env.get("db_user"), env.get("db_pwd"));
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
        return conn;
    }

    protected static boolean release(Connection conn, PreparedStatement exec, ResultSet r){
        if(exec!=null) {
            try {
                exec.close();
            } catch (SQLException e) {
                return false;
            }
        }
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                return false;
            }
        }
        if(r!=null){
            try {
                r.close();
            }catch (SQLException e){
                return false;
            }
        }
        return true;
    }

    protected static boolean judge(Connection conn){
        return conn == null;
    }

    public static void main(String... args) {
        Connection conn = DBConnection.getConn();
        assert conn != null;
        System.out.println(conn.toString());
    }
}

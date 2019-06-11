package top.arron206.model;


import java.sql.*;
import java.util.LinkedList;
import java.util.Map;

public class DBConnection {
    static Connection getConn(){
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

    static boolean release(Connection conn, PreparedStatement exec, ResultSet r){
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

    static void updateAll(Connection conn,PreparedStatement exec ,LinkedList<Integer> var1, LinkedList<Integer> var2) throws SQLException {
        int len = var1.size();
        for(int i=0;i<len;i++){
            exec.setInt(1, var1.get(i));
            exec.setInt(2,var2.get(i));
            exec.addBatch();
            if(i==len-1){
                exec.executeBatch();
                conn.commit();
                exec.clearBatch();
            }
        }
    }

    static boolean judge(Connection conn){
        return conn == null;
    }


//    protected  static void inti(Connection a){
//        a=DBConnection.getConn();
//    }

    public static void main(String... args) {
    }
}

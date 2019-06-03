package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private int id;
    private String name;
    private String province;
    private String city;
    private int totalScore;

    public Member(int id, String name, String province, String city, int totalScore) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.city = city;
        this.totalScore = totalScore;
    }

    public Member(String name, String province, String city, int totalScore) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.totalScore = totalScore;
    }

    public int getCompetitionInfo(List<CompetitionInfo> res){
        if(id == 0)
            return 6;
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        ResultSet r=null;
        if(DBConnection.judge(conn))
            return 2;
        try{
            conn.setAutoCommit(false);
            String querySQL = "SELECT * FROM CompetitionInformation WHERE memberId=?";
            exec = conn.prepareStatement(querySQL);
            exec.setInt(1,id);
            r = exec.executeQuery();
            conn.commit();
            while(r.next()){
                res.add(
                        new CompetitionInfo(
                                r.getInt(1),
                                r.getInt(2),
                                r.getString(3),
                                r.getInt(4),
                                r.getInt(5),
                                r.getInt(6)
                        )
                );
            }
        }catch (SQLException e){
            return 4;
        }
        finally {
            if(!DBConnection.release(conn, exec,r))
                return 5;
            return 1;
        }
    }

    public static int getAllMembers(List<Member> res){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        ResultSet r=null;
        if(DBConnection.judge(conn))
            return 2;
        try{
            conn.setAutoCommit(false);
            String querySQL = "SELECT * FROM Member";
            exec = conn.prepareStatement(querySQL);
            r = exec.executeQuery();
            conn.commit();
            while(r.next()){
                res.add(
                        new Member(
                                r.getInt(1),
                                r.getString(2),
                                r.getString(3),
                                r.getString(4),
                                r.getInt(5)
                        )
                );
            }
        }catch (SQLException e){
            return 4;
        } finally {
            if(!DBConnection.release(conn,exec,r))
                return 5;
            return 1;
        }
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", totalScore=" + totalScore +"\n";
    }

    public static void main(String... args){
        ArrayList<Member> l = new ArrayList<>();
        Member.getAllMembers(l);
        for(Member m:l){
            System.out.println(m);
        }
    }
}

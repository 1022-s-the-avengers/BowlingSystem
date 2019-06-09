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
    private int totalScore=-1;
    private CompetitionRes singleRes;
    private CompetitionRes doubleRes;
    private CompetitionRes tripleRes;
    private CompetitionRes pentaRes;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public CompetitionRes getSingleRes() {
        return singleRes;
    }

    public CompetitionRes getDoubleRes() {
        return doubleRes;
    }

    public CompetitionRes getTripleRes() {
        return tripleRes;
    }

    public CompetitionRes getPentaRes() {
        return pentaRes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Member(int id, String name, String province, String city, int totalScore) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.city = city;
        this.totalScore = totalScore;
        singleRes = new SingleRes(id);
        doubleRes = new DoubleRes(id);
        tripleRes = new TripleRes(id);
        pentaRes = new PentaRes(id);
    }

    public Member(String name, String province, String city, int totalScore) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.totalScore = totalScore;
        singleRes = new SingleRes(id);
        doubleRes = new DoubleRes(id);
        tripleRes = new TripleRes(id);
        pentaRes = new PentaRes(id);
    }

    public int getCompetitionInfo(List<CompetitionInfo> res){
        if(id == 0)
            return 6;
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        ResultSet r=null;
        if(DBConnection.judge(conn))
            return 2;
        boolean release = false;
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
                                r.getString(2),
                                r.getString(3),
                                r.getInt(4),
                                r.getInt(5),
                                r.getInt(6),
                                r.getInt(7)
                        )
                );
            }
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
        if(!release)
            return 5;
        return 1;
    }

    public static int getAllMembers(List<Member> res){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        ResultSet r=null;
        boolean release = false;
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
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
        if(!release)
            return 5;
        return 1;
    }

    public int insertMember(){
        if(name==null||province==null||city==null)
            return 6;
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(DBConnection.judge(conn))
            return 2;
        ResultSet r = null;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO Member(name, province, city, totalScore) VALUE (?,?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            exec.setString(1,name);
            exec.setString(2,province);
            exec.setString(3, city);
            exec.setInt(4, totalScore);
            if(exec.executeUpdate()!=1)
                return 3;
            conn.commit();
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
        if(!release)
            return 5;
        return 1;
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
    }
}
package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class CompetitionInfo {
    private int id;
    private String competitionType;
    private int description;
    private int foul;
    private int memberId;

    public CompetitionInfo(){
        ;
    }

    public CompetitionInfo(int id, String competitionType, int description, int foul, int memberId) {
        this.id = id;
        this.competitionType = competitionType;
        this.description = description;
        this.foul = foul;
        this.memberId = memberId;
    }

    public CompetitionInfo(String competitionType, int description, int foul, int memberId) {
        this.competitionType = competitionType;
        this.description = description;
        this.foul = foul;
        this.memberId = memberId;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setFoul(int foul) {
        this.foul = foul;
    }

    public int getId() {
        return id;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public int getDescription() {
        return description;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getFoul() {
        return foul;
    }

    public int insertInfo(){
        ResultSet r = null;
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO CompetitionInformation (competitionType, description, foul, memberId) VALUE (?,?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            exec.setString(1, competitionType);
            exec.setInt(2, description);
            exec.setInt(3, foul);
            exec.setInt(4, memberId);
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

    public static int insertList(String competitionType, LinkedList<Integer> description, LinkedList<Integer> foul){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO CompetitionInformation (competitionType, description, foul, memberId) VALUE (?,?,?,?)";
            exec = conn.prepareStatement(sql);
            int len = description.size();
            for(int i=0;i<60;i++){
                for(int j=1;j<=6;j++){
                    exec.setString(1,competitionType);
                    exec.setInt(2,description.get(i*6+j-1));
                    exec.setInt(3, foul.get(i*6+j-1));
                    exec.setInt(4, i+1);
                    exec.addBatch();
                    if(((i*6+j-1)!=0 && (i*6+j-1)%200==0) || (i==59)&&j==6){
                        exec.executeBatch();
                        conn.commit();
                        exec.clearBatch();
                    }
                }
            }
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, null);
        }
        if(!release)
            return 5;
        return 1;
    }

    public static int insertAllClassic(LinkedList<Integer> memberIds, LinkedList<Integer> descriptions, LinkedList<Integer> fouls){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String sql = "INSERT INTO CompetitionInformation (competitionType, description, foul, memberId) VALUE (?,?,?,?)";
            exec = conn.prepareStatement(sql);
            int len = memberIds.size();
            for(int i=0;i<len;i++){
                for(int j = 0;j<15;j++){
                    exec.setString(1,"精英赛");
                    exec.setInt(2,descriptions.get(i*15+j));
                    exec.setInt(3,fouls.get(i*15+j));
                    exec.setInt(4,memberIds.get(i));
                    exec.addBatch();
                }
            }
            exec.executeBatch();
            conn.commit();
            exec.clearBatch();
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, null);
        }
        if(!release)
            return 5;
        return 1;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", competitionType=" + competitionType +
                ", description='" + description + '\'' +
                ", memberId=" + memberId +"\n";
    }

    public static void main(String... args){
        CompetitionInfo cpi = new CompetitionInfo("个人赛",111, 1,1);
        cpi.insertInfo();
    }
}

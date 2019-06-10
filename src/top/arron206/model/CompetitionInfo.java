package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetitionInfo {
    private int id;
    private String competitionType;
    private String description;
    private int memberId;

    public CompetitionInfo(){
        ;
    }

    public CompetitionInfo(int id, String competitionType, String description, int memberId) {
        this.id = id;
        this.competitionType = competitionType;
        this.description = description;
        this.memberId = memberId;
    }

    public CompetitionInfo(String competitionType, String description, int memberId) {
        this.competitionType = competitionType;
        this.description = description;
        this.memberId = memberId;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public String getDescription() {
        return description;
    }

    public int getMemberId() {
        return memberId;
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
            String insertSQL = "INSERT INTO CompetitionInformation (competitionType, description, memberId) VALUE (?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            exec.setString(1, competitionType);
            exec.setString(2, description);
            exec.setInt(3, memberId);
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
                ", competitionType=" + competitionType +
                ", description='" + description + '\'' +
                ", memberId=" + memberId +"\n";
    }

    public static void main(String... args){
        CompetitionInfo cpi = new CompetitionInfo("个人赛","无描述",1);
        cpi.insertInfo();
    }
}

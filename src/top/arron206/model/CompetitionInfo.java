package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompetitionInfo {
    private int id;
    private int competitionType;
    private String description;
    private int turn;
    private int times;
    private int memberId;

    public CompetitionInfo(int id, int competitionType, String description, int turn, int times, int memberId) {
        this.id = id;
        this.competitionType = competitionType;
        this.description = description;
        this.turn = turn;
        this.times = times;
        this.memberId = memberId;
    }

    public CompetitionInfo(int competitionType, String description, int turn, int times, int memberId) {
        this.competitionType = competitionType;
        this.description = description;
        this.turn = turn;
        this.times = times;
        this.memberId = memberId;
    }

    public int insertInfo(){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO CompetitionInformation (competitionType, description, turn, times, memberId) VALUE (?,?,?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            exec.setInt(1, competitionType);
            exec.setString(2, description);
            exec.setInt(3, turn);
            exec.setInt(4, times);
            exec.setInt(5, memberId);
            if(exec.executeUpdate()!=1)
                return 3;
            conn.commit();
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
                ", turn=" + turn +
                ", times=" + times +
                ", memberId=" + memberId +"\n";
    }

    public static void main(String... args){
    }
}

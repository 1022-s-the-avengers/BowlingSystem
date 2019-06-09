package top.arron206.model;

import top.arron206.controller.ScoreSimulation.Competition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetitionInfo {
    private int id;
    private String competitionType;
    private String description;
    private int round;
    private int turn;
    private int times;
    private int memberId;

    public CompetitionInfo(){
        ;
    }

    public CompetitionInfo(int id, String competitionType, String description, int round, int turn, int times, int memberId) {
        this.id = id;
        this.competitionType = competitionType;
        this.description = description;
        this.round = round;
        this.turn = turn;
        this.times = times;
        this.memberId = memberId;
    }

    public CompetitionInfo(String competitionType, String description, int round, int turn, int times, int memberId) {
        this.competitionType = competitionType;
        this.description = description;
        this.round = round;
        this.turn = turn;
        this.times = times;
        this.memberId = memberId;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setTimes(int times) {
        this.times = times;
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

    public int getRound() {
        return round;
    }

    public int getTurn() {
        return turn;
    }

    public int getTimes() {
        return times;
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
            String insertSQL = "INSERT INTO CompetitionInformation (competitionType, description, round, turn, times, memberId) VALUE (?,?,?,?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            exec.setString(1, competitionType);
            exec.setString(2, description);
            exec.setInt(3,round);
            exec.setInt(4, turn);
            exec.setInt(5, times);
            exec.setInt(6, memberId);
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
                ", turn=" + turn +
                ", times=" + times +
                ", memberId=" + memberId +"\n";
    }

    public static void main(String... args){
        CompetitionInfo cpi = new CompetitionInfo("个人赛","无描述",1,1,1,1);
        cpi.insertInfo();
    }
}

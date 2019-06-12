package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Group {
    private int groupId;
    private String type;
    private int totalScore;

    public Group(int groupId, String type) {
        this.groupId = groupId;
        this.type = type;
    }

    public Group(int groupId, String type, int totalScore) {
        this.groupId = groupId;
        this.type = type;
        this.totalScore = totalScore;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getType() {
        return type;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public static int getRankList(ArrayList<Group> rank, String type){
        Connection conn = DBConnection.getConn();
        ResultSet r=null;
        boolean release = false;
        PreparedStatement exec=null;
        if(DBConnection.judge(conn))
            return 2;
        try{
            String querySQL = "SELECT * FROM TeamInfo WHERE type=? ORDER BY totalScore";
            exec = conn.prepareStatement(querySQL);
            exec.setString(1,type);
            r = exec.executeQuery();
            while(r.next()){
                rank.add(new Group(
                        r.getInt(2),
                        r.getString(3),
                        r.getInt(4)
                        )
                );
            }
            System.out.println(rank);
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
        if(!release)
            return 5;
        return 1;
    }


    public int getMembers(ArrayList<Member> res){
        Connection conn = DBConnection.getConn();
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        if(DBConnection.judge(conn))
            return 2;
        try{
            conn.setAutoCommit(false);
            String querySQL = "";
            switch (type){
                case "双人赛": querySQL = "SELECT * FROM Member WHERE teamIdDouble = ? ";break;
                case "三人赛": querySQL = "SELECT * FROM Member WHERE teamIdTriple = ? ";break;
                case "五人赛": querySQL = "SELECT * FROM Member WHERE teamIdPenta = ? ";break;
            }
            exec = conn.prepareStatement(querySQL);
            exec.setInt(1, groupId);
            r = exec.executeQuery();
            Member.generateMember(r, res);
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
        if(!release)
            return 5;
        return 1;
    }

    public int addGroupMember(ArrayList<Integer> membersId){
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        if(membersId == null)
            return 6;
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO GroupRelationship(memberId, teamId) VALUE (?,?)";
            exec = conn.prepareStatement(insertSQL);
            for(int i=0;i<membersId.size();i++){
                exec.setInt(1,membersId.get(i));
                exec.setInt(2, groupId);
                if(exec.executeUpdate()!=1)
                    return 3;
            }
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

    public static int addGroupList(LinkedList<LinkedList<Integer> > memberIds, LinkedList<Integer> teamIds,String type ){
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        if(memberIds==null||teamIds==null)
            return 6;
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        try{
            conn.setAutoCommit(false);
            String updateSQL = null;
            switch (type){
                case "双人赛":
                    updateSQL = "UPDATE Member SET teamIdDouble = ? WHERE id = ?";
                    break;
                case "三人赛":
                    updateSQL = "UPDATE Member SET teamIdTriple = ? WHERE id = ?";
                    break;
                case "五人赛":
                    updateSQL = "UPDATE Member SET teamIdPenta = ? WHERE id = ?";
                    break;
            }
            int len = memberIds.size();
            exec = conn.prepareStatement(updateSQL);
            for(int i=0;i<len;i++){
                int jLen = memberIds.get(i).size();
                for(int j=0;j<jLen;j++){
                    exec.setInt(1, teamIds.get(i));
                    exec.setInt(2,memberIds.get(i).get(j));
                    exec.addBatch();
                }
                if(i==len-1){
                    exec.executeBatch();
                    conn.commit();
                    exec.clearBatch();
                }
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

    public static int addGroup(String type){
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        int totalNum=0;
        switch (type){
            case "双人赛":
                totalNum=30;
                break;
            case "三人赛":
                totalNum=20;
                break;
            case "五人赛":
                totalNum=12;
                break;
        }
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO TeamInfo(teamId, type) VALUE (?,?)";
            exec = conn.prepareStatement(insertSQL);
            for(int i=1;i<=totalNum;i++) {
                exec.setInt(1,i);
                exec.setString(2, type);
                exec.addBatch();
                if((i!=0 && i%200==0) || i==totalNum){
                    exec.executeBatch();
                    conn.commit();
                    exec.clearBatch();
                }
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

    public int updateTotalScore(int score){
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "UPDATE TeamInfo SET totalScore=? WHERE id = ?";
            exec = conn.prepareStatement(insertSQL);
            exec.setInt(1,score);
            exec.setInt(2,groupId);
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

    public static int updateTotalScoreList(LinkedList<Integer> scores, LinkedList<Integer> teamIds){
        boolean release = false;
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        ResultSet r=null;
        PreparedStatement exec=null;
        try{
            conn.setAutoCommit(false);
            String updateSQL = "UPDATE TeamInfo SET totalScore = ? WHERE id = ?";
            exec = conn.prepareStatement(updateSQL);
            DBConnection.updateAll(conn, exec, scores, teamIds);
//            int len = scores.size();
//            for(int i=0;i<len;i++){
//                exec.setInt(1, scores.get(i));
//                exec.setInt(2, teamIds.get(i));
//                exec.addBatch();
//                if(i==len-1){
//                    exec.executeBatch();
//                    conn.commit();
//                    exec.clearBatch();
//                }
//            }
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
        if(!release)
            return 5;
        return 1;
    }
}
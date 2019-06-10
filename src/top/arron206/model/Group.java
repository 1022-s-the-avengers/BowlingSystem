package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private int groupId;
    private String type;
    public Group(int groupId, String type) {
        this.groupId = groupId;
        this.type = type;
    }

    public static int getRankList(ArrayList<Group> rank,String type){
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
                        r.getInt(1),
                        r.getString(2)
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


    public int getMembers(ArrayList<Member> res){
        Connection conn = DBConnection.getConn();
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        if(DBConnection.judge(conn))
            return 2;
        try{
            String querySQL = "SELECT * FROM Member WHERE id IN (SELECT memberId FROM GroupRelationship WHERE teamId = ? AND type=?)";
            exec = conn.prepareStatement(querySQL);
            exec.setInt(1, groupId);
            exec.setString(2,type);
            r = exec.executeQuery();
            while(r.next()){
                res.add(
                        new Member(
                                r.getInt(1),
                                r.getString(2),
                                r.getString(3),
                                r.getInt(4)
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

    public int addGroupMember(ArrayList<Member> members){
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        if(members == null)
            return 6;
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        try{
            String insertSQL = "INSERT INTO GroupRelationship(memberId, teamId) VALUE (?,?)";
            exec = conn.prepareStatement(insertSQL);
            for(int i=0;i<members.size();i++){
                exec.setInt(1,members.get(i).getId());
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

    public static int addGroup(String type,int teamNum){
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        ResultSet r=null;
        PreparedStatement exec=null;
        boolean release = false;
        try{
            String insertSQL = "INSERT INTO TeamInfo(teamId, type) VALUE (?,?)";
            for(int i=1;i<=teamNum;i++) {
                exec = conn.prepareStatement(insertSQL);
                exec.setInt(1,i);
                exec.setString(2, type);
                if(exec.executeUpdate()!=-1)
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
}
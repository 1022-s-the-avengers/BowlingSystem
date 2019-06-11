package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Member {
    private int id;
    private String name;
    private String province;
    private int totalScore=-1;
    private int credit = -1;
    private int teamIdDouble;
    private int teamIdTriple;
    private int teamIdPenta;

    public class CompetitionRes{
        private int id;
        private int[] res = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        private int memberId;
        private String type;
        private int round;

        public void setMsg(int[] res,String type, int round){
            this.res = res;
            this.type = type;
            this.round = round;
        }

        public CompetitionRes(int id, int[] res, int memberId, String type, int round) {
            this.id = id;
            setMsg(res,type,round);
            this.memberId = memberId;

        }

        public CompetitionRes(int[] res, String type,int round){
            setMsg(res,type,round);
            this.memberId = Member.this.id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        protected void addElem(PreparedStatement exec) throws SQLException {
            for(int i=0;i<10;i++){
                exec.setInt(i+1, res[i]);
            }
            exec.setInt(11, memberId);
            exec.setString(12,type);
            exec.setInt(13, round);
        }

        public int insertRes() {
            if(judge())
                return 6;
            boolean release;
            Connection conn = DBConnection.getConn();
            PreparedStatement exec=null;
            if(conn==null)
                return 2;
            try{
                conn.setAutoCommit(false);
                String insertSQL = "INSERT INTO CompetitionRes(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId,type, round) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                exec = conn.prepareStatement(insertSQL);
                addElem(exec);
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

        boolean judge(){
            return res[0]==-1||res[1]==-1||res[2]==-1||res[3]==-1||res[4]==-1||res[5]==-1||res[6]==-1||res[7]==-1||res[8]==-1||res[9]==-1;
        }

        @Override
        public String toString() {
            return  "  type=" + type +
                    ", round=" + round +
                    ", res" + res+
                    ", memberId=" + memberId ;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public int getTeamIdDouble() {
        return teamIdDouble;
    }

    public int getTeamIdTriple() {
        return teamIdTriple;
    }

    public int getTeamIdPenta() {
        return teamIdPenta;
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

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Member(int id, String name, String province, int totalScore, int credit, int teamIdDouble, int teamIdTriple, int teamIdPenta) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.totalScore = totalScore;
        this.credit = credit;
        this.teamIdDouble = teamIdDouble;
        this.teamIdTriple = teamIdTriple;
        this.teamIdPenta = teamIdPenta;
    }

    public Member(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public Member(int id){
        this.id = id;
    }

    public static int addAllRes(LinkedList<int[]> results, String type){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO CompetitionRes(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberid, round, type)  VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            int len = results.size();
            for(int i=0;i<len;i++){
                for(int j=0;j<12;j++) {
                    exec.setInt(j+1, results.get(i)[j]);
                }
                exec.setString(13,type);
                exec.addBatch();
                if((i!=0 && i%200==0)||i==len-1){
                    exec.executeBatch();
                    conn.commit();
                    exec.clearBatch();
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
                                r.getInt(3),
                                r.getInt(4),
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

    public CompetitionRes getMessage(int round, String type){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        ResultSet r=null;
        boolean release = false;
        if(DBConnection.judge(conn))
            return null;
        try{
            conn.setAutoCommit(false);
            String querySQL = "SELECT * FROM CompetitionRes WHERE memberId = ? AND round = ? AND type = ?";
            exec = conn.prepareStatement(querySQL);
            exec.setInt(1, this.id);
            exec.setInt(2,round);
            exec.setString(3,type);
            r = exec.executeQuery();
            conn.commit();
            r.next();
            int[] res = {
                    r.getInt(2),
                    r.getInt(3),
                    r.getInt(4),
                    r.getInt(5),
                    r.getInt(6),
                    r.getInt(7),
                    r.getInt(8),
                    r.getInt(9),
                    r.getInt(10),
                    r.getInt(11),
            };
            return new CompetitionRes(
                    r.getInt(1),
                    res,
                    r.getInt(12),
                    r.getString(13),
                    r.getInt(14)
            );
        }catch (SQLException e){
            return null;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
    }

    public static void generateMember(ResultSet r, List<Member> res) throws SQLException {
        while(r.next()){
            res.add(
                    new Member(
                            r.getInt(1),
                            r.getString(2),
                            r.getString(3),
                            r.getInt(4),
                            r.getInt(5),
                            r.getInt(6),
                            r.getInt(7),
                            r.getInt(8)
                    )
            );
        }
    }

    public static boolean returnMember(List<Member> res, ResultSet r) throws SQLException {
        generateMember(r,res);
        return true;
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
            returnMember(res, r);
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
        if(name==null||province==null)
            return 6;
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(DBConnection.judge(conn))
            return 2;
        ResultSet r = null;
        try{
            conn.setAutoCommit(false);
            String querySQL = "SELECT * FROM Member";
            exec = conn.prepareStatement(querySQL);
            ResultSet res = exec.executeQuery();
            if(res.next())
                return 6;
            String insertSQL = "INSERT INTO Member(name, province, totalScore) VALUE (?,?,?)";
            exec = conn.prepareStatement(insertSQL);
            exec.setString(1,name);
            exec.setString(2,province);
            exec.setInt(3, totalScore);
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

    public static int insertAllMember(LinkedList<String> names, LinkedList<String> provinces){
        boolean release = false;
        ResultSet r=null;
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        PreparedStatement exec=null;
        try{
            conn.setAutoCommit(false);
            String querySQL = "SELECT * FROM Member";
            exec = conn.prepareStatement(querySQL);
            r = exec.executeQuery();
            if(r.next())
                return 6;
            String insertSQL = "INSERT INTO Member(name, province, totalScore) VALUE (?,?,-1)";
            exec = conn.prepareStatement(insertSQL);
            int len = names.size();
            for(int i=0;i<len;i++){
                exec.setString(1,names.get(i));
                exec.setString(2,provinces.get(i));
                exec.addBatch();
                if(i==len-1 ){
                    exec.executeBatch();
                    conn.commit();
                    exec.clearBatch();
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

    public static int updateAllScore(LinkedList<Integer> memberIds, LinkedList<Integer> scores){
        boolean release = false;
        ResultSet r=null;
        PreparedStatement exec=null;
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        try{
            conn.setAutoCommit(false);
            String updateSQL = "UPDATE Member SET totalScore = ? WHERE id = ?";
            exec = conn.prepareStatement(updateSQL);
            int len = memberIds.size();
            for(int i = 0; i < len ;i++){
                exec.setInt(1,scores.get(i));
                exec.setInt(2,memberIds.get(i));
                exec.addBatch();
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

    public boolean insertOpt(Connection conn,int first, int second, String sql) throws SQLException {
        conn.setAutoCommit(false);
        PreparedStatement exec = conn.prepareStatement(sql);
        exec.setInt(1, first);
        exec.setInt(2, second);
        if(exec.executeUpdate()!=1)
            return false;
        conn.commit();
        return true;
    }

    public static int insertAllCredit(LinkedList<Integer> memberIds, LinkedList<Integer> credits){
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try {
            conn.setAutoCommit(false);
            String sql = "UPDATE Member SET credit = ? WHERE id = ?";
            exec = conn.prepareStatement(sql);
            DBConnection.updateAll(conn, exec, credits, memberIds);
//            int len = memberIds.size();
//            for(int i=0;i<len;i++){
//                exec.setInt(1, credits.get(i));
//                exec.setInt(2,memberIds.get(i));
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
            release=DBConnection.release(conn, null, null);
        }
        if(!release)
            return 5;
        return 1;
    }

    public int insertCredit(){
        if(credit==-1)
            return 6;
        Connection conn = DBConnection.getConn();
        boolean release = false;
        if(DBConnection.judge(conn))
            return 2;
        try{
            String insertSQL = "UPDATE Member SET credit=? WHERE id = ?";
            if(!insertOpt(conn,credit, id, insertSQL))
                return 3;
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, null, null);
        }
        if(!release)
            return 5;
        return 1;
    }

    public int updateTotalSocre(){
        if(totalScore==-1)
            return 6;
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        PreparedStatement exec=null;
        boolean release = false;
        ResultSet r = null;
        try{
            String insertSQL = "UPDATE Member SET totalScore=? WHERE id = ?";
            if(!insertOpt(conn,totalScore,id,insertSQL))
                return 3;
        }catch (SQLException e){
            return 4;
        }finally {
            release=DBConnection.release(conn, null, null);
        }
        if(!release)
            return 5;
        return 1;
    }

    public static int getRank(List<Member> res){
        PreparedStatement exec=null;
        boolean release = false;
        ResultSet r = null;
        Connection conn = DBConnection.getConn();
        if(DBConnection.judge(conn))
            return 2;
        try{
            conn.setAutoCommit(false);
            String querySQL = "SELECT * FROM Member GROUP BY totalScore LIMIT 16";
            exec = conn.prepareStatement(querySQL);
            r = exec.executeQuery();
            conn.commit();
            returnMember(res, r);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return getId() == member.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", totalScore=" + totalScore +"\n";
    }

    public static void main(String... args){
    }
}
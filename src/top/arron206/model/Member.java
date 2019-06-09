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
    private int totalScore=-1;
    private CompetitionRes res;

    class CompetitionRes{
        private int id;
        private int first=-1;
        private int second=-1;
        private int third=-1;
        private int forth=-1;
        private int fifth=-1;
        private int sixth=-1;
        private int seventh=-1;
        private int eighth=-1;
        private int ninth=-1;
        private int tenth=-1;
        private int memberId;
        private int type;
        private int round;

        public void setScore(int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth){
            this.first = first;
            this.second = second;
            this.third = third;
            this.forth = forth;
            this.fifth = fifth;
            this.sixth = sixth;
            this.seventh = seventh;
            this.eighth = eighth;
            this.ninth = ninth;
            this.tenth = tenth;
        }

        public CompetitionRes(int id, int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int memberId, int type, int round) {
            this.id = id;
            setScore(first,second,third,forth,fifth,sixth,seventh,eighth,ninth,tenth);
            this.memberId = memberId;
            this.type = type;
            this.round = round;
        }

        public CompetitionRes(int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int type,int round){
            setScore(first,second,third,forth,fifth,sixth,seventh,eighth,ninth,tenth);
            this.memberId = Member.this.id;
            this.type = type;
            this.round = round;
        }

        public int getId() {
            return id;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }

        public int getThird() {
            return third;
        }

        public int getForth() {
            return forth;
        }

        public int getFifth() {
            return fifth;
        }

        public int getSixth() {
            return sixth;
        }

        public int getSeventh() {
            return seventh;
        }

        public int getEighth() {
            return eighth;
        }

        public int getNinth() {
            return ninth;
        }

        public int getTenth() {
            return tenth;
        }

        public int getMemberId() {
            return memberId;
        }

        public int getType() {
            return type;
        }

        public int getRound() {
            return round;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public void setThird(int third) {
            this.third = third;
        }

        public void setForth(int forth) {
            this.forth = forth;
        }

        public void setFifth(int fifth) {
            this.fifth = fifth;
        }

        public void setSixth(int sixth) {
            this.sixth = sixth;
        }

        public void setSeventh(int seventh) {
            this.seventh = seventh;
        }

        public void setEighth(int eighth) {
            this.eighth = eighth;
        }

        public void setNinth(int ninth) {
            this.ninth = ninth;
        }

        public void setTenth(int tenth) {
            this.tenth = tenth;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setRound(int round) {
            this.round = round;
        }

        protected void addElem(PreparedStatement exec) throws SQLException {
            exec.setInt(1, first);
            exec.setInt(2,second);
            exec.setInt(3, third);
            exec.setInt(4,forth);
            exec.setInt(5, fifth);
            exec.setInt(6, sixth);
            exec.setInt(7, seventh);
            exec.setInt(8, eighth);
            exec.setInt(9, ninth);
            exec.setInt(10, tenth);
            exec.setInt(11, memberId);
            exec.setInt(12,type);
            exec.setInt(13, round);
        }

        public int insertRes() {
            if(judge())
                return 6;
            boolean release=false;
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

        protected boolean judge(){
            return first==-1||second==-1||third==-1||forth==-1||fifth==-1||sixth==-1||seventh==-1||eighth==-1||ninth==-1||tenth==-1;
        }

        @Override
        public String toString() {
            return  "  type=" + type +
                    ", round=" + round +
                    ", first=" + first +
                    ", second=" + second +
                    ", third=" + third +
                    ", forth=" + forth +
                    ", fifth=" + fifth +
                    ", sixth=" + sixth +
                    ", seventh=" + seventh +
                    ", eighth=" + eighth +
                    ", ninth=" + ninth +
                    ", tenth=" + tenth +
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

    public int getTotalScore() {
        return totalScore;
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

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Member(int id, String name, String province, int totalScore) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.totalScore = totalScore;
    }

    public Member(String name, String province, int totalScore) {
        this.name = name;
        this.province = province;
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

    public CompetitionRes getMessage(int round, int type){
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
            exec.setInt(3,type);
            r = exec.executeQuery();
            conn.commit();
            r.next();
            return new CompetitionRes(
                    r.getInt(1),
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
                    r.getInt(12),
                    r.getInt(13),
                    r.getInt(14)
            );
        }catch (SQLException e){
            return null;
        }finally {
            release=DBConnection.release(conn, exec, r);
        }
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
package top.arron206.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class CompetitionRes {
    private int id;
    private int first;
    private int second;
    private int third;
    private int forth;
    private int fifth;
    private int sixth;
    private int seventh;
    private int eighth;
    private int ninth;
    private int tenth;
    private int memberId;

    public CompetitionRes(int memberId) {
        this.memberId = memberId;
    }

    public CompetitionRes(int id, int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int memberId) {
        this.id = id;
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
        this.memberId = memberId;
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

    public void setNineth(int nineth) {
        this.ninth = nineth;
    }

    public void setTenth(int tenth) {
        this.tenth = tenth;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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
    }

    public abstract int insertRes();
}

class SingleRes extends CompetitionRes{
    public SingleRes(int memberId) {
        super(memberId);
    }

    public SingleRes(int id, int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int memberId) {
        super(id, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId);
    }

    @Override
    public int insertRes() {
        if(getFirst()==-1||getSecond()==-1||getThird()==-1||getForth()==-1||getFifth()==-1||getSixth()==-1||getSeventh()==-1||getEighth()==-1||getNinth()==-1||getTenth()==-1)
            return 6;
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release=false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO singleRes(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId) VALUE (?,?,?,?,?,?,?,?,?,?,?)";
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
}
class DoubleRes extends CompetitionRes{
    public DoubleRes(int memberId) {
        super(memberId);
    }

    public DoubleRes(int id, int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int memberId) {
        super(id, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId);
    }

    @Override
    public int insertRes() {
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO doubleRes(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId) VALUE (?,?,?,?,?,?,?,?,?,?,?)";
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
}
class TripleRes extends CompetitionRes{
    public TripleRes(int memberId) {
        super(memberId);
    }

    public TripleRes(int id, int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int memberId) {
        super(id, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId);
    }

    @Override
    public int insertRes() {
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO TripleRes(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId) VALUE (?,?,?,?,?,?,?,?,?,?,?)";
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
}

class PentaRes extends CompetitionRes{
    public PentaRes(int memberId) {
        super(memberId);
    }

    public PentaRes(int id, int first, int second, int third, int forth, int fifth, int sixth, int seventh, int eighth, int ninth, int tenth, int memberId) {
        super(id, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId);
    }

    @Override
    public int insertRes() {
        Connection conn = DBConnection.getConn();
        PreparedStatement exec=null;
        boolean release = false;
        if(conn==null)
            return 2;
        try{
            conn.setAutoCommit(false);
            String insertSQL = "INSERT INTO pentaRes(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, memberId) VALUE (?,?,?,?,?,?,?,?,?,?,?)";
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
}
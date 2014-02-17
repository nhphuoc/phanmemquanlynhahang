/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

/**
 *
 * @author nhphuoc
 */
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Table {

    private int ID;
    private String NAME;
    private int TYPE;
    private int LOCATION;
    private int NUMBER_OF_CHAIR;
    private int STATUS;
    private String NAME_LOCATION;

    
    connectDB connect = new connectDB();

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public int getTYPE() {
        return TYPE;
    }

    public int getLOCATION() {
        return LOCATION;
    }

    public int getNUMBER_OF_CHAIR() {
        return NUMBER_OF_CHAIR;
    }

    public int getSTATUS() {
        return STATUS;
    }
    public String getNAME_LOCATION() {
        return NAME_LOCATION;
    }
    public Table(int ID) {
        this.ID = ID;
    }
    public Table(int ID, String NAME) {
        this.ID = ID;
        this.NAME=NAME;
    }
    public Table(int ID, String NAME, int TYPE, int LOCATION, int NUMBER_OF_CHAIR, int STATUS) {
        this.ID = ID;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.LOCATION = LOCATION;
        this.NUMBER_OF_CHAIR = NUMBER_OF_CHAIR;
        this.STATUS = STATUS;
    }
    public Table(int ID, String NAME, int TYPE, int LOCATION, int NUMBER_OF_CHAIR, int STATUS,String NAME_LOCATION) {
        this.ID = ID;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.LOCATION = LOCATION;
        this.NUMBER_OF_CHAIR = NUMBER_OF_CHAIR;
        this.STATUS = STATUS;
        this.NAME_LOCATION=NAME_LOCATION;
    }
    public static Table[] getAll() throws SQLException {

        Statement state = connectDB.conn().createStatement();
        String sql = "call table_getAll()";
        CallableStatement calState = connectDB.conn().prepareCall(sql);
        ResultSet rs = calState.executeQuery(sql);
        rs.last();
        Table[] tables = new Table[rs.getRow()];
        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            tables[i] = new Table(rs.getInt("id"), rs.getString("name"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"));
            i++;
        }
        return tables;
    }
    
    public static Table getByID(int id){
        Table table;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "call table_getById(?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1, id);
            ResultSet rs = callstate.executeQuery();            
            while (rs.next()) {
                table = new Table(rs.getInt("id"), rs.getString("name_table"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"),rs.getString("name_location"));
                return table;
            }
        } catch (Exception e) {
        }        
        return null;
    }

    public static boolean updateStatus(int id, int status) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL table_update_status(?,?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setInt(2, status);
            int x = callstate.executeUpdate();
            if (x == 1) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

}

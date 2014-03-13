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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Table {

    private int ID;
    private static int IDTABLE; //// use for reservation table

    public static int getIDTABLE() {
        return IDTABLE;
    }

    public static void setIDTABLE(int IDTABLE) {
        Table.IDTABLE = IDTABLE;
    }

    public static String getTABLENAME() {
        return TABLENAME;
    }

    public static void setTABLENAME(String TABLENAME) {
        Table.TABLENAME = TABLENAME;
    }
    private static String TABLENAME; // use for reservation table
    private String NAME;
    private int TYPE;
    private int LOCATION;
    private int NUMBER_OF_CHAIR;
    private int STATUS;
    private String NAME_LOCATION;

    ConnectDB connect = new ConnectDB();

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
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
        this.NAME = NAME;
    }

    public Table(int ID, String NAME, int TYPE, int LOCATION, int NUMBER_OF_CHAIR, int STATUS) {
        this.ID = ID;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.LOCATION = LOCATION;
        this.NUMBER_OF_CHAIR = NUMBER_OF_CHAIR;
        this.STATUS = STATUS;
    }

    public Table(int ID, String NAME, int TYPE, int LOCATION, int NUMBER_OF_CHAIR, int STATUS, String NAME_LOCATION) {
        this.ID = ID;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.LOCATION = LOCATION;
        this.NUMBER_OF_CHAIR = NUMBER_OF_CHAIR;
        this.STATUS = STATUS;
        this.NAME_LOCATION = NAME_LOCATION;
    }

    public static Table[] getAll(Connection conn) {
        Table[] tables = null;
        try {
            String sql = "call table_getAll()";
            CallableStatement calState = conn.prepareCall(sql);
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            tables = new Table[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                tables[i] = new Table(rs.getInt("id"), rs.getString("name"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"));
                i++;
            }

        } catch (Exception e) {
        }
        return tables;
    }

    public static Table getByID(int id, Connection conn) {
        Table table;
        try {
            String sql = "call table_getById(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                table = new Table(rs.getInt("id"), rs.getString("name_table"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"), rs.getString("name_location"));
                return table;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Table[] getByLocation(int idLocation, Connection conn) {
        Table[] tables = null;
        try {
            String sql = "SELECT * FROM `table` WHERE `table`.`location`=" + idLocation + " AND `table`.`isActive`=TRUE;";
            CallableStatement calState = conn.prepareCall(sql);
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            tables = new Table[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                tables[i] = new Table(rs.getInt("id"), rs.getString("name"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

    public static Table[] getByDate(int day, int month, int year, Connection conn) throws SQLException {
        String sql = "call abc(?)";
        CallableStatement calState = conn.prepareCall(sql);
        calState.setInt(1, day);
        //calState.setInt(2, month);
        //calState.setInt(3, year);
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

    public static boolean updateStatus(int id, int status, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_update_status(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setInt(2, status);
            int x = callstate.executeUpdate();
            if (x >= 0) {
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

    public static boolean updateStatus(String name, int status, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_update_status_by_name(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setInt(2, status);
            int x = callstate.executeUpdate();
            if (x >= 0) {
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

    public static Table getByName(String name, Connection conn) {
        Table table;
        try {
            String sql = "call table_getByName(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                table = new Table(rs.getInt("id"), rs.getString("name_table"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"), rs.getString("name_location"));
                return table;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Vector selectTableByLocation(int idLocation, int status, Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "SELECT * FROM `table` WHERE `table`.`location`=" + idLocation + " AND `table`.status =" + status + " and `table`.`isActive`=TRUE;";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            boolean b;
            while (b=rs.next()) {
                
                vn.edu.vttu.model.Table tb = new vn.edu.vttu.model.Table(rs.getInt(1), rs.getString(2));
                result.add(tb);
                if (b == false) {
                    vn.edu.vttu.model.Table tb1 = new vn.edu.vttu.model.Table(0, "Không Có Bàn");
                    result.add(tb1);
                }
                
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    public static TableModel loadTableByStatus(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL table_getbyStatus()}");
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static TableModel loadTableByStatusSearch(String key, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL table_getByStatus_Search(?)}");
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static boolean insertNewTable(String name, int type, int location, int chair, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_insert_new_table(?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setInt(2, type);
            callstate.setInt(3, location);
            callstate.setInt(4, chair);
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

    public static boolean testTableName(String name, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean flag = false;
        try {
            CallableStatement calState = conn.prepareCall("call table_test_table_name(?)");
            calState.setString(1, name);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if (tb.getRowCount() <= 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean updateTableName(String name, int idTable, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_update_name(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setInt(2, idTable);
            int x = callstate.executeUpdate();
            if (x >= 0) {
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

    public static TableModel getByDateNotReservation(Timestamp dt, int num,int location, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL table_get_by_not_reservation_1(?,?,?)}");
            calState.setTimestamp(1, dt);
            calState.setInt(2, num);            
            calState.setInt(3, location);            
            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static Vector getByDateNotReservationVector(Timestamp dt, int num, Connection conn) {
        Vector result = new Vector();
        try {
            CallableStatement calState = conn.prepareCall("{CALL table_get_by_not_reservation(?,?)}");
            calState.setTimestamp(1, dt);
            calState.setInt(2, num);
            ResultSet rs = calState.executeQuery();
            vn.edu.vttu.model.Table tb1 = new vn.edu.vttu.model.Table(0, "Chọn Bàn");
            result.add(tb1);
            while (rs.next()) {
                vn.edu.vttu.model.Table tb = new vn.edu.vttu.model.Table(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

}

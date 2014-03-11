/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class RawMaterial {

    private int id;
    private String name;
    private int number;
    private int unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public RawMaterial(int id, String name, int number, int unit) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.unit = unit;
    }

    public static TableModel getAll(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL raw_material_get_all()}");
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static TableModel search(String key,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL raw_material_search(?)}");
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean insert(String name,float number, int unit, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_add(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setFloat(2, number);
            callstate.setInt(3, unit);
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
    public static boolean update(String name,float number,int unit,int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_update(?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setFloat(2, number);
            callstate.setInt(3, unit);
            callstate.setInt(4, id);
            int x = callstate.executeUpdate();
            if (x >=0) {
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
    public static boolean delete(int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_del(?)";
            CallableStatement callstate = conn.prepareCall(sql);

            callstate.setInt(1, id);
            int x = callstate.executeUpdate();
            if (x >=0) {
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
    public static boolean testName(String name,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean flag=false;
        try {
            CallableStatement calState = conn.prepareCall("{CALL raw_material_test_name(?)}");
            calState.setString(1, name);
            rs = calState.executeQuery();            
            tb = DbUtils.resultSetToTableModel(rs);
            if(tb.getRowCount()>0){
                flag=false;
            }else{
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
    public static boolean updateNumber(int id,float number, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_update_number(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setFloat(2, number);            
            int x = callstate.executeUpdate();
            if (x ==1) {
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
    public static TableModel getNumber(int id,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL raw_material_get_number(?)}");
            calState.setInt(1, id);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

}

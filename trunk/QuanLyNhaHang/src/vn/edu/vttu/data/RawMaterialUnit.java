/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class RawMaterialUnit {

    public static TableModel getListRawmetarialUnit(int idRaw, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("call raw_material_unit_get_list_unit(?)");
            calState.setInt(1, idRaw);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static TableModel getByIdRawmetaril(int idRaw, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("call raw_material_unit_get_raw_material(?)");
            calState.setInt(1, idRaw);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static TableModel getUnitRawMetarialParent(int idRaw, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("call raw_material_unit_get__unit_parent(?)");
            calState.setInt(1, idRaw);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static TableModel getUnitSon(int idRaw, int idUnitParent, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("call raw_material_unit_get__unit_son(?,?)");
            calState.setInt(1, idRaw);
            calState.setInt(2, idUnitParent);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    
    public static boolean insert(int id_raw, int id_unit,boolean b, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_unit_add(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_raw);
            callstate.setInt(2, id_unit);
            callstate.setBoolean(3, b);
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
    public static boolean delete(int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_unit_del(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);            
            int x = callstate.executeUpdate();
            if (x == 0) {
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
    public static Vector selectUnitByIdRawMaterial(int id_rawmaterial, Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "call raw_material_unit_get_unit_by_raw_material(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_rawmaterial);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.Unit tb = new vn.edu.vttu.model.Unit(rs.getInt(2), rs.getString(1));
                result.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }    
    
     public static boolean testUnit(int id_unit,int idRaw, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean t=false;
        try {
            CallableStatement calState = conn.prepareCall("call raw_material_unit_test_unit(?,?)");
            calState.setInt(1, id_unit);
            calState.setInt(2, idRaw);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if(tb.getRowCount()<=0){
                t= true;
            }else{
                t= false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}

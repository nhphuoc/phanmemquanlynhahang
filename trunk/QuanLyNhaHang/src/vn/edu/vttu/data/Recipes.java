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
public class Recipes {

    private int id;
    private int idrawmaterial;
    private int idService;
    private float number;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdrawmaterial() {
        return idrawmaterial;
    }

    public void setIdrawmaterial(int idrawmaterial) {
        this.idrawmaterial = idrawmaterial;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Recipes(int id, float number) {
        this.id = id;
        this.number = number;
    }       
    public static TableModel getRecipesByIdService(int id, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL recipes_get_by_service(?)}");
            calState.setInt(1, id);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static boolean insert(int id_raw_material, int idService, float number, int unitsub,Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL recipes_add(?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_raw_material);
            callstate.setInt(2, idService);
            callstate.setFloat(3, number);
            callstate.setInt(4, unitsub);
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

    public static boolean update(int id_raw_material, int idService, float number, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL recipes_update_by_id_service_id_raw_material(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idService);
            callstate.setInt(2, id_raw_material);
            callstate.setFloat(3, number);
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

    public static boolean update(int id, float number, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL recipes_update_by_id_recipes(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setFloat(2, number);
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

    public static boolean delete(int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL recipes_delete(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
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

    public static boolean countRecipesByIdService(int idService, int idStore, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        String count;
        boolean flag = false;
        try {
            CallableStatement calState = conn.prepareCall("{CALL recipes_count_by_idService_id_raw(?,?)}");
            calState.setInt(1, idService);
            calState.setInt(2, idStore);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            count = String.valueOf(tb.getValueAt(0, 0));
            if (count.equals("0")) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public static boolean countRecipesByIdService(int idService, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        String count;
        boolean flag = false;
        try {
            CallableStatement calState = conn.prepareCall("{CALL recipes_count_by_idService(?)}");
            calState.setInt(1, idService);            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            count = String.valueOf(tb.getValueAt(0, 0));
            if (count.equals("0")) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}

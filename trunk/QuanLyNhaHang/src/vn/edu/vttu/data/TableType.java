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

/**
 *
 * @author nhphuoc
 */
public class TableType {
    private int id;
    private String name;

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
    public TableType(int id, String name){
        this.id=id;
        this.name=name;
    }
    public TableType(int id){
        this.id=id;
    }
    public static TableType getByMaxID(Connection conn) {
        TableType tabletype;
        try {
            String sql = "call table_type_get_max_id()";
            CallableStatement callstate = conn.prepareCall(sql);            
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                tabletype = new TableType(rs.getInt("id"));
                return tabletype;
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public static Vector selectTableType(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "call table_type_getAll()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.TableType tb = new vn.edu.vttu.model.TableType(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
    public static boolean insert(String name,Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_type_add(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);            
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
    
}

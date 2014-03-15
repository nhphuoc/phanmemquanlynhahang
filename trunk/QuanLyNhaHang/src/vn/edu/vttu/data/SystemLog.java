/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class SystemLog {
    private int id;
    private String info;
    private Timestamp date;
   
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public SystemLog(int id, String info, Timestamp date){
        this.id=id;
        this.info=info;
        this.date=date;
    }
    public static SystemLog getRowSystemLog(Connection conn) {
        SystemLog slog=null;
        try {
            String sql = "call system_log_get_max_id";
            CallableStatement calState = conn.prepareCall(sql);
            ResultSet rs = calState.executeQuery(sql);                                                
            while (rs.next()) {
                slog = new SystemLog(rs.getInt("id"),rs.getString("info"),rs.getTimestamp("date"));                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return slog;
        
    }
    
}

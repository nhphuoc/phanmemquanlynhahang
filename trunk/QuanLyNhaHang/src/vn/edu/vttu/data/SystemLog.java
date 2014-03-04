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
    private int info;
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

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
    public SystemLog(int id, int info, Timestamp date){
        this.id=id;
        this.info=info;
        this.date=date;
    }
    public static SystemLog[] getRowSystemLog(Connection conn) {
        SystemLog[] slog = null;
        try {
            String sql = "select * from system_log order by id desc";
            CallableStatement calState = conn.prepareCall(sql);
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            slog = new SystemLog[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                slog[i] = new SystemLog(rs.getInt("id"),rs.getInt("info"),rs.getTimestamp("date"));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return slog;
        
    }
    
}

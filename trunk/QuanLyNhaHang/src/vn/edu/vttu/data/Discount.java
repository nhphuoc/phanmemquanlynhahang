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
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class Discount {
    private int id;
    private String name;
    private int type;
    private Timestamp beginDate;
    private Timestamp endDate;
    private int condition;   
    private int value;
    private String detail;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public Discount(int id, String name, int type,Timestamp beginDate,Timestamp endDate,int condition,int value,String detail){
        this.id=id;
        this.name=name;
        this.type=type;
        this.beginDate=beginDate;
        this.endDate=endDate;
        this.condition=condition;
        this.value=value;
        this.detail=detail;
    }
    public static Discount getByDate(Connection conn) {
        Discount discount;
        try {
            String sql = "call discount_get_by_date()";
            CallableStatement callstate = conn.prepareCall(sql);            
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                discount = new Discount(rs.getInt("id"), rs.getString("name"), rs.getInt("type"), rs.getTimestamp("beginDate"),rs.getTimestamp("endDate"),rs.getInt("valueInvoice"),rs.getInt("value"), rs.getString("detail"));
                return discount;
            }
        } catch (Exception e) {
        }
        return new Discount(-1, "Không có khuyến mãi",2,null,null,-1, -1, null);
    }
    public static TableModel getListPromotion(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL discount_get_all()}");            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }    
    public static boolean insert(String name, int type, Timestamp beginDate, Timestamp endDate, int condition, int valueInvoice, int value, String detail,  Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL discount_add(?,?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);                  
            callstate.setInt(2, type);                  
            callstate.setTimestamp(3, beginDate);                  
            callstate.setTimestamp(4, endDate);                  
            callstate.setInt(5, condition);                  
            callstate.setInt(6, valueInvoice);                  
            callstate.setInt(7, value);                  
            callstate.setString(8, detail);                  
            int x = callstate.executeUpdate();
            if (x == 1) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return flag;
    } 
    public static boolean update(String name, int type, Timestamp beginDate, Timestamp endDate, int condition, int valueInvoice, int value, String detail,int id,  Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL discount_update(?,?,?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);                  
            callstate.setInt(2, type);                  
            callstate.setTimestamp(3, beginDate);                  
            callstate.setTimestamp(4, endDate);                  
            callstate.setInt(5, condition);                  
            callstate.setInt(6, valueInvoice);                  
            callstate.setInt(7, value);                  
            callstate.setString(8, detail);                  
            callstate.setInt(9, id);                  
            int x = callstate.executeUpdate();
            if (x >= 1) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return flag;
    } 
    public static boolean updateStatus(int id,  Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL discount_update_status(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);                               
            int x = callstate.executeUpdate();
            if (x >= 1) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return flag;
    } 
    public static boolean delete(int id,Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL discount_delete(?)";
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
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return flag;
    } 
    public static boolean testDate(Timestamp dt,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean flag=false;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL discount_test_date(?)}");    
            calState.setTimestamp(1, dt);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if(tb.getRowCount()>0){
                flag=false;
            }else{
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }   
    
}

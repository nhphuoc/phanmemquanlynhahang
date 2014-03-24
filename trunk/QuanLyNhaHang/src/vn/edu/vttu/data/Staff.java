/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class Staff {
    private int id;
    private String name;
    private boolean  sex;
    private Date birthday;
    private String phone;
    private String adrress;
    private String email;
    private String pin;

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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    public Staff(int id,String name,boolean sex,Date birthday,String phone, String address,String email,String pin){
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.birthday=birthday;
        this.phone=phone;
        this.adrress=address;
        this.email=email;
        this.pin=pin;
    }
    public static TableModel getAll(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL staff_get_all()}");            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    } 
    public static boolean insertStaffInfo(String name, boolean  sex,Date birthday,String phone, String address,String email,String pin, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL staff_add_staff(?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);            
            callstate.setBoolean(2, sex);            
            callstate.setDate(3, birthday);            
            callstate.setString(4, phone);            
            callstate.setString(5, address);            
            callstate.setString(6, email);            
            callstate.setString(7, pin);            
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
    public static boolean updateStaffInfo(String name, boolean  sex,Date birthday,String phone, String address,String email,String pin,int id, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL staff_update_staff(?,?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);            
            callstate.setBoolean(2, sex);            
            callstate.setDate(3, birthday);            
            callstate.setString(4, phone);            
            callstate.setString(5, address);            
            callstate.setString(6, email);            
            callstate.setString(7, pin);            
            callstate.setInt(8, id);            
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
    public static Vector selectStaff(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "CALL staff_get_all()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();            
            vn.edu.vttu.model.Staff tb1 = new vn.edu.vttu.model.Staff(0, "Tất Cả");
            result.add(tb1);
            while (rs.next()) {                
                vn.edu.vttu.model.Staff tb = new vn.edu.vttu.model.Staff(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
    
    public static Vector selectStaffAccount(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "CALL staff_get_all()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();                        
            while (rs.next()) {                
                vn.edu.vttu.model.Staff tb = new vn.edu.vttu.model.Staff(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
    
}

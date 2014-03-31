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
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class Account {

    private int id;
    private int id_staff;
    private String user;
    private String pass;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Account(int id, int id_staff, String user, String pass, int type) {
        this.id = id;
        this.id_staff = id_staff;
        this.user = user;
        this.pass = pass;
        this.type = type;
    }

    public static boolean checkLogin(String u, String p, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean t;
        try {
            String sql = "call account_login(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, u);
            callstate.setString(2, p);
            rs = callstate.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if(tb.getRowCount()==1){
                t=true;
            }else{
                t=false;
            }
        } catch (Exception e) {
            t=false;
        }
        return t;
    }
    
    public static Account login(String u, String p, Connection conn) {
        Account acc = null;
        try {
            String sql = "call account_login(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, u);
            callstate.setString(2, p);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                acc = new Account(rs.getInt("id"), rs.getInt("id_staff_id"), rs.getString("username"), rs.getString("password"), rs.getInt("type"));                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acc;
    }

    public static boolean insert(int id_staff, String user, String pass, int type, boolean active, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL account_add(?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_staff);
            callstate.setString(2, user);
            callstate.setString(3, pass);
            callstate.setInt(4, type);
            callstate.setBoolean(5, active);
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

    public static boolean update(int id_staff, String user, String pass, int type, boolean active, int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL account_update(?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_staff);
            callstate.setString(2, user);
            callstate.setString(3, pass);
            callstate.setInt(4, type);
            callstate.setBoolean(5, active);
            callstate.setInt(6, id);
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
            String sql = "CALL account_del(?)";
            CallableStatement callstate = conn.prepareCall(sql);;
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
    public static boolean deleteByStaff(int id_staff, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL account_del_by_staff(?)";
            CallableStatement callstate = conn.prepareCall(sql);;
            callstate.setInt(1, id_staff);
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

    public static boolean testUsername(String username, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean t = false;
        try {
            CallableStatement calState = conn.prepareCall("{CALL account_test_user(?)}");
            calState.setString(1, username);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if (Integer.parseInt(tb.getValueAt(0, 0).toString()) >= 1) {
                t = false;
            } else {
                t = true;
            }
        } catch (Exception e) {
            t = false;
        }
        return t;
    }

    public static boolean testStaff(int id, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean t = false;
        try {
            CallableStatement calState = conn.prepareCall("{CALL account_test_staff(?)}");
            calState.setInt(1, id);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if (Integer.parseInt(tb.getValueAt(0, 0).toString()) >= 1) {
                t = false;
            } else {
                t = true;
            }
        } catch (Exception e) {
            t = false;
        }
        return t;
    }

    public static TableModel accountGetAll(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL account_get_all()}");
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
        }
        return tb;
    }
    public static boolean updatePass(String user,String pass, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL account_update_pass(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);            
            callstate.setString(1, user);            
            callstate.setString(2, pass);            
            int x = callstate.executeUpdate();
            if (x >=0 && x<2) {
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

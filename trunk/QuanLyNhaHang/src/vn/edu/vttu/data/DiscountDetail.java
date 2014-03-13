/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;

/**
 *
 * @author nhphuoc
 */
public class DiscountDetail {
    private int id_invoice;
    private int id_discount;

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
    }

    public int getId_discount() {
        return id_discount;
    }

    public void setId_discount(int id_discount) {
        this.id_discount = id_discount;
    }
    public static boolean insert(int idInvoice,int idDiscount, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL discount_detail_insert(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idInvoice);
            callstate.setInt(2, idDiscount);
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
}

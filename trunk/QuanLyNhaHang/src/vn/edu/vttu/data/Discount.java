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
    public Discount(int id, String name, int type,Timestamp beginDate,Timestamp endDate,int value,String detail){
        this.id=id;
        this.name=name;
        this.type=type;
        this.beginDate=beginDate;
        this.endDate=endDate;
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
                discount = new Discount(rs.getInt("id"), rs.getString("name"), rs.getInt("type"), rs.getTimestamp("beginDate"),rs.getTimestamp("endDate"), rs.getInt("value"), rs.getString("detail"));
                return discount;
            }
        } catch (Exception e) {
        }
        return new Discount(0, "Không có khuyến mãi",2,null,null, 0, null);
    }
    
}

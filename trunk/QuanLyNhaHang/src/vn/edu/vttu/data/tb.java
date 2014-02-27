/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author nhphuoc
 */
public class tb {
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    private String NAME;
    private int TYPE;
    private int LOCATION;
    private int NUMBER_OF_CHAIR;
    private int STATUS;
    private String NAME_LOCATION;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public int getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(int LOCATION) {
        this.LOCATION = LOCATION;
    }

    public int getNUMBER_OF_CHAIR() {
        return NUMBER_OF_CHAIR;
    }

    public void setNUMBER_OF_CHAIR(int NUMBER_OF_CHAIR) {
        this.NUMBER_OF_CHAIR = NUMBER_OF_CHAIR;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public String getNAME_LOCATION() {
        return NAME_LOCATION;
    }

    public void setNAME_LOCATION(String NAME_LOCATION) {
        this.NAME_LOCATION = NAME_LOCATION;
    }
    public tb(int ID, String NAME, int TYPE, int LOCATION, int NUMBER_OF_CHAIR, int STATUS) {
        this.ID = ID;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.LOCATION = LOCATION;
        this.NUMBER_OF_CHAIR = NUMBER_OF_CHAIR;
        this.STATUS = STATUS;
    }
    public static tb[] getByLocation(String idLocation, Connection conn) {
        tb[] tables = null;
        try {
            String sql = "call table_getByLocation(?)";
            CallableStatement calState = conn.prepareCall(sql);
            calState.setString(1, idLocation);
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            tables = new tb[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                tables[i] = new tb(rs.getInt("id"), rs.getString("name_table"), rs.getInt("type"), rs.getInt("location"), rs.getInt("numOfChair"), rs.getInt("status"));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }
    
}

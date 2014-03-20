/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.model;

import java.io.Serializable;

/**
 *
 * @author nhphuoc
 */
public class Table {

    private int idTable;
    private String nameTable;

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public Table(int idTable, String nameTable) {
        this.idTable = idTable;
        this.nameTable = nameTable;
    }

    @Override
    public String toString() {
        return nameTable;
    }

}

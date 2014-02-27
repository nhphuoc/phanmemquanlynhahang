/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

/**
 *
 * @author nhphuoc
 */
public class VariableStatic {
    public static int idTable;
    public static int[] arrIdTable; 
    public static String nameTable;
    public static int idReservation;
    public static int idCustomer;
    public static String nameCustomer;    
    public static String dateTimeReservation; 
    public static boolean status;

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        VariableStatic.status = status;
    }

    public static int getIdTable() {
        return idTable;
    }

    public static void setIdTable(int idTable) {
        VariableStatic.idTable = idTable;
    }

    public static int[] getArrIdTable() {
        return arrIdTable;
    }

    public static void setArrIdTable(int[] arrIdTable) {
        VariableStatic.arrIdTable = arrIdTable;
    }

    public static String getNameTable() {
        return nameTable;
    }

    public static void setNameTable(String nameTable) {
        VariableStatic.nameTable = nameTable;
    }

    public static int getIdReservation() {
        return idReservation;
    }

    public static void setIdReservation(int idReservation) {
        VariableStatic.idReservation = idReservation;
    }

    public static int getIdCustomer() {
        return idCustomer;
    }

    public static void setIdCustomer(int idCustomer) {
        VariableStatic.idCustomer = idCustomer;
    }

    public static String getNameCustomer() {
        return nameCustomer;
    }

    public static void setNameCustomer(String nameCustomer) {
        VariableStatic.nameCustomer = nameCustomer;
    }

    public static String getDateTimeReservation() {
        return dateTimeReservation;
    }

    public static void setDateTimeReservation(String dateTimeReservation) {
        VariableStatic.dateTimeReservation = dateTimeReservation;
    }
       
    
}

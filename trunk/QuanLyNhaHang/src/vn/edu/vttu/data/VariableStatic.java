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
    public static java.sql.Timestamp dateTimeReservation; 
    public static boolean status;
    private static int idTable_Change;
    private static int idRequest;
    private static int typeService;
    private static String typeServiceName;    
    private static int idService;    
    private static String nameService;   
    private static int prePay;
    private static int id_store;

    public static int getId_store() {
        return id_store;
    }

    public static void setId_store(int id_store) {
        VariableStatic.id_store = id_store;
    }

    public static int getPrePay() {
        return prePay;
    }

    public static void setPrePay(int prePay) {
        VariableStatic.prePay = prePay;
    }

    public static String getNameService() {
        return nameService;
    }

    public static void setNameService(String nameService) {
        VariableStatic.nameService = nameService;
    }

    public static int getIdService() {
        return idService;
    }

    public static void setIdService(int idService) {
        VariableStatic.idService = idService;
    }

    public static String getTypeServiceName() {
        return typeServiceName;
    }

    public static void setTypeServiceName(String typeServiceName) {
        VariableStatic.typeServiceName = typeServiceName;
    }

    public static int getTypeService() {
        return typeService;
    }

    public static void setTypeService(int typeService) {
        VariableStatic.typeService = typeService;
    }

    public static int getCostService() {
        return costService;
    }

    public static void setCostService(int costService) {
        VariableStatic.costService = costService;
    }
    private static int costService;

    public static int getIdRequest() {
        return idRequest;
    }

    public static void setIdRequest(int idRequest) {
        VariableStatic.idRequest = idRequest;
    }

    public static int getIdTable_Change() {
        return idTable_Change;
    }

    public static void setIdTable_Change(int idTable_Change) {
        VariableStatic.idTable_Change = idTable_Change;
    }

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

    public static java.sql.Timestamp getDateTimeReservation() {
        return dateTimeReservation;
    }

    public static void setDateTimeReservation(java.sql.Timestamp dateTimeReservation) {
        VariableStatic.dateTimeReservation = dateTimeReservation;
    }
       
    
}

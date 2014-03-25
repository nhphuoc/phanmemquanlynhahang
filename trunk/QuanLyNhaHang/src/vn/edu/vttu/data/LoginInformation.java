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
public class LoginInformation {
    public static int id;
    public static String user;
    public static int type;
    public static int id_staff;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoginInformation.id = id;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        LoginInformation.user = user;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        LoginInformation.type = type;
    }

    public static int getId_staff() {
        return id_staff;
    }

    public static void setId_staff(int id_staff) {
        LoginInformation.id_staff = id_staff;
    }    
    
}

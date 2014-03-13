/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package config;

/**
 *
 * @author nhphuoc
 */
public class InfoRestaurant {
    public static String IPdatabase;      
    public static int portdatabase;
    public static String namedatabase;

    
    public static String usernamedatabase;
    public static String passdatabase;
    public static String IPserver;
    
    // info restaurant
    private static String name;
    public static String phone;
    public static String email;
    public static String address;
    public static String logo;
    public static int hourAcceptReservationNomal;
    public static int hourAcceptReservationParty;
    public static int minuteWarningCustomer;
    
    public static String getIPdatabase() {
        return IPdatabase;
    }
    public static void setIPdatabase(String IPdatabase) {
        InfoRestaurant.IPdatabase = IPdatabase;
    }
    public static String getNamedatabase() {
        return namedatabase;
    }

    public static void setNamedatabase(String namedatabase) {
        InfoRestaurant.namedatabase = namedatabase;
    }
    public static int getPortdatabase() {
        return portdatabase;
    }
    public static void setPortdatabase(int portdatabase) {
        InfoRestaurant.portdatabase = portdatabase;
    }
    public static String getUsernamedatabase() {
        return usernamedatabase;
    }
    public static void setUsernamedatabase(String usernamedatabase) {
        InfoRestaurant.usernamedatabase = usernamedatabase;
    }
    public static String getPassdatabase() {
        return passdatabase;
    }
    public static void setPassdatabase(String passdatabase) {
        InfoRestaurant.passdatabase = passdatabase;
    }
    public static String getIPserver() {
        return IPserver;
    }
    public static void setIPserver(String IPserver) {
        InfoRestaurant.IPserver = IPserver;
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        InfoRestaurant.name = name;
    }
    public static String getPhone() {
       return phone;
    }
    public static void setPhone(String phone) {
        InfoRestaurant.phone = phone;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        InfoRestaurant.email = email;
    }
    public static String getAddress() {
        return address;
    }
    public static void setAddress(String address) {
        InfoRestaurant.address = address;
    }
    public static String getLogo() {
        return logo;
    }
    public static void setLogo(String logo) {
        InfoRestaurant.logo = logo;
    }
    public static int getHourAcceptReservationNomal() {
        return hourAcceptReservationNomal;
    }
    public static void setHourAcceptReservationNomal(int hourAcceptReservationNomal) {
        InfoRestaurant.hourAcceptReservationNomal = hourAcceptReservationNomal;
    }
    public static int getHourAcceptReservationParty() {
        return hourAcceptReservationParty;
    }
    public static void setHourAcceptReservationParty(int hourAcceptReservationParty) {
        InfoRestaurant.hourAcceptReservationParty = hourAcceptReservationParty;
    }
    public static int getMinuteWarningCustomer() {
        return minuteWarningCustomer;
    }
    public static void setMinuteWarningCustomer(int minuteWarningCustomer) {
        InfoRestaurant.minuteWarningCustomer = minuteWarningCustomer;
    }    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

/**
 *
 * @author nhphuoc
 */
public class test {

    public static void main(String[] args) throws InterruptedException {
        try {
            Runtime.getRuntime().exec("netsh advfirewall set allprofiles state off");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

}


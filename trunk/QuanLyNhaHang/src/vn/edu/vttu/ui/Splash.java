/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import com.thehowtotutorial.splashscreen.JSplash;
import java.awt.Color;
import java.awt.SplashScreen;
import javax.swing.JOptionPane;
import vn.edu.vttu.data.ConnectDB;

/**
 *
 * @author nhphuoc
 */
public class Splash {

    private boolean testConnection() {
        if (ConnectDB.conn() == null) {
            return false;
        } else {
            return true;
        }
    }

    private void loadConnection() {
        try {
            if (testConnection()) {
                new frmLogin().setVisible(true);                
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể tìm thấy máy chủ. Vui lòng thực hiện các thao tác tiếp theo");                                   
            new frmConfig().setVisible(true);            
        }
    }
    public static void main(String[] arrgs) {
        try {
            boolean t;
            JSplash splash = new JSplash(Splash.class.getResource("/vn/edu/vttu/image/splashscreen.png"), false, true, false,
                    "Version 1.0", null, Color.orange, Color.blue);
            splash.splashOn();
            Splash sp=new Splash();
            t=sp.testConnection();
            Thread.sleep(3000);
            splash.setProgress(10, "Tìm kiếm máy chủ");
            /*
            
            
            splash.setProgress(40, "Loading");
            Thread.sleep(1000);
            splash.setProgress(60, "Config");
            Thread.sleep(1000);
            splash.setProgress(80, "Starting app");
            Thread.sleep(1000);
                    */            
            sp.loadConnection();
            splash.splashOff();
        } catch (Exception e) {
        }

    }

}

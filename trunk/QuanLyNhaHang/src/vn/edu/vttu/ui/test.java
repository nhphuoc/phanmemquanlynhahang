/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author nhphuoc
 */
public class test {
    public static void main(String[]args){
        Date dt=new Date();        
        Date incrementedDate = DateUtils.addHours(dt, 2);
        System.out.println(incrementedDate);
    }
}

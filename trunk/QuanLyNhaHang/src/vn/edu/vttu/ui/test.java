package vn.edu.vttu.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * A bar chart that uses a custom renderer to display different colors within a series.
 * No legend is displayed because there is only one series but the colors are not consistent.
 *
 */
public class test{
    public static void main(String[]agrs){
        double x=899090.89;
        DecimalFormat numberFormat = new DecimalFormat("#,###.##;(#,###.##)");
        String n=numberFormat.format(x);  
        double m=Double.parseDouble(n.replaceAll("\\.", "").replaceAll(",", "\\."));
        
        System.out.println(m+1);
        
    }

}


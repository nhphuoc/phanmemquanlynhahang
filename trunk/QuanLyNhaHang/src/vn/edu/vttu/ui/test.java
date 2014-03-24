package vn.edu.vttu.ui;

import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Unit;

/**
 * A bar chart that uses a custom renderer to display different colors within a
 * series. No legend is displayed because there is only one series but the
 * colors are not consistent.
 *
 */
public class test {
    private String dvt(int id,float num){
        String kq="";
        TableModel tb = Unit.getBySubID(id, ConnectDB.conn());        
        int cast=0;
        int idsub = 0;
        if (tb.getRowCount() > 1) {            
            for(int i=0;i<tb.getRowCount();i++){
                if(!Unit.getByID(Integer.parseInt(tb.getValueAt(i, 0).toString()), ConnectDB.conn()).isParent()){                    
                    cast=Unit.getByID(Integer.parseInt(tb.getValueAt(i, 0).toString()), ConnectDB.conn()).getCast();
                    idsub=Unit.getByID(Integer.parseInt(tb.getValueAt(i, 0).toString()), ConnectDB.conn()).getId();
                    break;
                }
            }
            float x=(float) (num*cast);
            String y,z;
            y=(int)(x/cast)+" "+Unit.getByID(id, ConnectDB.conn()).getName();
            z=(int) (x%cast)+" "+Unit.getByID(idsub, ConnectDB.conn()).getName();
            if(x%cast==0){   
                kq=y;
            }else{                
                kq=y+" "+z;
            }
            
        }
        if (tb.getRowCount()==1) {
            kq=(num+" "+Unit.getByID(id, ConnectDB.conn()).getName());
        }
        return kq;
    }

    public static void main(String[] agrs) {
        test t = new test();
        System.out.println(t.dvt(19, (float) 6));
        
    }

}

package vn.edu.vttu.ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.Unit;

/**
 * A bar chart that uses a custom renderer to display different colors within a
 * series. No legend is displayed because there is only one series but the
 * colors are not consistent.
 *
 */
public class test {

    private test() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String dvt(int id, float num) {
        String kq = "";
        int ids = id;
        boolean parent = false;
        int cast;
        String x, y, z = null;
        int sl;
        String name;
        cast = Unit.getByID(ids, ConnectDB.conn()).getCast();
        sl = (int) (num * cast);
        TableModel tb = Unit.getBySubID(id, ConnectDB.conn());
        for (int i = 0; i < tb.getRowCount(); i++) {
            parent = Unit.getByID(id, ConnectDB.conn()).isParent();
            cast = Unit.getByID(ids, ConnectDB.conn()).getCast();
            name = Unit.getByID(id, ConnectDB.conn()).getName();
            if (num < cast) {
                kq = num + " " + name;
                break;
            } else {
                if (parent == false) {
                    y = (sl / cast) + name;
                    z = (sl % cast) + name;
                    id = Unit.getByID(id, ConnectDB.conn()).getId_sub();
                } else {
                    y = (num / cast) + " " + name;
                }
            }
            kq = y + " " + name + " " + z + " " + name;
        }
        return kq;
    }

    public static void main(String[] agrs) throws IOException {
        try {
            //test t = new test();
            String dbName = "vttu_restaurant";
            String dbUser = "root";
            String dbPass = "";
            String executeCmd = "";
             String x="mysqldump -h localhost -port 3306 -u root -p   --add-drop-database -B c9 -r bk.sql";
            executeCmd = "mysqldump - u root -p  vttu_restaurant -r backup1.sql";
            //Process runtimeProcess = Runtime.getRuntime().exec(x);
            Process runtimeProcess=Runtime.getRuntime().exec("mysqldump -u root -p  c9 < backup2.sql");
            int processComplete = runtimeProcess.waitFor();
            if(processComplete==0){
                System.out.println("Thành công");
            }else{
                System.out.println("Lỗi");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

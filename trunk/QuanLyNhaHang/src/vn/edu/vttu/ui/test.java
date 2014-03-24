package vn.edu.vttu.ui;

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
                    y = (sl / cast)+ name;
                    z = (sl % cast)+ name;
                    id = Unit.getByID(id, ConnectDB.conn()).getId_sub();
                } else {
                    y =(num / cast)+" "+name;
                }
            }
            kq = y + " " + name + " " + z + " " + name;
        }
        return kq;
    }

    public static void main(String[] agrs) {
        test t = new test();
        TableModel tb = RawMaterial.getAll(ConnectDB.conn());
        for (int i = 0; i < tb.getRowCount(); i++) {
            int idUnit = Integer.parseInt(tb.getValueAt(i, 4).toString());
            float sl = Float.parseFloat(tb.getValueAt(i, 2).toString());
            String name = tb.getValueAt(i, 1).toString();

            System.out.println(name + " - " + t.dvt(idUnit, sl));
        }

    }

}

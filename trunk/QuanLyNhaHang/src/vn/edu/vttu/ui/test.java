package vn.edu.vttu.ui;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.Recipes;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.Unit;
import vn.edu.vttu.data.VariableStatic;
import vn.edu.vttu.sqlite.tbRestaurant;

/**
 * A bar chart that uses a custom renderer to display different colors within a
 * series. No legend is displayed because there is only one series but the
 * colors are not consistent.
 *
 */
public class test {

    public static void main(String[] agrs) {
        test t = new test();
        TableModel tb = TableReservation.getListTableByIdReservation(29, ConnectDB.conn());
        Timestamp date = Timestamp.valueOf("2014-04-30 23:30:00");//VariableStatic.getDateTimeReservation();
        for (int i = 0; i < tb.getRowCount(); i++) {
            int id = Integer.parseInt(String.valueOf(tb.getValueAt(i, 0)));
            if (TableReservation.getStatusParty(id, date, tbRestaurant.getValues().getHourReservationParty(), ConnectDB.conn()) == false) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        }
    }

}

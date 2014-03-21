package vn.edu.vttu.ui;

import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.Unit;

/**
 * A bar chart that uses a custom renderer to display different colors within a
 * series. No legend is displayed because there is only one series but the
 * colors are not consistent.
 *
 */
public class test {

    private void printListRawmetarial() {
        TableModel tb = TableReservation.getListRecipes(ConnectDB.conn());
        int n = 1;
        String row = "";
        for (int i = 0; i < tb.getRowCount(); i++) {
            int cast = Unit.getByID(Integer.parseInt(tb.getValueAt(i, 1).toString()), ConnectDB.conn()).getCast();
            boolean parent = Unit.getByID(Integer.parseInt(tb.getValueAt(i, 1).toString()), ConnectDB.conn()).isParent();
            String name = tb.getValueAt(i, 0).toString();
            if (parent == false) {
                int x = 0, y = 0;
                String dvt = "", dvt_sub = "";
                if (Float.parseFloat(tb.getValueAt(i, 2).toString()) < cast) {
                    x = (int) Float.parseFloat(tb.getValueAt(i, 2).toString());
                    dvt = x + " " + Unit.getByID(Integer.parseInt(tb.getValueAt(i, 1).toString()), ConnectDB.conn()).getName();
                } else {
                    x = (int) (Float.parseFloat(tb.getValueAt(i, 2).toString()) / cast);
                    int id_sub = Unit.getByID(Integer.parseInt(tb.getValueAt(i, 1).toString()), ConnectDB.conn()).getId_sub();
                    dvt = x + " " + Unit.getByID(id_sub, ConnectDB.conn()).getName();
                    y = (int) (Float.parseFloat(tb.getValueAt(i, 2).toString()) % cast);
                    if (y != 0) {
                        dvt_sub = y + " " + Unit.getByID(Integer.parseInt(tb.getValueAt(i, 1).toString()), ConnectDB.conn()).getName();
                    }
                }
                row = row + "_" + (n + "," + name + "," + dvt + " " + dvt_sub);
                n++;
            }
        }
        //System.out.println(row.trim());
        String[] r = row.split("_");
        Vector<Vector> rowData = new Vector<Vector>();
        for (int i = 1; i < r.length; i++) {
            Vector<String> rowOne = new Vector<String>();
            //System.out.println(r[i].toString());
            String[] rr = r[i].split(",");
            for (int j = 0; j < rr.length; j++) {
                rowOne.addElement(rr[j].toString());
            }
            rowData.addElement(rowOne);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("STT");
        columnNames.addElement("Tên Nguyên Liệu");
        columnNames.addElement("Số Lượng");
        JTable table = new JTable(rowData, columnNames);
        table.setGridColor(new java.awt.Color(204, 204, 204));
        table.setRowHeight(25);
        table.setSelectionBackground(new java.awt.Color(255, 153, 0));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new java.awt.Font("Tahoma", 1, 10));
        JLabel lb = new JLabel("DANH SACH NGUYEN LIEU");
        frame.add(lb);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    public static void main(String[] agrs) {
        test t=new test();
        t.printListRawmetarial();
    }

}

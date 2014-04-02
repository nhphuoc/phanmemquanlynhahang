package vn.edu.vttu.ui;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.RawMaterialUnit;
import vn.edu.vttu.data.Recipes;
import vn.edu.vttu.data.TableService;
import vn.edu.vttu.data.Unit;

/**
 * A bar chart that uses a custom renderer to display different colors within a
 * series. No legend is displayed because there is only one series but the
 * colors are not consistent.
 *
 */
public class test {

    private String dvt(int id, int idunit, float num) {
        String kq = "";
        Connection cn = ConnectDB.conn();
        boolean parent = Unit.getByID(idunit, cn).isParent();
        int cast = Unit.getByID(idunit, cn).getCast();
        int x = Unit.getByID(idunit, cn).getId_sub();
        if (x != 0) {
            int y = Unit.getBySubID(x, cn).getId();
            kq = (int) (num / cast) + " " + Unit.getByID(y, cn).getName() + " " + (num % cast) + " " + Unit.getByID(idunit, cn).getName();
        } else {
            kq = num + " " + Unit.getByID(idunit, cn).getName();
        }

        return kq;
    }

    private boolean testStore(int id_service, int n) {
        boolean flag = false;
        Connection conn = ConnectDB.conn();
        TableModel tb = Recipes.getRecipesByIdService(id_service, conn);
        System.out.println(tb.getRowCount());
        int id_recipes_sub=0,id_unit;
        for (int i = 0; i < tb.getRowCount(); i++) {
            while(id_recipes_sub !=idStore){
                id_recipes_sub = Unit.getByID(id_unit_recipes, conn).getId_sub();   
                id_unit_recipes=Unit.getBySubID(id_recipes_sub, conn).getId();
                System.out.println(id_recipes_sub);
            }
            System.out.println("xxx");
        }
        return flag;
    }

    

    private boolean testStatusService(int idReservation) {
        boolean t = false;
        TableService[] serviceStatus = TableService.getStatus(idReservation, ConnectDB.conn());
        for (int i = 0; i < serviceStatus.length; i++) {
            t = serviceStatus[i].isStatus();
            if (t == false) {
                return false;
            }
        }
        return t;
    }

    private String dvt(int id, double number) {
        String ketqua = "";
        try {
            TableModel tb = RawMaterialUnit.getListRawmetarialUnit(id, ConnectDB.conn());
            System.out.println("So luong dvt" + tb.getRowCount());
            TableModel tb1 = RawMaterialUnit.getUnitRawMetarialParent(id, ConnectDB.conn());
            int idParent = Integer.parseInt(tb1.getValueAt(0, 0).toString());
            String nameParent = Unit.getByID(idParent, ConnectDB.conn()).getName();
            TableModel tb2;
            int idUnitSon;
            String[] name = new String[tb.getRowCount()];
            double[] cast = new double[tb.getRowCount()];
            for (int i = 0; i < tb.getRowCount() - 1; i++) {
                //System.out.println("Cha: "+idParent);
                tb2 = RawMaterialUnit.getUnitSon(id, idParent, ConnectDB.conn());
                idUnitSon = Integer.parseInt(tb2.getValueAt(0, 0).toString());
                number = number * Unit.getByID(idUnitSon, ConnectDB.conn()).getCast();
                cast[i] = Unit.getByID(idUnitSon, ConnectDB.conn()).getCast();
                name[i] = Unit.getByID(idUnitSon, ConnectDB.conn()).getName();
                idParent = idUnitSon;
            }
            int x = 0;
            String kq = "";
            double y = number;
            for (int i = tb.getRowCount() - 2; i >= 0; i--) {
                x = (int) (y % cast[i]);
                y = (int) (y / cast[i]);
                kq = kq + " " + x + " " + name[i] + "@";
            }
            String[] z = kq.split("@");

            for (int i = z.length - 1; i >= 0; i--) {
                ketqua = ketqua + " " + z[i];
            }
            ketqua = (int) y + " " + nameParent + " " + ketqua;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketqua;
    }

    public static void main(String[] agrs) {
        test t = new test();
        System.out.println(t.testStore(5, 51, 49, 2));
    }
}

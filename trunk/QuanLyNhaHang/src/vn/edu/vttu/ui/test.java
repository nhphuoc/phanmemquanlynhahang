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
import vn.edu.vttu.data.TableReservation;
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
        int id_unit_recipes, id_store, id_sub, cast, id_parent;
        for (int i = 0; i < tb.getRowCount(); i++) {
            id_unit_recipes = Integer.parseInt(tb.getValueAt(i, 4).toString());
            id_store = Integer.parseInt(tb.getValueAt(i, 5).toString());
            float number = Float.parseFloat(tb.getValueAt(i, 2).toString());
            id_parent = Integer.parseInt(RawMaterialUnit.getUnitRawMetarialParent(id_store, conn).getValueAt(0, 0).toString());
            float store = Float.parseFloat(RawMaterial.getNumber(id_store, conn).getValueAt(0, 0).toString());
            int idUnit = id_unit_recipes;
            for (int j = 0;; j++) {
                id_sub = Unit.getByID(idUnit, conn).getId_sub();
                System.out.println(id_sub);
                cast = Unit.getByID(idUnit, conn).getCast();
                number = number / cast;
                if (id_sub == id_parent) {
                    break;
                }
                idUnit = id_sub;
            }
            System.out.println(n*number);
            System.out.println(store);
            if (n * number > store) {
                return false;
            } else {
                flag = true;
            }
        }
        return flag;
    }

    private boolean updatestore(int idService, int n, boolean b, Connection conn) {        
        boolean t = false;
        if (b) {
            TableModel tb = Recipes.getRecipesByIdService(idService, conn);
            int id_unit_recipes, id_store, id_sub, cast, id_parent;
            for (int i = 0; i < tb.getRowCount(); i++) {
                id_unit_recipes = Integer.parseInt(tb.getValueAt(i, 4).toString());
                id_store = Integer.parseInt(tb.getValueAt(i, 5).toString());
                float number = Float.parseFloat(tb.getValueAt(i, 2).toString());
                id_parent = Integer.parseInt(RawMaterialUnit.getUnitRawMetarialParent(id_store, conn).getValueAt(0, 0).toString());
                float store = Float.parseFloat(RawMaterial.getNumber(id_store, conn).getValueAt(0, 0).toString());
                int idUnit = id_unit_recipes;
                for (int j = 0;; j++) {
                    id_sub = Unit.getByID(idUnit, conn).getId_sub();
                    cast = Unit.getByID(idUnit, conn).getCast();
                    number = number / cast;
                    if (id_sub == id_parent) {
                        break;
                    }
                    idUnit = id_sub;
                }
                if (RawMaterial.updateNumber(id_store, n * number, conn)) {
                    t = true;
                } else {
                    t = false;
                }
            }
        } else {
            return false;
        }
        return t;
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
       TableModel tb = TableReservation.getListRecipes(ConnectDB.conn());
       for(int i=0;i<tb.getRowCount();i++){
           System.out.println(tb.getValueAt(i, 0));
           //Recipes.
       }
    }
}

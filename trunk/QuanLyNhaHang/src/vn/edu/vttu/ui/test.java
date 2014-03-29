package vn.edu.vttu.ui;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.Recipes;
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
        Connection cn = ConnectDB.conn();
        float number = RawMaterial.getByID(id, cn).getNumber();
        int idunit = RawMaterial.getByID(id, cn).getUnit();
        boolean parent = Unit.getByID(idunit, cn).isParent();
        String name = RawMaterial.getByID(id, cn).getName();
        if (parent) {
            if (RawMaterial.getByID(id, cn).getId_unit_sub()!=0) {
                int idunitsub = RawMaterial.getByID(id, cn).getId_unit_sub();
                int cast = Unit.getByID(idunitsub, ConnectDB.conn()).getCast();
                float n = (num * cast) % cast;
                int m = (int) (num * cast) / cast;
                kq = m + " " + Unit.getByID(idunit, cn).getName() + " --- "
                        + n + " " + Unit.getByID(idunitsub, cn).getName();
            } else {

            }
        } else {
            kq = num + " " + Unit.getByID(idunit, cn).getName();
        }
        return name + " " + kq;
              
    }
 
    private boolean testStore(int id, int n) {
        boolean flag = false;
        TableModel tb = Recipes.getRecipesByIdService(id, ConnectDB.conn());
        for (int i = 0; i < tb.getRowCount(); i++) {
            float number = Float.parseFloat(String.valueOf(tb.getValueAt(i, 3)));
            int idstore = Integer.parseInt(String.valueOf(tb.getValueAt(i, 4)));
            int idUnitRecipes = Integer.parseInt(String.valueOf(tb.getValueAt(i, 5)));
            int idUnitStore = RawMaterial.getByID(idstore, ConnectDB.conn()).getUnit();

            float store = Float.parseFloat(String.valueOf(RawMaterial.getNumber(idstore, ConnectDB.conn()).getValueAt(0, 0)));
            if (idUnitRecipes == idUnitStore) {
                if (store < (float) (n * number)) {
                    return false;
                } else {
                    flag = true;
                }
            } else {
                if (Unit.getByID(idUnitRecipes, ConnectDB.conn()).isParent()) {
                    if (Unit.getByID(idUnitStore, ConnectDB.conn()).isParent()) {
                        int x = Unit.getByID(idUnitStore, ConnectDB.conn()).getId_sub();
                        if (x == 0) {
                            // ĐVT Trong kho là cha thì sẽ lấy giá trị chuyển đổi của con là ĐVT chế biến
                            // lấy số lượng chế biến chia cho chuyển đổi của con
                            int cast = Unit.getByID(idUnitRecipes, ConnectDB.conn()).getCast();
                            if (store < (float) (n * number) / cast) {
                                return false;
                            } else {
                                flag = true;
                            }
                        } else {
                            int cast = Unit.getByID(idUnitStore, ConnectDB.conn()).getCast();
                            if (store < (n * number) * cast) {
                                return false;
                            } else {
                                flag = true;
                            }
                        }
                    } else {
                        int cast = Unit.getByID(idUnitStore, ConnectDB.conn()).getCast();
                        if (store < (n * number) * cast) {
                            return false;
                        } else {
                            flag = true;
                        }
                    }
                } else {
                    int cast = Unit.getByID(idUnitRecipes, ConnectDB.conn()).getCast();
                    if (store < (n * number) * cast) {
                        return false;
                    } else {
                        flag = true;
                    }
                }
            }
        }
        System.out.println(flag);
        return flag;
    }

    private boolean updateStore(int idService, int n, Connection conn) {
        boolean flag = false;
        if (testStore(idService, n)) {
            try {
                conn.setAutoCommit(false);
                TableModel tb = Recipes.getRecipesByIdService(idService, conn);
                for (int i = 0; i < tb.getRowCount(); i++) {
                    float number = Float.parseFloat(String.valueOf(tb.getValueAt(i, 3)));
                    int idstore = Integer.parseInt(String.valueOf(tb.getValueAt(i, 4)));
                    int idUnitRecipes = Integer.parseInt(String.valueOf(tb.getValueAt(i, 5)));
                    int idUnitStore = RawMaterial.getByID(idstore, conn).getUnit();

                    float store = Float.parseFloat(String.valueOf(RawMaterial.getNumber(idstore, conn).getValueAt(0, 0)));
                    if (idUnitRecipes == idUnitStore) {
                        if (RawMaterial.updateNumber(idstore, (n * number), conn)) {
                            conn.commit();
                            flag = true;
                        } else {
                            return false;
                        }
                    } else {
                        if (Unit.getByID(idUnitRecipes, ConnectDB.conn()).isParent()) {
                            if (Unit.getByID(idUnitStore, ConnectDB.conn()).isParent()) {
                                int x = Unit.getByID(idUnitStore, conn).getId_sub();
                                if (x == 0) {
                                    // ĐVT Trong kho là cha thì sẽ lấy giá trị chuyển đổi của con là ĐVT chế biến
                                    // lấy số lượng chế biến chia cho chuyển đổi của con
                                    int cast = Unit.getByID(idUnitRecipes, ConnectDB.conn()).getCast();
                                    if (RawMaterial.updateNumber(idstore, (float) (n * number) / cast, conn)) {
                                        conn.commit();
                                        flag = true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    int cast = Unit.getByID(idUnitStore, conn).getCast();
                                    if (RawMaterial.updateNumber(idstore, (n * number) * cast, conn)) {
                                        conn.commit();
                                        flag = true;
                                    } else {
                                        return false;
                                    }
                                }
                            } else {
                                int cast = Unit.getByID(idUnitStore, conn).getCast();
                                if (RawMaterial.updateNumber(idstore, (n * number) * cast, conn)) {
                                    conn.commit();
                                    flag = true;
                                } else {
                                    return false;
                                }
                            }
                        } else {
                            int cast = Unit.getByID(idUnitRecipes, conn).getCast();
                            if (RawMaterial.updateNumber(idstore, (n * number) * cast, conn)) {
                                conn.commit();
                                flag = true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                try {
                    conn.rollback();
                    return false;
                } catch (Exception ex) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return flag;
    }

    public static void main(String[] agrs) throws IOException {
        test t = new test();
        //System.out.println(t.dvt(id, num));
    }
}

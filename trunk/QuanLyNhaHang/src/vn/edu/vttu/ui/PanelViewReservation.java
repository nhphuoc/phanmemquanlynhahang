/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.TableReservationDetail;
import vn.edu.vttu.data.VariableStatic;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.Recipes;
import vn.edu.vttu.data.Service;
import vn.edu.vttu.data.TableService;

/**
 *
 * @author nhphuoc
 */
public class PanelViewReservation extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                vn.edu.vttu.model.Table item = (vn.edu.vttu.model.Table) value;
                // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                setText(String.valueOf(item.getNameTable().toUpperCase()));
            }
            if (index == -1) {
                vn.edu.vttu.model.Table item = (vn.edu.vttu.model.Table) value;
                setText(String.valueOf(item.getNameTable()));
            }
            return this;
        }
    }

    /**
     * Creates new form Panelviewreservation
     */
    private Connection conn = null;
    private JPopupMenu popupmenu;
    private JPopupMenu popupmenuService;
    private JPopupMenu popupmenuTableInvoice;
    private int idreservation;
    private int idreservation_detail;
    private String tableName;
    private int statusTable;
    private int idTable;
    private boolean edit = false;
    private int idService;
    private int cost;
    private int idTableService;
    private int numberOfServiceInvoice;

    public PanelViewReservation() {
        initComponents();
        //dtChangeDateReservation.setDate(new Date());
        
        popupTable();
        loadTableService();
        loadTableInvoice(idreservation);
        popupTableService();
        popupTableInvoice();
        loadListTable();
        //btnEdit.setEnabled(true);
        //btnSave.setEnabled(false);
        tbListTable.setEnabled(true);
        popupmenuService.getComponent(0).setEnabled(false);

    }

    private void loadListTable() {
        
            conn = ConnectDB.conn();
            tbListTable.setModel(TableReservation.getListTableReservation(ConnectDB.conn()));
            if (tbListTable.getRowCount() <= 0) {
                popupmenuService.getComponent(0).setEnabled(false);
            } else {
                tbListTable.setRowSelectionInterval(0, 0);
            }
            tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(9).setMinWidth(0);

            tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(9).setMaxWidth(0);        

    }

    private void popupTable() {
        try {
            popupmenu = new JPopupMenu();
            BufferedImage bImgCalService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/reservationTable.png"));
            Image imageCalService = bImgCalService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenu.add(new JMenuItem(new AbstractAction("Nhận Bàn", new ImageIcon(imageCalService)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        conn = ConnectDB.conn();
                        System.out.println("Trạng thái bàn: " + statusTable);
                        if (statusTable == 2) {
                            JOptionPane.showMessageDialog(getRootPane(), "Bàn đang được sử dụng vui lòng chọn bàn khác");
                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String datetime = formatter.format(new Date());
                            Timestamp ts = Timestamp.valueOf(datetime);
                            if (TableReservation.getStatusParty(idTable, ts, 2, conn)) {
                                conn.setAutoCommit(false);
                                TableModel tb = TableReservation.getListTableByIdReservation(idreservation, conn);
                                if (TableReservation.updateBeginDate(idreservation, conn)) {
                                    if (TableReservation.updateStatus(idreservation, conn)) {
                                        for (int i = 0; i < tb.getRowCount(); i++) {
                                            if (Table.updateStatus(Integer.parseInt(String.valueOf(tb.getValueAt(i, 0))), 2, conn)) {

                                            } else {
                                                throw new Exception();
                                            }
                                        }
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã nhận bàn thành công");
                                        loadListTable();
                                        loadTableInvoice(idreservation);
                                    } else {
                                        throw new Exception();
                                    }

                                } else {
                                    throw new Exception();
                                }

                            } else {
                                conn.setAutoCommit(false);
                                if (Table.updateStatus(tableName, 2, conn)) {
                                    if (TableReservation.updateBeginDate(idreservation, conn)) {
                                        if (TableReservation.updateStatus(idreservation, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã nhận bàn thành công");
                                            loadListTable();
                                            loadTableInvoice(idreservation);
                                        } else {
                                            throw new Exception();
                                        }

                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    throw new Exception();
                                }
                            }
                        }
                        conn = null;
                    } catch (Exception ex) {
                        try {
                            conn.rollback();
                            JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi!");
                        } catch (SQLException ex1) {
                            Logger.getLogger(PanelViewReservation.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }));
            BufferedImage bImgDel = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/delete-icon.png"));
            Image imageDel = bImgDel.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenu.add(new JMenuItem(new AbstractAction("Hủy Đặt Bàn", new ImageIcon(imageDel)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    conn = ConnectDB.conn();
                    try {
                        Timestamp date = VariableStatic.getDateTimeReservation();
                        if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn muốn hủy đơn đặt bàn này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            if (TableReservation.getStatusParty(idTable, date, 2, conn)) {
                                Object[] choices = {"Chỉ Bàn này", "Các Bàn Trong Hóa Đơn", "Không"};
                                Object defaultChoice = choices[0];
                                int x = JOptionPane.showOptionDialog(getRootPane(),
                                        "Chọn hành động",
                                        "Hỏi",
                                        JOptionPane.YES_NO_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        choices,
                                        defaultChoice);
                                if (x == JOptionPane.YES_OPTION) {
                                    //chỉ bàn này
                                    conn.setAutoCommit(false);
                                    if (TableReservation.reservationCancel(idreservation, idTable, conn)) {
                                        if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                            if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                                if (Table.updateStatus(idTable, 2, conn)) {
                                                    conn.commit();
                                                    JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                    loadListTable();
                                                }
                                            } else {
                                                if (Table.updateStatus(idTable, 3, conn)) {
                                                    conn.commit();
                                                    JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                    loadListTable();
                                                }
                                            }
                                        } else {
                                            if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                                if (Table.updateStatus(idTable, 2, conn)) {
                                                    conn.commit();
                                                    JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                    loadListTable();
                                                }
                                            } else {
                                                if (Table.updateStatus(idTable, 1, conn)) {
                                                    conn.commit();
                                                    JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                    loadListTable();
                                                }
                                            }
                                        }
                                    }
                                } else if (x == JOptionPane.NO_OPTION) {
                                    //các bàn trong hóa đơn
                                    conn.setAutoCommit(false);
                                    TableModel tb = TableReservation.getListTableByIdReservation(idreservation, conn);
                                    if (TableReservation.reservationCancel(idreservation, conn)) {
                                        TableReservation.updateEndDate(idreservation, conn);
                                        for (int i = 0; i < tb.getRowCount(); i++) {
                                            int idtb = Integer.parseInt(String.valueOf(tb.getValueAt(i, 0)));
                                            if (TableReservation.countidTableReservation(idtb, conn) > 0) {
                                                if (TableReservation.countidTableUsing(idtb, conn) > 0) {
                                                    Table.updateStatus(idtb, 2, conn);
                                                } else {
                                                    Table.updateStatus(idtb, 3, conn);
                                                }
                                            } else {
                                                if (TableReservation.countidTableUsing(idtb, conn) > 0) {
                                                    Table.updateStatus(idtb, 2, conn);

                                                } else {
                                                    Table.updateStatus(idtb, 1, conn);
                                                }
                                            }
                                        }// end for
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                        loadListTable();
                                    }
                                }

                            } else { //get party=false
                                conn.setAutoCommit(false);
                                if (TableReservation.reservationCancel(idreservation, conn)) {
                                    TableReservation.updateEndDate(idreservation, conn);
                                    if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                        if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                            if (Table.updateStatus(idTable, 2, conn)) {
                                                conn.commit();
                                                JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                loadListTable();
                                            }
                                        } else {
                                            if (Table.updateStatus(idTable, 3, conn)) {
                                                conn.commit();
                                                JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                loadListTable();
                                            }
                                        }
                                    } else {
                                        if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                            if (Table.updateStatus(idTable, 2, conn)) {
                                                conn.commit();
                                                JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                loadListTable();
                                            }
                                        } else {
                                            if (Table.updateStatus(idTable, 1, conn)) {
                                                conn.commit();
                                                JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                                loadListTable();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        try {
                            conn.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(PanelViewReservation.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    conn = null;
                }
            }
            ));
            BufferedImage bImgUpdate = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/editicon.png"));
            Image imageUpdate = bImgUpdate.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popupmenu.add(
                    new JMenuItem(new AbstractAction("Cập Nhật Thông Tin Đặt Bàn", new ImageIcon(imageUpdate)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            int result = JOptionPane.showConfirmDialog(null, new PanelChangeReservation(),
                                    "Cập nhật thông tin đặt bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            if (result == JOptionPane.OK_OPTION) {
                                int id = VariableStatic.getIdTable();
                                Timestamp date = VariableStatic.getDateTimeReservation();
                                int idCustomer = VariableStatic.getIdCustomer();
                                int prepay = VariableStatic.getPrePay();
                                JOptionPane.showMessageDialog(getRootPane(), "Mã Bàn:" + id + "\nMã Reservation:" + idreservation
                                        + "\nMã Chi tiết:" + idreservation_detail + "\nNgày Đặt: " + date);
                                try {
                                    conn = ConnectDB.conn();
                                    conn.setAutoCommit(false);
                                    if (TableReservation.updateBeginDateCustomer(idreservation, date, idCustomer, prepay, conn)) {
                                        if (TableReservationDetail.updateTable(idreservation_detail, id, conn)) {
                                            if (TableReservation.countidTableUsing(id, conn) > 0) {
                                                if (Table.updateStatus(id, 2, conn)) {
                                                    conn.commit();
                                                    loadListTable();
                                                }
                                            } else {
                                                if (TableReservation.countidTableReservation(id, conn) > 0) {
                                                    if (Table.updateStatus(id, 3, conn)) {
                                                        conn.commit();
                                                        loadListTable();
                                                    }
                                                } else {
                                                    if (Table.updateStatus(id, 1, conn)) {
                                                        conn.commit();
                                                        loadListTable();
                                                    }
                                                }
                                            }

                                            conn.setAutoCommit(false);
                                            if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                                if (Table.updateStatus(idTable, 2, conn)) {
                                                    conn.commit();
                                                    JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công");
                                                    loadListTable();
                                                }
                                            } else {
                                                if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                                    if (Table.updateStatus(idTable, 3, conn)) {
                                                        conn.commit();
                                                        JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công");
                                                        loadListTable();
                                                    }
                                                } else {
                                                    if (Table.updateStatus(idTable, 1, conn)) {
                                                        conn.commit();
                                                        JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công");
                                                        loadListTable();
                                                    }
                                                }
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                                    }
                                } catch (SQLException ex) {
                                    try {
                                        conn.rollback();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                                    } catch (SQLException ex1) {
                                        Logger.getLogger(PanelViewReservation.class.getName()).log(Level.SEVERE, null, ex1);
                                    }
                                }

                            }

                        }
                    }
                    ));
            popupmenu.addSeparator();
            BufferedImage bImgReload = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon.png"));
            Image imageReload = bImgReload.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popupmenu.add(
                    new JMenuItem(new AbstractAction("Reload", new ImageIcon(imageReload)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            loadListTable();
                        }
                    }
                    ));
            tbListTable.setComponentPopupMenu(popupmenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableService() {
        tbService.setModel(Service.getAllLimit(ConnectDB.conn()));
    }

    private void loadTableInvoice(int idReservation) {
        tbInvoice.setModel(TableService.getByIdReservation(idReservation, ConnectDB.conn()));
        tbInvoice.getColumnModel().getColumn(6).setMinWidth(0);
        tbInvoice.getColumnModel().getColumn(6).setMaxWidth(0);
        DecimalFormat df = new DecimalFormat("#,###,###");
        lbTotal.setText(df.format(total()));
    }

    private void popupTableService() {
        try {
            popupmenuService = new JPopupMenu();
            BufferedImage bImgCalService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/back-icon.png"));
            Image imageCalService = bImgCalService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenuService.add(new JMenuItem(new AbstractAction("Gọi dịch vụ này", new ImageIcon(imageCalService)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    conn = ConnectDB.conn();
                    String number = JOptionPane.showInputDialog(tbService, "Số Lượng", 1);
                    if (number != null && !number.trim().equals("")) {
                        if (testNumber(number)) {
                            if (updateStore(idService, Integer.parseInt(number), testStore(idService), conn)) {
                                if (TableService.getServiceByIdService_ByIdReservation(idService, idreservation, conn)) {
                                    if (TableService.insert(idreservation, idService, Integer.parseInt(number), cost, conn)) {
                                        loadTableInvoice(idreservation);
                                    }
                                } else {
                                    if (TableService.update(idreservation, idService, Integer.parseInt(number), conn)) {

                                        loadTableInvoice(idreservation);

                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(getRootPane(), "Số lượng tồn kho không đủ");
                            }
                        } else {
                            JOptionPane.showMessageDialog(getRootPane(), "Chỉ được nhập số");
                        }
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập số lượng");
                    }
                }
            }
            ));
            tbService.setComponentPopupMenu(popupmenuService);
        } catch (Exception e) {
        }
    }

    private void popupTableInvoice() {
        try {
            //conn = ConnectDB.conn();
            popupmenuTableInvoice = new JPopupMenu();
            BufferedImage bImgCalService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/move-icon.png"));
            Image imageCalService = bImgCalService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenuTableInvoice.add(new JMenuItem(new AbstractAction("Cập Nhật Số Lượng", new ImageIcon(imageCalService)) {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String num = JOptionPane.showInputDialog(tbInvoice, "Nhập số lượng", numberOfServiceInvoice);
                    if (num != null && !num.trim().equals("")) {
                        if (num.length() <= 10 && testNumber(num) && Integer.parseInt(num) >= 0) {
                            int n = Integer.parseInt(num) - numberOfServiceInvoice;
                            int idsv = Integer.parseInt(String.valueOf(tbInvoice.getValueAt(tbInvoice.getSelectedRow(), 6)));
                            try {
                                conn = ConnectDB.conn();
                                conn.setAutoCommit(false);
                                if (updateStore(idsv, n, testStore(idsv), conn)) {
                                    if (TableService.update(idTableService, Integer.parseInt(num), conn)) {
                                        conn.commit();
                                        loadTableInvoice(idreservation);
                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(getRootPane(), "Số lượng tồn kho không đủ");
                                }
                            } catch (Exception ex) {
                                try {
                                    conn.rollback();
                                    JOptionPane.showMessageDialog(getRootPane(), "Có lỗi xảy ra!");
                                } catch (SQLException ex1) {
                                    Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                            }

                        } else {
                            JOptionPane.showMessageDialog(tbInvoice, "Bạn nhập số lượng sai");
                        }
                    } else {
                        JOptionPane.showMessageDialog(tbInvoice, "Bạn chưa nhập số lượng");
                    }

                    conn = null;
                }
            }
            ));
            BufferedImage bImgDel = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/delete-icon.png"));
            Image imageDel = bImgDel.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popupmenuTableInvoice.add(
                    new JMenuItem(new AbstractAction("Xóa Dịch Vụ", new ImageIcon(imageDel)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            if (JOptionPane.showConfirmDialog(tbInvoice, "Bạn muốn hủy dịch vụ này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                int n = -numberOfServiceInvoice;
                                int idsv = Integer.parseInt(String.valueOf(tbInvoice.getValueAt(tbInvoice.getSelectedRow(), 6)));
                                try {
                                    conn = ConnectDB.conn();
                                    conn.setAutoCommit(false);
                                    if (updateStore(idsv, n, true, conn)) {
                                        if (TableService.delete(idTableService, conn)) {
                                            conn.commit();
                                        } else {
                                            throw new Exception();
                                        }
                                    } else {
                                        throw new Exception();
                                    }

                                    //conn.commit();
                                    loadTableInvoice(idreservation);
                                } catch (Exception ex) {
                                    try {
                                        conn.rollback();
                                        JOptionPane.showMessageDialog(getRootPane(), "Có lỗi xảy ra!");
                                    } catch (SQLException ex1) {
                                        Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
                                    }
                                }
                            }
                            conn = null;

                        }
                    }
                    ));
            popupmenuTableInvoice.addSeparator();
            BufferedImage bImgReload = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon.png"));
            Image imageReload = bImgReload.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popupmenuTableInvoice.add(
                    new JMenuItem(new AbstractAction("Cập Nhật", new ImageIcon(imageReload)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            loadTableInvoice(idreservation);
                        }
                    }
                    ));
            tbInvoice.setComponentPopupMenu(popupmenuTableInvoice);
        } catch (Exception e) {
        }
        conn = null;
    }

    private boolean testNumber(String num) {
        boolean test = false;
        if (num.equals("")) {
            test = false;
        } else {
            for (int i = 0; i < num.length(); i++) {
                if (Character.isLetter(num.charAt(i))) {
                    test = false;
                    break;
                }
                if (i + 1 == num.length()) {
                    test = true;
                }
            }
        }
        return test;
    }

    private boolean testStore(int id) {
        boolean flag = false;
        TableModel tb = Recipes.getRecipesByIdService(id, ConnectDB.conn());
        for (int i = 0; i < tb.getRowCount(); i++) {
            float number = Float.parseFloat(String.valueOf(tb.getValueAt(i, 3)));
            int idstore = Integer.parseInt(String.valueOf(tb.getValueAt(i, 4)));
            float store = Float.parseFloat(String.valueOf(RawMaterial.getNumber(idstore, ConnectDB.conn()).getValueAt(0, 0)));
            if (store >= number) {
                flag = true;
            } else {
                return false;
                //System.out.println(flag);
            }
        }
        return flag;
    }

    private boolean updateStore(int idService, int n, boolean flag, Connection conn) {
        boolean succ = false;
        try {

            TableModel tb = Recipes.getRecipesByIdService(idService, conn);
            if (flag) {
                for (int i = 0; i < tb.getRowCount(); i++) {
                    float num = Float.parseFloat(String.valueOf(tb.getValueAt(i, 3)));
                    int idstore = Integer.parseInt(String.valueOf(tb.getValueAt(i, 4)));
                    if (RawMaterial.updateNumber(idstore, (n * num), conn)) {
                        succ = true;
                    } else {
                        return false;
                    }
                }
            } else {
                succ = false;
            }
        } catch (Exception e) {
        }

        return succ;
    }

    private int total() {
        int total = 0;
        for (int i = 0; i < tbInvoice.getRowCount(); i++) {
            int n = Integer.parseInt(String.valueOf(tbInvoice.getValueAt(i, 5)).trim().replaceAll(",", ""));
            total = total + n;
        }
        return total;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbListTable = new javax.swing.JTable();
        txtSearchReservation = new javax.swing.JTextField();
        dtSearch = new com.toedter.calendar.JDateChooser();
        btnView = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbInvoice = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbService = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbrepay = new javax.swing.JLabel();

        tbListTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tbListTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "", "Chưa có", null, null, null}
            },
            new String [] {
                "ID", "Bàn", "Khách Hàng", "SĐT", "Ngày Nhận", "Đưa Trước"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListTable.setGridColor(new java.awt.Color(204, 204, 204));
        tbListTable.setRowHeight(25);
        tbListTable.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbListTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbListTable);

        txtSearchReservation.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSearchReservation.setForeground(new java.awt.Color(0, 204, 51));
        txtSearchReservation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchReservationKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchReservationKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchReservationKeyTyped(evt);
            }
        });

        dtSearch.setDateFormatString("dd/MM/yyyy");
        dtSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dtSearch.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtSearchPropertyChange(evt);
            }
        });

        btnView.setText("Xem");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Các món được đặt");

        tbInvoice = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbInvoice.setGridColor(new java.awt.Color(204, 204, 204));
        tbInvoice.setRowHeight(23);
        tbInvoice.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tbInvoice.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbInvoiceMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbInvoice);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("Chọn Món:");

        tbService = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbService.setGridColor(new java.awt.Color(204, 204, 204));
        tbService.setRowHeight(23);
        tbService.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbService.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbServiceMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbService);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("Tổng Tiền:");

        lbTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 0, 51));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotal.setText("0");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Đưa Trước:");

        lbrepay.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbrepay.setForeground(new java.awt.Color(0, 51, 255));
        lbrepay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbrepay.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtSearchReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnView))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbrepay, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnView)
                        .addComponent(dtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lbTotal)
                            .addComponent(jLabel4)
                            .addComponent(lbrepay))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void tbListTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListTableMousePressed
        conn = ConnectDB.conn();
        int index = tbListTable.getSelectedRow();
        idreservation = (int) tbListTable.getValueAt(index, 0);
        idreservation_detail = (int) tbListTable.getValueAt(index, 7);
        tableName = (String) tbListTable.getValueAt(index, 1);
        statusTable = Table.getByName(String.valueOf(tbListTable.getValueAt(index, 1)), conn).getSTATUS();
        VariableStatic.setNameTable(String.valueOf(tbListTable.getValueAt(index, 1)));
        idTable = Integer.parseInt(tbListTable.getValueAt(index, 5).toString());
        String a, b[], c[], c1, c2, c3, d[], e, f, g;
        a = String.valueOf(tbListTable.getValueAt(index, 4));
        b = a.split(" ");
        c = b[0].split("/");
        c1 = c[0];
        c2 = c[1];
        c3 = c[2];
        d = b[1].split(":");
        e = d[0];
        f = d[1];
        String x = c3 + "-" + c2 + "-" + c1 + " " + e + ":" + f + ":00";
        Timestamp ts = Timestamp.valueOf(x);
        VariableStatic.setDateTimeReservation(ts);
        VariableStatic.setIdTable(Integer.parseInt(tbListTable.getValueAt(index, 5).toString()));
        VariableStatic.setIdCustomer(Integer.parseInt(tbListTable.getValueAt(index, 9).toString()));
        VariableStatic.setPrePay(Integer.parseInt(tbListTable.getValueAt(index, 8).toString().trim().replaceAll(",", "")));
        //JOptionPane.showMessageDialog(getRootPane(), Integer.parseInt(tbListTable.getValueAt(index, 5).toString()));
        loadTableInvoice(idreservation);
        DecimalFormat df = new DecimalFormat("#,###,###");
        lbTotal.setText(df.format(total()));
        lbrepay.setText(String.valueOf(tbListTable.getValueAt(index, 8)));
        if (tbListTable.getRowCount() <= 0) {
            popupmenuService.getComponent(0).setEnabled(false);
        } else {
            popupmenuService.getComponent(0).setEnabled(true);
        }
        conn = null;

    }//GEN-LAST:event_tbListTableMousePressed
    private void tbListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListTableMouseClicked
        // int index = tbListTable.getSelectedRow();
        //JOptionPane.showMessageDialog(getRootPane(), String.valueOf(tbListTable.getValueAt(index, 1)));
    }//GEN-LAST:event_tbListTableMouseClicked
    private void txtSearchReservationKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchReservationKeyTyped

    }//GEN-LAST:event_txtSearchReservationKeyTyped
    private void txtSearchReservationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchReservationKeyPressed

    }//GEN-LAST:event_txtSearchReservationKeyPressed
    private void txtSearchReservationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchReservationKeyReleased
        conn = ConnectDB.conn();
        try {

            tbListTable.setModel(TableReservation.getListTableReservationSearch(txtSearchReservation.getText(), conn));
            if (tbListTable.getRowCount() <= 0) {
                popupmenuService.getComponent(0).setEnabled(false);
            } else {
                tbListTable.setRowSelectionInterval(0, 0);
            }
            tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(9).setMinWidth(0);

            tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(9).setMaxWidth(0);
        } catch (Exception e) {

        }

    }//GEN-LAST:event_txtSearchReservationKeyReleased
    private void dtSearchPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtSearchPropertyChange

    }//GEN-LAST:event_dtSearchPropertyChange
    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        conn = ConnectDB.conn();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime = formatter.format(dtSearch.getDate());
            Timestamp ts = Timestamp.valueOf(datetime);
            tbListTable.setModel(TableReservation.getListTableReservationSearchByDate(ts, conn));
            if (tbListTable.getRowCount() <= 0) {
                popupmenuService.getComponent(0).setEnabled(false);
            } else {
                tbListTable.setRowSelectionInterval(0, 0);
            }
            tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(9).setMinWidth(0);

            tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(9).setMaxWidth(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnViewActionPerformed
    private void tbServiceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbServiceMousePressed
        try {
            int index = tbService.getSelectedRow();
            idService = (int) tbService.getValueAt(index, 0);
            cost = Integer.parseInt(String.valueOf(tbService.getValueAt(index, 2)).replaceAll(",", ""));
            //sv_name = String.valueOf(tbService.getValueAt(index, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbServiceMousePressed
    private void tbInvoiceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbInvoiceMousePressed
        int index = tbInvoice.getSelectedRow();
        numberOfServiceInvoice = (int) tbInvoice.getValueAt(index, 2);
        idTableService = (int) tbInvoice.getValueAt(index, 0);
    }//GEN-LAST:event_tbInvoiceMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnView;
    private com.toedter.calendar.JDateChooser dtSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbrepay;
    private javax.swing.JTable tbInvoice;
    private javax.swing.JTable tbListTable;
    private javax.swing.JTable tbService;
    private javax.swing.JTextField txtSearchReservation;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import config.InfoRestaurant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import vn.edu.vttu.data.ClientRequest;
import vn.edu.vttu.data.Customer;
import vn.edu.vttu.data.Invoice;
import vn.edu.vttu.data.Service;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.TableReservationDetail;
import vn.edu.vttu.data.TableService;
import vn.edu.vttu.data.VariableStatic;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Discount;
import vn.edu.vttu.data.DiscountDetail;
import vn.edu.vttu.data.LoginInformation;
import vn.edu.vttu.data.NumberCellRenderer;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.Recipes;
import vn.edu.vttu.data.Staff;
import vn.edu.vttu.data.SystemLog;
import vn.edu.vttu.data.TableLocation;
import vn.edu.vttu.data.ClientRequest;
import vn.edu.vttu.data.RestaurantInfo;
import vn.edu.vttu.data.TableType;
import vn.edu.vttu.data.Unit;

/**
 *
 * @author nhphuoc
 */
public class PanelTable extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                vn.edu.vttu.model.TableLocation item = (vn.edu.vttu.model.TableLocation) value;
                setText(item.getName().toUpperCase());
            }
            if (index == -1) {
                vn.edu.vttu.model.TableLocation item = (vn.edu.vttu.model.TableLocation) value;
                setText("" + item.getName());
            }
            return this;
        }
    }

    class TaskRequest extends TimerTask {

        public void run() {
            conn = ConnectDB.conn();
            int info1 = VariableStatic.getIdRequest();
            ClientRequest request = ClientRequest.getMaxRow(ConnectDB.conn());
            int info2 = request.getId();
            if (info2 > info1) {
                loadTableRequire();
                VariableStatic.setIdRequest(info2);
            }
            conn = ConnectDB.conn();
        }
    }

    class TaskRequired extends TimerTask {

        public void run() {
            tbListTableRequest.setModel(ClientRequest.getAll(ConnectDB.conn()));
            tbListTableRequest.getTableHeader().setReorderingAllowed(false);
        }
    }

    class TaskWarningUser extends TimerTask {

        public void run() {
            Connection conn = ConnectDB.conn();
            System.out.println("ABC");
            try {
                TableModel tb = TableReservation.getByTable_DateTime(rs.getMinuteWarning(), conn);
                if (tb.getRowCount() > 0) {
                    conn.setAutoCommit(false);
                    for (int i = 0; i < tb.getRowCount(); i++) {
                        int idtb = Integer.parseInt(String.valueOf(tb.getValueAt(i, 0)));
                        if (TableReservation.countidTableUsing(idtb, conn) > 0) {
                            if (TableReservation.countidTableReservation(idtb, conn) > 0) {
                                //Table.updateStatus(idtb, 5, conn);
                            } else {
                                Table.updateStatus(idtb, 2, conn);
                            }
                        } else {
                            Table.updateStatus(idtb, 4, conn);
                        }
                    }
                    conn.commit();
                    loadTable(idLocation);
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                    JOptionPane.showMessageDialog(getRootPane(), "Có lỗi xảy ra");
                } catch (SQLException ex) {
                    Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
    /**
     * Creates new form panel_main
     */
    private JPopupMenu popupMenu;
    private JPopupMenu popupMenuTableInvoice;
    JPopupMenu popup;
    JPopupMenu popupTableRequire;
    JScrollPane scrollpane;
    private int idTable;
    private int statusBG = -1;
    private int idTableReservation = -1;
    private int idCustomer = -1;
    private String nameCustomer = "";
    private Timestamp beginDate = null;
    private int idService;
    private int cost;
    private int numberOfServiceInvoice;
    private int idTableService;
    private String sv_name;
    private int rowTableService_selectEdit;
    private int total;
    private int statusTable;
    private int totalPay;
    private int discount;
    private Connection conn = null;
    private int idLocation = -1;
    private int idRequired;
    private int idTableRequire;
    private RestaurantInfo rs=RestaurantInfo.getinfo(ConnectDB.conn());

    public PanelTable() {
        initComponents();
        lbStaff.setText(Staff.getById(LoginInformation.getId_staff(), ConnectDB.conn()).getName());
        popupTableInvoice();
        loadTable(idLocation);
        loadTableRequire();
        loadTableService();
        popupTableRequire();
        fillkhCombo();

        TimerTask taskrequest = new TaskRequest();
        TimerTask taskrequired = new TaskRequired();
        TimerTask taskwarninguser = new TaskWarningUser();
        Timer timer = new Timer();
        timer.schedule(taskrequest, new Date(), 2000);
        //timer.schedule(taskrequired, new Date(), 2000);        
        timer.schedule(taskwarninguser, new Date(), rs.getMinuteWarning() * 60000);
        try {
            popup = new JPopupMenu();
            BufferedImage bImg1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/useTable.png"));
            Image image1 = bImg1.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Sử Dụng", new ImageIcon(image1)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        conn = ConnectDB.conn();
                        int count = TableReservation.countidTableReservation(idTable, conn);
                        if (count > 0) {
                            if (JOptionPane.showConfirmDialog(getRootPane(), "Bàn này có " + count + " khách hàng đang được đặt\n"
                                    + "Bạn có muốn tiếp tục sử dụng bàn này hay không?"
                                    + "\nGợi ý: Bạn có thể xem danh sách đặt bàn và thay đổi thông tin đặt bàn!", "Thông Báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                useTable(idTable, ConnectDB.conn());
                            }
                        } else {
                            useTable(idTable, ConnectDB.conn());
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setTextLable(idTable);
                }
            }));
            popup.addSeparator();
            BufferedImage bImg2 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/reservationTable.png"));
            Image image2 = bImg2.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Đặt Bàn", new ImageIcon(image2)) {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int result = JOptionPane.showConfirmDialog(null, new PanelTableReservation(),
                            "Đặt bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        reservationTable();
                    }
                }
            }
            ));
            BufferedImage bImg2_2 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Button-reservation_multi-icon.png"));
            Image image2_2 = bImg2_2.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(
                    new JMenuItem(new AbstractAction("Đặt Nhiều Bàn", new ImageIcon(image2_2)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            int result = JOptionPane.showOptionDialog(null, new PanelReservationMultiTable(),
                                    "Đặt nhiều bàn", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                            loadTable(idLocation);
                        }
                    }));
            BufferedImage bImg2_1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/calendar.png"));
            Image image2_1 = bImg2_1.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(
                    new JMenuItem(new AbstractAction("Xem Thông Tin Đặt Bàn", new ImageIcon(image2_1)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            int result = JOptionPane.showOptionDialog(null, new PanelViewReservation(),
                                    "Xem Danh Sách Đặt Bàn", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                            loadTable(idLocation);
                        }
                    }));

            popup.addSeparator();
            BufferedImage bImg3 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/cancelUseTable.png"));
            Image image3 = bImg3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Hủy Sử Dụng", new ImageIcon(image3)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            cancelUseTable();
                        }
                    }));
            BufferedImage bImg5 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Ok-icon.png"));
            Image image5 = bImg5.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.addSeparator();

            popup.add(
                    new JMenuItem(new AbstractAction("Thanh Toán", new ImageIcon(image5)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            try {
                                if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn muốn thanh toán hóa đơn này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    if (billing()) {
                                        if (JOptionPane.showConfirmDialog(tb_invoice, "Thanh toán thành công, Bạn muốn in hóa đơn không", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                            printInvoice();
                                        }
                                        loadTableInvoice();
                                        loadTable(idLocation);
                                        setTextLable(idTable);
                                        totalPay();
                                        loadTableInvoice();
                                    } else {
                                        JOptionPane.showMessageDialog(getRootPane(), "Không thể thanh toán");
                                    }
                                } else {
                                    if (billing()) {
                                        JOptionPane.showMessageDialog(getRootPane(), "Thanh toán thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                                        loadTableInvoice();
                                        loadTable(idLocation);
                                        setTextLable(idTable);
                                        totalPay();
                                    } else {
                                        JOptionPane.showMessageDialog(getRootPane(), "Không thể thanh toán");
                                    }
                                }
                            } catch (Exception ex) {
                            }
                        }
                    }));
            BufferedImage bImg6 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/print-icon.png"));
            Image image6 = bImg6.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("In Hóa Đơn", new ImageIcon(image6)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            try {
                                printInvoice();
                            } catch (Exception ex) {
                            }

                        }
                    }));
            BufferedImage bImg7 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/move-icon.png"));
            Image image7 = bImg7.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.addSeparator();
            popup.add(
                    new JMenuItem(new AbstractAction("Chuyển Bàn", new ImageIcon(image7)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            changeTable();
                        }
                    }));
            BufferedImage bImg8 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/merge-icon.png"));
            Image image8 = bImg8.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Ghép Bàn", new ImageIcon(image8)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            int result = JOptionPane.showConfirmDialog(null, new PanelMergeTable(),
                                    "Ghép bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            if (result == JOptionPane.OK_OPTION) {
                                int id = VariableStatic.getIdTable_Change();
                                try {
                                    conn = ConnectDB.conn();
                                    conn.setAutoCommit(false);
                                    TableReservation tbReser = TableReservation.getByTableByStatus(id, conn);
                                    int idReser = tbReser.getID();
                                    int idTable_Merge = VariableStatic.getIdTable_Change();
                                    if (TableReservationDetail.updateIdReservation(idTableReservation, idReser, idTable_Merge, conn)) {
                                        if (TableService.updateIdReservation(idTableReservation, idReser, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Ghép bàn " + Table.getByID(idTable, conn).getNAME() + " vào bàn"
                                                    + " " + Table.getByID(idTable_Merge, conn).getNAME() + " thành công");
                                        }
                                    }
                                } catch (Exception ex) {
                                    try {
                                        conn.rollback();
                                        JOptionPane.showMessageDialog(getRootPane(), "Ghép bàn không thành công");
                                    } catch (SQLException ex1) {
                                        Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
                                    }
                                }
                            }
                        }
                    }));
            BufferedImage bImg9 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/add-icon.png"));
            Image image9 = bImg9.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.addSeparator();

            popup.add(
                    new JMenuItem(new AbstractAction("Thêm Bàn", new ImageIcon(image9)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            conn = ConnectDB.conn();
                            String tablename = JOptionPane.showInputDialog(getRootPane(), "Nhập tên bàn");
                            if (tablename != null) {
                                if (Table.testTableName(tablename, conn)) {
                                    if (Table.insertNewTable(tablename, TableType.getByMaxID(conn).getId(), TableLocation.getByMinID(conn).getID(), 10, conn)) {
                                        JOptionPane.showMessageDialog(getRootPane(), "Thêm bàn thành công");
                                        SystemLog.insert("table", conn);
                                        loadTable(idLocation);
                                    } else {
                                        JOptionPane.showMessageDialog(getRootPane(), "Thêm bàn không thành công");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(getRootPane(), "Tên bàn đã có");
                                }
                            }
                        }
                    }));
            BufferedImage bImg10 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/editicon.png"));
            Image image10 = bImg10.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Đổi Tên Bàn", new ImageIcon(image10)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            conn = ConnectDB.conn();
                            String tablename = JOptionPane.showInputDialog(getRootPane(), "Nhập tên bàn", Table.getByID(idTable, conn).getNAME());
                            if (tablename != null) {
                                if (!tablename.equals(Table.getByID(idTable, conn).getNAME())) {
                                    if (tablename.length() > 30 || tablename.length() <= 0) {
                                        JOptionPane.showMessageDialog(getRootPane(), "Tên bàn quá dài hoặc chưa nhập");
                                    } else if (Table.testTableName(tablename, conn) == false) {
                                        JOptionPane.showMessageDialog(getRootPane(), "Tên bàn " + tablename + " đã có, vui lòng chọn tên khác");
                                    } else {
                                        if (Table.updateTableName(tablename, idTable, conn)) {
                                            SystemLog.insert("table", conn);
                                            JOptionPane.showMessageDialog(getRootPane(), "Cập nhật bàn thành công");
                                            loadTable(idLocation);
                                        } else {
                                            JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thất bại");
                                        }
                                    }
                                }
                            }
                            conn = null;
                        }
                    }));
            BufferedImage bImg11 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/delete-icon.png"));
            Image image11 = bImg11.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Xóa Bàn", new ImageIcon(image11)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            //new frmAddService(room_id).setVisible(true);
                        }
                    }));
            BufferedImage bImg12 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/khuvuc-icon.png"));
            Image image12 = bImg12.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.addSeparator();
            popup.add(
                    new JMenuItem(new AbstractAction("Quản Lý Vị Trí", new ImageIcon(image12)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            //new frmAddService(room_id).setVisible(true);
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadTable(int idLoca) {
        conn = ConnectDB.conn();
        layout_table.removeAll();
        enableButton(false);
        JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
        panel.setFont(new Font("Serif", Font.BOLD, 18));
        layout_table.add(panel);
        scrollpane = new JScrollPane(panel);
        layout_table.add(scrollpane, BorderLayout.CENTER);

        try {
            Table table[];
            if (idLoca == -1) {
                table = Table.getAll(conn);
            } else {
                table = Table.getByLocation(idLoca, conn);
            }
            int numberTable = table.length;
            final JLabel bt[] = new JLabel[numberTable];
            for (int i = 0; i < numberTable; i++) {
                bt[i] = new JLabel(table[i].getNAME());
                //bt[i].setFont(new Font("Serif", Font.BOLD, 18));
                if (table[i].getSTATUS() == 2) {// đang có khách
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon_thue.png"));
                    Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    bt[i].setIcon(icon);
                    bt[i].setPreferredSize(new Dimension(50, 50));
                } else if (table[i].getSTATUS() == 3) {// đặt bàn trước
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon_datban.png"));
                    Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    bt[i].setIcon(icon);
                    bt[i].setPreferredSize(new Dimension(50, 50));
                } else if (table[i].getSTATUS() == 4) {
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/warningTable.png"));
                    Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    bt[i].setIcon(icon);
                    bt[i].setPreferredSize(new Dimension(50, 50));
                } else if (table[i].getSTATUS() == 5) {
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/warningtb.png"));
                    Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    bt[i].setIcon(icon);
                    bt[i].setPreferredSize(new Dimension(50, 50));
                } else {
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon.png"));
                    Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    bt[i].setIcon(icon);
                    bt[i].setPreferredSize(new Dimension(50, 50));
                    //bt[i].setBackground(Color.WHITE);
                    bt[i].setOpaque(true);
                }

                //bt[i].setBackground(Color.GREEN);
                bt[i].setOpaque(true);
                bt[i].setHorizontalAlignment(SwingConstants.CENTER);
                BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon.png"));
                Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(image);
                String tooltip = "";
                if (statusTable == 2) {
                    tooltip = "Bàn đang được thuê";
                } else if (statusTable == 3) {
                    tooltip = "Bàn đang được đặt trước";
                } else {
                    tooltip = "Bàn đang trống";
                }
                bt[i].setToolTipText(tooltip);
                // bt[i].add(popup);
                final int idtb = table[i].getID();
                final int status = table[i].getSTATUS();

                final int x = i;
                bt[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        idTable = idtb;
                        if (SwingUtilities.isRightMouseButton(e)) {
                            popup.show(e.getComponent(), e.getX(), e.getY());

                            popup.getComponent(0).setEnabled(false);// Sử dụng
                            //line
                            popup.getComponent(2).setEnabled(false); //Đặt Bàn
                            popup.getComponent(3).setEnabled(false); //Xem Thông tin đặt bàn
                            popup.getComponent(4).setEnabled(false); //Đặt nhiều bàn
                            //line
                            popup.getComponent(6).setEnabled(false); //Hủy Sử Dụng
                            // line
                            popup.getComponent(8).setEnabled(false);//Thanh Toán
                            popup.getComponent(9).setEnabled(false);//In Trước Hóa Đơn
                            //line
                            popup.getComponent(11).setEnabled(false);// Chuyển Bàn
                            popup.getComponent(12).setEnabled(false);// Ghép Bàn
                            //line
                            popup.getComponent(14).setEnabled(false);//Thêm Bàn
                            popup.getComponent(15).setEnabled(false);// Đổi Tên Bàn
                            popup.getComponent(16).setEnabled(false);// Xóa Bàn
                            //line
                            popup.getComponent(18).setEnabled(false);// Quản lý khu vực
                            if (status == 2) { // bàn đang được sử dụng
                                popup.getComponent(0).setEnabled(false);// Sử dụng
                                //line
                                popup.getComponent(2).setEnabled(true); //Đặt Bàn
                                popup.getComponent(3).setEnabled(true); //Xem Thông tin đặt bàn
                                popup.getComponent(4).setEnabled(true); //Đặt nhiều bàn
                                //line
                                popup.getComponent(6).setEnabled(true); //Hủy Sử Dụng
                                // line
                                popup.getComponent(8).setEnabled(true);//Thanh Toán
                                popup.getComponent(9).setEnabled(true);//In Trước Hóa Đơn
                                //line
                                popup.getComponent(11).setEnabled(true);// Chuyển Bàn
                                popup.getComponent(12).setEnabled(true);// Ghép Bàn
                                //line
                                popup.getComponent(14).setEnabled(true);//Thêm Bàn
                                popup.getComponent(15).setEnabled(true);// Đổi Tên Bàn
                                popup.getComponent(16).setEnabled(false);// Xóa Bàn
                                //line
                                popup.getComponent(18).setEnabled(true);// Quản lý khu vực
                            } else if (status == 3) { // bàn được đặt
                                popup.getComponent(0).setEnabled(true);// Sử dụng
                                //line
                                popup.getComponent(2).setEnabled(true); //Đặt Bàn
                                popup.getComponent(3).setEnabled(true); //Xem Thông tin đặt bàn
                                popup.getComponent(4).setEnabled(true); //Đặt nhiều bàn
                                //line
                                popup.getComponent(6).setEnabled(false); //Hủy Sử Dụng
                                // line
                                popup.getComponent(8).setEnabled(false);//Thanh Toán
                                popup.getComponent(9).setEnabled(false);//In Trước Hóa Đơn
                                //line
                                popup.getComponent(11).setEnabled(false);// Chuyển Bàn
                                popup.getComponent(12).setEnabled(false);// Ghép Bàn
                                //line
                                popup.getComponent(14).setEnabled(true);//Thêm Bàn
                                popup.getComponent(15).setEnabled(true);// Đổi Tên Bàn
                                popup.getComponent(16).setEnabled(false);// Xóa Bàn
                                //line
                                popup.getComponent(18).setEnabled(true);// Quản lý khu vực
                            } else { //bàn chưa sử dụng
                                popup.getComponent(0).setEnabled(true);// Sử dụng
                                //line
                                popup.getComponent(2).setEnabled(true); //Đặt Bàn
                                popup.getComponent(3).setEnabled(true); //Xem Thông tin đặt bàn
                                popup.getComponent(4).setEnabled(true); //Đặt nhiều bàn
                                //line
                                popup.getComponent(6).setEnabled(false); //Hủy Sử Dụng
                                // line
                                popup.getComponent(8).setEnabled(false);//Thanh Toán
                                popup.getComponent(9).setEnabled(false);//In Trước Hóa Đơn
                                //line
                                popup.getComponent(11).setEnabled(false);// Chuyển Bàn
                                popup.getComponent(12).setEnabled(false);// Ghép Bàn
                                //line
                                popup.getComponent(14).setEnabled(true);//Thêm Bàn
                                popup.getComponent(15).setEnabled(true);// Đổi Tên Bàn
                                popup.getComponent(16).setEnabled(true);// Xóa Bàn
                                //line
                                popup.getComponent(18).setEnabled(true);// Quản lý khu vực
                            }
                        }
                        //if (SwingUtilities.isLeftMouseButton(e)) {
                        if (status == 2) {
                            enableButton(true);
                        } else if (status == 3) {
                            enableButton(false);
                            btnAddCustomer.setEnabled(true);
                            //popupMenu.getComponent(0).setEnabled(true);
                        } else {
                            enableButton(false);
                        }
                        Border border = LineBorder.createGrayLineBorder();
                        if (statusBG != -1) {
                            bt[statusBG].setBackground(Color.getColor("#F0F0F0"));
                            bt[statusBG].setBorder(null);
                        }
                        bt[x].setBackground(Color.pink);
                        bt[x].setBorder(border);
                        statusBG = x;
                        bt[x].repaint();

                        statusTable = status;
                        setTextLable(idTable);
                        loadTableInvoice();
                        totalPay();
                        VariableStatic.setIdTable(idTable);
                        conn = ConnectDB.conn();
                        VariableStatic.setNameTable(Table.getByID(idTable, conn).getNAME());
                        conn = null;
                    }
                    //}
                });
                panel.add(bt[i]);
            }
            layout_table.updateUI();
            layout_table.repaint();
            conn = null;
        } catch (Exception e) {

        }
    }

    private void loadTableService() {
        conn = ConnectDB.conn();
        tbService.setModel(Service.getAllLimit(conn));
        try {
            tbService.setRowSelectionInterval(0, 0);
            tbService.getColumnModel().getColumn(2).setCellRenderer(new NumberCellRenderer());
        } catch (Exception e) {
        }
        tbService.getTableHeader().setReorderingAllowed(false);
    }

    private void loadTableRequire() {
        tbListTableRequest.setModel(ClientRequest.getAll(ConnectDB.conn()));
        try {
            tbListTableRequest.setRowSelectionInterval(0, 0);
        } catch (Exception e) {
        }
        tbListTableRequest.getTableHeader().setReorderingAllowed(false);
        tbListTableRequest.getColumnModel().getColumn(3).setMaxWidth(0);
        tbListTableRequest.getColumnModel().getColumn(3).setMinWidth(0);
        tbListTableRequest.getColumnModel().getColumn(3).setPreferredWidth(0);
    }

    private void popupTableRequire() {

        try {
            popupTableRequire = new JPopupMenu();
            BufferedImage bImgAccept = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/ok-icon.png"));
            Image imageAccept = bImgAccept.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupTableRequire.add(new JMenuItem(new AbstractAction("Chấp Nhận", new ImageIcon(imageAccept)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection cn = ConnectDB.conn();
                    try {
                        cn.setAutoCommit(false);
                        if (ClientRequest.getById(idRequired, cn).getId_request() >= 2) {
                            if (ClientRequest.accept(idRequired, true, ConnectDB.conn())) {
                                loadTableRequire();
                            } else {
                                throw new SQLException();
                            }
                        } else {
                            if (ClientRequest.accept(idRequired, true, cn)) {
                                if (useTable(idTableRequire, cn)) {
                                    cn.commit();
                                    loadTableRequire();
                                }
                            } else {
                                throw new SQLException();
                            }
                        }
                    } catch (Exception ex) {
                        try {
                            cn.rollback();
                            JOptionPane.showMessageDialog(tbListTableRequest, "Đã xảy ra lỗi", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException ex1) {
                            Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
            ));
            BufferedImage bImgAcceptAll = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/check-all-icon.png"));
            Image imageAcceptall = bImgAcceptAll.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupTableRequire.add(new JMenuItem(new AbstractAction("Chấp Nhận Tất Cả", new ImageIcon(imageAcceptall)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(tbListTableRequest, "Bạn thật sự muốn chấp nhận khách hàng dùng tất cả bàn này?",
                            "Thông Báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Connection cnn = ConnectDB.conn();
                        try {
                            cnn.setAutoCommit(false);
                            for (int i = 0; i < tbListTableRequest.getRowCount(); i++) {
                                int idrequire = Integer.parseInt(tbListTableRequest.getValueAt(i, 3).toString());
                                int idtable = Integer.parseInt(tbListTableRequest.getValueAt(i, 0).toString());
                                if (ClientRequest.accept(idrequire, true, cnn)) {
                                    useTable(idtable, cnn);
                                } else {
                                    throw new SQLException();
                                }
                            }
                            cnn.commit();
                            loadTableRequire();
                        } catch (Exception ex) {
                            try {
                                cnn.rollback();
                            } catch (SQLException ex1) {
                                Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    }
                }
            }
            ));
            BufferedImage bImgNotAccept = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Button-Delete-icon.png"));
            Image imageNotAccept = bImgNotAccept.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupTableRequire.add(new JMenuItem(new AbstractAction("Từ Chối", new ImageIcon(imageNotAccept)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(tbListTableRequest, "Bạn thật sự muốn từ chối khách hàng dùng bàn này?",
                            "Thông Báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (ClientRequest.accept(idRequired, false, ConnectDB.conn())) {
                            loadTableRequire();
                        } else {
                            JOptionPane.showMessageDialog(tbListTableRequest, "Không thành công", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbListTableRequest.setComponentPopupMenu(popupTableRequire);
    }

    private void popupTableInvoice() {
        try {
            //conn = ConnectDB.conn();
            popupMenuTableInvoice = new JPopupMenu();
            BufferedImage bImgCalService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/move-icon.png"));
            Image imageCalService = bImgCalService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenuTableInvoice.add(new JMenuItem(new AbstractAction("Cập Nhật Số Lượng", new ImageIcon(imageCalService)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String num = JOptionPane.showInputDialog(tb_invoice, "Nhập số lượng", numberOfServiceInvoice);
                    if (num != null && !num.trim().equals("")) {
                        if (num.length() <= 10 && testNumber(num) && Integer.parseInt(num) >= 0) {
                            int n = Integer.parseInt(num) - numberOfServiceInvoice;
                            int idsv = Integer.parseInt(String.valueOf(tb_invoice.getValueAt(tb_invoice.getSelectedRow(), 6)));
                            try {
                                conn = ConnectDB.conn();
                                conn.setAutoCommit(false);
                                if (statusTable == 3) {
                                    if (testStore(idsv, Integer.parseInt(num))) {
                                        if (TableService.update(idTableService, Integer.parseInt(num), conn)) {
                                            conn.commit();
                                            loadTableInvoice();
                                            totalPay();
                                        } else {
                                            throw new Exception();
                                        }
                                    } else {
                                        if (JOptionPane.showConfirmDialog(tb_invoice, "Số lượng tồn kho không đủ, bạn vẫn muốn tiếp tục", "Hỏi",
                                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                            if (TableService.update(idTableService, Integer.parseInt(num), conn)) {
                                                conn.commit();
                                                loadTableInvoice();
                                                totalPay();
                                            } else {
                                                throw new Exception();
                                            }
                                        }
                                    }
                                }
                                if (statusTable == 2) {
                                    if (Integer.parseInt(num) <= numberOfServiceInvoice) {
                                        if (updateStore(idsv, n, true, conn)) {
                                            if (TableService.update(idTableService, Integer.parseInt(num), conn)) {
                                                conn.commit();
                                                loadTableInvoice();
                                                totalPay();
                                            } else {
                                                throw new Exception();
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(getRootPane(), "Số lượng tồn kho không đủ");
                                        }
                                    } else {
                                        if (updateStore(idsv, n, testStore(idsv, n), conn)) {
                                            if (TableService.update(idTableService, Integer.parseInt(num), conn)) {
                                                conn.commit();
                                                loadTableInvoice();
                                                totalPay();
                                            } else {
                                                throw new Exception();
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(getRootPane(), "Số lượng tồn kho không đủ");
                                        }
                                    }
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
                            JOptionPane.showMessageDialog(tb_invoice, "Bạn nhập số lượng sai");
                        }
                    } else {
                        JOptionPane.showMessageDialog(tb_invoice, "Bạn chưa nhập số lượng");
                    }

                    conn = null;
                }
            }
            ));
            BufferedImage bImgDel = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/delete-icon.png"));
            Image imageDel = bImgDel.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popupMenuTableInvoice.add(
                    new JMenuItem(new AbstractAction("Xóa Dịch Vụ", new ImageIcon(imageDel)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn muốn hủy dịch vụ này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                int n = -numberOfServiceInvoice;
                                int idsv = Integer.parseInt(String.valueOf(tb_invoice.getValueAt(tb_invoice.getSelectedRow(), 6)));
                                try {
                                    conn = ConnectDB.conn();
                                    conn.setAutoCommit(false);
                                    if (statusTable == 3) {

                                        if (TableService.delete(idTableService, conn)) {
                                            conn.commit();
                                        } else {
                                            throw new Exception();
                                        }
                                    }
                                    if (statusTable == 2) {
                                        if (updateStore(idsv, n, true, conn)) {
                                            if (TableService.delete(idTableService, conn)) {
                                                conn.commit();
                                            } else {
                                                throw new Exception();
                                            }
                                        } else {
                                            throw new Exception();
                                        }
                                    }

                                    //conn.commit();
                                    loadTableInvoice();
                                    totalPay();
                                } catch (Exception ex) {
                                    try {
                                        conn.rollback();
                                        JOptionPane.showMessageDialog(getRootPane(), "Có lỗi xảy ra!" + ex.getMessage());
                                    } catch (SQLException ex1) {
                                        Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
                                    }
                                }
                            }
                            conn = null;
                        }
                    }
                    ));
            popupMenuTableInvoice.addSeparator();
            BufferedImage bImgReload = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon.png"));
            Image imageReload = bImgReload.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popupMenuTableInvoice.add(
                    new JMenuItem(new AbstractAction("Cập Nhật", new ImageIcon(imageReload)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            loadTableInvoice();
                            totalPay();
                        }
                    }
                    ));
            tb_invoice.setComponentPopupMenu(popupMenuTableInvoice);
        } catch (Exception e) {
        }
        conn = null;
    }

    private boolean useTable(int id_table, Connection conn) throws SQLException {
        boolean t = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(new Date());
        Timestamp ts = Timestamp.valueOf(datetime);
        TableModel tb = TableReservation.getByTable_DateTime(id_table, ts, rs.getHourReservationNomal(), conn);
        if (TableReservation.getStatusParty(id_table, ts, InfoRestaurant.getHourAcceptReservationParty(), conn)) {
            JOptionPane.showMessageDialog(getRootPane(), "Bàn này đang được đặt bạn vui lòng chọn bàn khác hoặc thời gian khác");
        } else {
            if (tb.getRowCount() > 0) {
                JOptionPane.showMessageDialog(getRootPane(), "<html>Xin lỗi bạn không thể sử dụng bàn <b><font color='green'>" + Table.getByID(id_table, conn).getNAME() + "</font></b>ngay bây giờ.<br>"
                        + "Vì đã có khách hàng <font color='green'><b>" + String.valueOf(tb.getValueAt(0, 2)) + "</b></font> nhận bàn vào lúc <font color='blue'><b>" + String.valueOf(tb.getValueAt(0, 3)) + "</b></font>"
                        + "<br>Xin vui lòng chọn bàn khác!</html>");
            } else {
                try {
                    conn = ConnectDB.conn();
                    conn.setAutoCommit(false);
                    if (TableReservation.insert(true, conn)) {
                        int maxid_reservation = TableReservation.getMaxID(conn).getID();
                        if (TableReservationDetail.insert(id_table, maxid_reservation, conn)) {
                            if (Table.updateStatus(id_table, 2, conn)) {
                                conn.commit();
                                t = true;
                                loadTable(idLocation);
                            } else {
                                throw new Exception();
                            }
                        } else {
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    conn.rollback();
                    t = false;
                    JOptionPane.showMessageDialog(getRootPane(), "Có lỗi xảy ra!");
                }
            }
        }
        conn = null;
        return t;
    }

    private void cancelUseTable() {
        conn = ConnectDB.conn();
        try {
            conn.setAutoCommit(false);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime = formatter.format(new Date());
            Timestamp ts = Timestamp.valueOf(datetime);
            int count = tb_invoice.getRowCount();
            if (count < 1) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có thật sự hủy sử dụng bàn này không", "Thông Báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (TableReservation.getStatusParty(idTable, conn)) {
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
                            conn.setAutoCommit(false);
                            if (TableReservation.reservationCancel(idTableReservation, idTable, conn)) {
                                if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                    if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                        if (Table.updateStatus(idTable, 2, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                            loadTable(idLocation);
                                        } else {
                                            throw new Exception();
                                        }
                                    } else {
                                        if (Table.updateStatus(idTable, 3, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                            loadTable(idLocation);
                                        }
                                    }
                                } else {
                                    if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                        if (Table.updateStatus(idTable, 2, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                            loadTable(idLocation);
                                        }
                                    } else {
                                        if (Table.updateStatus(idTable, 1, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                            loadTable(idLocation);
                                        }
                                    }
                                }
                            } else {
                                throw new Exception();
                            }
                        }
                        if (x == JOptionPane.NO_OPTION) {
                            //các bàn trong hóa đơn
                            conn.setAutoCommit(false);
                            TableModel tb = TableReservation.getListTableByIdReservation(idTableReservation, conn);
                            if (TableReservation.reservationCancel(idTableReservation, conn)) {
                                TableReservation.updateEndDate(idTableReservation, conn);
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
                                loadTable(idLocation);
                                JOptionPane.showMessageDialog(getRootPane(), "Đã hủy sử dụng bàn thành công");
                            } else {
                                throw new Exception();
                            }
                        }
                    } else {
                        // khong phai ban dat tiec
                        conn.setAutoCommit(false);
                        if (TableReservation.reservationCancel(idTableReservation, conn)) {
                            TableReservation.updateEndDate(idTableReservation, conn);
                            if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                    if (Table.updateStatus(idTable, 2, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy sử dụng thành công");
                                        loadTable(idLocation);
                                    }
                                } else {
                                    if (Table.updateStatus(idTable, 3, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy sử dụng thành công");
                                        loadTable(idLocation);
                                    }
                                }
                            } else {
                                if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                    if (Table.updateStatus(idTable, 2, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy sử dụng thành công");
                                        loadTable(idLocation);
                                    }
                                } else {
                                    if (Table.updateStatus(idTable, 1, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy sử dụng thành công");
                                        loadTable(idLocation);
                                    }
                                }
                            }
                        } else {
                            throw new Exception();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Bàn đã được gọi món. Vui lòng thanh toán");
            }
        } catch (Exception ex) {
            try {
                conn.rollback();
                JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
            } catch (SQLException ex1) {
                Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        conn = null;
    }

    private void changeTable() {

        int result = JOptionPane.showConfirmDialog(null, new PanelChangeTable(),
                "Chuyển Bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int idTableChange = VariableStatic.getIdTable_Change();
            if (idTableChange == 0) {
                JOptionPane.showMessageDialog(getRootPane(), "Chưa có bàn nào được chọn");
            } else {
                if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn có thật sự muốn chuyển " + Table.getByID(idTable, conn).getNAME() + " "
                        + "Sang bàn " + Table.getByID(idTableChange, conn).getNAME(), "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    try {
                        conn = ConnectDB.conn();
                        conn.setAutoCommit(false);
                        if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                            if (Table.updateStatus(idTable, 3, conn)) {
                                if (Table.updateStatus(idTableChange, 1, conn)) {
                                    if (TableReservationDetail.updateTable(idTableReservation, idTable, idTableChange, conn)) {
                                        conn.commit();
                                        loadTable(idLocation);
                                    } else {
                                        throw new Exception();
                                    }
                                }
                            } else {
                                throw new Exception();
                            }
                        } else {
                            if (Table.updateStatus(idTable, 1, conn)) {
                                if (Table.updateStatus(idTableChange, 2, conn)) {
                                    if (TableReservationDetail.updateTable(idTableReservation, idTable, idTableChange, conn)) {
                                        conn.commit();
                                        loadTable(idLocation);
                                    } else {
                                        throw new Exception();
                                    }
                                }
                            } else {
                                throw new Exception();
                            }
                        }
                    } catch (Exception e) {
                        try {
                            conn.rollback();
                            JOptionPane.showMessageDialog(getRootPane(), "Da xay ra loi");
                        } catch (SQLException ex) {
                            Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        conn = null;
    }

    private void reservationTable() {
        conn = ConnectDB.conn();
        if (testDate(VariableStatic.getDateTimeReservation()) == false) {
            Timestamp ts = VariableStatic.getDateTimeReservation();
            int time;
            boolean t;
            t = TableReservation.getStatusParty(idTable, ts, rs.getHourReservationParty(), ConnectDB.conn());
            if (t) {
                time = rs.getHourReservationParty();
            } else {
                time = rs.getHourReservationParty();
            }
            TableModel tb = TableReservation.getByTable_DateTime(idTable, VariableStatic.getDateTimeReservation(),
                    time, ConnectDB.conn());
            if (tb.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(getRootPane(), "Ngày đặt phải lớn hơn ngày hiện tại");
            } else {
                if (t) {
                    JOptionPane.showMessageDialog(getRootPane(), "Bạn không thể đặt bàn vào thời gian này!");
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "<html>Bạn không thể đặt bàn <b><font color='green'>"
                            + Table.getByID(idTable, ConnectDB.conn()).getNAME() + "</font></b> trong thời gian này"
                            + " vì đã có khách hàng đang sử dụng hoặc sẽ nhận bàn vào ngày <font color='blue'><b>"
                            + String.valueOf(tb.getValueAt(0, 3)) + " </b></font>"
                            + "<br>Xin vui lòng chọn thời gian khác hoặc bàn khác!</html>");
                }
            }
        } else {
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                if (TableReservation.insert(false, VariableStatic.getIdCustomer(),
                        VariableStatic.getDateTimeReservation(), false, VariableStatic.getPrePay(), conn)) {
                    int maxid_reservation = TableReservation.getMaxID(conn).getID();
                    if (TableReservationDetail.insert(idTable, maxid_reservation, conn)) {
                        int stt;
                        if (statusTable == 2) {
                            stt = 2;
                        } else {
                            stt = 3;
                        }
                        if (Table.updateStatus(idTable, stt, conn)) {
                            conn.commit();
                            JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn thành công");
                            loadTable(idLocation);

                        } else {
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
                }
            } catch (Exception ex) {
                try {
                    conn.rollback();
                    // conn.close();
                    JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn không thành công", "Thông Báo", JOptionPane.OK_OPTION);
                } catch (SQLException ex1) {
                }
            }
        }
        conn = null;
    }

    private void setTextLable(int id) {
        conn = ConnectDB.conn();
        DecimalFormat df = new DecimalFormat("#,###,###");
        TableReservation tbReser = TableReservation.getByTableByStatus(id, conn);
        beginDate = tbReser.getBeginDate();
        idCustomer = tbReser.getCUSTOMER();
        nameCustomer = tbReser.getCUSTOMER_NAME();
        txtPrepay.setText(df.format(tbReser.getPrepay()));
        idTableReservation = tbReser.getID();
        // setText lable
        if (beginDate == null) {
            lbBeginDate.setText("Chưa sử dụng");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            String datetime = formatter.format(beginDate);
            lbBeginDate.setText(datetime);

        }
        lbCustomerName.setText(nameCustomer);
        lbTableName.setText(Table.getByID(id, conn).getNAME() + " - " + Table.getByID(id, conn).getNAME_LOCATION());
        conn = null;

    }

    private boolean testDate(Timestamp date) {
        conn = ConnectDB.conn();
        boolean flag = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(new Date());
        Timestamp ts = Timestamp.valueOf(datetime);
        TableModel tb = TableReservation.getByTable_DateTime(idTable, date, rs.getHourReservationNomal(), ConnectDB.conn());
        Date dt = new Date();
        //Date incrementedDate = DateUtils.addHours(dt, 2);
        if (statusTable == 1) {
            if (dt.compareTo(date) < 0) {
                flag = true;
            } else {
                flag = false;
            }
        } else {

            if (date.compareTo(ts) < 0 || tb.getRowCount() > 0) {
                flag = false;
            } else {
                flag = true;
            }
        }
        conn = null;
        return flag;
    }

    private void enableButton(boolean b) {
        btnAddCustomer.setEnabled(b);
        btnCancelTableUse.setEnabled(b);
        btnChangeTable.setEnabled(b);
        btnPayment.setEnabled(b);
        btnPrintPreviewInvoice.setEnabled(b);
        //popupMenu.getComponent(0).setEnabled(b);

    }

    private void loadTableInvoice() {
        Connection conn = ConnectDB.conn();
        tb_invoice.setModel(TableService.getByIdReservation(idTableReservation, conn));
        if (tb_invoice.getRowCount() > 0) {
            tb_invoice.setRowSelectionInterval(0, 0);
            tb_invoice.getColumnModel().getColumn(6).setMinWidth(0);
            tb_invoice.getColumnModel().getColumn(6).setMaxWidth(0);
        } else {
            tb_invoice.getColumnModel().getColumn(6).setMinWidth(0);
            tb_invoice.getColumnModel().getColumn(6).setMaxWidth(0);
        }
        tb_invoice.getColumnModel().getColumn(4).setCellRenderer(new NumberCellRenderer());
        tb_invoice.getColumnModel().getColumn(5).setCellRenderer(new NumberCellRenderer());
        tb_invoice.getTableHeader().setReorderingAllowed(false);
        totalPay();
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

    private void totalPay() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(new Date());
        Timestamp ts = Timestamp.valueOf(datetime);
        if (beginDate == null) {
            ts = Timestamp.valueOf(datetime);
        } else {
            ts = Timestamp.valueOf(formatter.format(beginDate));
        }
        conn = ConnectDB.conn();
        DecimalFormat df = new DecimalFormat("#,###,###");
        float service_Charges = 0;
        int discount_money = 0;
        int discount_percent = 0;
        int customer_pay = 0;
        int prepay = 0;
        double x;
        if (!txtService_Price.getText().trim().equals("") && testNumber(txtService_Price.getText())) {
            try {
                service_Charges = Float.parseFloat(txtService_Price.getText().trim().replaceAll("\\.", ""));
            } catch (Exception e) {
                service_Charges = Float.parseFloat(txtService_Price.getText().trim().replaceAll(",", ""));
            }

        }
        if (!txtCustomerPay.getText().trim().equals("")) {
            try {
                customer_pay = Integer.parseInt(txtCustomerPay.getText().trim().replaceAll("\\.", ""));
            } catch (Exception e) {
                customer_pay = Integer.parseInt(txtCustomerPay.getText().trim().replaceAll(",", ""));
            }

        }
        if (!txtPrepay.getText().trim().equals("")) {
            try {
                prepay = Integer.parseInt(txtPrepay.getText().trim().replaceAll("\\.", ""));
            } catch (Exception e) {
                prepay = Integer.parseInt(txtPrepay.getText().trim().replaceAll(",", ""));
            }

        }

        float total = TableService.totalPayment(idTableReservation, conn);
        if (!Discount.getByDate(ts, conn).getName().equals("Không có khuyến mãi")) {
            if (total >= Discount.getByDate(ts, conn).getCondition()) {
                if (Discount.getByDate(ts, conn).getType() == 0) {
                    discount_percent = Discount.getByDate(ts, conn).getValue();
                    txtDiscountPercent.setText(String.valueOf(discount_percent));
                }
            } else {
                txtDiscountPercent.setText("");
            }
            if (Discount.getByDate(ts, conn).getType() == 1) {
                if (total >= Discount.getByDate(ts, conn).getCondition()) {
                    discount_money = Discount.getByDate(ts, conn).getValue();
                    txtDiscountMoney.setText(df.format(discount_money));
                } else {
                    txtDiscountMoney.setText("");
                }
            }
        }
        //JOptionPane.showMessageDialog(getRootPane(), service_Charges);
        lbTotal.setText(String.valueOf(df.format(total)));
        float _discount = (discount_percent * total) / 100;
        if (total == 0) {
            totalPay = 0;
        } else {
            totalPay = (int) ((total + service_Charges) - (discount_money + _discount));
        }

        lbTotalPay.setText(String.valueOf(df.format(totalPay - prepay)));
        total = TableService.totalPayment(idTableReservation, conn);
        lbTotal.setText(String.valueOf(df.format(total)));
        discount = (int) (_discount + discount_money);

        if ((customer_pay + prepay) < totalPay) {
            lbChangeForCustomer.setText("Thiếu: " + String.valueOf(df.format(Math.abs(((customer_pay + prepay) - totalPay)))));
        } else {
            lbChangeForCustomer.setText(df.format((customer_pay + prepay) - totalPay));
        }
    }

    private boolean testStatusService(int idReservation) {
        boolean t = false;
        TableService[] serviceStatus = TableService.getStatus(idReservation, ConnectDB.conn());
        for (int i = 0; i < serviceStatus.length; i++) {
            t = serviceStatus[i].isStatus();
            if (t == false) {
                JOptionPane.showMessageDialog(tb_invoice, "Có dịch vụ đã gọi nhưng chưa phục vụ. Vui lòng hủy dịch vụ trước khi thanh toán",
                        "Thông Báo", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return t;
    }

    private boolean billing() throws SQLException {
        Connection conn = ConnectDB.conn();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:00");
        Timestamp ts;
        if (beginDate == null) {
            ts = Timestamp.valueOf(formatter.format(formatter.format(new Date())));
        } else {
            ts = Timestamp.valueOf(formatter.format(beginDate));
        }
        boolean flag = false;
        try {
            if (testStatusService(idTableReservation)) {
                conn.setAutoCommit(false);
                if (TableReservation.updateEndDate(idTableReservation, conn)) {
                    if (Invoice.insert(idTableReservation, LoginInformation.getId_staff(), totalPay, discount, txtNoteinvoice.getText(), conn)) {
                        int _total = 0;
                        try {
                            _total = Integer.parseInt(lbTotal.getText().replaceAll("\\.", ""));
                        } catch (Exception e) {
                            _total = Integer.parseInt(lbTotal.getText().replaceAll(",", ""));
                        }
                        if (Discount.getByDate(ts, conn).getCondition() >= _total) {
                            TableReservationDetail table_list[] = TableReservationDetail.getListTableByIdReservation(idTableReservation, conn);
                            for (int i = 0; i < table_list.length; i++) {
                                if (TableReservation.countidTableReservation(table_list[i].getID_TABLE(), conn) > 0) {
                                    if (Table.updateStatus(table_list[i].getID_TABLE(), 3, conn)) {
                                        flag = true;
                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    if (Table.updateStatus(table_list[i].getID_TABLE(), 1, conn)) {
                                        flag = true;
                                    } else {
                                        throw new Exception();
                                    }
                                }
                            }
                            conn.commit();
                        } else {
                            TableReservationDetail table_list[] = TableReservationDetail.getListTableByIdReservation(idTableReservation, conn);
                            for (int i = 0; i < table_list.length; i++) {
                                if (TableReservation.countidTableReservation(table_list[i].getID_TABLE(), conn) > 0) {
                                    if (Table.updateStatus(table_list[i].getID_TABLE(), 3, conn)) {
                                        flag = true;
                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    if (Table.updateStatus(table_list[i].getID_TABLE(), 1, conn)) {
                                        flag = true;
                                    } else {
                                        throw new Exception();
                                    }
                                }
                            }
                            conn.commit();
                            try {
                                DiscountDetail.insert(Invoice.getMaxID(conn), Discount.getByDate(ts, conn).getId(), conn);
                            } catch (Exception e) {
                            }
                        }

                    } else {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
            } else {
                flag = false;
            }
        } catch (Exception e) {
            conn.rollback();
            JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi!");
        }
        return flag;
    }

    private void printInvoice() {
        try {
            conn = ConnectDB.conn();
            DecimalFormat df = new DecimalFormat("#,###,###");
            String servicePay = "0";
            if (!txtService_Price.getText().equals("")) {
                servicePay = txtService_Price.getText();
            }
            int prepay;
            try {
                prepay = Integer.parseInt(String.valueOf(txtPrepay.getText().trim().replaceAll("\\.", "")));
            } catch (Exception e) {
                prepay = Integer.parseInt(String.valueOf(txtPrepay.getText().trim().replaceAll(",", "")));
            }
            TableReservationDetail[] tb = TableReservationDetail.getListTableByIdReservation(idTableReservation, conn);
            String ban = "";
            for (int i = 0; i < tb.length; i++) {
                int id = tb[i].getID_TABLE();
                ban = ban + "," + Table.getByID(id, conn).getNAME();
            }
            HashMap<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("idReservation", idTableReservation);
            parameter.put("totalFirst", lbTotal.getText());
            parameter.put("servicePayment", servicePay);
            parameter.put("paymentDown", df.format(discount));
            parameter.put("totalLast", df.format(totalPay - prepay));
            parameter.put("customer", lbCustomerName.getText());
            parameter.put("customer", lbCustomerName.getText());
            parameter.put("staff", lbStaff.getText());
            parameter.put("dateUse", lbBeginDate.getText());
            parameter.put("note", txtNoteinvoice.getText());
            parameter.put("duatruoc", txtPrepay.getText());
            parameter.put("tennhahang", rs.getName());
            parameter.put("diachi", "Địa Chỉ: " + rs.getAddress());
            parameter.put("dienthoai", "Điện Thoại: " + rs.getPhone());
            parameter.put("ban", ban);
            JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/vn/edu/vttu/report/Invoice.jrxml"));
            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parameter, conn);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void fillkhCombo() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.TableLocation> model = new Vector<vn.edu.vttu.model.TableLocation>();
        try {
            model = TableLocation.selectTableLocation(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        jcomboTableLocation.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        jcomboTableLocation.setModel(defaultComboBoxModel);
        jcomboTableLocation.setRenderer(new PanelTable.ItemRenderer());
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
        return flag;
    }

    private boolean updateStore(int idService, int n, boolean b, Connection conn) {
        boolean flag = false;
        if (b) {
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

    private void callService() {
        Connection conn = ConnectDB.conn();
        try {

            String number = "1"; //JOptionPane.showInputDialog(tbService, "Số Lượng", 1);
            if (number != null && !number.trim().equals("")) {
                if (testNumber(number)) {
                    if (statusTable == 3) {
                        if (testStore(idService, Integer.parseInt(number))) {
                            if (TableService.getServiceByIdService_ByIdReservation(idService, idTableReservation, conn)) {
                                if (TableService.insert(idTableReservation, idService, Integer.parseInt(number), cost, conn)) {
                                    loadTableInvoice();
                                    totalPay();
                                }
                            } else {
                                if (TableService.update(idTableReservation, idService, Integer.parseInt(number), conn)) {
                                    loadTableInvoice();
                                }
                            }

                        } else {
                            if (JOptionPane.showConfirmDialog(getRootPane(), "Số lượng tồn kho sắp hết bạn có muốn đặt", "Hỏi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                if (TableService.getServiceByIdService_ByIdReservation(idService, idTableReservation, conn)) {
                                    if (TableService.insert(idTableReservation, idService, Integer.parseInt(number), cost, conn)) {
                                        loadTableInvoice();
                                        totalPay();
                                    }
                                } else {
                                    if (TableService.update(idTableReservation, idService, Integer.parseInt(number), conn)) {
                                        loadTableInvoice();
                                    }
                                }
                            }
                        }
                    }
                    if (statusTable == 2) {
                        conn.setAutoCommit(false);
                        if (updateStore(idService, Integer.parseInt(number), testStore(idService, Integer.parseInt(number)), conn)) {
                            if (TableService.getServiceByIdService_ByIdReservation(idService, idTableReservation, conn)) {
                                if (TableService.insert(idTableReservation, idService, Integer.parseInt(number), cost, conn)) {
                                    conn.commit();
                                    loadTableInvoice();
                                    totalPay();
                                } else {
                                    throw new Exception();
                                }
                            } else {
                                if (TableService.update(idTableReservation, idService, Integer.parseInt(number), conn)) {
                                    conn.commit();
                                    loadTableInvoice();

                                } else {
                                    throw new Exception();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(getRootPane(), "Số lượng tồn kho không đủ");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Chỉ được nhập số");
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập số lượng");
            }
        } catch (Exception e) {
            try {
                conn.rollback();
                JOptionPane.showMessageDialog(getRootPane(), "Thất Bại");
            } catch (SQLException ex) {
                Logger.getLogger(PanelTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layout_service = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbService = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbListTableRequest = new javax.swing.JTable();
        layout_invoice = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbBeginDate = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbTableName = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        lbCustomerName = new javax.swing.JLabel();
        btnAddCustomer = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lbStaff = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        txtNoteinvoice = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_invoice = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDiscountPercent = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbChangeForCustomer = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lbTotalPay = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        txtService_Price = new javax.swing.JFormattedTextField();
        txtDiscountMoney = new javax.swing.JFormattedTextField();
        txtCustomerPay = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPrepay = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnPayment = new javax.swing.JButton();
        btnCancelTableUse = new javax.swing.JButton();
        btnPrintPreviewInvoice = new javax.swing.JButton();
        btnChangeTable = new javax.swing.JButton();
        layout_table = new javax.swing.JPanel();
        btnReloadTable = new javax.swing.JButton();
        jcomboTableLocation = new javax.swing.JComboBox();
        btnViewTableByLocation = new javax.swing.JButton();

        tbService.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbService = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Dịch Vụ", "Tên Dịch Vụ", "Đơn Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbService.setEditingRow(1);
        tbService.setGridColor(new java.awt.Color(204, 204, 204));
        tbService.setRowHeight(23);
        tbService.setSelectionBackground(new java.awt.Color(255, 153, 51));
        tbService.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbServiceMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbService);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("DANH SÁCH BÀN YÊU CẦU SỬ DỤNG");

        tbListTableRequest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbListTableRequest = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbListTableRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Bàn", "Tên Bàn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListTableRequest.setEditingRow(1);
        tbListTableRequest.setGridColor(new java.awt.Color(204, 204, 204));
        tbListTableRequest.setRowHeight(23);
        tbListTableRequest.setSelectionBackground(new java.awt.Color(255, 153, 51));
        tbListTableRequest.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbListTableRequest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbListTableRequestMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbListTableRequest);

        javax.swing.GroupLayout layout_serviceLayout = new javax.swing.GroupLayout(layout_service);
        layout_service.setLayout(layout_serviceLayout);
        layout_serviceLayout.setHorizontalGroup(
            layout_serviceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout_serviceLayout.createSequentialGroup()
                .addGroup(layout_serviceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout_serviceLayout.setVerticalGroup(
            layout_serviceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout_serviceLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));
        jPanel1.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lbBeginDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBeginDate.setForeground(new java.awt.Color(51, 153, 0));
        lbBeginDate.setText("Bàn Chưa Sử Dụng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Bàn");

        lbTableName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTableName.setForeground(new java.awt.Color(51, 153, 0));
        lbTableName.setText("Tên Bàn");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("KH:");

        lbCustomerName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbCustomerName.setForeground(new java.awt.Color(51, 153, 0));
        lbCustomerName.setText("Chọn Khách Hàng");

        btnAddCustomer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAddCustomer.setForeground(new java.awt.Color(0, 204, 102));
        btnAddCustomer.setText("+");
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("NV:");

        lbStaff.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbStaff.setForeground(new java.awt.Color(51, 153, 0));
        lbStaff.setText("Nhân Viên");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ghi Chú:");

        txtNoteinvoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNoteinvoice.setForeground(new java.awt.Color(0, 153, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("TG:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbCustomerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddCustomer))
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbBeginDate, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator3))
                        .addGap(184, 184, 184)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNoteinvoice)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbBeginDate)
                    .addComponent(jLabel3)
                    .addComponent(lbTableName)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(lbCustomerName)
                        .addComponent(btnAddCustomer))
                    .addComponent(jLabel7)
                    .addComponent(lbStaff))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNoteinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tb_invoice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        tb_invoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tb_invoice = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tb_invoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên Dịch Vụ", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_invoice.setGridColor(new java.awt.Color(204, 204, 204));
        tb_invoice.setRowHeight(30);
        tb_invoice.setSelectionBackground(new java.awt.Color(102, 204, 255));
        tb_invoice.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tb_invoice.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tb_invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_invoiceMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tb_invoice);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Phí Dịch Vụ:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 255));
        jLabel11.setText("VNĐ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 255));
        jLabel13.setText("VNĐ");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Đưa Trước:");

        txtDiscountPercent.setEditable(false);
        txtDiscountPercent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscountPercent.setForeground(new java.awt.Color(0, 102, 255));
        txtDiscountPercent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountPercentActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 255));
        jLabel15.setText("%");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Khách Đưa:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 255));
        jLabel17.setText("VNĐ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Tổng Tiền:");

        lbTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(0, 102, 255));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotal.setText("0");
        lbTotal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lbTotalPropertyChange(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 255));
        jLabel19.setText("VNĐ");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Thanh Toán: ");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Trả Lại");

        lbChangeForCustomer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbChangeForCustomer.setForeground(new java.awt.Color(0, 102, 255));
        lbChangeForCustomer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbChangeForCustomer.setText("0");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 255));
        jLabel25.setText("VNĐ");

        lbTotalPay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTotalPay.setForeground(new java.awt.Color(255, 51, 51));
        lbTotalPay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotalPay.setText("0");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 51, 51));
        jLabel27.setText("VNĐ");

        txtService_Price.setForeground(new java.awt.Color(0, 51, 255));
        txtService_Price.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtService_Price.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtService_Price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtService_Price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtService_PriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtService_PriceKeyTyped(evt);
            }
        });

        txtDiscountMoney.setEditable(false);
        txtDiscountMoney.setForeground(new java.awt.Color(0, 0, 255));
        txtDiscountMoney.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtDiscountMoney.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountMoney.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        txtCustomerPay.setForeground(new java.awt.Color(51, 0, 255));
        txtCustomerPay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtCustomerPay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustomerPay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCustomerPay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomerPayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCustomerPayKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Giảm Giá:");

        txtPrepay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPrepay.setForeground(new java.awt.Color(255, 102, 0));
        txtPrepay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrepay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrepayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrepayKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 102, 255));
        jLabel21.setText("VNĐ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtService_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDiscountPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiscountMoney))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPrepay))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCustomerPay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(126, 126, 126))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTotalPay, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbChangeForCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(lbTotal)
                    .addComponent(jLabel19)
                    .addComponent(txtService_Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtDiscountMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiscountPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel18))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtPrepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(txtCustomerPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lbTotalPay)
                            .addComponent(jLabel27)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(lbChangeForCustomer)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        btnPayment.setBackground(new java.awt.Color(0, 204, 255));
        btnPayment.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPayment.setForeground(new java.awt.Color(255, 51, 102));
        btnPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Ok-icon.png"))); // NOI18N
        btnPayment.setText("Thanh Toán");
        btnPayment.setToolTipText("Click tiến hành thao tác thanh toán hóa đơn cho khách hàng");
        btnPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentActionPerformed(evt);
            }
        });

        btnCancelTableUse.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelTableUse.setForeground(new java.awt.Color(51, 51, 255));
        btnCancelTableUse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/delete-icon.png"))); // NOI18N
        btnCancelTableUse.setText("Hủy Sử Dụng");
        btnCancelTableUse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelTableUseActionPerformed(evt);
            }
        });

        btnPrintPreviewInvoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrintPreviewInvoice.setForeground(new java.awt.Color(0, 204, 153));
        btnPrintPreviewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/print-icon.png"))); // NOI18N
        btnPrintPreviewInvoice.setText("In Trước HD");
        btnPrintPreviewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintPreviewInvoiceActionPerformed(evt);
            }
        });

        btnChangeTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChangeTable.setForeground(new java.awt.Color(255, 0, 255));
        btnChangeTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/move-icon.png"))); // NOI18N
        btnChangeTable.setText("Chuyển Bàn");
        btnChangeTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout_invoiceLayout = new javax.swing.GroupLayout(layout_invoice);
        layout_invoice.setLayout(layout_invoiceLayout);
        layout_invoiceLayout.setHorizontalGroup(
            layout_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout_invoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelTableUse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrintPreviewInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChangeTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout_invoiceLayout.createSequentialGroup()
                .addGroup(layout_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout_invoiceLayout.setVerticalGroup(
            layout_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout_invoiceLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelTableUse, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintPreviewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        layout_table.setLayout(new javax.swing.BoxLayout(layout_table, javax.swing.BoxLayout.LINE_AXIS));

        btnReloadTable.setText("Xem Tất Cả / Reload");
        btnReloadTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadTableActionPerformed(evt);
            }
        });

        btnViewTableByLocation.setText("Xem");
        btnViewTableByLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewTableByLocationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(layout_table, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jcomboTableLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewTableByLocation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReloadTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(layout_invoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layout_service, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layout_service, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(layout_invoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layout_table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReloadTable)
                    .addComponent(jcomboTableLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewTableByLocation)))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void tb_invoiceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_invoiceMousePressed
        int index = tb_invoice.getSelectedRow();
        numberOfServiceInvoice = (int) tb_invoice.getValueAt(index, 2);
        idTableService = (int) tb_invoice.getValueAt(index, 0);
        //JOptionPane.showMessageDialog(getRootPane(), idTableService);
    }//GEN-LAST:event_tb_invoiceMousePressed
    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        try {
            tbService.setModel(Service.searchByName(txtSearch.getText(), conn));
            tbService.setRowSelectionInterval(0, 0);
            tbService.getColumnModel().getColumn(2).setCellRenderer(new NumberCellRenderer());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtSearchKeyTyped
    private void txtService_PriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtService_PriceKeyReleased
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtService_Price.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtService_Price.getText().trim().replaceAll("\\.", ""));
                txtService_Price.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtService_Price.getText().trim().replaceAll(",", ""));
                txtService_Price.setText(String.valueOf(df.format(num)));
            }

        }
        totalPay();
    }//GEN-LAST:event_txtService_PriceKeyReleased
    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed
        conn = ConnectDB.conn();
        int result = JOptionPane.showConfirmDialog(null, new PanelSelectCustomer(),
                "Chọn Khách Hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(getRootPane(), Customer.getID());
            if (Customer.getID() > 0) {
                if (TableReservation.updateCustomer(Customer.getID(), idTableReservation, conn)) {
                    setTextLable(idTable);
                }
            }
        }
        conn = null;
    }//GEN-LAST:event_btnAddCustomerActionPerformed
    private void txtCustomerPayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerPayKeyReleased

        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtCustomerPay.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCustomerPay.getText().trim().replaceAll("\\.", ""));
                txtCustomerPay.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCustomerPay.getText().trim().replaceAll(",", ""));
                txtCustomerPay.setText(String.valueOf(df.format(num)));
            }
        }
        totalPay();

    }//GEN-LAST:event_txtCustomerPayKeyReleased
    private void txtService_PriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtService_PriceKeyTyped
        int key = evt.getKeyChar();
        String st = txtService_Price.getText();
        String stTest = "0123456789";
        if (key != evt.VK_BACK_SPACE
                && key != evt.VK_DELETE
                && key != evt.VK_ENTER) {
            int flag = 0;
            if (stTest.indexOf(evt.getKeyChar()) == -1) {
                flag++;
            }
            if (flag > 0) {

                evt.consume();
            }
        }
    }//GEN-LAST:event_txtService_PriceKeyTyped
    private void txtCustomerPayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerPayKeyTyped
        int key = evt.getKeyChar();
        String st = txtCustomerPay.getText();
        String stTest = "0123456789";
        if (key != evt.VK_BACK_SPACE
                && key != evt.VK_DELETE
                && key != evt.VK_ENTER) {
            int flag = 0;
            if (stTest.indexOf(evt.getKeyChar()) == -1) {
                flag++;
            }
            if (flag > 0) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtCustomerPayKeyTyped
    private void btnReloadTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadTableActionPerformed
        idLocation = -1;
        loadTable(idLocation);
    }//GEN-LAST:event_btnReloadTableActionPerformed
    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        try {
            if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn muốn thanh toán hóa đơn này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (billing()) {
                    if (JOptionPane.showConfirmDialog(tb_invoice, "Thanh toán thành công, Bạn muốn in hóa đơn không", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        printInvoice();
                    }
                    loadTableInvoice();
                    loadTable(idLocation);
                    setTextLable(idTable);
                    totalPay();
                    loadTableInvoice();
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Không thể thanh toán");
                }
            } else {
                if (billing()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Thanh toán thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    loadTableInvoice();
                    loadTable(idLocation);
                    setTextLable(idTable);
                    totalPay();
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Không thể thanh toán");
                }
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_btnPaymentActionPerformed
    private void btnViewTableByLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewTableByLocationActionPerformed
        vn.edu.vttu.model.TableLocation tbLocation = (vn.edu.vttu.model.TableLocation) jcomboTableLocation.getSelectedItem();
        idLocation = tbLocation.getId();
        loadTable(idLocation);
    }//GEN-LAST:event_btnViewTableByLocationActionPerformed
    private void btnChangeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeTableActionPerformed
        changeTable();
    }//GEN-LAST:event_btnChangeTableActionPerformed
    private void btnCancelTableUseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelTableUseActionPerformed
        cancelUseTable();
    }//GEN-LAST:event_btnCancelTableUseActionPerformed
    private void btnPrintPreviewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintPreviewInvoiceActionPerformed
        printInvoice();
    }//GEN-LAST:event_btnPrintPreviewInvoiceActionPerformed
    private void lbTotalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lbTotalPropertyChange

    }//GEN-LAST:event_lbTotalPropertyChange
    private void txtDiscountPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountPercentActionPerformed

    }//GEN-LAST:event_txtDiscountPercentActionPerformed
    private void txtPrepayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrepayKeyReleased
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtPrepay.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtPrepay.getText().trim().replaceAll("\\.", ""));
                txtPrepay.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtPrepay.getText().trim().replaceAll(",", ""));
                txtPrepay.setText(String.valueOf(df.format(num)));
            }
        }
        totalPay();
    }//GEN-LAST:event_txtPrepayKeyReleased
    private void txtPrepayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrepayKeyTyped
        int key = evt.getKeyChar();
        String st = txtPrepay.getText();
        String stTest = "0123456789";
        if (key != evt.VK_BACK_SPACE
                && key != evt.VK_DELETE
                && key != evt.VK_ENTER) {
            int flag = 0;
            if (stTest.indexOf(evt.getKeyChar()) == -1) {
                flag++;
            }
            if (flag > 0) {

                evt.consume();
            }
        }
    }//GEN-LAST:event_txtPrepayKeyTyped
    private void tbServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbServiceMouseClicked
        try {
            int index = tbService.getSelectedRow();
            rowTableService_selectEdit = index;
            idService = (int) tbService.getValueAt(index, 0);
            try {
                cost = Integer.parseInt(String.valueOf(tbService.getValueAt(index, 2)).replaceAll(",", ""));
            } catch (Exception e) {
                cost = Integer.parseInt(String.valueOf(tbService.getValueAt(index, 2)).replaceAll("\\.", ""));
            }
            sv_name = String.valueOf(tbService.getValueAt(index, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (evt.getClickCount() == 2) {
            try {
                callService();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_tbServiceMouseClicked

    private void tbListTableRequestMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListTableRequestMouseReleased
        int index = tbListTableRequest.getSelectedRow();
        idRequired = Integer.parseInt(tbListTableRequest.getValueAt(index, 3).toString());
        idTableRequire = Integer.parseInt(tbListTableRequest.getValueAt(index, 0).toString());
    }//GEN-LAST:event_tbListTableRequestMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnCancelTableUse;
    private javax.swing.JButton btnChangeTable;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnPrintPreviewInvoice;
    private javax.swing.JButton btnReloadTable;
    private javax.swing.JButton btnViewTableByLocation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JComboBox jcomboTableLocation;
    private javax.swing.JPanel layout_invoice;
    private javax.swing.JPanel layout_service;
    private javax.swing.JPanel layout_table;
    private javax.swing.JLabel lbBeginDate;
    private javax.swing.JLabel lbChangeForCustomer;
    private javax.swing.JLabel lbCustomerName;
    private javax.swing.JLabel lbStaff;
    private javax.swing.JLabel lbTableName;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbTotalPay;
    private javax.swing.JTable tbListTableRequest;
    private javax.swing.JTable tbService;
    private javax.swing.JTable tb_invoice;
    private javax.swing.JFormattedTextField txtCustomerPay;
    private javax.swing.JFormattedTextField txtDiscountMoney;
    private javax.swing.JTextField txtDiscountPercent;
    private javax.swing.JTextField txtNoteinvoice;
    private javax.swing.JTextField txtPrepay;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JFormattedTextField txtService_Price;
    // End of variables declaration//GEN-END:variables
}

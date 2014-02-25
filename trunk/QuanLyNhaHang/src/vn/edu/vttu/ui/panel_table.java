/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import vn.edu.vttu.data.Customer;
import vn.edu.vttu.data.Invoice;
import vn.edu.vttu.data.Service;
import vn.edu.vttu.data.Servicecost;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.Tablelocation;
import vn.edu.vttu.data.Tablereservation;
import vn.edu.vttu.data.Tablereservationdetail;
import vn.edu.vttu.data.Tableservice;
import vn.edu.vttu.data.VariableStatic;
import vn.edu.vttu.data.connectDB;

/**
 *
 * @author nhphuoc
 */
public class panel_table extends javax.swing.JPanel {

    /**
     * Creates new form panel_main
     */
    private JPopupMenu popupMenu;
    private JPopupMenu popupMenuTableInvoice;
    JPopupMenu popup;
    JScrollPane scrollpane;
    private int idTable;
    private int statusBG = -1;
    private int idTableReservation = -1;
    private int idCustomer = -1;
    private String nameCustomer = "";
    private String beginDate = "";
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

    public panel_table() {
        initComponents();
        popupTableService();
        popupTableInvoice();
        loadTable();
        loadTableService();

        try {

            popup = new JPopupMenu();
            BufferedImage bImg1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/useTable.png"));
            Image image1 = bImg1.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Sử Dụng", new ImageIcon(image1)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        useTable();
                    } catch (SQLException ex) {
                        Logger.getLogger(panel_table.class.getName()).log(Level.SEVERE, null, ex);
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
                    int result = JOptionPane.showConfirmDialog(null, new panel_table_reservation(),
                            "Đặt bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {

                        try {
                            conn = connectDB.conn();
                            conn.setAutoCommit(false);
                            JOptionPane.showMessageDialog(getRootPane(), VariableStatic.getIdCustomer() + "--" + VariableStatic.getDateTimeReservation());
                            if (Tablereservation.insert(false, VariableStatic.getIdCustomer(), VariableStatic.getDateTimeReservation(), conn)) {
                                int maxid_reservation = Tablereservation.getMaxID(conn).getID();
                                if (Tablereservationdetail.insert(idTable, maxid_reservation, conn)) {
                                    int stt;
                                    if (statusTable == 1) {
                                        stt = 1;
                                    } else {
                                        stt = 2;
                                    }
                                    if (Table.updateStatus(idTable, stt, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn thành công");
                                        loadTable();
                                        conn = null;
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            try {
                                conn.rollback();
                                // conn.close();
                                JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn không thành công", "Thông Báo", JOptionPane.OK_OPTION);
                            } catch (SQLException ex1) {
                            }
                        }

                    }
                }
            }
            ));
            BufferedImage bImg2_1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/calendar.png"));
            Image image2_1 = bImg2_1.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(
                    new JMenuItem(new AbstractAction("Xem Thông Tin Đặt Bàn", new ImageIcon(image2_1)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            int result = JOptionPane.showOptionDialog(null, new PanelViewReservation(),
                                    "Xem Danh Sách Đặt Bàn", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, new Object[]{},null);
                            
                        }
                    }));
            BufferedImage bImg2_2 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/cancelUseTable.png"));
            Image image2_2 = bImg2_2.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(
                    new JMenuItem(new AbstractAction("Đặt Nhiều Bàn", new ImageIcon(image2_2)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            
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
                            //new frmAddService(room_id).setVisible(true);
                        }
                    }));
            BufferedImage bImg4 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Stop-reservationtable-icon.png"));
            Image image4 = bImg4.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Hủy Đặt Bàn", new ImageIcon(image4)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            //new frmAddService(room_id).setVisible(true);
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
                            if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn muốn thanh toán hóa đơn này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                try {
                                    if (billing()) {
                                        if (JOptionPane.showConfirmDialog(tb_invoice, "Thanh toán thành công!\nBạn muốn in hóa đơn không", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                            loadTableInvoice();
                                            loadTable();
                                            setTextLable(idTable);
                                            totalPay();
                                        } else {
                                            loadTableInvoice();
                                            loadTable();
                                            setTextLable(idTable);
                                            totalPay();
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(getRootPane(), "Có lỗi xảy ra");
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(panel_table.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }));
            BufferedImage bImg6 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/print-icon.png"));
            Image image6 = bImg6.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("In Trước Hóa Đơn", new ImageIcon(image6)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            //new frmAddService(room_id).setVisible(true);
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
                            //new frmAddService(room_id).setVisible(true);
                        }
                    }));
            BufferedImage bImg8 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/merge-icon.png"));
            Image image8 = bImg8.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Ghép Bàn", new ImageIcon(image8)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            //new frmAddService(room_id).setVisible(true);
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
                            //new frmAddService(room_id).setVisible(true);
                        }
                    }));
            BufferedImage bImg10 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/editicon.png"));
            Image image10 = bImg10.getScaledInstance(18, 18, Image.SCALE_SMOOTH);

            popup.add(
                    new JMenuItem(new AbstractAction("Đổi Tên Bàn", new ImageIcon(image10)) {
                        @Override
                        public void actionPerformed(ActionEvent e
                        ) {
                            //new frmAddService(room_id).setVisible(true);
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
                    new JMenuItem(new AbstractAction("Quản Lý Khu Vực", new ImageIcon(image12)) {
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

    private void loadTable() {
        conn = connectDB.conn();
        layout_table.removeAll();
        enableButton(false);
        JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
        panel.setFont(new Font("Serif", Font.BOLD, 18));
        layout_table.add(panel);
        scrollpane = new JScrollPane(panel);
        layout_table.add(scrollpane, BorderLayout.CENTER);

        try {
            Table table[] = Table.getAll(conn);
            int numberTable = table.length;
            final JLabel bt[] = new JLabel[numberTable];
            for (int i = 0; i < numberTable; i++) {
                bt[i] = new JLabel(table[i].getNAME());
                //bt[i].setFont(new Font("Serif", Font.BOLD, 18));
                if (table[i].getSTATUS() == 1) {// đang có khách 
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon_thue.png"));
                    Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    bt[i].setIcon(icon);
                    bt[i].setPreferredSize(new Dimension(50, 50));
                } else if (table[i].getSTATUS() == 2) {// đặt bàn trước
                    BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon_datban.png"));
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
                bt[i].setToolTipText("<html>Thông tin bàn ăn<br>"
                        + "Thông tin bàn ăn 2</html>");
                // bt[i].add(popup);
                final int idtb = table[i].getID();
                final int status = table[i].getSTATUS();

                final int x = i;
                bt[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
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
                            popup.getComponent(7).setEnabled(false);// Hủy Đặt Bàn
                            // line
                            popup.getComponent(9).setEnabled(false);//Thanh Toán
                            popup.getComponent(10).setEnabled(false);//In Trước Hóa Đơn
                            //line
                            popup.getComponent(12).setEnabled(false);// Chuyển Bàn
                            popup.getComponent(13).setEnabled(false);// Ghép Bàn
                            //line
                            popup.getComponent(15).setEnabled(false);//Thêm Bàn
                            popup.getComponent(16).setEnabled(false);// Đổi Tên Bàn
                            popup.getComponent(17).setEnabled(false);// Xóa Bàn  
                            //line
                            popup.getComponent(19).setEnabled(false);// Quản lý khu vực      
                            if (status == 1) { // bàn đang được sử dụng
                                popup.getComponent(0).setEnabled(false);// Sử dụng
                                //line
                                popup.getComponent(2).setEnabled(true); //Đặt Bàn
                                popup.getComponent(3).setEnabled(true); //Xem Thông tin đặt bàn
                                popup.getComponent(4).setEnabled(true); //Đặt nhiều bàn
                                //line
                                popup.getComponent(6).setEnabled(true); //Hủy Sử Dụng
                                popup.getComponent(7).setEnabled(false);// Hủy Đặt Bàn
                                // line
                                popup.getComponent(9).setEnabled(true);//Thanh Toán
                                popup.getComponent(10).setEnabled(true);//In Trước Hóa Đơn
                                //line
                                popup.getComponent(12).setEnabled(true);// Chuyển Bàn
                                popup.getComponent(13).setEnabled(true);// Ghép Bàn
                                //line
                                popup.getComponent(15).setEnabled(true);//Thêm Bàn
                                popup.getComponent(16).setEnabled(true);// Đổi Tên Bàn
                                popup.getComponent(17).setEnabled(false);// Xóa Bàn  
                                //line
                                popup.getComponent(19).setEnabled(true);// Quản lý khu vực                    
                            } else if (status == 2) { // bàn được đặt
                                popup.getComponent(0).setEnabled(true);// Sử dụng
                                //line
                                popup.getComponent(2).setEnabled(true); //Đặt Bàn
                                popup.getComponent(3).setEnabled(true); //Xem Thông tin đặt bàn
                                popup.getComponent(4).setEnabled(true); //Đặt nhiều bàn
                                //line
                                popup.getComponent(6).setEnabled(false); //Hủy Sử Dụng
                                popup.getComponent(7).setEnabled(true);// Hủy Đặt Bàn
                                // line
                                popup.getComponent(9).setEnabled(false);//Thanh Toán
                                popup.getComponent(10).setEnabled(false);//In Trước Hóa Đơn
                                //line
                                popup.getComponent(12).setEnabled(true);// Chuyển Bàn
                                popup.getComponent(13).setEnabled(false);// Ghép Bàn
                                //line
                                popup.getComponent(15).setEnabled(true);//Thêm Bàn
                                popup.getComponent(16).setEnabled(true);// Đổi Tên Bàn
                                popup.getComponent(17).setEnabled(false);// Xóa Bàn  
                                //line
                                popup.getComponent(19).setEnabled(true);// Quản lý khu vực   
                            } else { //bàn chưa sử dụng
                                popup.getComponent(0).setEnabled(true);// Sử dụng
                                //line
                                popup.getComponent(2).setEnabled(true); //Đặt Bàn
                                popup.getComponent(3).setEnabled(true); //Xem Thông tin đặt bàn
                                popup.getComponent(4).setEnabled(true); //Đặt nhiều bàn
                                //line
                                popup.getComponent(6).setEnabled(false); //Hủy Sử Dụng
                                popup.getComponent(7).setEnabled(false);// Hủy Đặt Bàn
                                // line
                                popup.getComponent(9).setEnabled(false);//Thanh Toán
                                popup.getComponent(10).setEnabled(false);//In Trước Hóa Đơn
                                //line
                                popup.getComponent(12).setEnabled(false);// Chuyển Bàn
                                popup.getComponent(13).setEnabled(false);// Ghép Bàn
                                //line
                                popup.getComponent(15).setEnabled(true);//Thêm Bàn
                                popup.getComponent(16).setEnabled(true);// Đổi Tên Bàn
                                popup.getComponent(17).setEnabled(true);// Xóa Bàn  
                                //line
                                popup.getComponent(19).setEnabled(true);// Quản lý khu vực
                            }
                        }
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (status == 1) {
                                enableButton(true);
                            } else if (status == 2) {
                                btnAddCustomer.setEnabled(true);
                                btnChangeTable.setEnabled(true);
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
                            conn = connectDB.conn();
                            VariableStatic.setNameTable(Table.getByID(idTable, conn).getNAME());
                            conn = null;
                        }
                    }
                });
                panel.add(bt[i]);
            }
            layout_table.updateUI();
            layout_table.repaint();
            conn = null;
        } catch (Exception e) {
            conn = null;
            e.printStackTrace();
        }
    }

    private void loadTableService() {
        conn = connectDB.conn();
        tbService.setModel(Service.getAllService(conn));
        tbService.setRowSelectionInterval(0, 0);
    }

    private void popupTableService() {
        try {

            popupMenu = new JPopupMenu();
            BufferedImage bImgCalService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/back-icon.png"));
            Image imageCalService = bImgCalService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenu.add(new JMenuItem(new AbstractAction("Gọi dịch vụ này", new ImageIcon(imageCalService)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    conn = connectDB.conn();
                    String number = JOptionPane.showInputDialog(tbService, "Số Lượng", 1);
                    if (Tableservice.getServiceByIdService_ByIdReservation(idService, idTableReservation, conn)) {
                        if (Tableservice.insert(idTableReservation, idService, Integer.parseInt(number), cost, conn)) {
                            loadTableInvoice();
                            totalPay();
                        }
                    } else {
                        if (Tableservice.update(idTableReservation, idService, Integer.parseInt(number), conn)) {
                            loadTableInvoice();
                        }
                    }
                }
            }));

            popupMenu.addSeparator();
            BufferedImage bImgAddService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/add-icon.png"));
            Image imageAddService = bImgAddService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenu.add(new JMenuItem(new AbstractAction("Thêm Dịch Vụ", new ImageIcon(imageAddService)) {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            }));
            BufferedImage bImgServiceChangeName = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/editicon.png"));
            Image imageServiceChangeName = bImgServiceChangeName.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenu.add(new JMenuItem(new AbstractAction("Đổi Tên", new ImageIcon(imageServiceChangeName)) {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String name = JOptionPane.showInputDialog(tbService, "Nhập tên dịch vụ", sv_name);
                    if (name != null) {
                        if (Service.updateName(name, idService, conn)) {
                            loadTableService();
                        }
                    }
                }
            }));
            BufferedImage bImgServiceCost = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/dollar-icon.png"));
            Image imageServiceCost = bImgServiceCost.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenu.add(new JMenuItem(new AbstractAction("Cập Nhật Giá", new ImageIcon(imageServiceCost)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String costnew = JOptionPane.showInputDialog(tbService, "Nhập Giá", cost);
                    if (costnew != null) {
                        if (Servicecost.insert(idService, Integer.parseInt(costnew), conn)) {
                            loadTableService();
                        }
                    }
                }
            }));
            conn = null;
        } catch (Exception e) {
            conn = null;
            e.printStackTrace();
        }
        tbService.setComponentPopupMenu(popupMenu);
    }

    private void popupTableInvoice() {
        try {
            conn = connectDB.conn();
            popupMenuTableInvoice = new JPopupMenu();
            BufferedImage bImgCalService = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/move-icon.png"));
            Image imageCalService = bImgCalService.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenuTableInvoice.add(new JMenuItem(new AbstractAction("Cập Nhật Số Lượng", new ImageIcon(imageCalService)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String num = JOptionPane.showInputDialog(tb_invoice, "Nhập số lượng", numberOfServiceInvoice);
                    if (Tableservice.update(idTableService, Integer.parseInt(num), conn)) {
                        loadTableInvoice();
                        totalPay();
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Lỗi, Vui lòng thực hiện lại");
                    }
                }
            }));
            BufferedImage bImgDel = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/delete-icon.png"));
            Image imageDel = bImgDel.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenuTableInvoice.add(new JMenuItem(new AbstractAction("Xóa Dịch Vụ", new ImageIcon(imageDel)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    conn=connectDB.conn();
                    if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn muốn hủy dịch vụ này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (Tableservice.delete(idTableService, conn)) {
                            loadTableInvoice();
                            totalPay();
                        } else {
                            JOptionPane.showMessageDialog(tb_invoice, "Đã xảy ra lỗi. Vui lòng thực hiên lại");
                        }
                    }
                }
            }));
            popupMenuTableInvoice.addSeparator();
            BufferedImage bImgReload = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon.png"));
            Image imageReload = bImgReload.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupMenuTableInvoice.add(new JMenuItem(new AbstractAction("Cập Nhật", new ImageIcon(imageReload)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadTableInvoice();
                    totalPay();
                }
            }));
            tb_invoice.setComponentPopupMenu(popupMenuTableInvoice);
            conn = null;
        } catch (Exception e) {
            conn = null;
        }
    }

    private void useTable() throws SQLException {
        conn = connectDB.conn();
        try {
            Date dt = new Date();
            conn.setAutoCommit(false);
            if (Tablereservation.insert(true, conn)) {
                int maxid_reservation = Tablereservation.getMaxID(conn).getID();
                //JOptionPane.showMessageDialog(getRootPane(), maxid_reservation);
                if (Tablereservationdetail.insert(idTable, maxid_reservation, conn)) {
                    if (Table.updateStatus(idTable, 1, conn)) {
                        conn.commit();
                        loadTable();                        
                    }
                }
            }
            conn = null;
        } catch (Exception e) {
            conn.rollback();
            conn = null;
            JOptionPane.showMessageDialog(getRootPane(), "Không Thành Công");
        }
    }

    private void setTextLable(int id) {
        conn = connectDB.conn();
        Tablereservation tbReser = Tablereservation.getByTableByStatus(id, conn);
        beginDate = tbReser.getBeginDate();
        idCustomer = tbReser.getCUSTOMER();
        nameCustomer = tbReser.getCUSTOMER_NAME();
        idTableReservation = tbReser.getID();
        // setText lable 
        if (beginDate.equals("Chưa sử dụng")) {
            lbBeginDate.setText(beginDate);
        } else {
            String s[] = beginDate.split(" ");
            String d = s[0];
            String t = s[1];
            String d1[] = d.split("-");
            String t1[] = t.split(":");
            String datetime = t1[0] + ":" + t1[1] + " - " + d1[2] + "/" + d1[1] + "/" + d1[0];
            lbBeginDate.setText(datetime);

        }
        lbCustomerName.setText(nameCustomer);
        lbTableName.setText(Table.getByID(id, conn).getNAME() + " - " + Table.getByID(id, conn).getNAME_LOCATION());
        conn = null;
    }

    private void enableButton(boolean b) {
        btnAddCustomer.setEnabled(b);
        btnCancelTableUse.setEnabled(b);
        btnChangeTable.setEnabled(b);
        btnPayment.setEnabled(b);
        btnPrintPreviewInvoice.setEnabled(b);
        popupMenu.getComponent(0).setEnabled(b);

    }

    private void loadTableInvoice() {
        conn = connectDB.conn();
        tb_invoice.setModel(Tableservice.getByIdReservation(idTableReservation, conn));
        if (tb_invoice.getRowCount() > 0) {
            tb_invoice.setRowSelectionInterval(0, 0);
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

    private void totalPay() {
        conn = connectDB.conn();
        DecimalFormat df = new DecimalFormat("#,###,###");
        float service_Charges = 0;
        int discount_money = 0;
        int discount_percent = 0;
        int customer_pay = 0;
        double x;
        if (!txtService_Price.getText().equals("") && testNumber(txtService_Price.getText())) {
            service_Charges = Float.parseFloat(txtService_Price.getValue().toString());

        }
        if (!txtService_Price.getText().equals("")) {
            service_Charges = Float.parseFloat(txtService_Price.getValue().toString());

        }
        if (!txtDiscountMoney.getText().equals("")) {
            discount_money = Integer.parseInt(txtDiscountMoney.getValue().toString());

        }
        if (!txtDiscountPercent.getText().equals("")) {
            if (Integer.parseInt(txtDiscountPercent.getText()) < 100 && Integer.parseInt(txtDiscountPercent.getText()) >= 0) {
                discount_percent = Integer.parseInt(txtDiscountPercent.getText());
            } else {
                JOptionPane.showMessageDialog(txtDiscountPercent, "Nhập sai phần trăm");
                txtDiscountPercent.setText("100");
            }

        }
        if (!txtCustomerPay.getText().equals("")) {
            customer_pay = Integer.parseInt(txtCustomerPay.getValue().toString());
        }

        float total = Tableservice.totalPayment(idTableReservation, conn);
        //JOptionPane.showMessageDialog(getRootPane(), service_Charges);
        lbTotal.setText(String.valueOf(df.format(total)));
        float _discount = (discount_percent * total) / 100;
        if (total == 0) {
            totalPay = 0;
        } else {
            totalPay = (int) ((total + service_Charges) - (discount_money + _discount));
        }

        lbTotalPay.setText(String.valueOf(df.format(totalPay)));
        total = Tableservice.totalPayment(idTableReservation, conn);
        lbTotal.setText(String.valueOf(df.format(total)));
        discount = (int) (_discount + discount_money);

        if (customer_pay < totalPay) {
            lbChangeForCustomer.setText("Thiếu: " + String.valueOf(df.format(Math.abs(customer_pay - totalPay))));
        } else {
            lbChangeForCustomer.setText(df.format(customer_pay - totalPay));
        }
        conn = null;
    }

    private boolean billing() throws SQLException {
        /*
         1. update cac dich vu cua ban ve trang thai status=false
         2. update ngay ket thuc endDate cua table_reservation=now()
         3. update status ban
         3.1. Neu ban do khong co dat truoc. status=0
         3.2. Neu ban do co dat truoc. status=2
         */
        conn = connectDB.conn();
        boolean flag = false;
        try {
            conn.setAutoCommit(false);
            if (Tableservice.updateStstus(idTableReservation, conn)) {
                if (Tablereservation.updateEndDate(idTableReservation, conn)) {
                    if (Invoice.insert(idTableReservation, 1, totalPay, discount, txtNoteinvoice.getText(), conn)) {
                        if (Tablereservation.countidTableReservation(idTable, conn) > 0) {
                            if (Table.updateStatus(idTable, 2, conn)) {
                                conn.commit();
                                flag = true;
                            }
                        } else {
                            if (Table.updateStatus(idTable, 0, conn)) {
                                conn.commit();
                                flag = true;
                            }
                        }
                    }
                }
            }
            conn = null;
        } catch (Exception e) {
            flag = false;
            conn.rollback();
            conn = null;
        }
        return flag;
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
        btnPayment = new javax.swing.JButton();
        btnCancelTableUse = new javax.swing.JButton();
        btnPrintPreviewInvoice = new javax.swing.JButton();
        btnChangeTable = new javax.swing.JButton();
        layout_table = new javax.swing.JPanel();
        btnReloadTable = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        tbService.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbServiceMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbService);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout_serviceLayout = new javax.swing.GroupLayout(layout_service);
        layout_service.setLayout(layout_serviceLayout);
        layout_serviceLayout.setHorizontalGroup(
            layout_serviceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout_serviceLayout.createSequentialGroup()
                .addGroup(layout_serviceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(txtSearch))
                .addContainerGap())
        );
        layout_serviceLayout.setVerticalGroup(
            layout_serviceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout_serviceLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin"));

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

        tb_invoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        tb_invoice.setSelectionForeground(new java.awt.Color(255, 51, 51));
        tb_invoice.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tb_invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tb_invoiceMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tb_invoice);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Thanh Toán"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Phí Dịch Vụ:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 255));
        jLabel11.setText("VNĐ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 255));
        jLabel13.setText("VNĐ");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Giảm Giá:");

        txtDiscountPercent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscountPercent.setForeground(new java.awt.Color(0, 102, 255));
        txtDiscountPercent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountPercent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountPercentKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountPercentKeyTyped(evt);
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtService_PriceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtService_PriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtService_PriceKeyTyped(evt);
            }
        });

        txtDiscountMoney.setForeground(new java.awt.Color(0, 0, 255));
        txtDiscountMoney.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtDiscountMoney.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscountMoney.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscountMoney.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountMoneyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountMoneyKeyTyped(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiscountPercent, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(txtCustomerPay))
                        .addGap(5, 5, 5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiscountMoney)
                            .addComponent(txtService_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(126, 126, 126)
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
                            .addComponent(txtDiscountMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(txtDiscountPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jLabel14)
                        .addGap(20, 20, 20)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(lbChangeForCustomer)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
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

        btnPrintPreviewInvoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrintPreviewInvoice.setForeground(new java.awt.Color(0, 204, 153));
        btnPrintPreviewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/print-icon.png"))); // NOI18N
        btnPrintPreviewInvoice.setText("In Trước HD");

        btnChangeTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChangeTable.setForeground(new java.awt.Color(255, 0, 255));
        btnChangeTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/move-icon.png"))); // NOI18N
        btnChangeTable.setText("Chuyển Bàn");

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

        btnReloadTable.setText("Cập Nhật Danh Sách Bàn");
        btnReloadTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadTableActionPerformed(evt);
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
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReloadTable)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(layout_invoice, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReloadTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbServiceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbServiceMousePressed
        try {
            int index = tbService.getSelectedRow();
            rowTableService_selectEdit = index;
            idService = (int) tbService.getValueAt(index, 0);
            cost = (int) tbService.getValueAt(index, 2);
            sv_name = String.valueOf(tbService.getValueAt(index, 1));
        } catch (Exception e) {
        }


    }//GEN-LAST:event_tbServiceMousePressed

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
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtService_PriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtService_PriceKeyReleased
        try {
            txtService_Price.commitEdit();
            txtService_Price.setText(txtService_Price.getValue().toString());
            totalPay();

        } catch (ParseException ex) {
            Logger.getLogger(panel_table.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_txtService_PriceKeyReleased

    private void txtDiscountPercentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountPercentKeyReleased
        totalPay();
    }//GEN-LAST:event_txtDiscountPercentKeyReleased

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed

        int result = JOptionPane.showConfirmDialog(null, new panel_select_customer(),
                "Chọn Khách Hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(getRootPane(), Customer.getID());
            if (Customer.getID() > 0) {
                if (Tablereservation.updateCustomer(Customer.getID(), idTableReservation, conn)) {
                    setTextLable(idTable);
                }
            }
        }
    }//GEN-LAST:event_btnAddCustomerActionPerformed

    private void txtService_PriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtService_PriceKeyPressed
        totalPay();
    }//GEN-LAST:event_txtService_PriceKeyPressed

    private void txtDiscountMoneyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountMoneyKeyReleased
        try {
            txtDiscountMoney.commitEdit();
            totalPay();

        } catch (ParseException ex) {
            Logger.getLogger(panel_table.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_txtDiscountMoneyKeyReleased
    private void txtCustomerPayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerPayKeyReleased
        try {
            txtCustomerPay.commitEdit();
            totalPay();

        } catch (ParseException ex) {
            Logger.getLogger(panel_table.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

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

    private void txtDiscountMoneyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountMoneyKeyTyped
        int key = evt.getKeyChar();
        String st = txtDiscountMoney.getText();
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
    }//GEN-LAST:event_txtDiscountMoneyKeyTyped

    private void txtDiscountPercentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountPercentKeyTyped
        int key = evt.getKeyChar();
        String st = txtDiscountPercent.getText();
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
    }//GEN-LAST:event_txtDiscountPercentKeyTyped

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
        loadTable();
    }//GEN-LAST:event_btnReloadTableActionPerformed

    private void btnPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentActionPerformed
        if (JOptionPane.showConfirmDialog(tb_invoice, "Bạn muốn thanh toán hóa đơn này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (billing()) {
                    if (JOptionPane.showConfirmDialog(tb_invoice, "Thanh toán thành công!\nBạn muốn in hóa đơn không", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        loadTableInvoice();
                        loadTable();
                    } else {
                        loadTableInvoice();
                        loadTable();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(panel_table.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnPaymentActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnCancelTableUse;
    private javax.swing.JButton btnChangeTable;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnPrintPreviewInvoice;
    private javax.swing.JButton btnReloadTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
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
    private javax.swing.JTable tbService;
    private javax.swing.JTable tb_invoice;
    private javax.swing.JFormattedTextField txtCustomerPay;
    private javax.swing.JFormattedTextField txtDiscountMoney;
    private javax.swing.JTextField txtDiscountPercent;
    private javax.swing.JTextField txtNoteinvoice;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JFormattedTextField txtService_Price;
    // End of variables declaration//GEN-END:variables
}

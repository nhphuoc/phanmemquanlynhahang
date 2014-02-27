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
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.TableReservationDetail;
import vn.edu.vttu.data.VariableStatic;
import vn.edu.vttu.data.ConnectDB;

/**
 *
 * @author nhphuoc
 */
public class PanelViewReservation extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

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
    private int idreservation;
    private int idreservation_detail;
    private String tableName;
    private int statusTable;
    private int idTable;
    private boolean edit = false;

    public PanelViewReservation() {
        initComponents();
        //dtChangeDateReservation.setDate(new Date());
        loadListTable();
        popupTable();
        //btnEdit.setEnabled(true);
        //btnSave.setEnabled(false);
        tbListTable.setEnabled(true);

    }

    private void loadListTable() {
        try {
            conn = ConnectDB.conn();
            tbListTable.setModel(TableReservation.getListTableReservation(conn));
            tbListTable.setRowSelectionInterval(0, 0);
            tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

            tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
            tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
        } catch (Exception e) {
        }

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
                        conn.setAutoCommit(false);

                        if (statusTable == 1) {
                            JOptionPane.showMessageDialog(getRootPane(), "Bàn đang được sử dụng vui lòng chọn bàn khác");
                        } else {
                            if (Table.updateStatus(tableName, 1, conn)) {
                                if (TableReservation.updateBeginDate(idreservation, conn)) {
                                    if (TableReservation.updateStatus(idreservation, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã nhận bàn thành công");
                                        loadListTable();
                                    }

                                }
                            }
                        }
                        conn = null;
                    } catch (SQLException ex) {
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
                        conn.setAutoCommit(false);
                        if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn muốn hủy đơn đặt bàn này?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            if (TableReservation.reservationCancel(idreservation, conn)) {
                                if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                    if (Table.updateStatus(idTable, 2, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                        loadListTable();
                                    }
                                } else {
                                    if (Table.updateStatus(idTable, 0, conn)) {
                                        conn.commit();
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                        loadListTable();
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        try {
                            conn.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(PanelViewReservation.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }

                    conn = null;
                }
            }));
            BufferedImage bImgUpdate = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/editicon.png"));
            Image imageUpdate = bImgUpdate.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenu.add(new JMenuItem(new AbstractAction("Cập Nhật Thông Tin Đặt Bàn", new ImageIcon(imageUpdate)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, new PanelChangeReservation(),
                            "Đặt bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        int id = VariableStatic.getIdTable();
                        String date = VariableStatic.getDateTimeReservation();
                        JOptionPane.showMessageDialog(getRootPane(), "Mã Bàn:" + id + "\nMã Reservation:" + idreservation
                                + "\nMã Chi tiết:" + idreservation_detail + "\nNgày Đặt: " + date);
                        try {
                            conn = ConnectDB.conn();
                            conn.setAutoCommit(false);
                            if (TableReservation.updateBeginDate(idreservation, date, conn)) {
                                if (TableReservationDetail.updateTable(idreservation_detail, id, conn)) {
                                    conn.commit();
                                    JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công");
                                    loadListTable();
                                } else {
                                    JOptionPane.showMessageDialog(getRootPane(), "2");
                                }
                            } else {
                                JOptionPane.showMessageDialog(getRootPane(), "1");
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
            }));
            popupmenu.addSeparator();
            BufferedImage bImgReload = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon.png"));
            Image imageReload = bImgReload.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenu.add(new JMenuItem(new AbstractAction("Reload", new ImageIcon(imageReload)) {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            }));
            tbListTable.setComponentPopupMenu(popupmenu);
        } catch (Exception e) {
            e.printStackTrace();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tbListTable = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnView = new javax.swing.JButton();

        tbListTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tbListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
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

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 204, 51));

        jDateChooser1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btnView.setText("Xem");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnView))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnView)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                .addContainerGap())
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
        Date dt = new Date(a);

        String x = c1 + "/" + c2 + "/" + c3 + " " + e + ":" + f + ":00";
        VariableStatic.setDateTimeReservation(x);
        VariableStatic.setIdTable(Integer.parseInt(tbListTable.getValueAt(index, 5).toString()));
        //JOptionPane.showMessageDialog(getRootPane(), Integer.parseInt(tbListTable.getValueAt(index, 5).toString()));

        conn = null;

    }//GEN-LAST:event_tbListTableMousePressed

    private void tbListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListTableMouseClicked
        // int index = tbListTable.getSelectedRow();
        //JOptionPane.showMessageDialog(getRootPane(), String.valueOf(tbListTable.getValueAt(index, 1)));
    }//GEN-LAST:event_tbListTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnView;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tbListTable;
    // End of variables declaration//GEN-END:variables
}

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
            if (tbListTable.getRowCount() <= 0) {
                tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

                tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            } else {
                tbListTable.setRowSelectionInterval(0, 0);
                tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

                tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            }
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
                                    if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                        if (Table.updateStatus(idTable, 1, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                            loadListTable();
                                        }
                                    } else {
                                        if (Table.updateStatus(idTable, 2, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Đã hủy đặt bàn thành công");
                                            loadListTable();
                                        }
                                    }
                                } else {
                                    if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                        if (Table.updateStatus(idTable, 1, conn)) {
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
                            "Cập nhật thông tin đặt bàn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        int id = VariableStatic.getIdTable();
                        Timestamp date = VariableStatic.getDateTimeReservation();
                        JOptionPane.showMessageDialog(getRootPane(), "Mã Bàn:" + id + "\nMã Reservation:" + idreservation
                                + "\nMã Chi tiết:" + idreservation_detail + "\nNgày Đặt: " + date);
                        try {
                            conn = ConnectDB.conn();
                            conn.setAutoCommit(false);
                            if (TableReservation.updateBeginDate(idreservation, date, conn)) {
                                if (TableReservationDetail.updateTable(idreservation_detail, id, conn)) {
                                    if (TableReservation.countidTableUsing(id, conn) > 0) {
                                        if (Table.updateStatus(id, 1, conn)) {
                                            conn.commit();
                                            loadListTable();
                                        }
                                    } else {
                                        if (TableReservation.countidTableReservation(id, conn) > 0) {
                                            if (Table.updateStatus(id, 2, conn)) {
                                                conn.commit();
                                                loadListTable();
                                            }
                                        } else {
                                            if (Table.updateStatus(id, 0, conn)) {
                                                conn.commit();
                                                loadListTable();
                                            }
                                        }
                                    }

                                    conn.setAutoCommit(false);
                                    if (TableReservation.countidTableUsing(idTable, conn) > 0) {
                                        if (Table.updateStatus(idTable, 1, conn)) {
                                            conn.commit();
                                            JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công");
                                            loadListTable();
                                        }
                                    } else {
                                        if (TableReservation.countidTableReservation(idTable, conn) > 0) {
                                            if (Table.updateStatus(idTable, 2, conn)) {
                                                conn.commit();
                                                JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công");
                                                loadListTable();
                                            }
                                        } else {
                                            if (Table.updateStatus(idTable, 0, conn)) {
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
            }));
            popupmenu.addSeparator();
            BufferedImage bImgReload = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon.png"));
            Image imageReload = bImgReload.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popupmenu.add(new JMenuItem(new AbstractAction("Reload", new ImageIcon(imageReload)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadListTable();
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
        txtSearchReservation = new javax.swing.JTextField();
        dtSearch = new com.toedter.calendar.JDateChooser();
        btnView = new javax.swing.JButton();

        tbListTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tbListTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "", "Chưa có", null, null}
            },
            new String [] {
                "ID", "Bàn", "Khách Hàng", "SĐT", "Ngày Nhận"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtSearchReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(txtSearchReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnView)
                            .addComponent(dtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        String x = c3 + "-" + c2 + "-" + c1 + " " + e + ":" + f + ":00";
        Timestamp ts = Timestamp.valueOf(x);
        VariableStatic.setDateTimeReservation(ts);
        VariableStatic.setIdTable(Integer.parseInt(tbListTable.getValueAt(index, 5).toString()));
        //JOptionPane.showMessageDialog(getRootPane(), Integer.parseInt(tbListTable.getValueAt(index, 5).toString()));

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
                tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

                tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            } else {
                tbListTable.setRowSelectionInterval(0, 0);
                tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

                tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            }
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
                tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

                tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            } else {
                tbListTable.setRowSelectionInterval(0, 0);
                tbListTable.getColumnModel().getColumn(5).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMinWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMinWidth(0);

                tbListTable.getColumnModel().getColumn(5).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(6).setMaxWidth(0);
                tbListTable.getColumnModel().getColumn(7).setMaxWidth(0);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnViewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnView;
    private com.toedter.calendar.JDateChooser dtSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbListTable;
    private javax.swing.JTextField txtSearchReservation;
    // End of variables declaration//GEN-END:variables
}

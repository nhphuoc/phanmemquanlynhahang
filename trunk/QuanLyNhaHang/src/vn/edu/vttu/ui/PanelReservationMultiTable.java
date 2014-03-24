/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Customer;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableLocation;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.TableReservationDetail;
import vn.edu.vttu.sqlite.tbRestaurant;

/**
 *
 * @author nhphuoc
 */
public class PanelReservationMultiTable extends javax.swing.JPanel {

    class ItemRendererTable extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                vn.edu.vttu.model.Customer item = (vn.edu.vttu.model.Customer) value;
                setText(item.getName());
            }
            if (index == -1) {
                vn.edu.vttu.model.Customer item = (vn.edu.vttu.model.Customer) value;
                setText("" + item.getName());
            }
            return this;
        }
    }

    class ItemRendererLocation extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                vn.edu.vttu.model.TableLocation item = (vn.edu.vttu.model.TableLocation) value;
                setText(item.getName());
            }

            if (index == -1) {
                vn.edu.vttu.model.TableLocation item = (vn.edu.vttu.model.TableLocation) value;
                setText("" + item.getName());
            }

            return this;
        }
    }

    /**
     * Creates new form PanelReservationMultiTable
     */
    private Connection conn;
    private int idTable;
    private String listTable = "";
    private int idlocation;

    public PanelReservationMultiTable() {
        initComponents();
        fillComboCustomer();
        dtDateReservation.setDate(new Date());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(dtDateReservation.getDate());
        Timestamp ts = Timestamp.valueOf(datetime);
        fillkhCombo();
        vn.edu.vttu.model.TableLocation tb = (vn.edu.vttu.model.TableLocation) cobLocation.getSelectedItem();
        idlocation = tb.getId();
        loadTable(ts, idlocation);
    }

    private void loadTable(Timestamp ts, int location) {
        conn = ConnectDB.conn();
        tbListTable.setModel(Table.getByDateNotReservation(ts, tbRestaurant.getValues().getHourReservationParty(), location, conn));
        try {
            tbListTable.setRowSelectionInterval(0, 0);
        } catch (Exception e) {
        }
        conn = null;
    }

    private void fillComboCustomer() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.Customer> model = new Vector<vn.edu.vttu.model.Customer>();
        try {
            model = Customer.selectCustomer(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobCustomer.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobCustomer.setModel(defaultComboBoxModel);
        cobCustomer.setRenderer(new PanelReservationMultiTable.ItemRendererTable());
        conn = null;
    }

    private void fillkhCombo() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.TableLocation> model = new Vector<vn.edu.vttu.model.TableLocation>();
        try {
            model = TableLocation.selectTableLocation(ConnectDB.conn());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobLocation.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobLocation.setModel(defaultComboBoxModel);
        cobLocation.setRenderer(new PanelReservationMultiTable.ItemRendererLocation());
    }

    private boolean testDate(Timestamp date) {
        boolean flag = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(new Date());
        Timestamp ts = Timestamp.valueOf(datetime);
        if (date.compareTo(ts) < 0) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean reservationTable(Timestamp ts, int idCustomer) {
        boolean flag = false;
        if (testDate(ts) == false) {
            JOptionPane.showMessageDialog(getRootPane(), "Thời gian đặt phải lớn hơn thời gian hiện tại","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                int prepay = 0;
                if (!txtPrepay.getText().trim().equals("")) {

                    try {
                        prepay = Integer.parseInt(txtPrepay.getText().trim().replaceAll("\\.", ""));
                    } catch (Exception e) {
                        prepay = Integer.parseInt(txtPrepay.getText().trim().replaceAll(",", ""));
                    }
                }
                if (TableReservation.insert(false, idCustomer, ts, true, prepay, conn)) {
                    int maxid_reservation = TableReservation.getMaxID(conn).getID();
                    for (int i = 0; i < tbListTableReservation.getRowCount(); i++) {
                        int idtb = Integer.parseInt(String.valueOf(tbListTableReservation.getValueAt(i, 0)));
                        if (TableReservationDetail.insert(idtb, maxid_reservation, conn)) {
                            int stt;
                            if (TableReservation.countidTableUsing(idtb, conn) > 0) {
                                stt = 2;
                            } else {
                                if (TableReservation.countidTableReservation(idtb, conn) > 0) {
                                    stt = 3;
                                } else {
                                    stt = 1;
                                }
                            }
                            if (Table.updateStatus(idtb, stt, conn)) {

                            } else {
                                throw new Exception();
                            }
                        } else {
                            throw new Exception();
                        }
                    }//end for
                    conn.commit();
                    flag = true;
                }
            } catch (Exception ex) {
                try {
                    conn.rollback();
                    flag = false;
                } catch (SQLException ex1) {
                }
            }
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

        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListTableReservation = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dtDateReservation = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        cobCustomer = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cobLocation = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtPrepay = new javax.swing.JTextField();

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
        btnSave.setText("Lưu Lại");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        tbListTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Bàn", "Tên Bàn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
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
        tbListTable.setGridColor(new java.awt.Color(204, 204, 204));
        tbListTable.setRowHeight(23);
        tbListTable.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListTable);

        tbListTableReservation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Bàn", "Tên Bàn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
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
        tbListTableReservation.setGridColor(new java.awt.Color(204, 204, 204));
        tbListTableReservation.setRowHeight(23);
        tbListTableReservation.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbListTableReservation.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbListTableReservation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListTableReservationMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbListTableReservation);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 153, 0));
        jLabel4.setText("DANH SÁCH BÀN");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 153, 0));
        jLabel5.setText("BÀN ĐƯỢC ĐẶT");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Đặt Bàn"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 0));
        jLabel1.setText("Ngày Nhận");

        dtDateReservation.setDateFormatString("dd/MM/yyyy HH:mm");
        dtDateReservation.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtDateReservationPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 153, 0));
        jLabel2.setText("Khách Hàng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 153, 0));
        jLabel3.setText("Vị Trí");

        cobLocation.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cobLocationPropertyChange(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 153, 0));
        jLabel6.setText("Đưa Trước:");

        txtPrepay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrepayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrepayKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtDateReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cobLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrepay)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dtDateReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(cobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cobLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtPrepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(173, 173, 173))))
            .addGroup(layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(btnSave)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (listTable != null) {
            if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có chắc chắn cho đặt trước những bàn trong danh sách không?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                vn.edu.vttu.model.Customer tb = (vn.edu.vttu.model.Customer) cobCustomer.getSelectedItem();
                int idCustomer = tb.getId();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datetime = formatter.format(dtDateReservation.getDate());
                Timestamp ts = Timestamp.valueOf(datetime);
                if (reservationTable(ts, idCustomer)) {
                    JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn thành công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                    DefaultTableModel dm = (DefaultTableModel) tbListTableReservation.getModel();
                    for (int i = 0; i < tbListTableReservation.getRowCount(); i++) {
                        dm.removeRow(i);
                    }
                    dm.setNumRows(0);
                    btnSave.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn không thành công","Thông Báo",JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Xin lỗi, Bạn chưa chọn bàn để đặt","Thông Báo",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void dtDateReservationPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtDateReservationPropertyChange

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (formatter.format(new Date()).compareTo(formatter.format(dtDateReservation.getDate())) > 0) {
            dtDateReservation.setDate(new Date());
        }
        String datetime = formatter.format(dtDateReservation.getDate());
        Timestamp ts = Timestamp.valueOf(datetime);
        loadTable(ts, idlocation);
    }//GEN-LAST:event_dtDateReservationPropertyChange

    private void cobLocationPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cobLocationPropertyChange
        try {
            vn.edu.vttu.model.TableLocation tb = (vn.edu.vttu.model.TableLocation) cobLocation.getSelectedItem();
            idlocation = tb.getId();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime = formatter.format(dtDateReservation.getDate());
            Timestamp ts = Timestamp.valueOf(datetime);
            loadTable(ts, idlocation);
        } catch (Exception e) {
        }

    }//GEN-LAST:event_cobLocationPropertyChange

    private void tbListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListTableMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                tbListTableReservation.setRowSelectionInterval(0, 0);
            } catch (Exception e) {
            }
            DefaultTableModel model = (DefaultTableModel) tbListTableReservation.getModel();
            int index = tbListTable.getSelectedRow();
            String id = String.valueOf(tbListTable.getValueAt(index, 0));
            String name = String.valueOf(tbListTable.getValueAt(index, 1));
            if (tbListTableReservation.getRowCount() > 0) {
                boolean flag = false;
                for (int i = 0; i < tbListTableReservation.getRowCount(); i++) {
                    String id2 = String.valueOf(tbListTableReservation.getValueAt(i, 0));
                    if (id.equals(id2)) {
                        flag = false;
                        break;
                    } else {
                        flag = true;
                    }
                }
                if (flag == true) {
                    model.addRow(new Object[]{id, name});
                }
            } else {
                model.addRow(new Object[]{id, name});
            }
            if (tbListTableReservation.getRowCount() > 0) {
                btnSave.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tbListTableMouseClicked

    private void tbListTableReservationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListTableReservationMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultTableModel model = (DefaultTableModel) tbListTableReservation.getModel();
            int index = tbListTableReservation.getSelectedRow();
            model.removeRow(index);
        }
    }//GEN-LAST:event_tbListTableReservationMouseClicked

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
    }//GEN-LAST:event_txtPrepayKeyReleased

    private void txtPrepayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrepayKeyTyped
        int key = evt.getKeyChar();        
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cobCustomer;
    private javax.swing.JComboBox cobLocation;
    private com.toedter.calendar.JDateChooser dtDateReservation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbListTable;
    private javax.swing.JTable tbListTableReservation;
    private javax.swing.JTextField txtPrepay;
    // End of variables declaration//GEN-END:variables
}

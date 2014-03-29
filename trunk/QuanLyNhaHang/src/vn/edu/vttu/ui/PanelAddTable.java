/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Component;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableLocation;
import vn.edu.vttu.data.TableType;

/**
 *
 * @author nhphuoc
 */
public class PanelAddTable extends javax.swing.JPanel {

    class ItemRendererTableLocation extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                vn.edu.vttu.model.TableLocation item = (vn.edu.vttu.model.TableLocation) value;
                // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                setText(item.getName().toUpperCase());
            }

            if (index == -1) {
                vn.edu.vttu.model.TableLocation item = (vn.edu.vttu.model.TableLocation) value;
                setText("" + item.getName());
            }

            return this;
        }
    }

    class ItemRendererTableType extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                vn.edu.vttu.model.TableType item = (vn.edu.vttu.model.TableType) value;
                setText(item.getName());
            }

            if (index == -1) {
                vn.edu.vttu.model.TableType item = (vn.edu.vttu.model.TableType) value;
                setText("" + item.getName());
            }

            return this;
        }
    }

    /**
     * Creates new form PanelAddTable
     */
    private Connection conn;
    private boolean add = false;
    private String tablename;

    public PanelAddTable() {
        initComponents();
        loadTable();
        enableButton(true);
        try {
            fillcobTableLocation();
            fillComboTableType();
        } catch (Exception e) {
        }
        enableButton(true);
        txtTableName.requestFocus();
    }

    private void fillcobTableLocation() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.TableLocation> model = new Vector<vn.edu.vttu.model.TableLocation>();
        try {
            model = TableLocation.selectTableLocation(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobLocation.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobLocation.setModel(defaultComboBoxModel);
        cobLocation.setRenderer(new PanelAddTable.ItemRendererTableLocation());
        conn = null;
    }

    private void fillComboTableType() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.TableType> model = new Vector<vn.edu.vttu.model.TableType>();
        try {
            model = TableType.selectTableType(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobTableType.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobTableType.setModel(defaultComboBoxModel);
        cobTableType.setRenderer(new PanelAddTable.ItemRendererTableType());
        conn = null;
    }

    private void loadTable() {
        tbTable.setModel(Table.tableGetAll(ConnectDB.conn()));

        tbTable.getColumnModel().getColumn(5).setMinWidth(0);
        tbTable.getColumnModel().getColumn(6).setMinWidth(0);
        tbTable.getColumnModel().getColumn(7).setMinWidth(0);

        tbTable.getColumnModel().getColumn(5).setMaxWidth(0);
        tbTable.getColumnModel().getColumn(6).setMaxWidth(0);
        tbTable.getColumnModel().getColumn(7).setMaxWidth(0);
    }

    public static void setSelectedLocation(JComboBox comboBox, int value) {
        vn.edu.vttu.model.TableLocation item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.TableLocation) comboBox.getItemAt(i);
            if (item.getId() == value) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public static void setSelectedType(JComboBox comboBox, int value) {
        vn.edu.vttu.model.TableType item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.TableType) comboBox.getItemAt(i);
            if (item.getId() == value) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void enableButton(boolean b) {
        btnAdd.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDel.setEnabled(b);
        btnSave.setEnabled(!b);
        tbTable.setEnabled(b);

        txtTableName.setEnabled(!b);
        txtNumberPeople.setEnabled(!b);
        cobLocation.setEnabled(!b);
        cobTableType.setEnabled(!b);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTableName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cobLocation = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cobTableType = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtNumberPeople = new javax.swing.JTextField();
        btnAddType = new javax.swing.JButton();
        btnAddLocation = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnEdit = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDel = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnSave = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jButton1.setText("jButton1");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Tên Bàn");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("Loại Bàn");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 0));
        jLabel3.setText("Vị Trí");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 0));
        jLabel4.setText("Số Người");

        txtNumberPeople.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumberPeopleKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumberPeopleKeyTyped(evt);
            }
        });

        btnAddType.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAddType.setText("+");
        btnAddType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTypeActionPerformed(evt);
            }
        });

        btnAddLocation.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAddLocation.setText("+");
        btnAddLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLocationActionPerformed(evt);
            }
        });

        tbTable.setModel(new javax.swing.table.DefaultTableModel(
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
        tbTable.setGridColor(new java.awt.Color(204, 204, 204));
        tbTable.setRowHeight(25);
        tbTable.setSelectionBackground(new java.awt.Color(255, 102, 0));
        tbTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbTable);

        jToolBar1.setBackground(new java.awt.Color(153, 204, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAdd.setBackground(new java.awt.Color(153, 204, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/add-icon_24x24.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);
        jToolBar1.add(jSeparator1);

        btnEdit.setBackground(new java.awt.Color(153, 204, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/edit-icon-24x24.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);
        jToolBar1.add(jSeparator2);

        btnDel.setBackground(new java.awt.Color(153, 204, 255));
        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/delete-icon-24x24.png"))); // NOI18N
        btnDel.setText("Xóa");
        jToolBar1.add(btnDel);
        jToolBar1.add(jSeparator3);

        btnSave.setBackground(new java.awt.Color(153, 204, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
        btnSave.setText("Lưu Lại");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);
        jToolBar1.add(jSeparator4);

        jButton2.setBackground(new java.awt.Color(153, 204, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Refresh-icon-24x24.png"))); // NOI18N
        jButton2.setText("Reload");
        jToolBar1.add(jButton2);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("Tìm Kiếm Bàn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTableName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(25, 25, 25)
                        .addComponent(cobTableType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddLocation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cobLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumberPeople)))
                .addGap(56, 56, 56))
            .addComponent(jScrollPane1)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTableName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cobLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cobTableType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddLocation)
                    .addComponent(jLabel4)
                    .addComponent(txtNumberPeople, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int status = Integer.parseInt(tbTable.getValueAt(tbTable.getSelectedRow(), 7).toString());
        if (txtTableName.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên bàn", "Thông Báo", JOptionPane.ERROR_MESSAGE);
            txtTableName.requestFocus();
        } else {
            if (add == true) {
                if (Table.testTableName(txtTableName.getText(), ConnectDB.conn())) {
                    vn.edu.vttu.model.TableType tbType = (vn.edu.vttu.model.TableType) cobTableType.getSelectedItem();
                    int type = tbType.getId();
                    vn.edu.vttu.model.TableLocation tbLocation = (vn.edu.vttu.model.TableLocation) cobLocation.getSelectedItem();
                    int location = tbLocation.getId();
                    if (Table.insertNewTable(txtTableName.getText(), type, location, Integer.parseInt(txtNumberPeople.getText()), ConnectDB.conn())) {
                        JOptionPane.showMessageDialog(getRootPane(), "Thêm thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                        loadTable();
                        enableButton(true);
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Thêm thất bại", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Tên bàn đã có", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Sửa");
                if (tbTable.getValueAt(tbTable.getSelectedRow(), 0).toString().equals("")) {
                    JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn bàn", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (tablename.equals(txtTableName.getText())) {
                        vn.edu.vttu.model.TableType tbType = (vn.edu.vttu.model.TableType) cobTableType.getSelectedItem();
                        int type = tbType.getId();
                        vn.edu.vttu.model.TableLocation tbLocation = (vn.edu.vttu.model.TableLocation) cobLocation.getSelectedItem();
                        int location = tbLocation.getId();
                        if (Table.update(txtTableName.getText(), type, location,
                                Integer.parseInt(txtNumberPeople.getText()), Integer.parseInt(tbTable.getValueAt(tbTable.getSelectedRow(), 0).toString()), ConnectDB.conn())) {
                            JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                            loadTable();
                            enableButton(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Tên bàn đã có", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        enableButton(true);
        conn = null;

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTypeActionPerformed

        String location = JOptionPane.showInputDialog(getRootPane(), "Nhập Vị Trí");
        if (location != null) {            
            if (TableLocation.tableLocationTestName(location, ConnectDB.conn())) {
                if (TableLocation.insert(location, "", ConnectDB.conn())) {
                    JOptionPane.showMessageDialog(getRootPane(), "Thêm mới vị trí thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    fillcobTableLocation();
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Tên vị trí đã có", "Thông Báo", JOptionPane.ERROR_MESSAGE);
            }
        } else {
        }

    }//GEN-LAST:event_btnAddTypeActionPerformed

    private void btnAddLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLocationActionPerformed
        conn = ConnectDB.conn();
        String type = JOptionPane.showInputDialog(getRootPane(), "Nhập Loại Bàn");
        if (type != null) {
            if (TableType.insert(type, conn)) {
                JOptionPane.showMessageDialog(getRootPane(), "Thêm mới loại bàn thành công");
                fillComboTableType();
            }
        }
        conn = null;
    }//GEN-LAST:event_btnAddLocationActionPerformed

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked


    }//GEN-LAST:event_btnSaveMouseClicked

    private void tbTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTableMouseReleased
        int index = tbTable.getSelectedRow();
        txtTableName.setText(tbTable.getValueAt(index, 1).toString());
        txtNumberPeople.setText(tbTable.getValueAt(index, 4).toString());
        setSelectedLocation(cobLocation, Integer.parseInt(tbTable.getValueAt(index, 5).toString()));
        setSelectedType(cobTableType, Integer.parseInt(tbTable.getValueAt(index, 6).toString()));
    }//GEN-LAST:event_tbTableMouseReleased

    private void txtNumberPeopleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberPeopleKeyTyped
        int key = evt.getKeyChar();
        String st = txtNumberPeople.getText();
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
    }//GEN-LAST:event_txtNumberPeopleKeyTyped

    private void txtNumberPeopleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberPeopleKeyReleased

    }//GEN-LAST:event_txtNumberPeopleKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        enableButton(false);
        add = true;
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add = false;
        enableButton(false);
        tablename = txtTableName.getText();
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddLocation;
    private javax.swing.JButton btnAddType;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cobLocation;
    private javax.swing.JComboBox cobTableType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tbTable;
    private javax.swing.JTextField txtNumberPeople;
    private javax.swing.JTextField txtTableName;
    // End of variables declaration//GEN-END:variables
}

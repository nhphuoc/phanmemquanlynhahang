/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Unit;
import static vn.edu.vttu.ui.PanelService.setSelectedValueUnit;

/**
 *
 * @author nhphuoc
 */
public class PanelUnit extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                vn.edu.vttu.model.Unit item = (vn.edu.vttu.model.Unit) value;
                setText(item.getName().toUpperCase());
            }
            if (index == -1) {
                vn.edu.vttu.model.Unit item = (vn.edu.vttu.model.Unit) value;
                setText("" + item.getName());
            }
            return this;
        }
    }

    /**
     * Creates new form PanelUnit
     */
    private Connection conn;
    private boolean add = false;
    private String x;
    JTable table;

    public PanelUnit() {
        initComponents();
        pn.removeAll();
        loadUnit();
        enableControl(true);
        pn.updateUI();
        pn.repaint();
        fillcobUnit();
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setMinWidth(0);
    }

    private void loadData() {
        conn = ConnectDB.conn();
        //tbUnit.setModel(Unit.getAll(conn));
        conn = null;
    }

    private void loadUnit() {
        Vector<Vector> rowData = new Vector<Vector>();
        TableModel tb = Unit.getAll(ConnectDB.conn());
        for (int i = 0; i < tb.getRowCount(); i++) {
            String name = Unit.getByID(Integer.parseInt(tb.getValueAt(i, 3).toString()), ConnectDB.conn()).getName();
            Vector<String> rowOne = new Vector<String>();
            for (int j = 0; j < tb.getColumnCount(); j++) {
                rowOne.addElement(tb.getValueAt(i, j).toString());
            }
            rowOne.addElement(name);
            rowData.add(rowOne);
        }
        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Mã ĐVT");
        columnNames.addElement("Tên ĐVT");
        columnNames.addElement("Giá Trị");
        columnNames.addElement("MÃ ĐVT Cha");
        columnNames.addElement("ĐVT Cha");
        table = new JTable(rowData, columnNames);
        table.setGridColor(new java.awt.Color(204, 204, 204));
        table.setRowHeight(25);
        table.setSelectionBackground(new java.awt.Color(255, 153, 0));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new java.awt.Font("Tahoma", 1, 10));

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbServiceMouseReleased(evt);
            }

            private void tbServiceMouseReleased(MouseEvent evt) {
                int index = table.getSelectedRow();
                txtID.setText(table.getValueAt(index, 0).toString());
                txtName.setText(table.getValueAt(index, 1).toString());
                txtCast.setText(table.getValueAt(index, 2).toString());
                try {
                    setSelectedUnit(cobDVT, Integer.parseInt(String.valueOf(table.getValueAt(index, 3))));
                } catch (Exception e) {
                }

            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        pn.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillcobUnit() {

        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.Unit> model = new Vector<vn.edu.vttu.model.Unit>();
        try {
            model = Unit.selectUnit(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobDVT.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobDVT.setModel(defaultComboBoxModel);
        cobDVT.setRenderer(new PanelUnit.ItemRenderer());
        conn = null;

    }

    private void setSelectedUnit(JComboBox comboBox, int value) {
        vn.edu.vttu.model.Unit item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.Unit) comboBox.getItemAt(i);
            if (item.getId() == value) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void enableControl(boolean b) {
        btnadd.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDel.setEnabled(b);
        btnSave.setEnabled(!b);
        //tbUnit.setEnabled(b);
        txtName.setEnabled(!b);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btnadd = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnEdit = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDel = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cobDVT = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtCast = new javax.swing.JTextField();
        pn = new javax.swing.JPanel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("ID:");

        txtID.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("Tên:");

        jToolBar1.setBackground(new java.awt.Color(102, 153, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnadd.setBackground(new java.awt.Color(102, 153, 255));
        btnadd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnadd.setForeground(new java.awt.Color(255, 255, 255));
        btnadd.setText("Thêm");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnadd);
        jToolBar1.add(jSeparator1);

        btnEdit.setBackground(new java.awt.Color(102, 153, 255));
        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);
        jToolBar1.add(jSeparator2);

        btnDel.setBackground(new java.awt.Color(102, 153, 255));
        btnDel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDel.setForeground(new java.awt.Color(255, 255, 255));
        btnDel.setText("Xóa");
        btnDel.setFocusable(false);
        btnDel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDel);
        jToolBar1.add(jSeparator3);

        btnSave.setBackground(new java.awt.Color(102, 153, 255));
        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Lưu");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 0));
        jLabel3.setText("ĐVT Cha");

        cobDVT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 0));
        jLabel4.setText("Chuyển Đổi");

        pn.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                    .addComponent(cobDVT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCast)
                    .addComponent(txtName))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cobDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        add = true;
        enableControl(false);
        txtName.requestFocus();

    }//GEN-LAST:event_btnaddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add = false;
        enableControl(false);
        txtName.requestFocus();
        x = txtName.getText().trim();

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        conn = ConnectDB.conn();
        if (txtName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên đơn vị tính");
            txtName.requestFocus();
        } else if (txtName.getText().length() > 30) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn nhập đơn vị tính quá dài");
        } else {
            if (add == true) {
                if (Unit.testName(txtName.getText().trim(), conn) == false) {
                    JOptionPane.showMessageDialog(getRootPane(), "Tên đơn vị tính đã có trong CSDL");
                } else {

                    if (Unit.insert(txtName.getText().trim(), conn)) {
                        loadData();
                        enableControl(true);
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Thêm đơn vị tính không thành công");
                    }
                }
            } else {
                if (x.equals(txtName.getText().trim())) {
                    loadData();
                    enableControl(true);
                } else {
                    if (Unit.testName(txtName.getText().trim(), conn) == false) {
                        JOptionPane.showMessageDialog(getRootPane(), "Tên đơn vị tính đã có trong CSDL");
                    } else {

                        if (Unit.update(txtName.getText().trim(), Integer.parseInt(txtID.getText().trim()), conn)) {
                            loadData();
                            enableControl(true);
                        } else {
                            JOptionPane.showMessageDialog(getRootPane(), "Cập nhật đơn vị tính không thành công");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        if (txtID.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn đơn vị tính");
        } else {
            if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có muốn xóa không", "Hỏi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (Unit.delete(Integer.parseInt(txtID.getText().trim()), ConnectDB.conn())) {
                    loadData();
                    JOptionPane.showMessageDialog(getRootPane(), "Xóa thành công");
                }
            }
        }
    }//GEN-LAST:event_btnDelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnadd;
    private javax.swing.JComboBox cobDVT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pn;
    private javax.swing.JTextField txtCast;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}

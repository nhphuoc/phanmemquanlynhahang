/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.NumberCellRenderer;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.ServiceType;
import vn.edu.vttu.data.Unit;

/**
 *
 * @author nhphuoc
 */
public class PanelStore extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            try {
                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value != null) {
                    vn.edu.vttu.model.Unit item = (vn.edu.vttu.model.Unit) value;
                    // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                    setText(item.getName().toUpperCase());
                }

                if (index == -1) {
                    vn.edu.vttu.model.Unit item = (vn.edu.vttu.model.Unit) value;
                    setText("" + item.getName());
                }

            } catch (Exception e) {
            }

            return this;
        }
    }

    /**
     * Creates new form PanelStore
     */
    private Connection conn;
    private boolean add = false;
    private String name;
    private float number;
    private JTable table;

    public PanelStore() {
        initComponents();
        pn.removeAll();
        loadData();
        pn.updateUI();
        pn.repaint();
        fillcobUnit();
        enableControl(true);
    }

    private String dvt(int id, float num) {
        String kq = "";
        TableModel tb = Unit.getBySubID(id, ConnectDB.conn());
        int cast = 0;
        int idsub = 0;
        if (tb.getRowCount() > 1) {
            for (int i = 0; i < tb.getRowCount(); i++) {
                if (!Unit.getByID(Integer.parseInt(tb.getValueAt(i, 0).toString()), ConnectDB.conn()).isParent()) {
                    cast = Unit.getByID(Integer.parseInt(tb.getValueAt(i, 0).toString()), ConnectDB.conn()).getCast();
                    idsub = Unit.getByID(Integer.parseInt(tb.getValueAt(i, 0).toString()), ConnectDB.conn()).getId();
                    break;
                }
            }
            float x = (float) (num * cast);
            String y = (int) (x / cast) + " " + Unit.getByID(id, ConnectDB.conn()).getName();
            String z = (int) (x % cast) + " " + Unit.getByID(idsub, ConnectDB.conn()).getName();
            if (x % cast == 0) {
                kq = y;
            } else {
                kq = (y + " " + z);
            }

        }
        if (tb.getRowCount() == 1) {
            kq = (num + " " + Unit.getByID(id, ConnectDB.conn()).getName());
        }
        return kq;
    }

    private void loadData() {
        pn.removeAll();
        TableModel tb = RawMaterial.getAll(ConnectDB.conn());
        Vector<Vector> rowData = new Vector<Vector>();
        for (int i = 0; i < tb.getRowCount(); i++) {
            Vector<String> rowOne = new Vector<String>();
            for (int j = 0; j < 2; j++) {
                rowOne.addElement(tb.getValueAt(i, j).toString());
            }
            rowOne.addElement(dvt(Integer.parseInt(tb.getValueAt(i, 4).toString()), Float.parseFloat(tb.getValueAt(i, 2).toString())));
            rowOne.addElement(tb.getValueAt(i, 4).toString());
            rowData.addElement(rowOne);
        }
        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Mã Nguyên Liệu");
        columnNames.addElement("Tên Nguyên Liệu");
        columnNames.addElement("Số Lượng");
        columnNames.addElement("");

        table = new JTable(rowData, columnNames);        
        table.setGridColor(new java.awt.Color(204, 204, 204));
        table.setRowHeight(25);
        table.setSelectionBackground(new java.awt.Color(255, 153, 0));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new java.awt.Font("Tahoma", 1, 12));        
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setMinWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        pn.add(scrollPane, BorderLayout.CENTER);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int index = table.getSelectedRow();
                txtID.setText(table.getValueAt(index, 0).toString());
                txtNAme.setText(table.getValueAt(index, 1).toString());
                txtNumber.setText(table.getValueAt(index, 2).toString());
                setSelectedValue(cobUnit, Integer.parseInt(table.getValueAt(index, 3).toString()));
            }
        }); 
        table.getTableHeader().setReorderingAllowed(false);
        pn.updateUI();
        pn.repaint();

        //tbStore.getColumnModel().getColumn(4).setMinWidth(0);
        //tbStore.getColumnModel().getColumn(4).setMaxWidth(0);
        //tbStore.getColumnModel().getColumn(2).setCellRenderer(new NumberCellRenderer());
    }

    public void setSelectedValue(JComboBox comboBox, int value) {
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
        btnAdd.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDelete.setEnabled(b);
        btnSave.setEnabled(!b);
        txtNAme.setEnabled(!b);
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
        cobUnit.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobUnit.setModel(defaultComboBoxModel);
        cobUnit.setRenderer(new PanelStore.ItemRenderer());
        conn = null;
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cobUnit = new javax.swing.JComboBox();
        btnAddUnit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNAme = new javax.swing.JTextField();
        txtNumber = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnCreateInvoice = new javax.swing.JButton();
        pn = new javax.swing.JPanel();

        jButton1.setText("jButton1");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("ID:");

        txtID.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 0));
        jLabel3.setText("Tên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 0));
        jLabel4.setText("ĐVT:");

        cobUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddUnit.setText("+");
        btnAddUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUnitActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 0));
        jLabel5.setText("Số Lượng:");

        txtNumber.setEditable(false);
        txtNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumberKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumberKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumberKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNAme)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cobUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddUnit))
                    .addComponent(txtNumber)
                    .addComponent(txtID)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNAme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cobUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(262, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Tìm Kiếm:");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

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

        btnEdit.setBackground(new java.awt.Color(153, 204, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/edit-icon-24x24.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnDelete.setBackground(new java.awt.Color(153, 204, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/delete-icon-24x24.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        btnSave.setBackground(new java.awt.Color(153, 204, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon-24x24.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        btnReload.setBackground(new java.awt.Color(153, 204, 255));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Refresh-icon-24x24.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReload);

        btnPrint.setBackground(new java.awt.Color(153, 204, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/print-icon-24x24.png"))); // NOI18N
        btnPrint.setText("Print");
        jToolBar1.add(btnPrint);

        btnCreateInvoice.setBackground(new java.awt.Color(153, 204, 255));
        btnCreateInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/table-import-icon.png"))); // NOI18N
        btnCreateInvoice.setText("Nhập Hàng");
        btnCreateInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateInvoiceActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCreateInvoice);

        pn.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add = true;
        enableControl(false);
        txtNAme.setText("");
        txtNumber.setText("0");
        txtNAme.requestFocus();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add = false;
        enableControl(false);
        txtNAme.requestFocus();
        name = txtNAme.getText();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        conn = ConnectDB.conn();
        vn.edu.vttu.model.Unit unit = (vn.edu.vttu.model.Unit) cobUnit.getSelectedItem();
        int _unit = unit.getId();
        if (txtNAme.getText().trim().equals("") && txtNAme.getText().trim().length() > 50) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên hoặc tên quá dài");
            txtNAme.requestFocus();
        } else if (txtNumber.getText().trim().equals("")) {
            txtNumber.setText("0");
        } else {
            if (add == true) {
                if (RawMaterial.testName(txtNAme.getText().trim(), conn) == false) {
                    JOptionPane.showMessageDialog(getRootPane(), "Tên hàng hóa đã có");
                } else {
                    if (RawMaterial.insert(txtNAme.getText(),0, _unit, conn)) {
                        loadData();
                        enableControl(true);
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                        enableControl(true);
                    }
                }
            } else {
                if (!name.equals(txtNAme.getText().trim())) {
                    if (RawMaterial.testName(txtNAme.getText().trim(), conn) == false) {
                        JOptionPane.showMessageDialog(getRootPane(), "Tên đã có trong CSDL");
                    } else {
                        float _number = 0;
                        try {
                            _number = Float.parseFloat(txtNumber.getText().trim().replaceAll("\\.", ""));
                        } catch (Exception e) {
                            _number = Float.parseFloat(txtNumber.getText().trim().replaceAll(",", ""));
                        }
                        if (RawMaterial.update(txtNAme.getText().trim(), _unit, Integer.parseInt(txtID.getText().trim()), conn)) {
                            loadData();
                            enableControl(true);
                        }
                    }
                } else {
                    if (RawMaterial.update(txtNAme.getText().trim(), _unit, Integer.parseInt(txtID.getText().trim()), conn)) {
                        loadData();
                        enableControl(true);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed

        loadData();
        enableControl(true);
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnAddUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUnitActionPerformed
        String input = JOptionPane.showInputDialog(btnAddUnit, "Nhập đơn vị tính");
        conn = ConnectDB.conn();
        if (input != null && !input.equals("")) {
            if (Unit.testName(input, conn)) {
                if (Unit.insert(input, conn)) {
                    JOptionPane.showMessageDialog(btnAddUnit, "Thành công");
                    fillcobUnit();
                } else {
                    JOptionPane.showMessageDialog(btnAddUnit, "Không thành công");
                }
            } else {
                JOptionPane.showMessageDialog(btnAddUnit, "Tên Đơn vị tính đã có");
            }
        }
    }//GEN-LAST:event_btnAddUnitActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (txtID.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn hàng hóa để xóa");
        } else {
            if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn thật sự muốn xóa", "Hỏi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    if (RawMaterial.delete(Integer.parseInt(txtID.getText().trim()), ConnectDB.conn())) {
                        loadData();
                        JOptionPane.showMessageDialog(getRootPane(), "Xóa thành công");
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(getRootPane(), "<html><font color='red'>Xóa không thành công, Vì có nhiều dữ liệu liên quan</font></html>");
                }

            }
        }


    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed

        try {
            TableModel tb = (RawMaterial.search(txtSearch.getText().trim(), ConnectDB.conn()));
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberKeyPressed
        int key = evt.getKeyChar();
        String st = txtNumber.getText();
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
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtNumber.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtNumber.getText().trim().replaceAll("\\.", ""));
                txtNumber.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtNumber.getText().trim().replaceAll(",", ""));
                txtNumber.setText(String.valueOf(df.format(num)));
            }

        }
    }//GEN-LAST:event_txtNumberKeyPressed

    private void txtNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberKeyReleased
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtNumber.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtNumber.getText().trim().replaceAll("\\.", ""));
                txtNumber.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtNumber.getText().trim().replaceAll(",", ""));
                txtNumber.setText(String.valueOf(df.format(num)));
            }
        }
    }//GEN-LAST:event_txtNumberKeyReleased

    private void txtNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberKeyTyped
        int key = evt.getKeyChar();
        String st = txtNumber.getText();
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
    }//GEN-LAST:event_txtNumberKeyTyped

    private void btnCreateInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateInvoiceActionPerformed
        JOptionPane.showOptionDialog(null, new PanelAddStoreInvoice(),
                "Viết Phiếu Chi", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
        TableModel tb = RawMaterial.getAll(ConnectDB.conn());
        loadData();
    }//GEN-LAST:event_btnCreateInvoiceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddUnit;
    private javax.swing.JButton btnCreateInvoice;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cobUnit;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pn;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNAme;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

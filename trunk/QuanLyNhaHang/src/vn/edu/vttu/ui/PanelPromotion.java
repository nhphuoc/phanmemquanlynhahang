/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Discount;
import vn.edu.vttu.data.NumberCellRenderer;

/**
 *
 * @author nhphuoc
 */
public class PanelPromotion extends javax.swing.JPanel {

    /**
     * Creates new form PanelPromotion
     */
    private Connection conn;
    private boolean add = false;

    public PanelPromotion() {
        initComponents();
        loadData();
        binddingData(0);
        enableControl(true);
    }

    private void loadData() {
        conn = ConnectDB.conn();
        tbListPromotion.setModel(Discount.getListPromotion(conn));
        if (tbListPromotion.getRowCount() <= 0) {
            tbListPromotion.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbListPromotion.getColumnModel().getColumn(10).setPreferredWidth(0);
            tbListPromotion.getColumnModel().getColumn(9).setMinWidth(0);
            tbListPromotion.getColumnModel().getColumn(10).setMinWidth(0);
            tbListPromotion.getColumnModel().getColumn(9).setMaxWidth(0);
            tbListPromotion.getColumnModel().getColumn(10).setMaxWidth(0);
        } else {
            tbListPromotion.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbListPromotion.getColumnModel().getColumn(10).setPreferredWidth(0);
            tbListPromotion.getColumnModel().getColumn(9).setMinWidth(0);
            tbListPromotion.getColumnModel().getColumn(10).setMinWidth(0);
            tbListPromotion.getColumnModel().getColumn(9).setMaxWidth(0);
            tbListPromotion.getColumnModel().getColumn(10).setMaxWidth(0);
        }
        tbListPromotion.getColumnModel().getColumn(7).setCellRenderer(new NumberCellRenderer());
        tbListPromotion.getColumnModel().getColumn(6).setCellRenderer(new NumberCellRenderer());
        tbListPromotion.getColumnModel().getColumn(9).setCellRenderer(new NumberCellRenderer());
        tbListPromotion.getColumnModel().getColumn(10).setCellRenderer(new NumberCellRenderer());
        conn = null;
    }

    private void binddingData(int index) {
        if (tbListPromotion.getRowCount() > 0) {
            txtID.setText(String.valueOf(tbListPromotion.getValueAt(index, 0)));
            txtName.setText(String.valueOf(tbListPromotion.getValueAt(index, 1)));
            if (String.valueOf(tbListPromotion.getValueAt(index, 2)) != null || !String.valueOf(tbListPromotion.getValueAt(index, 2)).equals("")) {
                try {
                    String dt = String.valueOf(tbListPromotion.getValueAt(index, 2));
                    java.util.Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dt);
                    dtBeginDate.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(PanelPromotion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (String.valueOf(tbListPromotion.getValueAt(index, 3)) != null || !String.valueOf(tbListPromotion.getValueAt(index, 3)).equals("")) {
                try {
                    String dt = String.valueOf(tbListPromotion.getValueAt(index, 3));
                    java.util.Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dt);
                    dtEndDate.setDate(date);
                } catch (ParseException ex) {
                    Logger.getLogger(PanelPromotion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            cobPromotion.setSelectedItem(String.valueOf(tbListPromotion.getValueAt(index, 4)));
            cobCondition.setSelectedItem(String.valueOf(tbListPromotion.getValueAt(index, 5)));
            txtCostInvoiceCondition.setText(String.valueOf(tbListPromotion.getValueAt(index, 6)));
            txtCostPromotion.setText(String.valueOf(tbListPromotion.getValueAt(index, 7)));
            txtDetail.setText(String.valueOf(tbListPromotion.getValueAt(index, 8)));
        }
    }

    private void enableControl(boolean b) {
        btnAdd.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDelete.setEnabled(b);
        btnSave.setEnabled(!b);
        tbListPromotion.setEnabled(b);

        txtName.setEnabled(!b);
        txtCostInvoiceCondition.setEnabled(!b);
        txtCostPromotion.setEnabled(!b);
        dtBeginDate.setEnabled(!b);
        dtEndDate.setEnabled(!b);
        cobCondition.setEnabled(!b);
        cobPromotion.setEnabled(!b);
        txtDetail.setEnabled(!b);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cobPromotion = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        dtBeginDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        dtEndDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtCostInvoiceCondition = new javax.swing.JTextField();
        txtCostPromotion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDetail = new javax.swing.JTextArea();
        cobCondition = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListPromotion = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnEdit = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnDelete = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnSave = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnReload = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Khuyến Mãi"));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("ID:");

        txtID.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("Tên:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 0));
        jLabel3.setText("Hình Thức");

        cobPromotion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiền Mặt", "Phần Trăm" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 0));
        jLabel4.setText("Bắt Đầu: ");

        dtBeginDate.setDate(new Date());
        dtBeginDate.setDateFormatString("dd/MM/yyyy HH:mm");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 0));
        jLabel5.setText("Kết Thúc:");

        dtEndDate.setDate(new Date());
        dtEndDate.setDateFormatString("dd/MM/yyyy HH:mm");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 0));
        jLabel7.setText("Điều Kiện:");

        txtCostInvoiceCondition.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostInvoiceConditionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostInvoiceConditionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostInvoiceConditionKeyTyped(evt);
            }
        });

        txtCostPromotion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostPromotionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostPromotionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostPromotionKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 0));
        jLabel8.setText("Chi Tiết Chương Trình:");

        txtDetail.setColumns(20);
        txtDetail.setLineWrap(true);
        txtDetail.setRows(5);
        jScrollPane2.setViewportView(txtDetail);

        cobCondition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất Cả Hóa Đơn", "Hóa Đơn Trên" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dtBeginDate, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addComponent(dtEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(61, 61, 61)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cobPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cobCondition, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCostInvoiceCondition)
                            .addComponent(txtCostPromotion, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(dtBeginDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cobPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCostPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCostInvoiceCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cobCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
        );

        tbListPromotion = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbListPromotion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Chương Trình", "Bắt Đầu", "Kết Thúc", "Hình Thức", "Áp Dụng", "Điều Kiện", "Giá Trị", "Chi Tiết"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListPromotion.setGridColor(new java.awt.Color(204, 204, 204));
        tbListPromotion.setRowHeight(25);
        tbListPromotion.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbListPromotion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListPromotionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListPromotion);

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

        btnDelete.setBackground(new java.awt.Color(153, 204, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/delete-icon-24x24.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);
        jToolBar1.add(jSeparator3);

        btnSave.setBackground(new java.awt.Color(153, 204, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon-24x24.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);
        jToolBar1.add(jSeparator4);

        btnReload.setBackground(new java.awt.Color(153, 204, 255));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Refresh-icon-24x24.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReload);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        enableControl(false);
        add = false;
        txtName.requestFocus();
        dtBeginDate.enable(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tbListPromotionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListPromotionMouseClicked
        int index = tbListPromotion.getSelectedRow();
        binddingData(index);
    }//GEN-LAST:event_tbListPromotionMouseClicked

    private void txtCostPromotionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostPromotionKeyTyped
        int key = evt.getKeyChar();
        String st = txtCostPromotion.getText();
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
    }//GEN-LAST:event_txtCostPromotionKeyTyped

    private void txtCostPromotionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostPromotionKeyPressed
        int key = evt.getKeyChar();
        String st = txtCostPromotion.getText();
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
        if (!txtCostPromotion.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCostPromotion.getText().trim().replaceAll("\\.", ""));
                txtCostPromotion.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCostPromotion.getText().trim().replaceAll(",", ""));
                txtCostPromotion.setText(String.valueOf(df.format(num)));
            }

            if (cobPromotion.getSelectedItem().equals("Phần Trăm")) {
                try {
                    if (txtCostPromotion.getText().trim().replaceAll("\\.", "").length() > 3 || Integer.parseInt(txtCostPromotion.getText().trim().replaceAll("\\.", "")) > 100) {
                        txtCostPromotion.setText("0");
                    }
                } catch (Exception e) {
                    if (txtCostPromotion.getText().trim().replaceAll(",", "").length() > 3 || Integer.parseInt(txtCostPromotion.getText().trim().replaceAll("\\.", "")) > 100) {
                        txtCostPromotion.setText("0");
                    }
                }
            }
        }
    }//GEN-LAST:event_txtCostPromotionKeyPressed

    private void txtCostPromotionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostPromotionKeyReleased
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtCostPromotion.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCostPromotion.getText().trim().replaceAll("\\.", ""));
                txtCostPromotion.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCostPromotion.getText().trim().replaceAll(",", ""));
                txtCostPromotion.setText(String.valueOf(df.format(num)));
            }
            if (cobPromotion.getSelectedItem().equals("Phần Trăm")) {
                try {
                    if (txtCostPromotion.getText().trim().replaceAll("\\.", "").length() > 3 || Integer.parseInt(txtCostPromotion.getText().trim().replaceAll("\\.", "")) > 100) {
                        txtCostPromotion.setText("0");
                    }
                } catch (Exception e) {
                    if (txtCostPromotion.getText().trim().replaceAll(",", "").length() > 3 || Integer.parseInt(txtCostPromotion.getText().trim().replaceAll("\\.", "")) > 100) {
                        txtCostPromotion.setText("0");
                    }
                }
            }
        }
    }//GEN-LAST:event_txtCostPromotionKeyReleased

    private void txtCostInvoiceConditionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostInvoiceConditionKeyPressed
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtCostInvoiceCondition.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCostInvoiceCondition.getText().trim().replaceAll("\\.", ""));
                txtCostInvoiceCondition.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCostInvoiceCondition.getText().trim().replaceAll(",", ""));
                txtCostInvoiceCondition.setText(String.valueOf(df.format(num)));
            }
        }
    }//GEN-LAST:event_txtCostInvoiceConditionKeyPressed

    private void txtCostInvoiceConditionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostInvoiceConditionKeyTyped
        int key = evt.getKeyChar();
        String st = txtCostInvoiceCondition.getText();
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
    }//GEN-LAST:event_txtCostInvoiceConditionKeyTyped

    private void txtCostInvoiceConditionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostInvoiceConditionKeyReleased
        int key = evt.getKeyChar();
        String st = txtCostInvoiceCondition.getText();
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
        if (!txtCostInvoiceCondition.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCostInvoiceCondition.getText().trim().replaceAll("\\.", ""));
                txtCostInvoiceCondition.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCostInvoiceCondition.getText().trim().replaceAll(",", ""));
                txtCostInvoiceCondition.setText(String.valueOf(df.format(num)));
            }
        }
    }//GEN-LAST:event_txtCostInvoiceConditionKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        enableControl(false);
        add = true;
        txtName.requestFocus();
        txtName.setText("");
        txtDetail.setText("");
        txtCostInvoiceCondition.setText("");
        txtCostPromotion.setText("");

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        conn = ConnectDB.conn();
        String name = txtName.getText().trim();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetimeBegin = formatter.format(dtBeginDate.getDate());
        Timestamp tsBegin = Timestamp.valueOf(datetimeBegin);
        String datetimeEnd = formatter.format(dtEndDate.getDate());
        Timestamp tsEnd = Timestamp.valueOf(datetimeEnd);
        if (txtName.getText().trim().length() > 50 || txtName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên chương trình hoặc tên >50 ký tự","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (dtBeginDate.getDate().compareTo(new Date()) < 0) {
            JOptionPane.showMessageDialog(getRootPane(), "Ngày bắt đầu phải lớn hơn ngày hiện tại","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (dtEndDate.getDate().compareTo(dtBeginDate.getDate()) < 0) {
            JOptionPane.showMessageDialog(getRootPane(), "Ngày kết thúc phải lớn hơn ngày bắt đầu","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (txtCostPromotion.getText().trim().equals("")) {
            txtCostPromotion.setText("0");
        } else {
            int type = 0;
            int condition = 0;
            if (cobPromotion.getSelectedItem().equals("Tiền Mặt")) {
                type = 1;
            } else {
                type = 0;
            }
            if (cobCondition.getSelectedItem().equals("Tất Cả Hóa Đơn")) {
                condition = 0;
            } else {
                condition = 1;
            }
            int conditionvalue = 0;
            try {
                conditionvalue = Integer.parseInt(txtCostInvoiceCondition.getText().trim().replaceAll("\\.", ""));
            } catch (Exception e) {
                conditionvalue = Integer.parseInt(txtCostInvoiceCondition.getText().trim().replaceAll(",", ""));
            }

            int value = 0;
            try {
                value = Integer.parseInt(txtCostPromotion.getText().trim().replaceAll("\\.", ""));
            } catch (Exception e) {
                value = Integer.parseInt(txtCostPromotion.getText().trim().replaceAll(",", ""));
            }

            String detail = txtDetail.getText();
            if (add == true) {
                if (Discount.testDate(tsBegin, conn) == false || Discount.testDate(tsEnd, conn) == false) {
                    JOptionPane.showMessageDialog(getRootPane(), "Đã có chương trình khuyến mãi khác","Thông Báo",JOptionPane.ERROR_MESSAGE);
                } else {
                    if (Discount.insert(name, type, tsBegin, tsEnd, condition, conditionvalue, value, detail, conn)) {
                        JOptionPane.showMessageDialog(getRootPane(), "Thêm Thành Công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                        loadData();
                        enableControl(true);
                    }
                }
            } else {
                if (txtID.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn chương trình khuyến mãi nào","Thông Báo",JOptionPane.ERROR_MESSAGE);
                } else {
                    int id = Integer.parseInt(txtID.getText().trim());
                    try {
                        conn.setAutoCommit(false);
                        if (Discount.updateStatus(id, conn)) {
                            if (Discount.insert(name, type, tsBegin, tsEnd, condition, conditionvalue, value, detail, conn)) {
                                conn.commit();
                                loadData();
                                JOptionPane.showMessageDialog(getRootPane(), "Cập nhật Thành Công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                                enableControl(true);
                            } else {
                                throw new Exception();
                            }

                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        try {
                            conn.rollback();
                            JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi","Thông Báo",JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException ex) {
                            Logger.getLogger(PanelPromotion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }

        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (txtID.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn chương trình khuyến mãi","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else {
            if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có muốn xóa chương trình khuyến mãi này không", "hỏi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                conn = ConnectDB.conn();
                if (Discount.delete(Integer.parseInt(txtID.getText()), conn)) {
                    JOptionPane.showMessageDialog(getRootPane(), "Thành công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        loadData();
    }//GEN-LAST:event_btnReloadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cobCondition;
    private javax.swing.JComboBox cobPromotion;
    private com.toedter.calendar.JDateChooser dtBeginDate;
    private com.toedter.calendar.JDateChooser dtEndDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tbListPromotion;
    private javax.swing.JTextField txtCostInvoiceCondition;
    private javax.swing.JTextField txtCostPromotion;
    private javax.swing.JTextArea txtDetail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}

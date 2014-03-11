/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.RawMaterial;
import vn.edu.vttu.data.Recipes;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.VariableStatic;

/**
 *
 * @author nhphuoc
 */
public class PanelCooking extends javax.swing.JPanel {

    /**
     * Creates new form PanelCooking
     */
    private Connection conn;
    private JPopupMenu popup;
    private int idService;
    private String nameService;
    private boolean add = false;
    private int idStore = -1;
    private int idRecipes;
    private float number;

    public PanelCooking() {
        initComponents();
        loadStore();

        idService = VariableStatic.getIdService();
        nameService = VariableStatic.getNameService();
        lbServiceName.setText("Chế biến :" + nameService);
        loadRecipes();
        popuptbCook();
        popuptbStore();
    }

    private void loadStore() {
        tbStore.setModel(RawMaterial.getAll(ConnectDB.conn()));
        if (tbStore.getRowCount() <= 0) {
            //tbStore.setRowSelectionInterval(0, 0);
        } else {
            tbStore.setRowSelectionInterval(0, 0);
        }
        tbStore.getColumnModel().getColumn(4).setMinWidth(0);
        tbStore.getColumnModel().getColumn(4).setMaxWidth(0);
    }

    private void loadRecipes() {
        tbCook.setModel(Recipes.getRecipesByIdService(idService, ConnectDB.conn()));
        if (tbCook.getRowCount() <= 0) {
            //tbStore.setRowSelectionInterval(0, 0);
        } else {
            tbCook.setRowSelectionInterval(0, 0);
        }
    }
    /*
     private void enableControl(boolean b) {
     btnAdd.setEnabled(b);
     btnEdit.setEnabled(b);
     btnDelete.setEnabled(b);
     btnSave.setEnabled(!b);
     txtNumber.setEnabled(!b);
     txtStoreName.setEnabled(!b);

     }
     */

    private void popuptbCook() {
        try {
            popup = new JPopupMenu();
            BufferedImage bImg1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/editicon.png"));
            Image image1 = bImg1.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Cập nhật số lượng", new ImageIcon(image1)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String number = JOptionPane.showInputDialog(tbStore, "Số Lượng", String.valueOf(tbCook.getValueAt(tbCook.getSelectedRow(), 3))).replaceAll(",", ".");
                    if (number != null && !number.trim().equals("")) {
                        if (testNumber(number)) {
                            if (Recipes.update(idRecipes, Float.parseFloat(number), ConnectDB.conn())) {
                                loadRecipes();
                            } else {
                                JOptionPane.showMessageDialog(getRootPane(), "Đã có lỗi xảy ra");
                            }
                        } else {
                            JOptionPane.showMessageDialog(getRootPane(), "Bạn nhập không phải là số");
                        }
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập số lượng");
                    }
                }
            }));
            BufferedImage bImg2 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/delete-icon-24x24.png"));
            Image image2 = bImg2.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Xóa mục đang chọn", new ImageIcon(image2)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có thật sự muốn xóa hay không", "Hỏi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (Recipes.delete(Integer.parseInt(String.valueOf(tbCook.getValueAt(tbCook.getSelectedRow(), 0))), ConnectDB.conn())) {
                            loadRecipes();
                            JOptionPane.showMessageDialog(getRootPane(), "Xóa thành công");
                        } else {
                            JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                        }
                    }
                }
            }));
            BufferedImage bImg3 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon-24x24.png"));
            Image image3 = bImg3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Cập nhật lại danh sách", new ImageIcon(image3)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadRecipes();
                }
            }));
        } catch (Exception e) {
        }
        tbCook.setComponentPopupMenu(popup);
    }

    private void popuptbStore() {
        try {
            popup = new JPopupMenu();
            BufferedImage bImg1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/add-icon_24x24.png"));
            Image image1 = bImg1.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Thêm vào nguyên liệu", new ImageIcon(image1)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String number = JOptionPane.showInputDialog(tbStore, "Số Lượng", 1).trim().replaceAll(",", ".");
                    if (number != null && !number.trim().equals("")) {
                        if (testNumber(number)) {
                            if (idStore == -1) {
                                JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn nguyên liệu.\n"
                                        + "Vui lòng click vào nguyên liệu cần chọn và thực hiện lại thao tác");
                            } else {

                                if (Recipes.countRecipesByIdService(idService, idStore, ConnectDB.conn())) {
                                    if (Recipes.insert(idStore, idService, Float.parseFloat(number), ConnectDB.conn())) {
                                        loadRecipes();
                                    } else {
                                        JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                                    }
                                } else {
                                    if (Recipes.update(idStore, idService, Float.parseFloat(number), ConnectDB.conn())) {
                                        loadRecipes();
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(getRootPane(), "Bạn nhập không phải là số");
                        }
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập số lượng");
                    }
                }
            }));
            BufferedImage bImg3 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/Refresh-icon-24x24.png"));
            Image image3 = bImg3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            popup.add(new JMenuItem(new AbstractAction("Cập nhật lại danh sách", new ImageIcon(image3)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadStore();
                }
            }));
        } catch (Exception e) {
        }
        tbStore.setComponentPopupMenu(popup);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbCook = new javax.swing.JTable();
        lbServiceName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbStore = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();

        tbCook = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbCook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Hàng", "Đơn Vị Tính", "Số Lượng"
            }
        ));
        tbCook.setGridColor(new java.awt.Color(204, 204, 204));
        tbCook.setRowHeight(25);
        tbCook.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbCook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCookMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCook);

        lbServiceName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbServiceName.setText("Tên Dịch Vụ");

        tbStore = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbStore.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên", "ĐVT", "Tồn Kho"
            }
        ));
        tbStore.setGridColor(new java.awt.Color(204, 204, 204));
        tbStore.setRowHeight(23);
        tbStore.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbStore.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbStoreMouseClicked(evt);
            }
        });
        tbStore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbStoreKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbStore);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Chọn Nguyên Liệu");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbServiceName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addComponent(txtSearch)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbServiceName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbStoreMouseClicked
        int index = tbStore.getSelectedRow();
        idStore = Integer.parseInt(String.valueOf(tbStore.getValueAt(index, 0)));
    }//GEN-LAST:event_tbStoreMouseClicked

    private void tbStoreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbStoreKeyReleased
        int index = tbStore.getSelectedRow();
        idStore = Integer.parseInt(String.valueOf(tbStore.getValueAt(index, 0)));
    }//GEN-LAST:event_tbStoreKeyReleased

    private void tbCookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCookMouseClicked
        int index = tbCook.getSelectedRow();
        idRecipes = Integer.parseInt(String.valueOf(tbCook.getValueAt(index, 0)));
    }//GEN-LAST:event_tbCookMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        tbStore.setModel(RawMaterial.search(txtSearch.getText().trim(), ConnectDB.conn()));
        if (tbStore.getRowCount() <= 0) {
            //tbStore.setRowSelectionInterval(0, 0);
        } else {
            tbStore.setRowSelectionInterval(0, 0);
            idStore=Integer.parseInt(String.valueOf(tbStore.getValueAt(0, 0)));
        }
        tbStore.getColumnModel().getColumn(4).setMinWidth(0);
        tbStore.getColumnModel().getColumn(4).setMaxWidth(0);
    }//GEN-LAST:event_txtSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbServiceName;
    private javax.swing.JTable tbCook;
    private javax.swing.JTable tbStore;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

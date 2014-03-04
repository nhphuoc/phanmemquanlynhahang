/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Service;
import vn.edu.vttu.data.ServiceCost;
import vn.edu.vttu.data.ServiceType;
import vn.edu.vttu.data.UploadFile;
import vn.edu.vttu.data.VariableStatic;

/**
 *
 * @author nhphuoc
 */
public class PanelService extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            try {
                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value != null) {
                    vn.edu.vttu.model.ServiceType item = (vn.edu.vttu.model.ServiceType) value;
                    // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                    setText(item.getName().toUpperCase());
                }

                if (index == -1) {
                    vn.edu.vttu.model.ServiceType item = (vn.edu.vttu.model.ServiceType) value;
                    setText("" + item.getName());
                }

            } catch (Exception e) {
            }

            return this;
        }
    }

    public class ComboItem {

        private String value;
        private String label;

        public ComboItem(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
    /**
     * Creates new form PanelService
     */
    private Connection conn;
    private boolean add = false;
    private int cost = -1;
    private int type = -1;
    private String name;
    private int id;
    private String unit;
    private int store = 0;
    private String note;
    private String img;
    private String filename;
    private String fileAddress;

    public PanelService() {
        initComponents();
        loadTableService(1);
        enableButton(true);
        fillcobServiceType();

    }

    private void loadTableService(int id) {
        try {
            conn = ConnectDB.conn();
            tbService.setModel(Service.getAll(conn));
            if (tbService.getRowCount() > 0) {
                tbService.setRowSelectionInterval(0, 0);
                tbService.getColumnModel().getColumn(7).setMinWidth(0);
                tbService.getColumnModel().getColumn(7).setMaxWidth(0);
                tbService.getColumnModel().getColumn(8).setMinWidth(0);
                tbService.getColumnModel().getColumn(8).setMaxWidth(0);
            } else {
                tbService.getColumnModel().getColumn(7).setMinWidth(0);
                tbService.getColumnModel().getColumn(7).setMaxWidth(0);
                tbService.getColumnModel().getColumn(8).setMinWidth(0);
                tbService.getColumnModel().getColumn(8).setMaxWidth(0);
            }
            int index = 0;
            bindingTexFeild(index);
            conn = null;
        } catch (Exception e) {
        }

    }

    private void fillcobServiceType() {

        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.TableLocation> model = new Vector<vn.edu.vttu.model.TableLocation>();
        try {
            model = ServiceType.selectServiceType(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobType.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobType.setModel(defaultComboBoxModel);
        cobType.setRenderer(new PanelService.ItemRenderer());
        conn = null;

    }

    public static void setSelectedValue(JComboBox comboBox, int value) {
        vn.edu.vttu.model.ServiceType item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.ServiceType) comboBox.getItemAt(i);
            if (item.getId() == value) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void enableButton(boolean b) {
        btnAdd.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDelete.setEnabled(b);
        btnSave.setEnabled(!b);
        txtName.setEnabled(!b);
        txtUnit.setEnabled(!b);
        txtStore.setEnabled(!b);
        txtCost.setEnabled(!b);
        txtNote.setEnabled(!b);
        tbService.setEnabled(b);
    }

    private void bindingTexFeild(int index) {
        try {
            txtID.setText(String.valueOf(tbService.getValueAt(index, 0)));
            txtName.setText(String.valueOf(tbService.getValueAt(index, 1)));
            txtUnit.setText(String.valueOf(tbService.getValueAt(index, 3)));
            txtCost.setText(String.valueOf(tbService.getValueAt(index, 4)));
            txtStore.setText(String.valueOf(tbService.getValueAt(index, 5)));
            txtNote.setText(String.valueOf(tbService.getValueAt(index, 6)));
            setSelectedValue(cobType, Integer.parseInt(String.valueOf(tbService.getValueAt(index, 8))));
            img = String.valueOf(tbService.getValueAt(index, 7));
            fileAddress = String.valueOf(tbService.getValueAt(index, 7));
            BufferedImage bImg2 = ImageIO.read(new File(img));
            Image image2 = bImg2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lbImages.setIcon(new ImageIcon(image2));
        } catch (Exception e) {
        }

    }

    private boolean add() {
        boolean flag = false;
        if (txtName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên dịch vụ");
            flag = false;
            txtName.requestFocus();
        } else if (txtName.getText().length() > 50) {
            JOptionPane.showMessageDialog(getRootPane(), "Tên dịch vụ quá dài");
            flag = false;
            txtName.setText("");
            txtName.requestFocus();
        } else if (txtCost.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn giá");
            flag = false;
            txtCost.requestFocus();
        } else if (txtStore.getText().trim().equals("")) {
            txtStore.setText("0");
            flag = false;
        } else {
            name = txtName.getText();
            vn.edu.vttu.model.ServiceType svType = (vn.edu.vttu.model.ServiceType) cobType.getSelectedItem();
            type = svType.getId();
            unit = txtUnit.getText();
            cost = Integer.parseInt(txtCost.getText().replaceAll(",", ""));
            store = Integer.parseInt(txtStore.getText());
            note = txtNote.getText();
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                if (Service.insert(name, type, unit, store, note, fileAddress, conn)) {
                    int idSV = (int) Service.getMaxId(conn).getValueAt(0, 0);
                    if (ServiceCost.insert(idSV, cost, conn)) {
                        UploadFile ftpUploader = new UploadFile("127.0.0.1", "nhphuoc", "123456");
                        ftpUploader.uploadFile(fileAddress, filename, "/images/");
                        ftpUploader.disconnect();
                        conn.commit();
                        flag = true;
                    }
                }
                conn = null;
            } catch (Exception e) {
                try {
                    conn.rollback();
                    flag = false;
                } catch (SQLException ex) {
                    Logger.getLogger(PanelService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return flag;
    }

    private boolean update() {
        boolean flag = false;
        if (txtName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên dịch vụ");
            flag = false;
            txtName.requestFocus();
        } else if (txtName.getText().length() > 50) {
            JOptionPane.showMessageDialog(getRootPane(), "Tên dịch vụ quá dài");
            flag = false;
            txtName.setText("");
            txtName.requestFocus();
        } else if (txtCost.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn giá");
            flag = false;
            txtCost.requestFocus();
        } else if (txtStore.getText().trim().equals("")) {
            txtStore.setText("0");
            flag = false;
        } else {
            id = Integer.parseInt(txtID.getText());
            name = txtName.getText();
            vn.edu.vttu.model.ServiceType svType = (vn.edu.vttu.model.ServiceType) cobType.getSelectedItem();
            type = svType.getId();
            unit = txtUnit.getText();
            cost = Integer.parseInt(txtCost.getText().replaceAll(",", ""));
            store = Integer.parseInt(txtStore.getText());
            note = txtNote.getText();
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                if (Service.update(id, name, type, unit, store, note, img, conn)) {
                    if (ServiceCost.insert(id, cost, conn)) {
                        if (fileAddress.equals(img)) {
                            conn.commit();
                            flag = true;
                        } else {
                            UploadFile ftpUploader = new UploadFile("127.0.0.1", "nhphuoc", "123456");
                            ftpUploader.uploadFile(fileAddress, filename, "/images/");
                            ftpUploader.disconnect();
                            conn.commit();
                            flag = true;
                        }
                    }
                    conn = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                    flag=false;
                } catch (SQLException ex) {
                    Logger.getLogger(PanelService.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane3 = new javax.swing.JScrollPane();
        tbService = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAddType = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUnit = new javax.swing.JTextField();
        txtStore = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        lbImages = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCost = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cobType = new javax.swing.JComboBox();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        tbService = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbService.setModel(new javax.swing.table.DefaultTableModel(
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
        tbService.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbService.setGridColor(new java.awt.Color(204, 204, 204));
        tbService.setRowHeight(25);
        tbService.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbServiceMouseReleased(evt);
            }
        });
        tbService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbServiceKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbService);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Mã Dịch Vụ: ");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtID.setPreferredSize(new java.awt.Dimension(6, 16));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("Tên Dịch Vụ:");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 0));
        jLabel3.setText("Loại Dịch Vụ:");

        btnAddType.setText("+");
        btnAddType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTypeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 0));
        jLabel4.setText("Đơn Vị Tính:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 0));
        jLabel5.setText("Tồn Kho:");

        txtUnit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtStore.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 0));
        jLabel6.setText("Ghi Chú:");

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNote.setRows(5);
        jScrollPane1.setViewportView(txtNote);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 0));
        jLabel7.setText("Ảnh:");

        lbImages.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImages.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        lbImages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbImagesMouseClicked(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/add-icon.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/editicon.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/delete-icon.png"))); // NOI18N
        btnDelete.setText("Xóa");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Refresh-icon.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/print-icon.png"))); // NOI18N
        btnPrint.setText("Print");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 0));
        jLabel8.setText("Đơn Giá:");

        txtCost.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jButton1.setText("+");

        cobType.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtName)
                                        .addComponent(txtUnit)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(txtCost)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtStore, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAddType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(lbImages, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnAddType)
                    .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jButton1))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStore, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lbImages, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addContainerGap())
        );

        btnSearch.setText("Tìm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)
                        .addGap(0, 69, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add = true;
        fillcobServiceType();
        txtName.setText("");
        txtID.setText("");
        txtNote.setText("");
        txtStore.setText("0");
        txtUnit.setText("");
        txtCost.setText("");
        txtName.requestFocus();
        try {
            BufferedImage bImg1 = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/noImage.jpg"));
            Image image1 = bImg1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lbImages.setIcon(new ImageIcon(image1));
        } catch (Exception e) {
        }
        enableButton(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add = false;
        enableButton(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (add == true) {
            if (add()) {
                enableButton(true);
                loadTableService(1);
            }
        }else{
            if(update()){
                enableButton(true);
                loadTableService(1);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        enableButton(true);
        loadTableService(1);
    }//GEN-LAST:event_btnReloadActionPerformed

    private void tbServiceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbServiceMouseReleased
        int index = tbService.getSelectedRow();
        bindingTexFeild(index);
    }//GEN-LAST:event_tbServiceMouseReleased

    private void btnAddTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTypeActionPerformed
        int result = JOptionPane.showConfirmDialog(null, new PanelSelectServiceType(),
                "Chọn loại dịch vụ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

        }
    }//GEN-LAST:event_btnAddTypeActionPerformed

    private void tbServiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbServiceKeyReleased
        int index = tbService.getSelectedRow();
        bindingTexFeild(index);
    }//GEN-LAST:event_tbServiceKeyReleased

    private void lbImagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImagesMouseClicked
        try {
            JFileChooser fchooser = new JFileChooser();
            fchooser.showOpenDialog(getRootPane());
            File f = fchooser.getSelectedFile();
            filename = f.getName();
            fileAddress = f.getAbsolutePath();
            BufferedImage bImg2 = ImageIO.read(f);
            Image image2 = bImg2.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lbImages.setIcon(new ImageIcon(image2));
        } catch (Exception e) {

        }


    }//GEN-LAST:event_lbImagesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddType;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cobType;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbImages;
    private javax.swing.JTable tbService;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStore;
    private javax.swing.JTextField txtUnit;
    // End of variables declaration//GEN-END:variables
}

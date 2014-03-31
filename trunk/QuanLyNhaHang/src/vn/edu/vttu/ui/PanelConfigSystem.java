/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import config.InfoRestaurant;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import vn.edu.vttu.data.Account;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.LoginInformation;
import vn.edu.vttu.data.MD5;
import vn.edu.vttu.data.RestaurantInfo;
import vn.edu.vttu.data.ServiceType;
import vn.edu.vttu.data.Staff;
import vn.edu.vttu.sqlite.TbConnection;
import vn.edu.vttu.sqlite.UpdateValues;

/**
 *
 * @author nhphuoc
 */
public class PanelConfigSystem extends javax.swing.JPanel {

    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            try {
                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);

                if (value != null) {
                    vn.edu.vttu.model.Staff item = (vn.edu.vttu.model.Staff) value;
                    // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                    setText(item.getName().toUpperCase());
                }

                if (index == -1) {
                    vn.edu.vttu.model.Staff item = (vn.edu.vttu.model.Staff) value;
                    setText("" + item.getName());
                }

            } catch (Exception e) {
            }

            return this;
        }
    }

    /**
     * Creates new form PanelConfigSystem
     */
    private boolean add = false;
    private String username;
    private int id_staff;
    private String passs;

    public PanelConfigSystem() {
        initComponents();
        loadInfoRestaurant();
        enableControl(true);
        fillStaff();
        lbUsername.setText(LoginInformation.getUser());
        tbAccount.setModel(Account.accountGetAll(ConnectDB.conn()));
        tbAccount.getTableHeader().setReorderingAllowed(false);
        tbAccount.getColumnModel().getColumn(6).setPreferredWidth(0);
        tbAccount.getColumnModel().getColumn(6).setMinWidth(0);
        tbAccount.getColumnModel().getColumn(6).setMaxWidth(0);
        tbAccount.getColumnModel().getColumn(7).setPreferredWidth(0);
        tbAccount.getColumnModel().getColumn(7).setMinWidth(0);
        tbAccount.getColumnModel().getColumn(7).setMaxWidth(0);
    }

    private void loadInfoRestaurant() {
        RestaurantInfo rsInfo=RestaurantInfo.getinfo(ConnectDB.conn());
        txtName.setText(rsInfo.getName());
        txtPhone.setText(rsInfo.getPhone());
        txtEmail.setText(rsInfo.getEmail());
        txtAddress.setText(rsInfo.getAddress());
        txtHourNomal.setText(rsInfo.getHourReservationNomal()+"");
        txtParty.setText(rsInfo.getHourReservationParty()+"");
        txtWarning.setText(rsInfo.getMinuteWarning()+"");
        txtLogo.setText(rsInfo.getLogo());
        try {
            BufferedImage bImg = ImageIO.read(new File(rsInfo.getLogo()));
            Image image = bImg.getScaledInstance(220, 105, Image.SCALE_SMOOTH);
            ImageIcon format = new ImageIcon(image);
            lbLogo.setIcon(format);
            lbLogo.updateUI();
            lbLogo.repaint();
        } catch (Exception e) {
        }
        
    }

    private void enableControl(boolean b) {
        btnAdd.setEnabled(b);
        btnEdit.setEnabled(b);
        btnDel.setEnabled(b);
        btnSaveAccount.setEnabled(!b);
        txtID.setEnabled(!b);
        cobStaff.setEnabled(!b);
        txtPass.setEnabled(!b);
        txtUser.setEnabled(!b);
        cobType.setEnabled(!b);
        check.setEnabled(!b);
    }

    private void fillStaff() {
        Vector<vn.edu.vttu.model.ServiceType> model = new Vector<vn.edu.vttu.model.ServiceType>();
        try {
            model = Staff.selectStaffAccount(ConnectDB.conn());
        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobStaff.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobStaff.setModel(defaultComboBoxModel);
        cobStaff.setRenderer(new PanelConfigSystem.ItemRenderer());
    }

    public static void setSelectedStaff(JComboBox comboBox, int value) {
        vn.edu.vttu.model.Staff item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.Staff) comboBox.getItemAt(i);
            if (item.getId() == value) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void add() {
        vn.edu.vttu.model.Staff staff = (vn.edu.vttu.model.Staff) cobStaff.getSelectedItem();
        int id = staff.getId();
        if (txtUser.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập username","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (txtPass.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập password","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (txtUser.getText().trim().length() > 50 || txtUser.getText().trim().length() < 3) {
            JOptionPane.showMessageDialog(getRootPane(), "Tên đăng nhập >3 và <50 ký tự","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (Account.testUsername(txtUser.getText(), ConnectDB.conn()) == false) {
            JOptionPane.showMessageDialog(getRootPane(), "Tên đăng nhập đã được sử dụng","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (Account.testStaff(id, ConnectDB.conn()) == false) {
            JOptionPane.showMessageDialog(getRootPane(), "Nhân viên đã có tài khoản","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else {
            String pass = MD5.encryptMD5(txtPass.getText());
            int type = 0;
            if (cobType.getSelectedItem().equals("Administrator")) {
                type = 0;
            } else {
                type = 1;
            }
            boolean active;
            if (check.isSelected()) {
                active = true;
            } else {
                active = false;
            }

            if (Account.insert(id, txtUser.getText(), pass, type, active, ConnectDB.conn())) {
                JOptionPane.showMessageDialog(getRootPane(), "Thêm tài khoản thành công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                tbAccount.setModel(Account.accountGetAll(ConnectDB.conn()));
                tbAccount.getTableHeader().setReorderingAllowed(false);
                enableControl(true);
                tbAccount.getColumnModel().getColumn(6).setPreferredWidth(0);
                tbAccount.getColumnModel().getColumn(6).setMinWidth(0);
                tbAccount.getColumnModel().getColumn(6).setMaxWidth(0);
                tbAccount.getColumnModel().getColumn(7).setPreferredWidth(0);
                tbAccount.getColumnModel().getColumn(7).setMinWidth(0);
                tbAccount.getColumnModel().getColumn(7).setMaxWidth(0);
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Thất bại","Thông Báo",JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void update() {
        vn.edu.vttu.model.Staff staff = (vn.edu.vttu.model.Staff) cobStaff.getSelectedItem();
        int id = staff.getId();
        boolean user = false;
        boolean sstaff = false;
        if (!username.equals(txtUser.getText())) {
            if (Account.testUsername(txtUser.getText(), ConnectDB.conn()) == false) {
                user = false;
            } else {
                user = true;
            }
        } else {
            user = true;
        }

        if (id_staff != id) {
            if (Account.testStaff(id, ConnectDB.conn()) == false) {
                sstaff = false;
            } else {
                sstaff = true;
            }
        } else {
            sstaff = true;
        }
        if (txtUser.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập username","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (txtPass.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập password","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (txtUser.getText().trim().length() > 50 || txtUser.getText().trim().length() < 3) {
            JOptionPane.showMessageDialog(getRootPane(), "Tên đăng nhập >3 và <50 ký tự","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (user == false) {
            JOptionPane.showMessageDialog(getRootPane(), "Tên đăng nhập đã được sử dụng","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else if (sstaff == false) {
            JOptionPane.showMessageDialog(getRootPane(), "Nhân viên đã có tài khoản","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else {
            String pass = "";
            if (passs.equals(txtPass.getText())) {
                pass = txtPass.getText();
            } else {
                pass = MD5.encryptMD5(txtPass.getText());
            }
            int type = 0;
            if (cobType.getSelectedItem().equals("Administrator")) {
                type = 0;
            } else {
                type = 1;
            }
            boolean active;
            if (check.isSelected()) {
                active = true;
            } else {
                active = false;
            }
            int idacc = Integer.parseInt(txtID.getText());
            if (Account.update(id, txtUser.getText(), pass, type, active, idacc, ConnectDB.conn())) {
                JOptionPane.showMessageDialog(getRootPane(), "Cập nhật tài khoản thành công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                tbAccount.setModel(Account.accountGetAll(ConnectDB.conn()));
                tbAccount.getTableHeader().setReorderingAllowed(false);
                enableControl(true);
                tbAccount.getColumnModel().getColumn(6).setPreferredWidth(0);
                tbAccount.getColumnModel().getColumn(6).setMinWidth(0);
                tbAccount.getColumnModel().getColumn(6).setMaxWidth(0);
                tbAccount.getColumnModel().getColumn(7).setPreferredWidth(0);
                tbAccount.getColumnModel().getColumn(7).setMinWidth(0);
                tbAccount.getColumnModel().getColumn(7).setMaxWidth(0);
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Thất bại","Thông Báo",JOptionPane.ERROR_MESSAGE);
            }

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

        jPanel8 = new javax.swing.JPanel();
        tabTrash = new javax.swing.JTabbedPane();
        panelUser = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPassOld = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtPassNew = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtRePass = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        btnSaveChangesPass = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panelRestaurantInfo = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lbLogo = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtHourNomal = new javax.swing.JTextField();
        txtParty = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtWarning = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtLogo = new javax.swing.JTextField();
        btnChooseLogo = new javax.swing.JButton();
        panelRestoreData = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cobStaff = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        check = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        cobType = new javax.swing.JComboBox();
        txtUser = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnEdit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnDel = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnSaveAccount = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnReload = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbAccount = new javax.swing.JTable();
        txtPass = new javax.swing.JPasswordField();
        panelBackupdata = new javax.swing.JPanel();
        txtLink = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Thay đổi mật khẩu đăng nhập");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Tên Đăng Nhập:");

        lbUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbUsername.setText("Tên Đăng Nhập:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mật Khẩu Cũ:");

        txtPassOld.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPassOld.setToolTipText("");
        txtPassOld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Mật Khẩu Mới:");

        txtPassNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Nhập Lại Mật Khẩu: ");

        txtRePass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        btnSaveChangesPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
        btnSaveChangesPass.setText("Lưu Lại");
        btnSaveChangesPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveChangesPassActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("(*)");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setText("(*)");

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 0));
        jLabel9.setText("(*)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(btnSaveChangesPass, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtRePass, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                .addComponent(txtPassOld))
                            .addComponent(txtPassNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassOld, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPassNew, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRePass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSaveChangesPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelUserLayout = new javax.swing.GroupLayout(panelUser);
        panelUser.setLayout(panelUserLayout);
        panelUserLayout.setHorizontalGroup(
            panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUserLayout.setVerticalGroup(
            panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabTrash.addTab("Người Dùng", panelUser);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 183, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 102, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("THÔNG TIN NHÀ HÀNG");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tên Nhà Hàng:");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Địa Chỉ:");

        txtAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("ĐiệnThoại:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        lbLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        lbLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLogoMouseClicked(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
        btnSave.setText("Lưu Lại");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Email");

        txtPhone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPhone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Thời gian Cho phép đặt bàn(giờ)");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Thời Gian cảnh báo nhận bàn(phút):");

        txtHourNomal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtHourNomal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        txtParty.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtParty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Đặt Tiệc: ");

        txtWarning.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtWarning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Logo:");

        txtLogo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        btnChooseLogo.setText("...");
        btnChooseLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseLogoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel12)))
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName)
                    .addComponent(txtAddress)
                    .addComponent(txtEmail)
                    .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLogo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChooseLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtHourNomal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtParty, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtHourNomal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtParty, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseLogo))
                .addGap(5, 5, 5)
                .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRestaurantInfoLayout = new javax.swing.GroupLayout(panelRestaurantInfo);
        panelRestaurantInfo.setLayout(panelRestaurantInfoLayout);
        panelRestaurantInfoLayout.setHorizontalGroup(
            panelRestaurantInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRestaurantInfoLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRestaurantInfoLayout.setVerticalGroup(
            panelRestaurantInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelRestaurantInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabTrash.addTab("Thông Tin Nhà Hàng", panelRestaurantInfo);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 0));
        jLabel14.setText("Quyền");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 0));
        jLabel15.setText("Password");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 0));
        jLabel21.setText("Nhân Viên:");

        cobStaff.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 0));
        jLabel22.setText("Username:");

        check.setText("Hoạt Động");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 153, 0));
        jLabel23.setText("ID:");

        txtID.setEditable(false);

        cobType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administrator", "Nhân Viên" }));

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
        jToolBar1.add(jSeparator2);

        btnEdit.setBackground(new java.awt.Color(153, 204, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/edit-icon-24x24.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);
        jToolBar1.add(jSeparator3);

        btnDel.setBackground(new java.awt.Color(153, 204, 255));
        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/delete-icon-24x24.png"))); // NOI18N
        btnDel.setText("Xóa");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDel);
        jToolBar1.add(jSeparator4);

        btnSaveAccount.setBackground(new java.awt.Color(153, 204, 255));
        btnSaveAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon-24x24.png"))); // NOI18N
        btnSaveAccount.setText("Lưu");
        btnSaveAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAccountActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSaveAccount);
        jToolBar1.add(jSeparator5);

        btnReload.setBackground(new java.awt.Color(153, 204, 255));
        btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Refresh-icon-24x24.png"))); // NOI18N
        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReload);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 153, 0));
        jLabel24.setText("Tìm Kiếm: ");

        tbAccount.setModel(new javax.swing.table.DefaultTableModel(
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
        tbAccount.setGridColor(new java.awt.Color(204, 204, 204));
        tbAccount.setRowHeight(25);
        tbAccount.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbAccount.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbAccountMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbAccount);

        javax.swing.GroupLayout panelRestoreDataLayout = new javax.swing.GroupLayout(panelRestoreData);
        panelRestoreData.setLayout(panelRestoreDataLayout);
        panelRestoreDataLayout.setHorizontalGroup(
            panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(panelRestoreDataLayout.createSequentialGroup()
                .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRestoreDataLayout.createSequentialGroup()
                        .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUser)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRestoreDataLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cobStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRestoreDataLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(check))))
                    .addGroup(panelRestoreDataLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 125, Short.MAX_VALUE))
        );
        panelRestoreDataLayout.setVerticalGroup(
            panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRestoreDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(cobStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRestoreDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
        );

        tabTrash.addTab("Quản Lý Tài Khoản", panelRestoreData);

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Lưu");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Restore");

        javax.swing.GroupLayout panelBackupdataLayout = new javax.swing.GroupLayout(panelBackupdata);
        panelBackupdata.setLayout(panelBackupdataLayout);
        panelBackupdataLayout.setHorizontalGroup(
            panelBackupdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackupdataLayout.createSequentialGroup()
                .addGroup(panelBackupdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackupdataLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(txtLink, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(panelBackupdataLayout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jButton1)))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        panelBackupdataLayout.setVerticalGroup(
            panelBackupdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackupdataLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addGroup(panelBackupdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBackupdataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addContainerGap(237, Short.MAX_VALUE))
        );

        tabTrash.addTab("Sao Lưu Dữ Liệu", panelBackupdata);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTrash, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTrash)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseLogoActionPerformed
        try {
            JFileChooser fchooser = new JFileChooser();
            fchooser.showOpenDialog(getRootPane());
            File f = fchooser.getSelectedFile();
            txtLogo.setText(f.getAbsolutePath());
            BufferedImage bImg2 = ImageIO.read(f);
            Image image2 = bImg2.getScaledInstance(220, 105, Image.SCALE_SMOOTH);
            lbLogo.setIcon(new ImageIcon(image2));
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnChooseLogoActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (txtName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập tên nhà hàng","Thông Báo",JOptionPane.ERROR_MESSAGE);
            txtName.requestFocus();
        } else if (txtAddress.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập địa chỉ","Thông Báo",JOptionPane.ERROR_MESSAGE);
            txtAddress.requestFocus();
        } else if (txtPhone.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập số điện thoại","Thông Báo",JOptionPane.ERROR_MESSAGE);
            txtPhone.requestFocus();
        } else if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa nhập số điện thoại","Thông Báo",JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
        } else {            
            if (RestaurantInfo.update(
                    txtName.getText().trim(),
                    txtPhone.getText().trim(),
                    txtAddress.getText().trim(),
                    txtEmail.getText().trim(),
                    txtLogo.getText(),
                    Integer.parseInt(txtHourNomal.getText()),
                    Integer.parseInt(txtParty.getText()),
                    Integer.parseInt(txtWarning.getText()),
                    ConnectDB.conn()
                    )) {
                JOptionPane.showMessageDialog(getRootPane(), "Cập nhật thông tin nhà hàng thành công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);
                loadInfoRestaurant();
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Cập nhật không thành công","Thông Báo",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void lbLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLogoMouseClicked

    }//GEN-LAST:event_lbLogoMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            JFileChooser fchooser = new JFileChooser();
            fchooser.showOpenDialog(getRootPane());
            fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            File f = fchooser.getSelectedFile();
            txtLink.setText(f.getAbsolutePath());
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String executeCmd = "";
        String u = TbConnection.getValues().getUser();
        String p = TbConnection.getValues().getPass();
        String db = TbConnection.getValues().getDbname();
        //String Mysqlpath = getMysqlBinPath(u, p, db);
        executeCmd = "mysqldump - u " + u + " -p" + p + " " + db
                + " -r, backup.sql";
        try {
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Thành Công");
            } else {
                System.out.println("Thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add = true;
        enableControl(false);
        txtUser.setText("");
        txtPass.setText("");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add = false;
        enableControl(false);
        username = txtUser.getText();
        vn.edu.vttu.model.Staff staff = (vn.edu.vttu.model.Staff) cobStaff.getSelectedItem();
        id_staff = staff.getId();
        passs = txtPass.getText();
        System.out.println(passs);

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAccountActionPerformed
        if (add == true) {
            add();
        } else {
            update();
        }
    }//GEN-LAST:event_btnSaveAccountActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        tbAccount.setModel(Account.accountGetAll(ConnectDB.conn()));
        tbAccount.getTableHeader().setReorderingAllowed(false);
        enableControl(true);
        tbAccount.getColumnModel().getColumn(6).setPreferredWidth(0);
        tbAccount.getColumnModel().getColumn(6).setMinWidth(0);
        tbAccount.getColumnModel().getColumn(6).setMaxWidth(0);
        tbAccount.getColumnModel().getColumn(7).setPreferredWidth(0);
        tbAccount.getColumnModel().getColumn(7).setMinWidth(0);
        tbAccount.getColumnModel().getColumn(7).setMaxWidth(0);
    }//GEN-LAST:event_btnReloadActionPerformed

    private void tbAccountMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAccountMouseReleased
        int index = tbAccount.getSelectedRow();
        txtID.setText(tbAccount.getValueAt(index, 0).toString());
        txtUser.setText(tbAccount.getValueAt(index, 3).toString());
        String quyen = tbAccount.getValueAt(index, 4).toString();
        if (quyen.equals("Administrator")) {
            cobType.setSelectedItem("Administrator");
        } else {
            cobType.setSelectedItem("Nhân Viên");
        }
        String trangthai = tbAccount.getValueAt(index, 5).toString();
        if (trangthai.equals("Hoạt Động")) {
            check.setSelected(true);
        } else {
            check.setSelected(false);
        }
        setSelectedStaff(cobStaff, Integer.parseInt(tbAccount.getValueAt(index, 6).toString()));
        txtPass.setText(tbAccount.getValueAt(index, 7).toString());

    }//GEN-LAST:event_tbAccountMouseReleased

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        if (txtID.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn tài khoản nào","Thông Báo",JOptionPane.ERROR_MESSAGE);
        }else if(LoginInformation.getId()==Integer.parseInt(txtID.getText())){
            JOptionPane.showMessageDialog(getRootPane(), "Tài khoản đang đăng nhập, không thể xóa","Thông Báo",JOptionPane.ERROR_MESSAGE);
        } else {
            if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có muốn xóa tài khoản này", "Hỏi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (Account.delete(Integer.parseInt(txtID.getText()), ConnectDB.conn())) {
                    JOptionPane.showMessageDialog(getRootPane(), "Xóa thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    tbAccount.setModel(Account.accountGetAll(ConnectDB.conn()));
                    tbAccount.getTableHeader().setReorderingAllowed(false);
                    enableControl(true);
                    tbAccount.getColumnModel().getColumn(6).setPreferredWidth(0);
                    tbAccount.getColumnModel().getColumn(6).setMinWidth(0);
                    tbAccount.getColumnModel().getColumn(6).setMaxWidth(0);
                    tbAccount.getColumnModel().getColumn(7).setPreferredWidth(0);
                    tbAccount.getColumnModel().getColumn(7).setMinWidth(0);
                    tbAccount.getColumnModel().getColumn(7).setMaxWidth(0);

                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Xóa thành không công", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnSaveChangesPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveChangesPassActionPerformed
       if(!MD5.encryptMD5(txtPassOld.getText()).equals(LoginInformation.getPass())){
           JOptionPane.showMessageDialog(getRootPane(), "Mật khẩu cũ không đúng","Thông Báo",JOptionPane.ERROR_MESSAGE);
           txtPassOld.requestFocus();
       }else if(txtPassNew.getText().equals("")){
           JOptionPane.showMessageDialog(getRootPane(), "Chưa nhập mật khẩu mới","Thông Báo",JOptionPane.ERROR_MESSAGE);
           txtPassOld.requestFocus();
       }else if(!txtRePass.getText().equals(txtPassNew.getText())){
           JOptionPane.showMessageDialog(getRootPane(), "Nhập lại mật khẩu không đúng","Thông Báo",JOptionPane.ERROR_MESSAGE);
           txtRePass.requestFocus();
       }else{
           if(Account.updatePass(LoginInformation.getUser(),MD5.encryptMD5(txtRePass.getText()),ConnectDB.conn())){
               JOptionPane.showMessageDialog(getRootPane(), "Cập nhật mật khẩu thành công","Thông Báo",JOptionPane.INFORMATION_MESSAGE);    
               txtPassOld.setText("");
               txtPassNew.setText("");
               txtRePass.setText("");
           }else{
               JOptionPane.showMessageDialog(getRootPane(), "Thất Bại","Thông Báo",JOptionPane.INFORMATION_MESSAGE);               
           }
       }
    }//GEN-LAST:event_btnSaveChangesPassActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChooseLogo;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveAccount;
    private javax.swing.JButton btnSaveChangesPass;
    private javax.swing.JCheckBox check;
    private javax.swing.JComboBox cobStaff;
    private javax.swing.JComboBox cobType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JPanel panelBackupdata;
    private javax.swing.JPanel panelRestaurantInfo;
    private javax.swing.JPanel panelRestoreData;
    private javax.swing.JPanel panelUser;
    private javax.swing.JTabbedPane tabTrash;
    private javax.swing.JTable tbAccount;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHourNomal;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLink;
    private javax.swing.JTextField txtLogo;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtParty;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPassNew;
    private javax.swing.JPasswordField txtPassOld;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JPasswordField txtRePass;
    private javax.swing.JTextField txtUser;
    private javax.swing.JTextField txtWarning;
    // End of variables declaration//GEN-END:variables
}

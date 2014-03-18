/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import config.InfoRestaurant;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import vn.edu.vttu.data.ColoredTableCellRenderer;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.NumberCellRenderer;
import vn.edu.vttu.data.Service;
import vn.edu.vttu.data.ServiceCost;
import vn.edu.vttu.data.ServiceType;
import vn.edu.vttu.data.Unit;
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

    class ItemRendererUnit extends BasicComboBoxRenderer {

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

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            setText("Xem Ảnh");
            return this;
        }
    }

    static class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                // delete row method (when "delete" is pressed)
                System.out.println("Key \"Delete\" Pressed");
            }
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
    private int unit;
    private int store = 0;
    private String note;
    private String img;
    private String filename;
    private String fileAddress;
    private int indexx = 0;

    public PanelService() {
        initComponents();
        loadTableService(indexx);
        enableButton(true);
        fillcobServiceType();
        fillcobUnit();

    }

    private void loadTableService(int index) {
        try {
            conn = ConnectDB.conn();
            tbService.setModel(Service.serviceGetAll(conn));
            if (tbService.getRowCount() > 0) {
                tbService.setRowSelectionInterval(index, 0);
            }
            tbService.getColumnModel().getColumn(6).setMinWidth(0);
            tbService.getColumnModel().getColumn(6).setMaxWidth(0);

            tbService.getColumnModel().getColumn(7).setMinWidth(0);
            tbService.getColumnModel().getColumn(7).setMaxWidth(0);

            tbService.getColumnModel().getColumn(8).setMinWidth(0);
            tbService.getColumnModel().getColumn(8).setMaxWidth(0);
            for (int i = 0; i < tbService.getColumnCount(); i++) {
                tbService.getColumnModel().getColumn(i).setCellRenderer(new ColoredTableCellRenderer());
            }
            bindingTexFeild(index);
            conn = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbService.getColumnModel().getColumn(4).setCellRenderer(new NumberCellRenderer());

    }

    private void fillcobServiceType() {

        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.ServiceType> model = new Vector<vn.edu.vttu.model.ServiceType>();
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
        cobUnit.setRenderer(new PanelService.ItemRendererUnit());
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

    public static void setSelectedValueUnit(JComboBox comboBox, int value) {
        vn.edu.vttu.model.Unit item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.Unit) comboBox.getItemAt(i);
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
        btnChooseImage.setEnabled(!b);
        txtName.setEnabled(!b);
        txtCost.setEnabled(!b);
        txtNote.setEnabled(!b);
        tbService.setEnabled(b);
    }

    private void bindingTexFeild(int index) {
        DecimalFormat df = new DecimalFormat("#,###,###");
        try {
            txtID.setText(String.valueOf(tbService.getValueAt(index, 0)));
            txtName.setText(String.valueOf(tbService.getValueAt(index, 1)));
            txtCost.setText(df.format(Integer.parseInt(String.valueOf(tbService.getValueAt(index, 4)).trim())));

            txtNote.setText(String.valueOf(tbService.getValueAt(index, 5)));
            setSelectedValue(cobType, Integer.parseInt(String.valueOf(tbService.getValueAt(index, 7))));
            try {
                setSelectedValueUnit(cobUnit, Integer.parseInt(String.valueOf(tbService.getValueAt(index, 8))));
            } catch (Exception e) {
            }

            txtLinkImage.setText(String.valueOf(tbService.getValueAt(index, 6)));

        } catch (Exception e) {
            e.printStackTrace();
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
        } else {
            name = txtName.getText();
            vn.edu.vttu.model.ServiceType svType = (vn.edu.vttu.model.ServiceType) cobType.getSelectedItem();
            type = svType.getId();
            vn.edu.vttu.model.Unit _unit = (vn.edu.vttu.model.Unit) cobUnit.getSelectedItem();
            unit = _unit.getId();
            try {
                cost = Integer.parseInt(txtCost.getText().trim().replaceAll("\\.", ""));
            } catch (Exception e) {
                cost = Integer.parseInt(txtCost.getText().trim().replaceAll(",", ""));
            }

            note = txtNote.getText();
            img = txtLinkImage.getText();
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                if (Service.insert(name, type, unit, note, "images/" + new File(img).getName(), conn)) {
                    int idSV = (int) Service.getMaxId(conn).getValueAt(0, 0);
                    if (ServiceCost.insert(idSV, cost, conn)) {
                        UploadFile ftpUploader = new UploadFile(InfoRestaurant.getIPserver(), InfoRestaurant.getUsernameserver(), InfoRestaurant.getPassserver());
                        ftpUploader.uploadFile(img, new File(img).getName(), "/images/");
                        ftpUploader.disconnect();
                        conn.commit();
                        flag = true;
                    }
                }
                conn = null;
            } catch (Exception e) {

                try {
                    conn.rollback();
                    JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi\n" + e.getMessage());
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
        } else {
            id = Integer.parseInt(txtID.getText());
            name = txtName.getText();
            vn.edu.vttu.model.ServiceType svType = (vn.edu.vttu.model.ServiceType) cobType.getSelectedItem();
            type = svType.getId();
            vn.edu.vttu.model.Unit _unit = (vn.edu.vttu.model.Unit) cobUnit.getSelectedItem();
            unit = _unit.getId();
            try {
                cost = Integer.parseInt(txtCost.getText().replaceAll("\\.", ""));
            } catch (Exception e) {
                cost = Integer.parseInt(txtCost.getText().replaceAll(",", ""));
            }

            note = txtNote.getText();
            img = txtLinkImage.getText();
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                if (Service.update(id, name, type, unit, note, img, conn)) {
                    if (ServiceCost.insert(id, cost, conn)) {
                        if (!tbService.getValueAt(tbService.getSelectedRow(), 6).equals(txtLinkImage.getText().trim())) {
                            try {
                                UploadFile ftpUploader = new UploadFile(InfoRestaurant.getIPserver(), InfoRestaurant.getUsernameserver(), InfoRestaurant.getPassserver());
                                ftpUploader.uploadFile(img, new File(img).getName(), "/images/");
                                ftpUploader.disconnect();
                            } catch (Exception e) {
                                throw new Exception();
                            }

                            conn.commit();
                            flag = true;
                        } else {
                            conn.commit();
                            flag = true;
                        }
                    }
                    conn = null;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                    JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                    flag = false;
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCost = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cobType = new javax.swing.JComboBox();
        txtNote = new javax.swing.JTextField();
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
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnPrint = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnViewImage = new javax.swing.JButton();
        btnCooking = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtLinkImage = new javax.swing.JTextField();
        btnChooseImage = new javax.swing.JButton();
        cobUnit = new javax.swing.JComboBox();
        btnAddUnit = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        lbImages = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        tbService = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbService.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        tbService.setSelectionBackground(new java.awt.Color(102, 153, 255));
        tbService.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 0));
        jLabel6.setText("Ghi Chú:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 0));
        jLabel7.setText("Ảnh:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 0));
        jLabel8.setText("Đơn Giá:");

        txtCost.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostKeyTyped(evt);
            }
        });

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cobType.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

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
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
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
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
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
        jToolBar1.add(jSeparator5);

        btnPrint.setBackground(new java.awt.Color(153, 204, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/print-icon-24x24.png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrint);
        jToolBar1.add(jSeparator6);

        btnViewImage.setBackground(new java.awt.Color(153, 204, 255));
        btnViewImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Images-icon.png"))); // NOI18N
        btnViewImage.setText("Xem Ảnh");
        btnViewImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewImageActionPerformed(evt);
            }
        });
        jToolBar1.add(btnViewImage);

        btnCooking.setBackground(new java.awt.Color(153, 204, 255));
        btnCooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Cook-Book-icon.png"))); // NOI18N
        btnCooking.setText("Chế Biến");
        btnCooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCookingActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCooking);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 0));
        jLabel11.setText("Link Ảnh:");

        txtLinkImage.setEditable(false);

        btnChooseImage.setText("...");
        btnChooseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseImageActionPerformed(evt);
            }
        });

        cobUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddUnit.setText("+");
        btnAddUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUnitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel11)
                        .addGap(25, 25, 25)
                        .addComponent(txtLinkImage, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChooseImage, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(cobUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(29, 29, 29)
                                .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddType)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnAddType)
                    .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jButton1)
                    .addComponent(cobUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtLinkImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseImage))
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(199, 199, 199)
                .addComponent(jLabel7)
                .addGap(122, 122, 122))
        );

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        lbImages.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImages.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));
        lbImages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbImagesMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 0));
        jLabel10.setText("Tìm Kiếm Dịch Vụ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbImages, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbImages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add = true;
        fillcobServiceType();
        txtName.setText("");
        txtID.setText("");
        txtNote.setText("");
        txtCost.setText("");
        txtLinkImage.setText("");
        txtName.requestFocus();
        enableButton(false);

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add = false;
        enableButton(false);
        indexx = tbService.getSelectedRow();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (add == true) {
            if (add()) {
                enableButton(true);
                loadTableService(indexx);
            }
        } else {
            if (update()) {
                enableButton(true);
                System.out.println(indexx);
                loadTableService(indexx);
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
        VariableStatic.setIdService(Integer.parseInt(String.valueOf(tbService.getValueAt(index, 0))));
        VariableStatic.setNameService(String.valueOf(tbService.getValueAt(index, 1)));
    }//GEN-LAST:event_tbServiceMouseReleased

    private void btnAddTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTypeActionPerformed

        String input = JOptionPane.showInputDialog(getRootPane(), "Nhập loại dịch vụ");
        if (input != null) {
            if (input.length() > 50) {
                JOptionPane.showMessageDialog(getRootPane(), "Bạn nhập tên quá dài");
            } else {
                conn = ConnectDB.conn();
                if (ServiceType.insert(input, conn)) {
                    fillcobServiceType();
                    JOptionPane.showMessageDialog(getRootPane(), "Thêm loại thành công");
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                }
            }
        }

    }//GEN-LAST:event_btnAddTypeActionPerformed

    private void tbServiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbServiceKeyReleased
        int index = tbService.getSelectedRow();
        bindingTexFeild(index);
        VariableStatic.setIdService(Integer.parseInt(String.valueOf(tbService.getValueAt(index, 0))));
        VariableStatic.setNameService(String.valueOf(tbService.getValueAt(index, 1)));
    }//GEN-LAST:event_tbServiceKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String input = JOptionPane.showInputDialog(getRootPane(), "Nhập đơn giá mới");
        if (input != null) {
            if (input.length() > 10) {
                JOptionPane.showMessageDialog(getRootPane(), "Số quá lớn");
            } else {
                conn = ConnectDB.conn();
                if (ServiceCost.insert(Integer.parseInt(txtID.getText()), Integer.parseInt(input), conn)) {
                    txtCost.setText(input);
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Đã xảy ra lỗi");
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyPressed
        int key = evt.getKeyChar();
        String st = txtCost.getText();
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
        if (!txtCost.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCost.getText().trim().replaceAll("\\.", ""));
                txtCost.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCost.getText().trim().replaceAll(",", ""));
                txtCost.setText(String.valueOf(df.format(num)));
            }

        }
    }//GEN-LAST:event_txtCostKeyPressed

    private void txtCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyTyped
        int key = evt.getKeyChar();
        String st = txtCost.getText();
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
    }//GEN-LAST:event_txtCostKeyTyped

    private void txtCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyReleased
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtCost.getText().trim().equals("")) {
            try {
                Long num = Long.parseLong(txtCost.getText().trim().replaceAll("\\.", ""));
                txtCost.setText(String.valueOf(df.format(num)));
            } catch (Exception e) {
                Long num = Long.parseLong(txtCost.getText().trim().replaceAll(",", ""));
                txtCost.setText(String.valueOf(df.format(num)));
            }

        }
    }//GEN-LAST:event_txtCostKeyReleased

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        try {
            conn = ConnectDB.conn();
            tbService.setModel(Service.searchGetAll(txtSearch.getText(), conn));
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
            tbService.getColumnModel().getColumn(4).setCellRenderer(new NumberCellRenderer());
            int index = 0;
            bindingTexFeild(index);
            conn = null;
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        try {
            HashMap<String, Object> parameter = new HashMap<String, Object>();
            //parameter.put("idReservation", idTableReservation);
            String sql = "call service_get_all()";
            JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/vn/edu/vttu/report/Invoice.jrxml"));
            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parameter, conn);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnPrintActionPerformed

    private void lbImagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImagesMouseClicked


    }//GEN-LAST:event_lbImagesMouseClicked

    private void btnChooseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseImageActionPerformed
        try {
            JFileChooser fchooser = new JFileChooser();
            fchooser.showOpenDialog(getRootPane());
            File f = fchooser.getSelectedFile();
            txtLinkImage.setText(f.getAbsolutePath());
            BufferedImage bImg2 = ImageIO.read(f);
            Image image2 = bImg2.getScaledInstance(122, 144, Image.SCALE_SMOOTH);
            lbImages.setIcon(new ImageIcon(image2));
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnChooseImageActionPerformed

    private void btnViewImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewImageActionPerformed
        /*
         int result = JOptionPane.showOptionDialog(null, new PanelViewImageService(),
         "Xem Danh Sách Đặt Bàn", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
         */
        Image image = null;
        try {
            URL url = new URL(
                    "http://" + InfoRestaurant.getIPserver() + "/" + txtLinkImage.getText());
            image = ImageIO.read(url);
            lbImages.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         try {
         BufferedImage bImg2 = ImageIO.read(new File(txtLinkImage.getText()));
         Image image2 = bImg2.getScaledInstance(122, 144, Image.SCALE_SMOOTH);
         lbImages.setIcon(new ImageIcon(image2));
         } catch (Exception e) {
         }
         */

    }//GEN-LAST:event_btnViewImageActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (txtID.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(getRootPane(), "Bạn chưa chọn dịch vụ");
        } else {
            conn = ConnectDB.conn();
            if (Service.countServiceUsing(Integer.parseInt(txtID.getText().trim()), conn)) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có thật sự muốn xóa dịch vụ này không?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    if (Service.delete(Integer.parseInt(txtID.getText().trim()), conn)) {
                        JOptionPane.showMessageDialog(getRootPane(), "Đã xóa thành công");
                        loadTableService(1);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Dịch vụ đang được sử dụng, không thể xóa");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            System.out.println("press");
        }
    }//GEN-LAST:event_btnAddKeyPressed

    private void btnCookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCookingActionPerformed
        int result = JOptionPane.showOptionDialog(null, new PanelCooking(),
                "Xem Danh Sách Đặt Bàn", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }//GEN-LAST:event_btnCookingActionPerformed

    private void btnAddUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUnitActionPerformed
        int result = JOptionPane.showConfirmDialog(null, new PanelUnit(),
                "Thêm Đơn Vị Tính", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            fillcobUnit();
        }
    }//GEN-LAST:event_btnAddUnitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddType;
    private javax.swing.JButton btnAddUnit;
    private javax.swing.JButton btnChooseImage;
    private javax.swing.JButton btnCooking;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnViewImage;
    private javax.swing.JComboBox cobType;
    private javax.swing.JComboBox cobUnit;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbImages;
    private javax.swing.JTable tbService;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLinkImage;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

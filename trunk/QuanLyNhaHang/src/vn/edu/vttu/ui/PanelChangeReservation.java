/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Component;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.VariableStatic;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Customer;

/**
 *
 * @author nhphuoc
 */
public class PanelChangeReservation extends javax.swing.JPanel {

    class ItemRendererTable extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                vn.edu.vttu.model.Table item = (vn.edu.vttu.model.Table) value;
                // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                setText(item.getNameTable());
            }

            if (index == -1) {
                vn.edu.vttu.model.Table item = (vn.edu.vttu.model.Table) value;
                setText("" + item.getNameTable());
            }

            return this;
        }
    }
    class ItemRendererCustomer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                vn.edu.vttu.model.Customer item = (vn.edu.vttu.model.Customer) value;
                // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                setText(item.getName());
            }

            if (index == -1) {
                vn.edu.vttu.model.Customer item = (vn.edu.vttu.model.Customer) value;
                setText("" + item.getName());
            }

            return this;
        }
    }

    /**
     * Creates new form frmChageReservation
     */
    private Connection conn;
    private int idTable;
    private String nameTable;
    private Timestamp date;
    private int statusBG = -1;

    public PanelChangeReservation() {
        initComponents();
        //tbChangeReservation.setRowSelectionInterval(0, 0);        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        date = VariableStatic.getDateTimeReservation();
        dtDateReservation.setDate(date);
        DecimalFormat df = new DecimalFormat("#,###,###");
        txtRepay.setText(df.format(VariableStatic.getPrePay()));
        fillComboTable();
        fillComboCustomer();
        setSelectedValueCustomer(cobCustomer, VariableStatic.getIdCustomer());
        vn.edu.vttu.model.Customer idcus = (vn.edu.vttu.model.Customer) cobCustomer.getSelectedItem();
        int idcustomer = idcus.getId();
        if (idTable != 0) {
            VariableStatic.setIdCustomer(idcustomer);
        }
        
        
        vn.edu.vttu.model.Table idtb = (vn.edu.vttu.model.Table) cobTable.getSelectedItem();
        int idTable = idtb.getIdTable();
        if (idTable != 0) {
            VariableStatic.setIdTable(idTable);
        }
        txtRepay.setText(String.valueOf(VariableStatic.getPrePay()));
    }

    private void fillComboTable() {
        Vector<vn.edu.vttu.model.Table> model = new Vector<vn.edu.vttu.model.Table>();
        try {
            model = Table.getByDateNotReservationVector(date, 2, ConnectDB.conn());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobTable.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobTable.setModel(defaultComboBoxModel);
        cobTable.setRenderer(new PanelChangeReservation.ItemRendererTable());
        conn = null;
    }
    private void fillComboCustomer() {
        Vector<vn.edu.vttu.model.Customer> model = new Vector<vn.edu.vttu.model.Customer>();
        try {
            model = Customer.selectCustomer2(ConnectDB.conn());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobCustomer.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobCustomer.setModel(defaultComboBoxModel);
        cobCustomer.setRenderer(new PanelChangeReservation.ItemRendererCustomer());        
    }
    public static void setSelectedValueCustomer(JComboBox comboBox, int value) {
        vn.edu.vttu.model.Customer item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (vn.edu.vttu.model.Customer) comboBox.getItemAt(i);
            if (item.getId() == value) {
                comboBox.setSelectedIndex(i);
                break;
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

        dtDateReservation = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cobTable = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtRepay = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cobCustomer = new javax.swing.JComboBox();

        dtDateReservation.setDate(new Date());
        dtDateReservation.setDateFormatString("dd/MM/yyyy HH:mm");
        dtDateReservation.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dtDateReservation.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtDateReservationPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Chọn Ngày");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("Chọn Bàn:");

        cobTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cobTablePropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 0));
        jLabel4.setText("Đưa Trước:");

        txtRepay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRepayKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRepayKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("Khách Hàng:");

        cobCustomer.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cobCustomerPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cobTable, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(15, 15, 15)
                        .addComponent(dtDateReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cobCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRepay))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cobTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(dtDateReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtRepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dtDateReservationPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtDateReservationPropertyChange

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(dtDateReservation.getDate());
        Timestamp ts = Timestamp.valueOf(datetime);
        date = Timestamp.valueOf(datetime);
        VariableStatic.setDateTimeReservation(ts);


    }//GEN-LAST:event_dtDateReservationPropertyChange

    private void cobTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cobTablePropertyChange
        try {            
            vn.edu.vttu.model.Table idtb = (vn.edu.vttu.model.Table) cobTable.getSelectedItem();
            int idTable = idtb.getIdTable();
            if (idTable != 0) {
                VariableStatic.setIdTable(idTable);
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_cobTablePropertyChange

    private void cobCustomerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cobCustomerPropertyChange
       try {            
            vn.edu.vttu.model.Customer idCus = (vn.edu.vttu.model.Customer) cobCustomer.getSelectedItem();
            int idCustomer = idCus.getId();
            if (idCustomer != 0) {
                VariableStatic.setIdCustomer(idCustomer);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cobCustomerPropertyChange

    private void txtRepayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepayKeyReleased
        DecimalFormat df = new DecimalFormat("#,###,###");
        if (!txtRepay.getText().trim().equals("")) {
            Long num = Long.parseLong(txtRepay.getText().trim().replaceAll("\\.", ""));
            txtRepay.setText(String.valueOf(df.format(num)));
            VariableStatic.setPrePay(Integer.parseInt(txtRepay.getText().trim().replaceAll("\\.", "")));
        }        
    }//GEN-LAST:event_txtRepayKeyReleased

    private void txtRepayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepayKeyTyped
       int key = evt.getKeyChar();
        String st = txtRepay.getText();
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
    }//GEN-LAST:event_txtRepayKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cobCustomer;
    private javax.swing.JComboBox cobTable;
    private com.toedter.calendar.JDateChooser dtDateReservation;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtRepay;
    // End of variables declaration//GEN-END:variables
}

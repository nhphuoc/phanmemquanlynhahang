/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import com.lowagie.text.Cell;
import com.lowagie.text.Row;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Customer;
import vn.edu.vttu.data.ExportExcel;
import vn.edu.vttu.data.Invoice;
import vn.edu.vttu.data.NumberCellRenderer;
import vn.edu.vttu.data.Staff;
import vn.edu.vttu.data.TableService;

/**
 *
 * @author nhphuoc
 */
public class PanelSaleInvoice extends javax.swing.JPanel {

    class ItemRendererInvoice extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                vn.edu.vttu.model.Customer item = (vn.edu.vttu.model.Customer) value;
                // đây là thông tin ta sẽ hiển thị , đối bảng khác sẽ khác cột chúng ta sẽ đổi lại tên tương ứng
                setText(item.getName().toUpperCase());
            }

            if (index == -1) {
                vn.edu.vttu.model.Customer item = (vn.edu.vttu.model.Customer) value;
                setText("" + item.getName());
            }

            return this;
        }
    }

    class ItemRendererStaff extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
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

            return this;
        }
    }

    /**
     * Creates new form PanelSaleInvoice
     */
    private Connection conn;

    public PanelSaleInvoice() {
        initComponents();
        fillcobCustomer();
        fillcobStaff();
    }

    private void fillcobCustomer() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.Customer> model = new Vector<vn.edu.vttu.model.Customer>();
        try {
            model = Customer.selectCustomer1(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobCustomer.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobCustomer.setModel(defaultComboBoxModel);
        cobCustomer.setRenderer(new PanelSaleInvoice.ItemRendererInvoice());
        conn = null;
    }

    private void fillcobStaff() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.Staff> model = new Vector<vn.edu.vttu.model.Staff>();
        try {
            model = Staff.selectStaff(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobStaff.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobStaff.setModel(defaultComboBoxModel);
        cobStaff.setRenderer(new PanelSaleInvoice.ItemRendererStaff());
        conn = null;
    }

    private void loadInvoice() {
        conn = ConnectDB.conn();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime1 = formatter.format(dtFromDate.getDate());
        Timestamp ts1 = Timestamp.valueOf(datetime1);
        String datetime2 = formatter.format(dtToDate.getDate());
        Timestamp ts2 = Timestamp.valueOf(datetime2);
        vn.edu.vttu.model.Staff staff = (vn.edu.vttu.model.Staff) cobStaff.getSelectedItem();
        int idStaff = staff.getId();
        vn.edu.vttu.model.Customer customer = (vn.edu.vttu.model.Customer) cobCustomer.getSelectedItem();
        int idCustomer = customer.getId();
        int choise = 0;
        if (idStaff == 0 && idCustomer == 0) {
            choise = 0;
        } else if (idStaff == 0 && idCustomer != 0) {
            choise = 1;
        } else if (idStaff != 0 && idCustomer == 0) {
            choise = 2;
        } else if (idStaff != 0 && idCustomer != 0) {
            choise = 3;
        }
        tbInvoiceList.setModel(Invoice.getAll(ts1, ts2, idStaff, idCustomer, choise, conn));
        tbInvoiceList.getColumnModel().getColumn(7).setMinWidth(0);
        tbInvoiceList.getColumnModel().getColumn(7).setMaxWidth(0);
        tbInvoiceList.getColumnModel().getColumn(4).setCellRenderer(new NumberCellRenderer());
        tbInvoiceList.getColumnModel().getColumn(5).setCellRenderer(new NumberCellRenderer());

        conn = null;
    }

    private void loadInvoiceDetail(int id) {
        tbInvoiceDetail.setModel(TableService.getByIdReservationInvoice(id, ConnectDB.conn()));
        tbInvoiceDetail.getColumnModel().getColumn(0).setMinWidth(0);        
        tbInvoiceDetail.getColumnModel().getColumn(6).setMinWidth(0);
        tbInvoiceDetail.getColumnModel().getColumn(0).setMaxWidth(0);        
        tbInvoiceDetail.getColumnModel().getColumn(6).setMaxWidth(0);
        tbInvoiceDetail.getColumnModel().getColumn(4).setCellRenderer(new NumberCellRenderer());
        tbInvoiceDetail.getColumnModel().getColumn(5).setCellRenderer(new NumberCellRenderer());
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
        jLabel2 = new javax.swing.JLabel();
        dtFromDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dtToDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cobStaff = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cobCustomer = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        lbNumberInvoice = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSearchInvoice = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnExportToExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbInvoiceList = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbInvoiceDetail = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setBackground(new java.awt.Color(255, 153, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HÓA ĐƠN BÁN HÀNG");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Từ Ngày:");

        dtFromDate.setDate(new Date());
        dtFromDate.setDateFormatString("dd/MM/yyyy");
        dtFromDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtFromDatePropertyChange(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Đến Ngày:");

        dtToDate.setDate(new Date());
        dtToDate.setDateFormatString("dd/MM/yyyy");
        dtToDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtToDatePropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Nhân Viên:");

        cobStaff.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Khách Hàng:");

        cobCustomer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tổng Doanh Thu:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Tổng Hóa Đơn:");

        lbTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 0, 0));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTotal.setText("0");

        lbNumberInvoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbNumberInvoice.setForeground(new java.awt.Color(0, 51, 255));
        lbNumberInvoice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbNumberInvoice.setText("0");

        jToolBar1.setBackground(new java.awt.Color(102, 153, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnSearchInvoice.setBackground(new java.awt.Color(102, 153, 255));
        btnSearchInvoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSearchInvoice.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Search-icon.png"))); // NOI18N
        btnSearchInvoice.setText("Tim Kiếm Hóa Đơn");
        btnSearchInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchInvoiceActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSearchInvoice);

        jToolBar2.setBackground(new java.awt.Color(31, 114, 70));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnExportToExcel.setBackground(new java.awt.Color(31, 114, 70));
        btnExportToExcel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExportToExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportToExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Excel-icon.png"))); // NOI18N
        btnExportToExcel.setText("Xuất Ra Excel");
        btnExportToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportToExcelActionPerformed(evt);
            }
        });
        jToolBar2.add(btnExportToExcel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cobCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cobStaff, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lbTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNumberInvoice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(dtFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(dtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cobStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(7, 7, 7)
                .addComponent(lbNumberInvoice)
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal)
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 97, Short.MAX_VALUE))
        );

        tbInvoiceList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbInvoiceList = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        tbInvoiceList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ngày Thanh Toán", "Bàn - Tầng", "Khuyễn Mãi", "Giảm Giá", "Thanh Toán", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbInvoiceList.setGridColor(new java.awt.Color(204, 204, 204));
        tbInvoiceList.setRowHeight(25);
        tbInvoiceList.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbInvoiceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbInvoiceList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbInvoiceListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbInvoiceList);

        tbInvoiceDetail.getTableHeader().setReorderingAllowed(false);
        tbInvoiceDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Dịch Vụ", "Số Lượng", "ĐVT", "Đơn Giá", "Thành Tiền"
            }
        ));
        tbInvoiceDetail.setGridColor(new java.awt.Color(204, 204, 204));
        tbInvoiceDetail.setRowHeight(25);
        tbInvoiceDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tbInvoiceDetail);

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 102, 0));
        jLabel8.setText("Chi Tiết Hóa Đơn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchInvoiceActionPerformed
        loadInvoice();
        int row = Integer.parseInt(String.valueOf(tbInvoiceList.getRowCount()));
        lbNumberInvoice.setText(String.valueOf(row) + " Hóa Đơn");
        int total = 0;
        for (int i = 0; i < row; i++) {
            int n = Integer.parseInt(String.valueOf(tbInvoiceList.getValueAt(i, 5)).replaceAll(",", ""));
            total = total + n;
        }
        DecimalFormat df = new DecimalFormat("#,###,###");
        lbTotal.setText(df.format(total) + " VNĐ");

    }//GEN-LAST:event_btnSearchInvoiceActionPerformed

    private void dtFromDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtFromDatePropertyChange
        if (dtFromDate.getDate().compareTo(dtToDate.getDate()) > 0) {
            dtToDate.setDate(dtFromDate.getDate());
        }
    }//GEN-LAST:event_dtFromDatePropertyChange

    private void dtToDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtToDatePropertyChange
        if (dtFromDate.getDate().compareTo(dtToDate.getDate()) > 0) {
            dtFromDate.setDate(dtToDate.getDate());
        }
    }//GEN-LAST:event_dtToDatePropertyChange

    private void btnExportToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportToExcelActionPerformed
        ExportExcel ex = new ExportExcel();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String from = formatter.format(dtFromDate.getDate());
        String to = formatter.format(dtToDate.getDate());
        String fileName = "THONG_KE_HOA_DON_TU_NGAY_" + from + "_DEN_" + to;
        String sheetName = "THỐNG KÊ HÓA ĐƠN";
        String header = "THỐNG KÊ HÓA ĐƠN TỪ NGÀY " + from + " ĐẾN " + to;
        int col = tbInvoiceList.getColumnCount() - 1;
        int row = tbInvoiceList.getRowCount();
        ex.exportExcel(fileName, header, sheetName, col, row, tbInvoiceList.getModel());
    }//GEN-LAST:event_btnExportToExcelActionPerformed

    private void tbInvoiceListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbInvoiceListMouseReleased
        int index = tbInvoiceList.getSelectedRow();
        int id = Integer.parseInt(String.valueOf(tbInvoiceList.getValueAt(index, 7)));
        loadInvoiceDetail(id);
    }//GEN-LAST:event_tbInvoiceListMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportToExcel;
    private javax.swing.JButton btnSearchInvoice;
    private javax.swing.JComboBox cobCustomer;
    private javax.swing.JComboBox cobStaff;
    private com.toedter.calendar.JDateChooser dtFromDate;
    private com.toedter.calendar.JDateChooser dtToDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbNumberInvoice;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JTable tbInvoiceDetail;
    private javax.swing.JTable tbInvoiceList;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.ExportExcel;
import vn.edu.vttu.data.Invoice;
import vn.edu.vttu.data.NumberCellRenderer;
import vn.edu.vttu.data.PaymentInvoice;

/**
 *
 * @author nhphuoc
 */
public class PanelStatiticsPaymentRawmaterialInvoice extends javax.swing.JPanel {

    public class ColumnSorter implements Comparator {

        int colIndex;
        boolean ascending;

        ColumnSorter(int colIndex, boolean ascending) {
            this.colIndex = colIndex;
            this.ascending = ascending;
        }

        public int compare(Object a, Object b) {
            Vector v1 = (Vector) a;
            Vector v2 = (Vector) b;
            Object o1 = v1.get(colIndex);
            Object o2 = v2.get(colIndex);

            // Treat empty strains like nulls
            if (o1 instanceof String && ((String) o1).length() == 0) {
                o1 = null;
            }
            if (o2 instanceof String && ((String) o2).length() == 0) {
                o2 = null;
            }

            // Sort nulls so they appear last, regardless
            // of sort order
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return 1;
            } else if (o2 == null) {
                return -1;
            } else if (o1 instanceof Comparable) {
                if (ascending) {
                    return ((Comparable) o1).compareTo(o2);
                } else {
                    return ((Comparable) o2).compareTo(o1);
                }
            } else {
                if (ascending) {
                    return o1.toString().compareTo(o2.toString());
                } else {
                    return o2.toString().compareTo(o1.toString());
                }
            }
        }
    }

    /**
     * Creates new form PanelStatiticsRevenue
     */
    public PanelStatiticsPaymentRawmaterialInvoice() {
        initComponents();
    }

    private void statiticsDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetimeStart = formatter.format(dtFormDate.getDate());
        Timestamp tsStart = Timestamp.valueOf(datetimeStart);
        String datetimeEnd = formatter.format(dtToDate.getDate());
        Timestamp tsToDate = Timestamp.valueOf(datetimeEnd);
        tbResult.setModel(PaymentInvoice.getPaymentInvoiceRawmaterialInvoiceByDay(tsStart, tsToDate, ConnectDB.conn()));
    }

    private void statiticsMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetimeStart = formatter.format(dtFormDate.getDate());
        Timestamp tsStart = Timestamp.valueOf(datetimeStart);
        String datetimeEnd = formatter.format(dtToDate.getDate());
        Timestamp tsToDate = Timestamp.valueOf(datetimeEnd);
        tbResult.setModel(PaymentInvoice.getPaymentInvoiceRawmaterialInvoiceByMonth(tsStart, tsToDate, ConnectDB.conn()));
    }

    private void statiticsYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetimeStart = formatter.format(dtFormDate.getDate());
        Timestamp tsStart = Timestamp.valueOf(datetimeStart);
        String datetimeEnd = formatter.format(dtToDate.getDate());
        Timestamp tsToDate = Timestamp.valueOf(datetimeEnd);
        tbResult.setModel(PaymentInvoice.getPaymentInvoiceRawmaterialInvoiceByYear(tsStart, tsToDate, ConnectDB.conn()));
    }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending) {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }

    private void showChart(String title, String begin, String end) {
        /*
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int row = tbResult.getRowCount();
        for (int i = 0; i < row; i++) {
            float value = 0;
            try {
                try {
                    value = Float.parseFloat(String.valueOf(tbResult.getValueAt(i, 3)).trim().replaceAll("\\.", "")) / 1000;
                } catch (Exception e) {
                    value = Float.parseFloat(String.valueOf(tbResult.getValueAt(i, 3)).trim().replaceAll(",", "")) / 1000;
                }
            } catch (Exception e) {
                value = 0;
            }
            String date = String.valueOf(tbResult.getValueAt(i, 0)).trim();

            dataset.addValue(value, "TỔNG CHI", date);
        }

        JFreeChart chart = ChartFactory.createBarChart("THỐNG KÊ CHI\nTỪ " + title + " " + begin + " ĐẾN " + title + " " + end, title, "Số Tiền(Đơn vị: nghìn đồng)", dataset);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.black);
        ChartPanel CP = new ChartPanel(chart);
        pnChart.removeAll();
        pnChart.add(CP);
        pnChart.updateUI();
        pnChart.repaint();
        //ChartFrame frame = new ChartFrame("Thống kê doanh thu", chart);
        //frame.setSize(450, 350);
        //frame.setVisible(true);
        */
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
        cobStatiticsCondition = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        dtFormDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dtToDate = new com.toedter.calendar.JDateChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btnStatitics = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnExport = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lbTongChi = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbResult = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Thống Kê Theo: ");

        cobStatiticsCondition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ngày", "Tháng", "Năm" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText("Từ Ngày:");

        dtFormDate.setDateFormatString("dd/MM/yyyy");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 0));
        jLabel3.setText("Đến Ngày:");

        dtToDate.setDateFormatString("dd/MM/yyyy");

        jToolBar1.setBackground(new java.awt.Color(102, 153, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnStatitics.setBackground(new java.awt.Color(102, 153, 255));
        btnStatitics.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnStatitics.setForeground(new java.awt.Color(255, 255, 255));
        btnStatitics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/statistics.png"))); // NOI18N
        btnStatitics.setText("Thống Kê");
        btnStatitics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatiticsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnStatitics);

        jToolBar2.setBackground(new java.awt.Color(31, 114, 70));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnExport.setBackground(new java.awt.Color(31, 114, 70));
        btnExport.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Excel-icon.png"))); // NOI18N
        btnExport.setText("Xuất Ra Excel");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        jToolBar2.add(btnExport);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 153, 0));
        jLabel8.setText("Tổng Chi:");

        lbTongChi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTongChi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTongChi.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cobStatiticsCondition, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtFormDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 33, Short.MAX_VALUE)
                            .addComponent(dtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(29, 29, 29)
                        .addComponent(lbTongChi, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cobStatiticsCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(dtFormDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(dtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbTongChi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thời Gian", "HĐ Nhập/Phiếu Chi", "Số Lượng", "Tổng Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbResult.setGridColor(new java.awt.Color(204, 204, 204));
        tbResult.setRowHeight(25);
        tbResult.setSelectionBackground(new java.awt.Color(255, 153, 0));
        tbResult.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnStatiticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatiticsActionPerformed
        int index = cobStatiticsCondition.getSelectedIndex();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
        switch (index) {
            case 0:
                statiticsDay();
                sortAllRowsBy((DefaultTableModel) tbResult.getModel(), 0, true);
                //showChart("Ngày", format.format(dtFormDate.getDate()), format.format(dtToDate.getDate()));// thống kê theo ngày
                break;
            case 1:
                statiticsMonth();
                sortAllRowsBy((DefaultTableModel) tbResult.getModel(), 0, true);
                //showChart("Tháng", format1.format(dtFormDate.getDate()), format1.format(dtToDate.getDate()));
                break;
            case 2:
                statiticsYear();
                sortAllRowsBy((DefaultTableModel) tbResult.getModel(), 0, true);
                //showChart("Năm", format2.format(dtFormDate.getDate()), format2.format(dtToDate.getDate()));
                break;
            default:
                statiticsDay();
                sortAllRowsBy((DefaultTableModel) tbResult.getModel(), 0, true);
                //showChart("Ngày", format.format(dtFormDate.getDate()), format.format(dtToDate.getDate()));// thống kê theo ngày
                break;
        }
        tbResult.getColumnModel().getColumn(2).setCellRenderer(new NumberCellRenderer());
        tbResult.getColumnModel().getColumn(3).setCellRenderer(new NumberCellRenderer());
        float tong = 0;
        for(int i=0;i<tbResult.getRowCount();i++){
            float x;
            try {
                x=Float.parseFloat(tbResult.getValueAt(i, 3).toString());
            } catch (Exception e) {
                x=Float.parseFloat(tbResult.getValueAt(i, 3).toString().replaceAll(",", ""));
            }
            tong=tong+x;
        }
        DecimalFormat df = new DecimalFormat("#,###,###");
        lbTongChi.setText(df.format(tong));
        tbResult.getTableHeader().setReorderingAllowed(false);
    }//GEN-LAST:event_btnStatiticsActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        ExportExcel ex = new ExportExcel();
        int index = cobStatiticsCondition.getSelectedIndex();
        String parten="dd-MM-yyyy";
        switch(index){
            case 0:
                parten="dd-MM-yyyy";
                break;
            case 1: 
                parten="MM-yyyy";
                break;
            case 2:
                parten="yyyy";
                break;
            default:
                parten="dd-MM-yyyy";
                break;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(parten);
        String from = formatter.format(dtFormDate.getDate());
        String to = formatter.format(dtToDate.getDate());
        String fileName = "THONG_KE_CHI_PHI_TU_NGAY_" + from + "_DEN_" + to;
        String sheetName = "THỐNG KÊ CHI PHÍ";
        String header = "THỐNG KÊ CHI PHÍ TỪ " + from + " ĐẾN " + to;
        int col = tbResult.getColumnCount();
        int row = tbResult.getRowCount();        
        ex.exportExcel(fileName, header, sheetName, col, row, tbResult.getModel());
    }//GEN-LAST:event_btnExportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnStatitics;
    private javax.swing.JComboBox cobStatiticsCondition;
    private com.toedter.calendar.JDateChooser dtFormDate;
    private com.toedter.calendar.JDateChooser dtToDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbTongChi;
    private javax.swing.JTable tbResult;
    // End of variables declaration//GEN-END:variables
}

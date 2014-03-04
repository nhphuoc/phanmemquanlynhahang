/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import vn.edu.vttu.data.ConnectDB;
import vn.edu.vttu.data.Customer;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableLocation;
import vn.edu.vttu.data.TableReservation;
import vn.edu.vttu.data.TableReservationDetail;
import vn.edu.vttu.data.VariableStatic;

/**
 *
 * @author nhphuoc
 */
public class PanelReservationMultiTable extends javax.swing.JPanel {

    class ItemRendererTable extends BasicComboBoxRenderer {

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
     * Creates new form PanelReservationMultiTable
     */
    private Connection conn;
    private int idLocation = -1;
    private int idTable;
    private String listTable = "";

    public PanelReservationMultiTable() {
        initComponents();
        loadTableLocation();
        fillComboCustomer();
    }

    private void loadTableLocation() {
        conn = ConnectDB.conn();
        layout_location.removeAll();
        TableLocation tbLocation[] = TableLocation.getAll(conn);
        JPanel jp = new JPanel(new GridLayout(0, 1, 5, 5));
        jp.setMaximumSize(new Dimension(300, 150));
        layout_location.add(jp);
        JScrollPane jcroll = new JScrollPane(jp);
        layout_location.add(jcroll, BorderLayout.CENTER);
        JButton btAll = new JButton("Tất Cả");
        btAll.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                idLocation = -1;
                loadTable(idLocation);
            }
        });
        jp.add(btAll);
        JButton bt[] = new JButton[tbLocation.length];
        for (int i = 0; i < tbLocation.length; i++) {
            bt[i] = new JButton(tbLocation[i].getNAME());
            bt[i].setPreferredSize(new Dimension(50, 30));
            final int x = i;
            final int id = tbLocation[i].getID();
            bt[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    idLocation = id;
                    loadTable(idLocation);
                }
            });
            jp.add(bt[i]);
        }
        layout_location.updateUI();
        layout_location.repaint();
        conn = null;
    }

    private void loadTable(int id) {
        conn = ConnectDB.conn();
        layout_table.removeAll();

        Table table[];
        if (id == -1) {
            table = Table.getAll(conn);
        } else {
            table = Table.getByLocation(id, conn);
        }
        int numberTable = table.length;
        JPanel jp = new JPanel(new GridLayout(0, 6, 5, 5));
        //jp.setMaximumSize(new Dimension(400, 150));
        layout_table.add(jp);
        JScrollPane jcroll = new JScrollPane(jp);
        layout_table.add(jcroll, BorderLayout.CENTER);
        final JButton bt[] = new JButton[numberTable];
        for (int i = 0; i < numberTable; i++) {
            final int status = table[i].getSTATUS();
            final int x = i;
            final int idtable = table[i].getID();
            bt[i] = new JButton(table[i].getNAME());
            bt[i].setPreferredSize(new Dimension(30, 30));
            bt[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    idTable = idtable;
                    if (listTable == null) {
                        listTable = "";
                    }
                    listTable = listTable + idTable + "-";
                    loadTableReservation();
                    System.out.println(listTable);

                }
            });
            jp.add(bt[i]);
        }
        layout_table.updateUI();
        layout_table.repaint();
        conn = null;
    }

    private void loadTableReservation() {
        conn = ConnectDB.conn();
        layout_table_select.removeAll();
        JPanel jp = new JPanel(new GridLayout(0, 2, 5, 5));
        //jp.setMaximumSize(new Dimension(400, 150));
        layout_table_select.add(jp);
        JScrollPane jcroll = new JScrollPane(jp);
        layout_table_select.add(jcroll, BorderLayout.CENTER);
        String[] table = listTable.split("-");
        JButton bt[] = new JButton[table.length];
        for (int i = 0; i < table.length; i++) {
            bt[i] = new JButton(Table.getByID(Integer.parseInt(table[i]), conn).getNAME());
            bt[i].setPreferredSize(new Dimension(50, 30));
            bt[i].setFont(new Font("Tahoma", Font.BOLD, 13));
            bt[i].setOpaque(true);
            jp.add(bt[i]);
        }
        layout_table_select.updateUI();
        layout_table_select.repaint();
        conn = null;
    }

    private void fillComboCustomer() {
        conn = ConnectDB.conn();
        Vector<vn.edu.vttu.model.Table> model = new Vector<vn.edu.vttu.model.Table>();
        try {
            model = Customer.selectCustomer(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultComboBoxModel defaultComboBoxModel = new javax.swing.DefaultComboBoxModel(model);
        cobCustomer.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        cobCustomer.setModel(defaultComboBoxModel);
        cobCustomer.setRenderer(new PanelReservationMultiTable.ItemRendererTable());
        conn = null;
    }

    private boolean testDate(Timestamp date) {
        boolean flag = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(new Date());
        Timestamp ts = Timestamp.valueOf(datetime);
        if (date.compareTo(ts) < 0) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean reservationTable(Timestamp ts, int idCustomer) {
        boolean flag = false;
        if (testDate(ts) == false) {
            JOptionPane.showMessageDialog(getRootPane(), "Thời gian đặt phải lớn hơn thời gian hiện tại");
        } else {
            try {
                conn = ConnectDB.conn();
                conn.setAutoCommit(false);
                String arr[] = listTable.split("-");
                if (TableReservation.insert(false, idCustomer, ts, conn)) {
                    int maxid_reservation = TableReservation.getMaxID(conn).getID();
                    for (int i = 0; i < arr.length; i++) {
                        int idtb = Integer.parseInt(arr[i]);
                        if (TableReservationDetail.insert(idtb, maxid_reservation, conn)) {
                            int stt;
                            if (TableReservation.countidTableUsing(idtb, conn) > 0) {
                                stt = 1;
                            } else {
                                if (TableReservation.countidTableReservation(idtb, conn) > 0) {
                                    stt = 2;
                                } else {
                                    stt = 0;
                                }
                            }
                            Table.updateStatus(idtb, stt, conn);
                        }
                    }//end for
                    conn.commit();
                    flag = true;

                }
            } catch (SQLException ex) {
                try {
                    conn.rollback();
                    flag = false;
                    // conn.close();

                } catch (SQLException ex1) {
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

        layout_table = new javax.swing.JPanel();
        layout_location = new javax.swing.JPanel();
        layout_table_select = new javax.swing.JPanel();
        dtDateReservation = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cobCustomer = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        layout_table.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Bàn"));
        layout_table.setAutoscrolls(true);
        layout_table.setLayout(new java.awt.GridLayout(1, 0));

        layout_location.setBorder(javax.swing.BorderFactory.createTitledBorder("Vị Trí"));
        layout_location.setAutoscrolls(true);
        layout_location.setLayout(new javax.swing.BoxLayout(layout_location, javax.swing.BoxLayout.LINE_AXIS));

        layout_table_select.setBorder(javax.swing.BorderFactory.createTitledBorder("Bàn Được Chọn"));
        layout_table_select.setLayout(new javax.swing.BoxLayout(layout_table_select, javax.swing.BoxLayout.LINE_AXIS));

        dtDateReservation.setDateFormatString("dd/MM/yyyy HH:mm");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 0));
        jLabel1.setText("Ngày Nhận");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 153, 0));
        jLabel2.setText("Khách Hàng");

        cobCustomer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("+");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vn/edu/vttu/image/Save-icon_24x24.png"))); // NOI18N
        btnSave.setText("Lưu Lại");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layout_location, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layout_table, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(layout_table_select, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dtDateReservation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(cobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addGap(51, 51, 51))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layout_location, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtDateReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(layout_table_select, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addGap(5, 5, 5))
                    .addComponent(layout_table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (listTable != null) {
            if (JOptionPane.showConfirmDialog(getRootPane(), "Bạn có chắc chắn cho đặt trước những bàn trong danh sách không?", "Hỏi?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                vn.edu.vttu.model.Customer tb = (vn.edu.vttu.model.Customer) cobCustomer.getSelectedItem();
                int idCustomer = tb.getId();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datetime = formatter.format(dtDateReservation.getDate());
                Timestamp ts = Timestamp.valueOf(datetime);
                if (reservationTable(ts, idCustomer)) {
                    JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn thành công");
                    listTable = null;
                    System.out.println(listTable);
                    layout_table_select.removeAll();
                    layout_table_select.updateUI();
                    layout_table_select.repaint();
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Đặt bàn không thành công");
                }
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Xin lỗi, Bạn chưa chọn bàn để đặt");
        }
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cobCustomer;
    private com.toedter.calendar.JDateChooser dtDateReservation;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel layout_location;
    private javax.swing.JPanel layout_table;
    private javax.swing.JPanel layout_table_select;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import vn.edu.vttu.data.ConnectDB;
import static vn.edu.vttu.data.ConnectDB.conn;
import vn.edu.vttu.data.Table;
import vn.edu.vttu.data.TableLocation;
import vn.edu.vttu.data.VariableStatic;

/**
 *
 * @author nhphuoc
 */
public class tableMap extends javax.swing.JFrame {

    /**
     * Creates new form tableMap
     */
    private Connection conn;

    public tableMap() {
        initComponents();
        loadTable();
    }

    private void loadTable() {
        conn = ConnectDB.conn();
        layout_table.removeAll();
        JScrollPane jscroll = new JScrollPane();
        layout_table.add(jscroll,BorderLayout.CENTER);
        layout_table.setAutoscrolls(true);
       // JPanel jp = new JPanel(new GridLayout(0,1));
       // layout_table.add(jp);
        

        try {
            Table table[] = Table.getAll(conn);
            TableLocation tbLocation[] = TableLocation.getAll(conn);
            JPanel jp1[] = new JPanel[tbLocation.length];
            int numberTable = table.length;
            final JLabel bt[] = new JLabel[numberTable];
            for (int j = 0; j < tbLocation.length; j++) {
                JPanel jpLocationName = new JPanel(new GridLayout(0, 1));
                jpLocationName.setMaximumSize(new Dimension(10, 5));
                JLabel lb = new JLabel(tbLocation[j].getNAME());
                jpLocationName.setBackground(Color.red);
                lb.setForeground(Color.white);
                lb.setFont(new Font("Serif", Font.BOLD, 13));
                lb.setMaximumSize(new Dimension(10, 10));
                lb.setBackground(Color.orange);
                lb.setOpaque(true);
                jpLocationName.add(lb);
                layout_table.add(jpLocationName);
                JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
                layout_table.add(panel);
                for (int i = 0; i < Table.getByLocation((j + 1), conn).length; i++) {
                    bt[i] = new JLabel(Table.getByLocation((j + 1), conn)[i].getNAME());
                    //bt[i].setFont(new Font("Serif", Font.BOLD, 18));
                    if (table[i].getSTATUS() == 1) {// đang có khách 
                        BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon_thue.png"));
                        Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(image);
                        bt[i].setIcon(icon);
                        bt[i].setPreferredSize(new Dimension(50, 50));
                    } else if (table[i].getSTATUS() == 2) {// đặt bàn trước
                        BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon_datban.png"));
                        Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(image);
                        bt[i].setIcon(icon);
                        bt[i].setPreferredSize(new Dimension(50, 50));
                    } else {
                        BufferedImage bImg = ImageIO.read(getClass().getResourceAsStream("/vn/edu/vttu/image/table_icon.png"));
                        Image image = bImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(image);
                        bt[i].setIcon(icon);
                        bt[i].setPreferredSize(new Dimension(50, 50));
                        //bt[i].setBackground(Color.WHITE);
                        bt[i].setOpaque(true);
                    }

                    //bt[i].setBackground(Color.GREEN);
                    bt[i].setOpaque(true);
                    bt[i].setHorizontalAlignment(SwingConstants.CENTER);
                    bt[i].setToolTipText("<html>Thông tin bàn ăn<br>"
                            + "Thông tin bàn ăn 2</html>");
                    // bt[i].add(popup);
                    final int idtb = table[i].getID();
                    final int status = table[i].getSTATUS();

                    final int x = i;
                    bt[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {

                        }
                    });
                    panel.add(bt[i]);
                }
            }
           // JScrollPane scrollpane = new JScrollPane(jp);
       // layout_table.add(scrollpane, BorderLayout.CENTER);
            layout_table.updateUI();
            layout_table.repaint();
            conn = null;
        } catch (Exception e) {
            conn = null;
            e.printStackTrace();
        }
    }

    private void loadTableMap() {
        conn = ConnectDB.conn();
        layout_table.removeAll();
        TableLocation tbLocation[] = TableLocation.getAll(conn);
        JPanel jp1[] = new JPanel[tbLocation.length];
        JButton bt[] = new JButton[15];
        for (int i = 0; i < tbLocation.length; i++) {
            JPanel jp = new JPanel(new GridLayout(0, 1));
            jp.setMaximumSize(new Dimension(400, 10));
            JLabel lb = new JLabel(tbLocation[i].getNAME());
            jp.setBackground(Color.red);
            lb.setForeground(Color.white);
            lb.setFont(new Font("Serif", Font.BOLD, 13));
            lb.setMaximumSize(new Dimension(10, 10));
            lb.setBackground(Color.orange);
            lb.setOpaque(true);
            jp.add(lb);
            layout_table.add(jp);
            jp1[i] = new JPanel(new GridLayout(0, 5, 5, 5));
            layout_table.add(jp1[i]);
            for (int j = 0; j < 15; j++) {
                bt[j] = new JButton("sjf");
                jp1[i].add(bt[j]);
            }
        }

        layout_table.updateUI();
        layout_table.repaint();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        layout_table.setBorder(javax.swing.BorderFactory.createTitledBorder("dsdsfsed"));
        layout_table.setAutoscrolls(true);
        layout_table.setLayout(new javax.swing.BoxLayout(layout_table, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layout_table, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layout_table, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tableMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tableMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tableMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tableMap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tableMap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel layout_table;
    // End of variables declaration//GEN-END:variables
}

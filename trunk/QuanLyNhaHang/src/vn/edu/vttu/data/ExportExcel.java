/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import vn.edu.vttu.ui.PanelSaleInvoice;

/**
 *
 * @author nhphuoc
 */
public class ExportExcel {
    public void exportExcel(String fileName,String header,String sheetName, int col,int row,TableModel tb){
        try {            
            JFileChooser chooser = new JFileChooser();
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Chọn thư mục");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            String input = JOptionPane.showInputDialog(null,"Nhập tên file cần lưu",fileName);
            if (input != null && !input.trim().equals("")) {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String file = chooser.getSelectedFile() + "/" + input + ".xls";
                    File exlFile = new File(file);
                    WritableWorkbook writableWorkbook = Workbook
                            .createWorkbook(exlFile);
                    WritableSheet writableSheet = writableWorkbook.createSheet(
                            sheetName, 0);
                    WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 16);
                    cellFont.setBoldStyle(WritableFont.BOLD);
                    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
                    Label labelhEADER = new Label(0, 0, header, cellFormat);
                    writableSheet.addCell(labelhEADER);
                    for (int n = 0; n < col; n++) {
                        Label labelCol = new Label(n, 2, tb.getColumnName(n));
                        writableSheet.addCell(labelCol);
                    }
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            String x = "";
                            try {
                                x = tb.getValueAt(i, j).toString();
                            } catch (Exception e) {
                                x = "";
                            }
                            Label label = new Label(j, (i + 3), x);
                            writableSheet.addCell(label);
                        }
                    }                    
                    writableWorkbook.write();
                    writableWorkbook.close();
                    JOptionPane.showMessageDialog(null, "Đã lưu thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "File chưa được lưu", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "File không được lưu\nLưu ý: Tên file không được bỏ trống", "Thông Báo", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException ex) {
            Logger.getLogger(PanelSaleInvoice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
    
}

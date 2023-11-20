package com.phanlop.khoahoc.Controller;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.phanlop.khoahoc.Entity.CTHoaDon;
import com.phanlop.khoahoc.Entity.HoaDon;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
 
public class UserExcelExpoter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<HoaDon> listUsers;
    private HoaDon hoadon;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public UserExcelExpoter(List<HoaDon> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
    public UserExcelExpoter(HoaDon hd) {
        this.hoadon = hd;
        workbook = new XSSFWorkbook();
    }
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("HoaDons");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "HoaDon ID", style);      
        createCell(row, 1, "Tong tien", style);       
        createCell(row, 2, "Ten KH", style);
        createCell(row, 3, "Ngay mua", style);
        createCell(row, 4, "Số Course", style);
    }


    private void writeHeaderLine1() {
        sheet = workbook.createSheet("HoaDons");
         
        Row row = sheet.createRow(0);

        // int columnWidth = 120;
        //dat chieu rong cot 2
        // sheet.setColumnWidth(1, columnWidth);
        // sheet.setColumnWidth(0, columnWidth);
        // sheet.setColumnWidth(2, columnWidth);
        // sheet.setColumnWidth(3, columnWidth);
        // sheet.setColumnWidth(4, columnWidth);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "", style);      
        createCell(row, 1, "", style);       
        createCell(row, 2, "F9 - UNIVERSITY", style);
        createCell(row, 3, "", style);
        createCell(row, 4, "", style);

        Row row1 = sheet.createRow(1);
        createCell(row1, 0, "", style);      
        createCell(row1, 1, "", style);       
        createCell(row1, 2, "", style);
        createCell(row1, 3, "", style);
        createCell(row1, 4, "", style);

        Row row2 = sheet.createRow(2);
        createCell(row2, 0, "", style);      
        createCell(row2, 1, "", style);
        String title = "Hoa Don - " + hoadon.getHoaDonId();   
        createCell(row2, 2, title, style);
        createCell(row2, 3, "", style);
        createCell(row2, 4, "", style);

        Row row3 = sheet.createRow(3);
        createCell(row3, 0, "HoaDon ID", style);      
        createCell(row3, 1, "Tong tien", style);       
        createCell(row3, 2, "Ten KH", style);
        createCell(row3, 3, "Ngay mua", style);
        createCell(row3, 4, "Số Course", style);

        Row row4 = sheet.createRow(4);
            createCell(row4, 0, hoadon.getHoaDonId(), style);
            createCell(row4, 1, hoadon.getTongTien(), style);
            createCell(row4, 2, hoadon.getUser().getFullName(), style);

            Instant createdDate = hoadon.getNgayMua();
            String formattedDate = createdDate.atZone(ZoneId.systemDefault()).format(formatter);
            createCell(row4, 3, formattedDate, style);
            int soCourse = hoadon.getListCTHD().size();
            createCell(row4, 4, soCourse, style);

        
        Row row5 = sheet.createRow(5);
        createCell(row5, 0, "Chi tiết hóa đơn", style);      
        createCell(row5, 1, "", style);       
        createCell(row5, 2, "", style);
        createCell(row5, 3, "", style);
        createCell(row5, 4, "", style);

        Row row6 = sheet.createRow(6);
        createCell(row6, 0, "ID", style);      
        createCell(row6, 1, "Ten Khoa Hoc", style);       
        createCell(row6, 2, "Price", style);
        createCell(row6, 3, "", style);
        createCell(row6, 4, "", style);


    }
     
     private void writeDataLines1() {
        int rowCount = 7;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
            
            List<CTHoaDon> listCTHD = hoadon.getListCTHD();
            for (CTHoaDon cthd : listCTHD) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                
                createCell(row, columnCount++, cthd.getCTHDId(), style);
                createCell(row, columnCount++, cthd.getCourse().getCourseName(), style);
                String gia = cthd.getGia() + " VND";
                createCell(row, columnCount++, gia, style);
                createCell(row, 3, "", style);
                createCell(row, 4, "", style);
                
            }
    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
    
        if (value != null) {
            if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else {
                cell.setCellValue(value.toString());
            }
        } else {
            // Nếu giá trị là null, có thể xử lý một cách phù hợp tại đây, ví dụ: cell.setCellValue("");
        }
    
        cell.setCellStyle(style);
    }
    
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (HoaDon user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, user.getHoaDonId(), style);
            createCell(row, columnCount++, user.getTongTien(), style);
            createCell(row, columnCount++, user.getUser().getFullName(), style);
            // createCell(row, columnCount++, user.getCreatedDate(), style);

            Instant createdDate = user.getNgayMua();
            String formattedDate = createdDate.atZone(ZoneId.systemDefault()).format(formatter);
            createCell(row, columnCount++, formattedDate, style);
            int soCourse = user.getListCTHD().size();
            createCell(row, columnCount++, soCourse, style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }

    public void export1(HttpServletResponse response) throws IOException {
        writeHeaderLine1();
        writeDataLines1();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}

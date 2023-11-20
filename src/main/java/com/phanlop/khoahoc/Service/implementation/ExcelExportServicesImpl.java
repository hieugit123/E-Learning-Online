package com.phanlop.khoahoc.Service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.DTO.HoaDonDTO;
import com.phanlop.khoahoc.Service.ExcelExportServices;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class ExcelExportServicesImpl implements ExcelExportServices {

    @Override
    public void exportToExcel(List<HoaDonDTO> hoaDonList, String filePath) {
        // TODO Auto-generated method stub
        // Workbook workbook = new XSSFWorkbook();
        // Sheet sheet = workbook.createSheet("HoaDonData");

        // // Tạo dòng header
        // Row headerRow = sheet.createRow(0);
        // headerRow.createCell(0).setCellValue("Ngay Mua");
        // headerRow.createCell(1).setCellValue("Tong Tien");

        // // Đổ dữ liệu vào các dòng
        // int rowNum = 1;
        // for (HoaDonDTO hoaDonDTO : hoaDonList) {
        //     Row row = sheet.createRow(rowNum++);
        //     row.createCell(0).setCellValue(hoaDonDTO.getNgayMua().toString());
        //     row.createCell(1).setCellValue(hoaDonDTO.getTongTien());
        // }

        // // Lưu workbook vào file
        // try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
        //     workbook.write(fileOut);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // } finally {
        //     try {
        //         workbook.close();
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }
    }
    
}

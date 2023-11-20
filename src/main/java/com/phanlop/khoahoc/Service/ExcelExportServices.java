package com.phanlop.khoahoc.Service;

import java.util.List;

import com.phanlop.khoahoc.DTO.HoaDonDTO;

public interface ExcelExportServices {
    void exportToExcel(List<HoaDonDTO> hoaDonList, String filePath);
}

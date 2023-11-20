package com.phanlop.khoahoc.Service;

import java.time.Instant;
import java.util.List;

import com.phanlop.khoahoc.Entity.HoaDon;

public interface HoaDonServices {
    void saveHD(HoaDon hd);
    List<HoaDon> getAll();
    List<HoaDon> filterHoaDonByDate(Instant startDate, Instant endDate);
    HoaDon findHDById(Long id);
    List<HoaDon> layDanhSachHoaDonTheoNgay(Instant ngay);
}

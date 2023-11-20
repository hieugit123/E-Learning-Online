package com.phanlop.khoahoc.Service.implementation;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.HoaDon;
import com.phanlop.khoahoc.Repository.HoaDonRepository;
import com.phanlop.khoahoc.Service.HoaDonServices;

@Service
public class HoaDonServicesImpl implements HoaDonServices{
    @Autowired
    private HoaDonRepository hdRepository;
    @Override
    public void saveHD(HoaDon hd) {
        // TODO Auto-generated method stub
        hdRepository.save(hd);
    }

    @Override
    public List<HoaDon> getAll() {
        // TODO Auto-generated method stub
        return hdRepository.findAll();
    }

    @Override
    public List<HoaDon> filterHoaDonByDate(Instant startDate, Instant endDate) {
        // TODO Auto-generated method stub
        return hdRepository.findByNgayMuaBetween(startDate, endDate);
    }

    @Override
    public HoaDon findHDById(Long id) {
        // TODO Auto-generated method stub
        return hdRepository.findById(id).get();
    }
    
    @Override
    public List<HoaDon> layDanhSachHoaDonTheoNgay(Instant ngay) {
        // Triển khai logic lấy danh sách hóa đơn từ cơ sở dữ liệu dựa trên ngày
        // Ví dụ, sử dụng phương thức tương ứng từ repository
        return hdRepository.findByNgayMua(ngay);
    }
}

package com.phanlop.khoahoc.Repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phanlop.khoahoc.Entity.HoaDon;

public interface HoaDonRepository extends JpaRepository<HoaDon, Long>{
    List<HoaDon> findByNgayMuaBetween(Instant startDate, Instant endDate);
    List<HoaDon> findByNgayMua(Instant ngay);
}
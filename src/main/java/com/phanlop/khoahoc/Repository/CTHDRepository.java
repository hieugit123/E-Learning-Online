package com.phanlop.khoahoc.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phanlop.khoahoc.Entity.CTHoaDon;
import com.phanlop.khoahoc.Entity.Course;

public interface CTHDRepository extends JpaRepository<CTHoaDon, Long>{
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phanlop.khoahoc.Service;

/**
 *
 * @author mxuan
 */
import com.phanlop.khoahoc.Entity.DanhGia;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface DanhGiaServices {
    List<DanhGia> getAllDanhGia(Course course);
    Long getSoLuongDanhGia();
    int calculateAvarageRating(UUID course);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.DanhGia;
import com.phanlop.khoahoc.Repository.DanhGiaRepository;
import com.phanlop.khoahoc.Service.DanhGiaServices;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author mxuan
 */
@Service
public class DanhGiaServicesImpl implements DanhGiaServices{
    @Autowired
    private DanhGiaRepository danhgiaRepository;
    @Override
    public List<DanhGia> getAllDanhGia(Course course) {
        return null;
    }

    @Override
    public Long getSoLuongDanhGia() {
        Long soluong = danhgiaRepository.count();
        return soluong;
    }

    @Override
    public int calculateAvarageRating(UUID courseId) {
        List<DanhGia> reviews = danhgiaRepository.getDanhGiaByCourseCourseID(courseId);
        int totalStars = 0;

        for (DanhGia review : reviews) {
            totalStars += review.getSao();
        }

        if (reviews.size() > 0) {
            return (int) totalStars / reviews.size();
        } else {
            return 0; // Trường hợp không có đánh giá, trả về 0.0
        }
    }
  
}

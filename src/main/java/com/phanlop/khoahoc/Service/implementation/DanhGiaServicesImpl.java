/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.DanhGia;
import com.phanlop.khoahoc.Entity.User;
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
    public double calculateAvarageRating(UUID courseId) {
        List<DanhGia> reviews = danhgiaRepository.getDanhGiaByCourseCourseID(courseId);
        int totalStars = 0;

        for (DanhGia review : reviews) {
            totalStars += review.getSao();
        }

        if (!reviews.isEmpty()) {
            return (double) totalStars / reviews.size();
        } else {
            return 0; // Trường hợp không có đánh giá, trả về 0.0
        }
    }

    @Override
    public DanhGia findDGByUserAndCourse(Course course, User user) {
        // TODO Auto-generated method stub
        List<DanhGia> dg = danhgiaRepository.getDanhGiaByCourseCourseIdAndUserUserId(course, user);  
        if(dg.isEmpty())
            return null;     
        return dg.get(0);
    }

    @Override
    public void saveDanhGia(DanhGia dg) {
        // TODO Auto-generated method stub
        danhgiaRepository.save(dg);
    }
  
}

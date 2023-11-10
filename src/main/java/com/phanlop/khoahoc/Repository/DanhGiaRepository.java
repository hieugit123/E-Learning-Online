/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phanlop.khoahoc.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.DanhGia;
import com.phanlop.khoahoc.Entity.User;



/**
 *
 * @author mxuan
 */
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long>{
    List<DanhGia> getDanhGiaByCourseCourseID(UUID course);
    @Query("SELECT dg FROM DanhGia dg WHERE dg.course = :course AND dg.user = :user")
    List<DanhGia> getDanhGiaByCourseCourseIdAndUserUserId(Course course, User user);
}

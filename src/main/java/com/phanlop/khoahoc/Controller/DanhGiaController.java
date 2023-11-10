/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phanlop.khoahoc.Controller;

/**
 *
 * @author mxuan
 */
import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.*;
import com.phanlop.khoahoc.Entity.*;
import com.phanlop.khoahoc.Service.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DanhGiaController {
    private final DanhGiaServices danhgiaService;
    private final CourseServices courseService;
    private final UserServices userServices;

    //Để tạm đây
    @PostMapping("/checkout/saveSession")
    public ResponseEntity<String> checkout(@RequestBody List<String> selectedItems, HttpSession session){
        session.setAttribute("listSpPayment", selectedItems);
        return ResponseEntity.ok("success");
    }

    
    @GetMapping("/danhgia/{courseID}")
    public List<DanhGia> getAllDanhGia(@PathVariable UUID courseId){
        return null;
    }

    @PostMapping("/danhgia/add")
    public ResponseEntity<String> addDanhGia(@RequestParam(defaultValue = "") String soSaoInput, @RequestParam(defaultValue = "") String motaDG, @RequestParam(defaultValue = "") String courseId, Authentication authentication){
        System.out.println("da vao cu");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        int soSao = Integer.parseInt(soSaoInput);
        Course course = courseService.getCourseById(UUID.fromString(courseId));
        DanhGia dg = new DanhGia();
        dg.setContentDanhgia(motaDG);
        dg.setCourse(course);
        dg.setUser(user);
        dg.setSao(soSao);
        danhgiaService.saveDanhGia(dg);

        
        
        return ResponseEntity.ok("success");
    }
    
    
}

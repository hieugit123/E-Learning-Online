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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DanhGiaController {
    private final DanhGiaServices danhgiaService;
    private final CourseServices courseService;
    private final UserServices userServices;
    private final HoaDonServices hoaDonServices;


    //để tạm đây
    // @PostMapping("/xuatHD")
    // public ResponseEntity<byte[]> xuatHD(HttpServletResponse response, @RequestBody List<String> selectedItems) throws IOException {
    //     System.out.println("da vao xuathd");
    //     response.setContentType("application/octet-stream");
    //     DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    //     String currentDateTime = dateFormatter.format(new Date());
         
    //     String headerKey = "Content-Disposition";
    //     String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
    //     response.setHeader(headerKey, headerValue);
         
    //     List<HoaDon> listHD = new ArrayList<>();
    //     int lenght = selectedItems.size();
    //     for(int i=0; i<lenght; i++){
    //         Long longValue = Long.parseLong(selectedItems.get(i));
    //         HoaDon hd = hoaDonServices.findHDById(longValue);
    //         listHD.add(hd);
    //     }
         
    //     // Create Excel file and export data
    //     UserExcelExpoter excelExporter = new UserExcelExpoter(listHD);
    //     byte[] excelBytes = excelExporter.export();

    //     // Set response headers
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    //     headers.setContentDispositionFormData("attachment", "users_" + currentDateTime + ".xlsx");

    //     // Return the Excel file as a response entity
    //     return ResponseEntity.ok().headers(headers).body(excelBytes);
    // }


    @PostMapping("/saveDSHD")
    public ResponseEntity<String> luuDSHD(@RequestBody List<String> selectedItems, HttpSession session){
        session.setAttribute("listHD", selectedItems);
        return ResponseEntity.ok("success");
    }
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

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
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DanhGiaController {
    private final DanhGiaServices danhgiaService;
    private final CourseServices courseServices;
    private final UserServices userServices;
    private final HoaDonServices hdServices;
    private final CartServices cartServices;
    private final ChiTraServices chitraServices;

    @GetMapping("/admin/getCTChiTra/{idChiTra}")
    public List<LuotMuaDTO> getChiTietChiTra(@PathVariable Long idChiTra) {
        List<LuotMuaDTO> list = new ArrayList<>();
        //lay cac hoa don trong thang hien tai
        List<HoaDon> dshd = hdServices.getAll();
        
        ChiTraGV chitra = chitraServices.findByID(idChiTra);
        User user = chitra.getTeacher();
        List<Course> courses = courseServices.findCourseOfTeacher(user);

        ZoneId zoneId2 = ZoneId.of("Asia/Ho_Chi_Minh");
        int thang=chitra.getThang();
        for (int i=0;i<dshd.size();i++) {
            LocalDate localDate=LocalDate.ofInstant(dshd.get(i).getNgayMua(), zoneId2);
            int thanglocal=localDate.getMonthValue();
            if(thanglocal!=thang){
                dshd.remove(i);
                i--;
            }
        }
        int count = 0;
        int tong = 0;
        for(Course c : courses){
            List<UserDTO> listUser = new ArrayList<>();
            for(HoaDon hd : dshd){
                List<CTHoaDon> listCTHD = hd.getListCTHD();
                for(CTHoaDon cthd : listCTHD){
                    UUID id = cthd.getCourse().getCourseID();
                    if(id.compareTo(c.getCourseID()) == 0){
                        UserDTO user1 = ObjectMapperUtils.map(hd.getUser(), UserDTO.class);
                        listUser.add(user1);
                        count++;
                    }
                }
            }
            CourseDTO course = ObjectMapperUtils.map(c, CourseDTO.class);
            LuotMuaDTO luotmua = new LuotMuaDTO();
            tong += course.getGia()*listUser.size();
            luotmua.setCourse(course);
            luotmua.setListUser(listUser);
            list.add(luotmua);
        }
        System.out.println("count: "+count);
        System.out.println("Tong:" + tong);
        return list;
    }

    @GetMapping("/clearCart")
    public ResponseEntity<String> clearCart(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        cartServices.clearCart(user);
        return ResponseEntity.ok("success");
    }

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
        Course course = courseServices.getCourseById(UUID.fromString(courseId));
        DanhGia dg = new DanhGia();
        dg.setContentDanhgia(motaDG);
        dg.setCourse(course);
        dg.setUser(user);
        dg.setSao(soSao);
        danhgiaService.saveDanhGia(dg);
        return ResponseEntity.ok("success");
    }
    
    
}

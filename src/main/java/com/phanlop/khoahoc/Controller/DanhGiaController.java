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
import com.phanlop.khoahoc.Repository.AssignmentRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
//import com.phanlop.khoahoc.Repository.FileRepository;
import com.phanlop.khoahoc.Service.*;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.ui.Model;

@RestController
@RequiredArgsConstructor
@RequestMapping("/danhgia")
public class DanhGiaController {
    private final DanhGiaServices danhgiaService;
    private final CourseServices courseService;
    @GetMapping("/{courseID}")
    public List<DanhGia> getAllDanhGia(@PathVariable UUID courseId){
        return null;
    }
    
    
}

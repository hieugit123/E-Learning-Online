package com.phanlop.khoahoc.Controller;

//import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.*;
import com.phanlop.khoahoc.Entity.*;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Service.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
//import com.phanlop.khoahoc.Utils.ObjectMapperUtils;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.data.repository.query.Param;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.UUID;
//import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class GiangVienController {
    private final CourseServices courseService;
    private final UserServices userServices;
    private final DepartmentRepository departmentRepository;
    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @GetMapping("/createCourse")
    public String getForm(){
        return "createCourse";
    }
    
    @GetMapping("/courseDangDo")
    public String getFormCourseDangDo(Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        List<Course> courses = courseService.findCourseDangDo(user);
        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("khoaId", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("currentPage", 1 + 1);
        return "courseDangDo";
    }

    @GetMapping("/hoadon")
    public String getPageQLHD() {
            return "thanhtoan_teacher";
    }
}

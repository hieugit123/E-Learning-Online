package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.DTO.ThongkeCourseDTO;
import com.phanlop.khoahoc.Entity.*;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phanlop.khoahoc.Service.ChatlogServices;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.UserServices;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ThongkeController {
    private final UserServices userServices;
    private final CourseServices courseServices;
    private final EnrollmentServices enrollmentServices;
    @GetMapping("/thongke")
    public String thongkemain(@RequestParam("teacherid") long teacherid,Model model){
        User user=userServices.getUserById(teacherid);
        List<Course> courses=courseServices.findCourseOfTeacher(user);
        int sum=0;
        List<Enrollment> enrollments=enrollmentServices.getAll();
        for (Enrollment enrollment : enrollments) {
            for (Course course : courses) {
                if(enrollment.getCourse().getCourseID()==course.getCourseID()){
                    sum+=course.getGia();
                }
            }
        }
        List<Course> coursetop3=new ArrayList<>();
        for (Course course : courses) {
            int count=0;
            for (Enrollment enrollment : enrollments) {
                if(course.getCourseID()==enrollment.getCourse().getCourseID())
                    count++;
            }
            Course temp=course;
            temp.setGia(temp.getGia()*count);
            temp.setState(count);
            coursetop3.add(temp);
        }
        Collections.sort(coursetop3, Comparator.comparingDouble(Course::getGia).reversed());
        List<Course> top3toview=new ArrayList<>();
        top3toview.add(coursetop3.get(0));
        top3toview.add(coursetop3.get(1));
        top3toview.add(coursetop3.get(2));
        int flag=0;
        model.addAttribute("flag", flag);
        model.addAttribute("courses", top3toview);
        model.addAttribute("tongdoanhthu", sum);
        model.addAttribute("teacherid", teacherid);
        return "thongke";
    }
    @GetMapping("/thongkedate")
    public String thongkedate(@RequestParam("teacherid") long teacherid,
                        @RequestParam("startDate") Date startDate,
                        @RequestParam("endDate") Date endDate,Model model){
        User user=userServices.getUserById(teacherid);
        List<Course> courses=courseServices.findCourseOfTeacher(user);
        List<Enrollment> enrollments=enrollmentServices.getAll();
        String staString=String.valueOf(startDate);
        String endString=String.valueOf(endDate);
        LocalDate startLocal=LocalDate.parse(staString, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocal=LocalDate.parse(endString,DateTimeFormatter.ISO_LOCAL_DATE);


        List<Course> teacherCourseHaveBeenBuy=new ArrayList<>();
        ZoneId zoneId2 = ZoneId.of("Asia/Ho_Chi_Minh");  
        int sum=0;
        List<ThongkeCourseDTO> coursefix=new ArrayList<>();
        for (Course course : courses) {
            int count=0;
            LocalDate local=null;
            for (Enrollment enrollment : enrollments) {
                if(course.getCourseID()==enrollment.getCourse().getCourseID()){
                    local=LocalDate.ofInstant(enrollment.getDateJoined(),zoneId2);
                    if(local.isAfter(startLocal)&& local.isBefore(endLocal)){
                        count++;
                    }
                }
            }
            ThongkeCourseDTO coursedto=new ThongkeCourseDTO();
            coursedto.courseName=course.getCourseName();
            coursedto.gia=course.getGia();
            coursedto.courseid=course.getCourseID();
            coursedto.courseRevenue=course.getGia()*count;
            sum+=coursedto.courseRevenue;
            coursedto.courseBuyCount=count;
            coursedto.avatar=course.getCourseAvt();
            coursefix.add(coursedto);
        }
       
        int flag=1; 
        model.addAttribute("tongdoanhthu", sum);
        model.addAttribute("flag", flag);
        model.addAttribute("teacherid", teacherid);
        model.addAttribute("courses", coursefix);
        return "thongke";
    }
    @GetMapping("/thongke1")
    public String thongke1(@RequestParam("teacherid") long teacherid,Model model){
        User user=userServices.getUserById(teacherid);
        
        int flag=1;
        int tongdoanhthu=0;
        List<Course> courses=new ArrayList<>();
        model.addAttribute("tongdoanhthu", tongdoanhthu);
        model.addAttribute("courses", courses);
        model.addAttribute("flag", flag);
        model.addAttribute("teacherid", teacherid);
        return "thongke";
    }




    @GetMapping("/thongke2")
    public String thongke2(@RequestParam("teacherid") long teacherid,Model model){
        User user=userServices.getUserById(teacherid);
        List<Course> courses=courseServices.findCourseOfTeacher(user);
        List<Enrollment> enrollments=enrollmentServices.getAll();
        
        List<ThongkeCourseDTO> coursefix=new ArrayList<>();
        for (Course course : courses) {
            int count=0;
            for (Enrollment enrollment : enrollments) {
                if(course.getCourseID()==enrollment.getCourse().getCourseID()){
                    count++;
                }
            }
            ThongkeCourseDTO coursedto=new ThongkeCourseDTO();
            coursedto.courseName=course.getCourseName();
            coursedto.gia=course.getGia();
            coursedto.courseid=course.getCourseID();
            coursedto.courseRevenue=course.getGia()*count;
            coursedto.courseBuyCount=count;
            coursedto.avatar=course.getCourseAvt();
            coursefix.add(coursedto);
        }
       
        int flag=2;
        model.addAttribute("flag", flag);
        model.addAttribute("teacherid", teacherid);
        model.addAttribute("courses", coursefix);
        return "thongke";
    }
}
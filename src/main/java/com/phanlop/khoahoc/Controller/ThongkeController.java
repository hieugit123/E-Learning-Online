package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Entity.*;

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
            coursetop3.add(temp);
        }
        Collections.sort(coursetop3, Comparator.comparingDouble(Course::getGia).reversed());
        List<Course> top3toview=new ArrayList<>();
        top3toview.add(coursetop3.get(0));
        top3toview.add(coursetop3.get(1));
        top3toview.add(coursetop3.get(2));
        model.addAttribute("top3", top3toview);
        model.addAttribute("tongdoanhthu", sum);
        model.addAttribute("teacherid", teacherid);
        return "thongke";
    }
}

package com.phanlop.khoahoc.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.DanhGiaServices;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private static String search="";
    private final CourseServices courseService;
    private final DanhGiaServices danhGiaServices;
    @GetMapping("/search")
    public String searchCourses(@RequestParam("searchText") String searchText, Model model) {
        if(searchText==""){
            List<Course> listCourse = courseService.getAllCourses();
            model.addAttribute("listCourse", listCourse);
            search=searchText;
            return "search";
        }
        List<Course> listCourse = courseService.filterBySearch1(searchText);
        search=searchText;
        model.addAttribute("searchText", searchText);
        model.addAttribute("listCourse", listCourse);
        return "search";
    }

    @PostMapping("search/SortBy")
    public String sortCourses(
            @RequestParam("priceOrder") String priceOrder,
            Model model
    ) {
        // Gọi phương thức trong service để lấy danh sách khóa học đã lọc
        List<Course> filteredCourses = courseService.sortCoursesByGia(priceOrder);

        // Đưa danh sách khóa học đã lọc vào model để hiển thị lên trang index
        model.addAttribute("listCourse", filteredCourses);

        return "search";
    }

    @PostMapping("search/filter")
    public String FilterCourse(
        @ModelAttribute("searchText") String searchText,
        @RequestParam("priceRange") Double range,
        @RequestParam("priceOrder") String courseOrder,
        @RequestParam("rating") int rating,
        Model model){
            List<Course> filteredCourses=new ArrayList<>();
            
            if(searchText==""){
                filteredCourses=courseService.getAllCourses();
            }
            else{
                filteredCourses = courseService.filterBySearch1(searchText);
            }
            List<Course> sortedCourses=new ArrayList<>();
            if(range==1 && rating==0){
                if (courseOrder.equals("asc")) {
                    Collections.sort(filteredCourses, Comparator.comparingDouble(Course::getGia));
                } else if (courseOrder.equals("desc")) {
                    Collections.sort(filteredCourses, Comparator.comparingDouble(Course::getGia).reversed());
                }
                model.addAttribute("searchText", searchText);
                model.addAttribute("listCourse", filteredCourses);
                return "search";
            }
            else if(range!=1 && rating!=0){
            if(range==0){
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice ==0 && danhGiaServices.calculateAvarageRating(course.getCourseID())>=rating) {
                        sortedCourses.add(course);
                    }
                }
            }
            else if(range==500000){
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice <500000 && coursePrice>0 && danhGiaServices.calculateAvarageRating(course.getCourseID())>=rating) {
                        sortedCourses.add(course);
                    }
                }
            }
            else if(range==51000){
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice >=500000 && coursePrice <1000000 && danhGiaServices.calculateAvarageRating(course.getCourseID())>=rating) {
                        sortedCourses.add(course);
                    }
                }
            }
            else{
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice >1000000 && danhGiaServices.calculateAvarageRating(course.getCourseID())>=rating) {
                        sortedCourses.add(course);
                    }
                }
            }
        }
        else if(range!=1 && rating==0  ){
            if(range==0){
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice ==0) {
                        sortedCourses.add(course);
                    }
                }
            }
            else if(range==500000){
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice <500000) {
                        sortedCourses.add(course);
                    }
                }
            }
            else if(range==51000){
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice >=500000) {
                        sortedCourses.add(course);
                    }
                }
            }
            else{
                for (Course course : filteredCourses) {
                    double coursePrice = course.getGia();
                    if (coursePrice >1000000 ) {
                        sortedCourses.add(course);
                    }
                }
            }
        }
        else if(range==1 && rating!=0  ){
            for (Course course : filteredCourses) {
                    if (danhGiaServices.calculateAvarageRating(course.getCourseID()) ==rating) {
                        sortedCourses.add(course);
                    }
                }
        }
         // Sắp xếp danh sách khóa học theo giá tăng dần (hoặc giảm dần) nếu cần
            
         if (courseOrder.equals("asc")) {
            Collections.sort(sortedCourses, Comparator.comparingDouble(Course::getGia));
        } else if (courseOrder.equals("desc")) {
            Collections.sort(sortedCourses, Comparator.comparingDouble(Course::getGia).reversed());
        }

        // Đưa danh sách khóa học đã sắp xếp vào model để hiển thị lên trang search
        model.addAttribute("searchText", searchText);
        model.addAttribute("listCourse", sortedCourses);
        return "search";
    }
}

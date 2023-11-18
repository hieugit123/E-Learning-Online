package com.phanlop.khoahoc.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phanlop.khoahoc.DTO.AdminThongkeDTO;
import com.phanlop.khoahoc.DTO.ThongkeCourseDTO;
import com.phanlop.khoahoc.DTO.UserCourseCountDTO;
import com.phanlop.khoahoc.DTO.UserDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.UserServices;

import lombok.RequiredArgsConstructor;
// import lombok.experimental.var;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServices userServices;
    private final CourseServices courseServices;
    private final EnrollmentServices enrollmentServices;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/course","/"})
    public String getCoursePage(){
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/chapter"})
    public String getChapterPage(){
        return "admin_chapter";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/discuss"})
    public String getDiscussionPage(){
        return "admin_discuss";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/document"})
    public String getDocumentPage(){
        return "admin_document";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping({"/assignment"})
    public String getAssignmentPage(){
        return "admin_assignment";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/courseChoDuyet")
    public String getCourseChoDuyet(Model model){       
        List<Course> courses = courseServices.findCourseChoDuyet();
        model.addAttribute("courses", courses);
        model.addAttribute("flag", 5);
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/duyetCourse/{courseId}")
    public String duyetCourse(@PathVariable UUID courseId, Model model){
        Course c = courseServices.getCourseById(courseId);
        c.setState(1);
        courseServices.saveCourse(c);
        List<Course> courses = courseServices.findCourseChoDuyet();
        model.addAttribute("courses", courses);
        model.addAttribute("flag", 5);
        return "admin";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/courseChoDuyetDetail/{id}")
    public String getChitiet(@PathVariable UUID id, Model model){
        Course course = courseServices.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("flag", 6);
        List<Lesson> lessons = new ArrayList<>(course.getListLessons().stream().toList());
        lessons.sort(Comparator.comparing(Lesson::getLessonSort));
        model.addAttribute("lessons", lessons);
        return "admin";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping({"/thongketongquat"})
    public String thongketong(Model model){
         
        List<Course> courses=courseServices.getAllCourses();
        Role role = new Role();
        role.setRoleId(2);
        role.setRoleName("ROLE_STUDENT");
        List<User> hv=userServices.findUserByRole(role);
        int hvsize=hv.size();
        Role gvrole=new Role();
        gvrole.setRoleId(3);
        gvrole.setRoleName("ROLE_TEACHER");
        List<User> gv=userServices.findUserByRole(gvrole);
        int gvsize=gv.size();
        int sum=0;
        List<Enrollment> enrollments=enrollmentServices.getAll();
        for (Enrollment enrollment : enrollments) {
            for (Course course : courses) {
                if(enrollment.getCourse().getCourseID()==course.getCourseID()){
                    sum+=course.getGia();
                }
            }
        }
        List<AdminThongkeDTO> coursetop10=new ArrayList<>();
        for (Course course : courses) {
            int count=0;
            for (Enrollment enrollment : enrollments) {
                if(course.getCourseID()==enrollment.getCourse().getCourseID())
                    count++;
            }
            if(count>0){
                AdminThongkeDTO temp=new AdminThongkeDTO();
                temp.setCourseName(course.getCourseName());
                temp.setBuyCount(count);
                temp.setCourseID(course.getCourseID());
                temp.setRevenue(course.getGia()*count);
                sum+=temp.getRevenue()*15/100;
                temp.setSystemRevenue(temp.getRevenue()*15/100);
                coursetop10.add(temp);
            // Course temp=course;
            // temp.setGia(temp.getGia()*count);
            // sum+=((temp.getGia()*15)/100);
            // temp.setState(count);
            // coursetop10.add(temp);
            } 
        }
        Collections.sort(coursetop10, Comparator.comparingDouble(AdminThongkeDTO::getSystemRevenue).reversed());
        while(coursetop10.size()>10){
            coursetop10.remove(coursetop10.size()-1);
        }
        model.addAttribute("hocviennumber", hvsize);
        model.addAttribute("giangviennumber", gvsize);
        model.addAttribute("coursetop10", coursetop10);
        model.addAttribute("flag",1);
        model.addAttribute("sum", sum);
        return "admin";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping({"/thongketop5"})
    public String thongketop5(@RequestParam(value = "startDate", required = false) Date startDate,
                        @RequestParam(value = "endDate", required = false) Date endDate,Model model){
        
        List<Enrollment> enrollments=enrollmentServices.getAll();
        List<User> users=userServices.getAllUsers();
        List<UserCourseCountDTO> userstatistic=new ArrayList<>();
        for (User user : users) {
            int count=0;
            int sum=0;
            for (Enrollment enrollment : enrollments) {
                if(user.getUserId()==enrollment.getUser().getUserId()){
                    count++;
                    sum+=enrollment.getCourse().getGia();
                }
            }
            UserCourseCountDTO temp=new UserCourseCountDTO();
            temp.fullName=user.getFullName();
            temp.haveBuy=count;
            temp.spend=sum;
            userstatistic.add(temp);
        }
        Collections.sort(userstatistic, Comparator.comparingDouble(UserCourseCountDTO::getSpend).reversed());
        for(int i=userstatistic.size()-1;i>=5;i--){
            userstatistic.remove(i);
        }
        model.addAttribute("user", userstatistic);
        model.addAttribute("flag", 2);
        
        return "admin";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping({"/thongkengay"})
    public String thongkengay(@RequestParam(value="startDate",required=false) Date startDate,
                        @RequestParam(value ="endDate",required = false) Date endDate,Model model){
        
        String staString=String.valueOf(startDate);
        String endString=String.valueOf(endDate);
        if(staString=="null"||endString=="null"){
            staString="2023-11-09";
            endString="2023-11-11";
        }
        LocalDate startLocal=LocalDate.parse(staString, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocal=LocalDate.parse(endString,DateTimeFormatter.ISO_LOCAL_DATE);
        List<Course> courses=courseServices.getAllCourses();
        
        int sum=0;
        List<Enrollment> enrollments=enrollmentServices.getAll();
        ZoneId zoneId2 = ZoneId.of("Asia/Ho_Chi_Minh");  
        List<ThongkeCourseDTO> coursefix=new ArrayList<>();
        List<User>usr=userServices.getAllUsers();
        List<UserDTO>userDTO=new ArrayList<>();
        for(int i=0;i<usr.size();i++){
            LocalDate local=null;
            local=LocalDate.ofInstant(usr.get(i).getModifiedDate(),zoneId2);
            if(local.isAfter(startLocal)&& local.isBefore(endLocal)){
                LocalDate currentDate = LocalDate.now(); 
                long daysBetween = ChronoUnit.DAYS.between(local, currentDate);
                UserDTO usrDTO=new UserDTO();
                usrDTO.setAvatar(usr.get(i).getAvatar());
                usrDTO.setFullName(usr.get(i).getFullName());
                usrDTO.setEmail(usr.get(i).getEmail());
                usrDTO.setUserId(usr.get(i).getUserId());
                usrDTO.setOffLine(daysBetween);
                userDTO.add(usrDTO);
                
            }
            else{
                usr.remove(i);
            }
        }
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
            if(count!=0){
            ThongkeCourseDTO coursedto=new ThongkeCourseDTO();
            coursedto.courseName=course.getCourseName();
            coursedto.gia=course.getGia();
            coursedto.courseid=course.getCourseID();
            coursedto.courseRevenue=(course.getGia()*count)*15/100;
            sum+=coursedto.courseRevenue;
            coursedto.courseBuyCount=count;
            coursedto.avatar=course.getCourseAvt();
            coursefix.add(coursedto);
            }
        }
        Collections.sort(coursefix, Comparator.comparingDouble(ThongkeCourseDTO::getGia).reversed());
        model.addAttribute("tonguser", usr.size());
        model.addAttribute("flag", 3);
        model.addAttribute("tongdoanhthu", sum);
        model.addAttribute("courses", coursefix);
        return "admin";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping({"/qluser"})
    public String qluser(Model model){
        List<User>usr=userServices.getAllUsers();
        usr.remove(0);
        List<UserDTO>userDTO=new ArrayList<>();
        ZoneId zoneId2 = ZoneId.of("Asia/Ho_Chi_Minh");  
        for(int i=0;i<usr.size();i++){
            LocalDate local=null;
            local=LocalDate.ofInstant(usr.get(i).getModifiedDate(),zoneId2);
                LocalDate currentDate = LocalDate.now(); 
                long daysBetween = ChronoUnit.DAYS.between(local, currentDate);
                UserDTO usrDTO=new UserDTO();
                usrDTO.setAvatar(usr.get(i).getAvatar());
                usrDTO.setFullName(usr.get(i).getFullName());
                usrDTO.setEmail(usr.get(i).getEmail());
                usrDTO.setCreatedDate(usr.get(i).getCreatedDate());
                usrDTO.setModifiedDate(usr.get(i).getModifiedDate());
                usrDTO.setUserId(usr.get(i).getUserId());
                usrDTO.setOffLine(daysBetween);
                usrDTO.setMota(usr.get(i).getMota());
                userDTO.add(usrDTO);
             
        }
        model.addAttribute("users", userDTO);
        model.addAttribute("flag", 4);
        
        return "admin";
    }
}

package com.phanlop.khoahoc.Controller;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import com.phanlop.khoahoc.Service.LessonServices;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final DepartmentRepository departmentRepository;
    private final LessonServices lessonServices;
    private final CourseServices courseService;
    private final UserServices userServices;
    private final EnrollmentServices enrollmentServices;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/")
    public String getHomePage(@RequestParam(defaultValue = "0") int khoa,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "12") int pageSize,
                              Authentication authentication,
                              Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        page = page - 1;
        Page<Course> courses = courseService.filterByUserAndDepartment(khoa, user, page, pageSize);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("khoaId", khoa);
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", page + 1);

        return "main";
    }
    
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/courseThamKhaoo")
    public String getHomeCoursesThamKhao(@RequestParam(defaultValue = "0") int khoa,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "12") int pageSize,
                              Authentication authentication,
                              Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        page = page - 1;
        Page<Course> courses = courseService.filterByUserAndDepartment1(khoa, user, page, pageSize);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("khoaId", khoa);
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", page + 1);

        return "courseThamkhao";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/admin")
    public String getHomeAdminPage(@RequestParam(defaultValue = "0") int khoa,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "12") int pageSize,
                              Authentication authentication,
                              Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        page = page - 1;
        Page<Course> courses = courseService.filterByUserAndDepartmentAdmin(khoa, user, page, pageSize);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("khoaId", khoa);
        model.addAttribute("totalPages", courses.getTotalPages());
        model.addAttribute("currentPage", page + 1);
        return "main_admin";
    }
    //SỬA
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/detail/{courseId}")
    public String getDetail(@PathVariable String courseId, Model model) {
        Course myCourse = courseService.getCourseById(UUID.fromString(courseId));
        model.addAttribute("course", myCourse);
        List<Lesson> lessons = new ArrayList<>(myCourse.getListLessons().stream().toList());
        lessons.sort(Comparator.comparing(Lesson::getLessonSort));
        model.addAttribute("lessons", lessons);
        return "course_intro";
    }
    //SỬA
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/detailCourse/{courseId}")
    public String getDetailCourse(@PathVariable String courseId, Model model) {
        Course myCourse = courseService.getCourseById(UUID.fromString(courseId));
        model.addAttribute("course", myCourse);
        List<Lesson> lessons = new ArrayList<>(myCourse.getListLessons().stream().toList());
        lessons.sort(Comparator.comparing(Lesson::getLessonSort));
        model.addAttribute("lessons",lessons);
        return "courseChitiet";
    }
    //SỬA
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @GetMapping("/admin/detail/{courseId}")
    public String getAdminDetail(@PathVariable String courseId, Model model) {
        Course myCourse = courseService.getCourseById(UUID.fromString(courseId));
        model.addAttribute("course", myCourse);
        List<Lesson> lessons = new ArrayList<>(myCourse.getListLessons().stream().toList());
        lessons.sort(Comparator.comparing(Lesson::getLessonSort));
        model.addAttribute("lessons", lessons);
        return "course_intro_admin";
    }
    //SỬA
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN')")
    @GetMapping("/admin/member/{courseId}")
    public String getCourseMember(@PathVariable String courseId, Model model) {
        Course myCourse = courseService.getCourseById(UUID.fromString(courseId));
        model.addAttribute("course", myCourse);
        List<Lesson> lessons = new ArrayList<>(myCourse.getListLessons().stream().toList());
        lessons.sort(Comparator.comparing(Lesson::getLessonSort));
        model.addAttribute("lessons", lessons);
        return "course_intro_member_admin";
    }
    //SỬA
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/detail/{courseId}/lesson/{lessonId}")
    public String getChapterView(@PathVariable Integer lessonId, @PathVariable String courseId, Model model){
        Lesson lesson = lessonServices.getLessonById(lessonId);
        Course course = courseService.getCourseById(UUID.fromString(courseId));
        if (lesson != null && course != null){
            List<Lesson> lessons = new ArrayList<>(course.getListLessons().stream().toList());
            lessons.sort(Comparator.comparing(Lesson::getLessonSort));
            if (lesson.getLessonVideo().contains("youtube.com/embed/")){
                model.addAttribute("isYoutube", true);
            } else {
                model.addAttribute("isYoutube", false);
            }
            model.addAttribute("lesson", lesson);
            model.addAttribute("course", course);
            model.addAttribute("lessons", lessons);
        }
        return "chapter_watch";
    }
    
    //SỬA
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/dangkyCourse/{id}")
    public String dkyCourse(@PathVariable UUID id, Authentication authentication, Model model){
        Course course = courseService.getCourseById(id);
//        try {
//            course = courseServices.getCourseById(UUID.fromString(courseId));
//        } catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body("Mã khóa học không đúng định dạng");
//        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        if (course != null && user != null){
            Enrollment enroll = enrollmentServices.getEnrollmentByUserAndCourse(user, course);
            if (enroll == null){
                Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
                enrollmentId.setUserId(user.getUserId());
                enrollmentId.setCourseId(course.getCourseID());
                Enrollment enrollment = new Enrollment();
                enrollment.setId(enrollmentId);
                enrollment.setUser(user);
                enrollment.setAccessType(AccessType.PENDING);
                enrollment.setCourse(course);
                enrollmentServices.saveEnrollment(enrollment);
//                return ResponseEntity.ok("Đã gửi yêu cầu tham gia khóa học! Vui lòng chờ duyệt");
                Lesson lesson = lessonServices.getLessonById(0);
                if (lesson != null){
                    List<Lesson> lessons = new ArrayList<>(course.getListLessons().stream().toList());
                    lessons.sort(Comparator.comparing(Lesson::getLessonSort));
                    if (lesson.getLessonVideo().contains("youtube.com/embed/")){
                        model.addAttribute("isYoutube", true);
                    } else {
                        model.addAttribute("isYoutube", false);
                    }
                    model.addAttribute("lesson", lesson);
                    model.addAttribute("course", course);
                    model.addAttribute("lessons", lessons);
                }
                return "chapter_watch";
            } else {
//                return ResponseEntity.badRequest().body("Bạn đã gửi yêu cầu cho người sở hữu hoặc đã tham gia khóa học này");
            }
        }
//        return ResponseEntity.badRequest().body("Khóa học yêu cầu không tồn tại!");
           return "chapter_watch";
    }
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping("/account_info")
    public String accountInfo() {
        // Do something to get account info
        return "account_info";
    }
}

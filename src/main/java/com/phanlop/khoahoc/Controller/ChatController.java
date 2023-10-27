package com.phanlop.khoahoc.Controller;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phanlop.khoahoc.Entity.Chatlog;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.ChatlogServices;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.UserServices;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserServices userServices;
    private final CourseServices courseServices;
    private final ChatlogServices chatlogServices;
    private final EnrollmentServices enrollmentServices;
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/chat")
    public String chat(@RequestParam("usid") String id,Model model) {
        Long userid=Long.parseLong(id);
        User usr=userServices.getUserById(userid);
        List<Course> courses= courseServices.findCoursesbyTeacherId(usr);
        model.addAttribute("usrId", id);
        model.addAttribute("courses", courses);
        return "chat";
    }
    @GetMapping("/chatteacher")
    public String chatteacher(@RequestParam("usid") String id,Model model) {
        Long userid=Long.parseLong(id);
        User usr=userServices.getUserById(userid);
        List<Course> courses=courseServices.findCourseByUserId(usr);
        model.addAttribute("usr", usr);
        model.addAttribute("courses", courses);
        return "chatteacher";
    }
    @GetMapping("/chat2")
    public String chat(@RequestParam("courseId") String courseId, 
    @RequestParam("userId") String userId, Model model) {
        // Xử lý các thông tin và logic liên quan đến courseId và userId ở đây
        Long userid=Long.parseLong(userId);
        User usr=userServices.getUserById(userid);
        List<Course> courses= courseServices.findCoursesbyTeacherId(usr);
        UUID couid=UUID.fromString(courseId);
        Course course=courseServices.getCourseById(couid);
        User teacher=userServices.getUserByCourseId(couid);
        List<Chatlog> chatlist=chatlogServices.getAllMessagesBetween(usr,course);
        model.addAttribute("chatlist", chatlist);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courseId", courseId);
        model.addAttribute("user", usr);
        model.addAttribute("courses", courses);
        return "chat2";
    }
    @GetMapping("/chat3")
    public String chat(@RequestParam("courseId") String courseId, 
    @RequestParam("userId") String userId,
    @RequestParam("teacherid") String teacherId, Model model) {
        // Xử lý các thông tin và logic liên quan đến courseId và userId ở đây
        Long userid=Long.parseLong(userId);
        User usr=userServices.getUserById(userid);
        ///
        UUID couid=UUID.fromString(courseId);
        Course course=courseServices.getCourseById(couid);
        Long teachid=Long.parseLong(teacherId);
        User teacher=userServices.getUserById(teachid);
        List<Chatlog> chatlist=chatlogServices.getAllMessagesBetween(usr,course);
        model.addAttribute("chatlist", chatlist);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courseId", courseId);
        model.addAttribute("user", usr);
        return "chat3";
    }
    @PostMapping("/chat3")
    public ModelAndView sendMessagebyteach(@RequestParam("userid") long userid, 
    @RequestParam("teacherid") long teacherid,
    @RequestParam("courseid") UUID courseid,@RequestParam("messagecontent") String message, 
    Model model) {
        Chatlog log = new Chatlog();
        User usr=userServices.getUserById(userid);
        User teacher=userServices.getUserById(teacherid);
        log.setCourseBuyer(usr);
        log.setCourseOwner(teacher);
        Course course=courseServices.getCourseById(courseid);
        log.setCourse(course);
        log.setContent(message);
        log.setSendBy(1);
        chatlogServices.saveChat(log);
        String url="chat3?courseId="+courseid+"&"+"userId="+userid+"&teacherid="+teacherid;
        ModelAndView modelAndView = new ModelAndView("redirect:/"+url);
        return modelAndView;
    }
    @PostMapping("/chat2")
    public ModelAndView sendMessage(@RequestParam("userid") long userid, 
    @RequestParam("teacherid") long teacherid,@RequestParam("courseid") UUID courseid,@RequestParam("messagecontent") String message, Model model) {
        Chatlog log = new Chatlog();
        User usr=userServices.getUserById(userid);
        User teacher=userServices.getUserById(teacherid);
        log.setCourseBuyer(usr);
        log.setCourseOwner(teacher);
        Course course=courseServices.getCourseById(courseid);
        log.setCourse(course);
        log.setContent(message);
        log.setSendBy(0);
        chatlogServices.saveChat(log);
        String url="chat2?courseId="+courseid+"&"+"userId="+userid;
        ModelAndView modelAndView = new ModelAndView("redirect:/"+url);
        return modelAndView;
    }
    @GetMapping("/chatto")
    public String chatto(@RequestParam("courseid") String courseId, 
    @RequestParam("teacherid") String userId, Model model) {
        // Xử lý các thông tin và logic liên quan đến courseId và userId ở đây
        Long userid=Long.parseLong(userId);
        User teacher=userServices.getUserById(userid);
        ////
        UUID coursid=UUID.fromString(courseId);
        Course course=courseServices.getCourseById(coursid);
        List<User> enrollments=enrollmentServices.getUsersEnrolledInCourse(course);
        model.addAttribute("courseid", courseId);
        model.addAttribute("users", enrollments);
        model.addAttribute("teacherid", userId);
        return "chatto";
    }
}

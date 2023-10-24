package com.phanlop.khoahoc.Controller;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.DTO.CreateCourseDTO;
import com.phanlop.khoahoc.DTO.CreateLessonDTO;
import com.phanlop.khoahoc.DTO.DepartmentDTO;
import com.phanlop.khoahoc.DTO.DetailUserDTO;
import com.phanlop.khoahoc.DTO.LessonDTO;
import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.LessonServices;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseServices courseServices;
    private final LessonServices lessonServices;
    private final UserServices userServices;
    private final DepartmentRepository departmentRepository;
//    private final FileServices fileServices;
    private final EnrollmentServices enrollmentServices;
//    private final FileRepository fileRepository;

    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    @GetMapping("/search")
    public List<CourseDTO> searchCourse(Authentication authentication, @Param("text") String text){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(courseServices.filterBySearch(user, text), CourseDTO.class);
    }


    //Mới
    @GetMapping("/search-student")
    public List<CourseDTO> searchCourse11111(Authentication authentication,@Param("text") String text){
        return ObjectMapperUtils.mapAll(courseServices.filterBySearch1(text), CourseDTO.class);
    }

    


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/owned")
    public List<CourseDTO> searchOwnCourse(Authentication authentication, @RequestParam(defaultValue = "") String text){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        return ObjectMapperUtils.mapAll(courseServices.searchByCourseOwner(user, text), CourseDTO.class);
    }

    @GetMapping("/detail/{courseId}")
    public CourseDTO getCourseDetails(@PathVariable UUID courseId, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        Course course = courseServices.getCourseById(courseId);
        if (courseServices.isOwned(course, user.getUserId()) || courseServices.isAccess(course, user.getUserId())){
            return ObjectMapperUtils.map(course, CourseDTO.class);
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<CourseDTO> addCourse(@ModelAttribute CreateCourseDTO courseDTO, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
//        File file = fileServices.addFile(courseDTO.getCourseAvt());
        Department department = departmentRepository.findById(courseDTO.getDepartmentId()).orElse(null);
        if (department  != null){
            Course course = ObjectMapperUtils.map(courseDTO, Course.class);
//            course.setCourseAvt(file.getFileLink());
            course.setDepartment(department);
            course.setCourseOwner(user);
            courseServices.saveCourse(course);
            return ResponseEntity.ok(ObjectMapperUtils.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/edit")
        public ResponseEntity<CourseDTO> editCourse(@ModelAttribute CreateCourseDTO courseDTO, Authentication authentication) {
            Course course = courseServices.getCourseById(courseDTO.getCourseID());
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userServices.getUserByUserName(userDetails.getUsername());
//            File file = fileServices.addFile(courseDTO.getCourseAvt());
            Department department = departmentRepository.findById(courseDTO.getDepartmentId()).orElse(null);
            if (department  != null && course != null){
                course.setDepartment(department);
                course.setCourseOwner(user);
                course.setCourseName(courseDTO.getCourseName());
                course.setCourseDes(courseDTO.getCourseDes());
//                if (file != null){
//                    course.setCourseAvt(file.getFileLink());
//                }
                courseServices.saveCourse(course);
                return ResponseEntity.ok(ObjectMapperUtils.map(course, CourseDTO.class));
            }
            return ResponseEntity.badRequest().build();
        }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/delete/{courseId}")
    public ResponseEntity<CourseDTO> removeCourse(@PathVariable UUID courseId){
        Course course = courseServices.getCourseById(courseId);
        if (course != null){
            courseServices.deleteCourse(courseId);
            return ResponseEntity.ok(ObjectMapperUtils.map(course, CourseDTO.class));
        }
        return ResponseEntity.badRequest().build();
    }
    //SỬA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping("/lesson/get/{lessonId}")
    public ResponseEntity<LessonDTO> getChapter(@PathVariable Integer lessonId){
        Lesson lesson = lessonServices.getLessonById(lessonId);
        if (lesson == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(ObjectMapperUtils.map(lesson, LessonDTO.class));
    }

    //SỬA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/lesson/add")
    public ResponseEntity<LessonDTO> addLesson(@ModelAttribute CreateLessonDTO lessonDTO){
//        File file = fileServices.addFile(lessonDTO.getLessonVideoMulti());
        Course course = courseServices.getCourseById(lessonDTO.getCourseId());
        if (course == null)
            return ResponseEntity.badRequest().build();

        Lesson lesson = ObjectMapperUtils.map(lessonDTO, Lesson.class);
        lesson.setCourse(course);
        lesson.setLessonSort(lessonServices.getMaxSortOfCourse(course) + 1);
//        if (file != null){
//            lesson.setLessonVideo(file.getFileLink());
//        } else {
            lesson.setLessonVideo(lessonDTO.getYoutubeUrl());
//        }
        LessonDTO dto = ObjectMapperUtils.map(lessonServices.saveLesson(lesson), LessonDTO.class);
        return ResponseEntity.ok(dto);
    }
    //SỬA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/lesson/edit")
    public ResponseEntity<LessonDTO> editLesson(@ModelAttribute CreateLessonDTO lessonDTO){
//        File file = fileServices.addFile(lessonDTO.getLessonVideoMulti());
        Lesson lesson = lessonServices.getLessonById(lessonDTO.getLessonId());
        lesson.setLessonTitle(lessonDTO.getLessonTitle());
        lesson.setLessonContent(lessonDTO.getLessonContent());
//        if (file != null){
//            lesson.setLessonVideo(file.getFileLink());
//        } else {
            lesson.setLessonVideo(lessonDTO.getYoutubeUrl());
//        }
        LessonDTO dto = ObjectMapperUtils.map(lessonServices.saveLesson(lesson), LessonDTO.class);
        return ResponseEntity.ok(dto);
    }
    //SỬA
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/lesson/remove/{lessonId}")
    public ResponseEntity<LessonDTO> addChapter(@PathVariable Integer lessonId){
        Lesson lesson = lessonServices.getLessonById(lessonId);
        if (lesson == null)
            return ResponseEntity.badRequest().build();
        lessonServices.deleteLesson(lessonId);
        return ResponseEntity.ok(ObjectMapperUtils.map(lesson, LessonDTO.class));
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/joinRequest")
    public ResponseEntity<String> joinCourse(@RequestParam String courseId, Authentication authentication){
        Course course = null;
        try {
            course = courseServices.getCourseById(UUID.fromString(courseId));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Mã khóa học không đúng định dạng");
        }

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
                return ResponseEntity.ok("Đã gửi yêu cầu tham gia khóa học! Vui lòng chờ duyệt");
            } else {
                return ResponseEntity.badRequest().body("Bạn đã gửi yêu cầu cho người sở hữu hoặc đã tham gia khóa học này");
            }
        }
        return ResponseEntity.badRequest().body("Khóa học yêu cầu không tồn tại!");
    }
    
    //SỬA
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/file/add/{courseId}")
//    public ResponseEntity<FileDTO> addFileToCourse(@ModelAttribute MultipartFile fileUpload, @PathVariable UUID courseId, Authentication authentication){
//        File file = fileServices.addFile(fileUpload);
//        Course course = courseServices.getCourseById(courseId);
//        if (course != null && file != null){
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            User user = userServices.getUserByUserName(userDetails.getUsername());
//            file.setUploadedUser(user);
//            file.getCourses().add(course);
////            course.getListDocuments().add(file);
//            File saved = fileRepository.save(file);
//            courseServices.saveCourse(course);
//            return ResponseEntity.ok(ObjectMapperUtils.map(file, FileDTO.class));
//        }
//        return ResponseEntity.badRequest().build();
//    }
    //SỬA
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/file/add2/{courseId}")
//    public ResponseEntity<FileDTO> addFileToCourse(@RequestParam UUID fileId, @PathVariable UUID courseId, Authentication authentication){
//        File file = fileRepository.findById(fileId).orElse(null);
//        Course course = courseServices.getCourseById(courseId);
//        if (course != null && file != null){
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            User user = userServices.getUserByUserName(userDetails.getUsername());
//            file.setUploadedUser(user);
//            file.getCourses().add(course);
////            course.getListLessons().add(file);
//            File saved = fileRepository.save(file);
//            courseServices.saveCourse(course);
//            return ResponseEntity.ok(ObjectMapperUtils.map(file, FileDTO.class));
//        }
//        return ResponseEntity.badRequest().build();
//    }
    //SỬA
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/file/remove/{courseId}")
//    public ResponseEntity<FileDTO> removeFromCourse(@RequestParam UUID fileId, @PathVariable UUID courseId){
//        File file = fileRepository.findById(fileId).orElse(null);
//        Course course = courseServices.getCourseById(courseId);
//        if (course != null && file != null){
//            file.getCourses().remove(course);
////            course.getListLessons().remove(file);
//            File saved = fileRepository.save(file);
//            courseServices.saveCourse(course);
//            return ResponseEntity.ok(ObjectMapperUtils.map(saved, FileDTO.class));
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/members/{courseId}")
    public List<DetailUserDTO> getMembers(@PathVariable UUID courseId) {
        Course course = courseServices.getCourseById(courseId);
        if (course != null){
            return course.getEnrollments().stream().map(item->ObjectMapperUtils.map(item.getUser(), DetailUserDTO.class)).toList();
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/members/{courseId}/add")
    public List<DetailUserDTO> addMember(@PathVariable UUID courseId, @RequestParam Long userId) {
        Course course = courseServices.getCourseById(courseId);
        User user = userServices.getUserById(userId);
        if (course != null && user != null){
            Enrollment enroll = enrollmentServices.getEnrollmentByUserAndCourse(user, course);
            if (enroll == null){
                Enrollment enrollment = new Enrollment();
                Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
                enrollmentId.setCourseId(course.getCourseID());
                enrollmentId.setUserId(user.getUserId());
                enrollment.setId(enrollmentId);
                enrollment.setCourse(course);
                enrollment.setUser(user);
                enrollment.setAccessType(AccessType.ACCEPT);
                enrollmentServices.saveEnrollment(enrollment);
            }
            else {
                enroll.setAccessType(AccessType.ACCEPT);
                enrollmentServices.saveEnrollment(enroll);
            }
            return ObjectMapperUtils.mapAll(course.getEnrollments().stream().map(Enrollment::getUser).toList(), DetailUserDTO.class);
        }
        return Collections.emptyList();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/members/{courseId}/remove")
    public List<DetailUserDTO> removeMember(@PathVariable UUID courseId, @RequestParam Long userId) {
        Course course = courseServices.getCourseById(courseId);
        User user = userServices.getUserById(userId);
        if (course != null && user != null){
            Enrollment enrollment = enrollmentServices.getEnrollmentByUserAndCourse(user, course);
            enrollmentServices.deleteEnrollment(enrollment);
            return ObjectMapperUtils.mapAll(course.getEnrollments().stream().map(Enrollment::getUser).toList(), DetailUserDTO.class);
        }
        return Collections.emptyList();
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/document/{courseId}")
//    public List<FileDTO> getDocuments(@PathVariable UUID courseId){
//        Course course = courseServices.getCourseById(courseId);
//        if (course != null){
//            return ObjectMapperUtils.mapAll(course.getListDocuments(), FileDTO.class);
//        }
//        return Collections.emptyList();
//    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/document/{courseId}/remove")
//    public List<FileDTO> removeDocument(@PathVariable UUID courseId, @RequestParam UUID documentId){
//        Course course = courseServices.getCourseById(courseId);
//        File file = fileServices.getFileByUUID(documentId);
//        if (course != null && file != null){
////            course.getListDocuments().remove(file);
//            file.getCourses().remove(course);
//            courseServices.saveCourse(course);
//            fileRepository.save(file);
//            return ObjectMapperUtils.mapAll(course.getListDocuments(), FileDTO.class);
//        }
//        return Collections.emptyList();
//    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/document/{courseId}/add")
//    public List<FileDTO> addDocument(@PathVariable UUID courseId, @RequestParam UUID documentId){
//        Course course = courseServices.getCourseById(courseId);
//        File file = fileServices.getFileByUUID(documentId);
//        if (course != null && file != null){
//            course.getListDocuments().add(file);
//            file.getCourses().add(course);
//            courseServices.saveCourse(course);
//            fileRepository.save(file);
//            return ObjectMapperUtils.mapAll(course.getListDocuments(), FileDTO.class);
//        }
//        return Collections.emptyList();
//    }
    //SỬA
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/assignment/{lessonId}")
//    public List<AssignmentDTO> getAssignments(@PathVariable int lessonId){
//        Lesson lesson = lessonServices.getLessonById(lessonId);
//        if (lesson != null){
//            return ObjectMapperUtils.mapAll(lesson.getListAssignments(), AssignmentDTO.class);
//        }
//        return Collections.emptyList();
//    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
//    @PostMapping("/assignment/{courseId}/remove")
//    public List<AssignmentDTO> getAssignments(@PathVariable UUID courseId, @RequestParam Integer assignmentId){
//        Course course = courseServices.getCourseById(courseId);
//        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
//        if (course != null && assignment != null){
//            course.getListAssignments().remove(assignment);
//            courseServices.saveCourse(course);
//            return ObjectMapperUtils.mapAll(course.getListAssignments(), AssignmentDTO.class);
//        }
//        return Collections.emptyList();
//    }

    @GetMapping("/departments")
    public List<DepartmentDTO> getAllDepartments(){
        return ObjectMapperUtils.mapAll(departmentRepository.findAll(), DepartmentDTO.class);
    }
    

}

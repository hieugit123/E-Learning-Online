package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.DTO.UserCourseCountDTO;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.User;

import java.util.List;

public interface EnrollmentServices {
    List<Enrollment> getAll();
    Enrollment saveEnrollment(Enrollment enrollment);
    void deleteEnrollment(Enrollment enrollment);
    List<Enrollment> getEnrollmentsByUser(User user);
    List<Enrollment> getEnrollmentsByCourse(Course course);
    Enrollment getEnrollmentByUserAndCourse(User user, Course course);
    List<Course> getCoursesEnrolledByUser(User user);
    List<User> getUsersEnrolledInCourse(Course course);
    
}

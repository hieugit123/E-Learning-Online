package com.phanlop.khoahoc.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;

public interface CourseServices {
    List<Course> getAllCourses();
    List<Course> searchByCourseOwner(User user, String text);
    Course getCourseById(UUID courseId);
    Course saveCourse(Course course);
    void deleteCourse(UUID courseId);
    Page<Course> filterByUserAndDepartment(int departmentId, User user, int pageNo, int pageSize);
    Page<Course> filterByUserAndDepartment1(int departmentId, User user, int pageNo, int pageSize);
    Page<Course> filterByUserAndDepartmentAdmin(int departmentId, User user, int pageNo, int pageSize);
    // Filter course
    boolean isOwned(Course course, Long userId);
    boolean isAccess(Course course, Long userId);
    List<Course> filterOwnedByUser(List<Course> courses, Long userId);
    List<Course> filterNoOwnedByUser(User user);
    List<Course> filterAccessByUser(List<Course> courses, Long userId);
    List<Course> filterBySearch(User user, String search);
    List<Course> filterBySearch1(String search);
    List<Course> findCourseDangDo(User user);
}

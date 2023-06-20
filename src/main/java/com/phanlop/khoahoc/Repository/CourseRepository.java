package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Page<Course> findByDepartment(Department department, Pageable pageable);
//    SELECT c.course_name FROM Course c, enrollment e WHERE c.courseid = e.course_id AND e.user_id = 2;
    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND c.department = :department AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    Page<Course> findEnrolledCoursesByDepartmentSortedByDateJoined(@Param("user") User user, @Param("department") Department department, @Param("accessType") AccessType accessType, Pageable pageable);
    @Query("SELECT c FROM Course c WHERE c NOT IN (SELECT e.course FROM Enrollment e WHERE e.user = :user) AND c.department = :department")
    Page<Course> findUnenrolledCoursesByDepartment(@Param("user") User user, @Param("department") Department department, Pageable pageable);


    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    Page<Course> findEnrolledCoursesSortedByDateJoined(@Param("user") User user, @Param("accessType") AccessType accessType, Pageable pageable);
    @Query("SELECT c FROM Course c WHERE c NOT IN (SELECT e.course FROM Enrollment e WHERE e.user = :user)")
    Page<Course> findUnenrolledCoursesSortedByDateCreated(@Param("user") User user, Pageable pageable);


    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    List<Course> findBySearchList(@Param("user") User user, @Param("accessType") AccessType accessType);

    Page<Course> findCourseByCourseOwnerAndDepartmentOrderByCreateDateDesc(User user, Department department, Pageable pageable);
    Page<Course> findCourseByCourseOwnerOrderByCreateDateDesc(User user, Pageable pageable);
}

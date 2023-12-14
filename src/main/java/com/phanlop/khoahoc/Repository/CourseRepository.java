package com.phanlop.khoahoc.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.User;

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
    @Query("SELECT c FROM Course c WHERE c.courseOwner = :user AND c.stateGuiAdmin = 0")
    Page<Course> findCourseDangDoByUser(@Param("user") User user, Pageable pageable);


    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.user = :user AND e.accessType = :accessType ORDER BY e.dateJoined DESC")
    List<Course> findBySearchList(@Param("user") User user, @Param("accessType") AccessType accessType);


    //Mới
    @Query("SELECT c FROM Course c WHERE c NOT IN (SELECT e.course FROM Enrollment e WHERE e.user = :user)")
    List<Course> findCourseUserNotOwned(@Param("user") User user);

    Page<Course> findCourseByCourseOwnerAndDepartmentOrderByCreateDateDesc(User user, Department department, Pageable pageable);
    Page<Course> findCourseByCourseOwnerOrderByCreateDateDesc(User user, Pageable pageable);

    //phan trang student
    @Query("SELECT c FROM Course c WHERE c NOT IN (SELECT e.course FROM Enrollment e WHERE e.user = :user)")
    Page<Course> findCourseUser(User user, Pageable pageable);
    //phan trang home
    @Query("SELECT c FROM Course c")
    Page<Course> findCourse(Pageable pageable);
    //Filter
    @Query("SELECT c FROM Course c ORDER BY c.gia ASC")
    List<Course> findByOrderByGiaAsc();
    @Query("SELECT c FROM Course c ORDER BY c.gia DESC")
    List<Course> findByOrderByGiaDesc();

    List<Course> findByGiaBetween(double minPrice,double maxPrice);
    Course findByCourseID(UUID courseID);
    List<Course> findByCourseOwner(User courseOwner);
    Page<Course> findBycourseNameContainingIgnoreCase(String name, Pageable pageable);
    
}

package com.phanlop.khoahoc.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.User;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Enrollment.EnrollmentId> {
    List<Enrollment> findByUser(User user);
    @Query("SELECT c FROM Enrollment c WHERE c.course = :course")
    List<Enrollment> findByCourse(Course course);
    Enrollment findByUserAndCourse(User user, Course course);
}

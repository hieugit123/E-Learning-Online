package com.phanlop.khoahoc.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.phanlop.khoahoc.Entity.Cart;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
    @Query("SELECT c.course FROM Cart c WHERE c.user = :user")
    List<Course> findCourseInCartOfUser(@Param("user") User user);
    @Query("SELECT c.course FROM Cart c WHERE c.user = :user AND c.course = :course")
    List<Course> IsInCart(@Param("user") User user, @Param("course") Course course);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user = :user AND c.course = :course")
    void deleteByUserAndCourse(@Param("user") User user, @Param("course") Course course);
}

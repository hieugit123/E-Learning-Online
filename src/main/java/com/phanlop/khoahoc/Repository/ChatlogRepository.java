package com.phanlop.khoahoc.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phanlop.khoahoc.Entity.Chatlog;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
public interface ChatlogRepository extends JpaRepository<Chatlog, Long>{
     @Query("SELECT c FROM Chatlog c WHERE c.courseBuyer = :courseBuyer AND c.course = :course")
     List<Chatlog> getContentOf(@Param("courseBuyer") User courseBuyer,@Param("course") Course course);
}

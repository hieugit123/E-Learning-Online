package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Assignment;
import com.phanlop.khoahoc.Entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    @Query("SELECT a FROM Assignment a WHERE a.lesson = :lesson ORDER BY a.createdDate DESC")
    public List<Assignment> findAllAssignmentsByCourse(@Param("lesson") Lesson lesson);
}

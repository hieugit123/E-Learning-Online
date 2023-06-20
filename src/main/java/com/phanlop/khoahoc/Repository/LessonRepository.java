package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourse(Course course);
}

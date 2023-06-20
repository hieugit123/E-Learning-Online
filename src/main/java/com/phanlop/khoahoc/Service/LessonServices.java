package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Course;

import java.util.List;

public interface LessonServices {
    List<Lesson> getAllLessons();
    Lesson getLessonById(int lessonId);
    Lesson saveLesson(Lesson lesson);
    void deleteLesson(int lessonId);
    List<Lesson> getLessonsByCourse(Course course);
    int getMaxSortOfCourse(Course course);
}

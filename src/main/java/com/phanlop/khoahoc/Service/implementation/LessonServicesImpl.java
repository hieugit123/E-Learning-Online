package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.phanlop.khoahoc.Repository.LessonRepository;
import com.phanlop.khoahoc.Service.LessonServices;

@Service
public class LessonServicesImpl implements LessonServices {
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonById(int chapterId) {
        return lessonRepository.findById(chapterId).orElse(null);
    }

    @Override
    public Lesson saveLesson(Lesson chapter) {
        return lessonRepository.save(chapter);
    }

    @Override
    public void deleteLesson(int chapterId) {
        lessonRepository.findById(chapterId).ifPresent(chapter -> lessonRepository.delete(chapter));
    }

    @Override
    public List<Lesson> getLessonsByCourse(Course course) {
        return lessonRepository.findByCourse(course);
    }

    @Override
    public int getMaxSortOfCourse(Course course) {
        List<Lesson> list = this.lessonRepository.findByCourse(course);
        int max = 0;
        for (Lesson lesson : list) {
            if (lesson.getLessonSort() > max)
                max = lesson.getLessonSort();
        }
        return max;
    }
}

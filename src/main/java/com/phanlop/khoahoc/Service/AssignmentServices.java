package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.Assignment;
import com.phanlop.khoahoc.Entity.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AssignmentServices {
    public List<Assignment> getAllAssignments();
    public Assignment getAssignmentById(int assignId);
    public Assignment save(Assignment assignment);
    public void delete(int assignmentId);
    public List<Assignment> getAllAssignmentsByLesson(Lesson lesson);

}

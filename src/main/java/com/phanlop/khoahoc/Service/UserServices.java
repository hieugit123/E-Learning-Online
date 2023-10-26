package com.phanlop.khoahoc.Service;

import com.phanlop.khoahoc.Entity.User;

import java.util.List;
import java.util.UUID;

public interface UserServices {
    List<User> getAllUsers();
    User getUserById(Long userId);
    User saveUser(User user);
    void deleteUser(Long userId);
    User getUserByUserName(String username);
    void resetPassword(User user, String newPassword);
    List<User> searchStudents(String keywords);
    List<User> getTeacherByUserEnrollment(User user);
    User getUserByCourseId(UUID courseid);
}

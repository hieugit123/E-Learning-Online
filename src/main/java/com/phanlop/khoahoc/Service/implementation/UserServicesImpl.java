package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.EnrollmentRepository;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.EnrollmentServices;
import com.phanlop.khoahoc.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByEmail(username);
    }
    @Override
    public void resetPassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User> searchStudents(String keywords) {
        Role role = roleRepository.findByRoleName("ROLE_STUDENT");
        List<User> list = userRepository.findUserByRole(role);
        if (keywords.equals(""))
            return list;
        list.removeIf(user -> !user.getEmail().toLowerCase().contains(keywords) && !user.getFullName().toLowerCase().contains(keywords));
        return list;
    }

    @Override
    public List<User> getTeacherByUserEnrollment(User user) {
        List<Enrollment> enrollments=enrollmentRepository.findByUser(user);
        List<Course> courses=new ArrayList<>();
        for(Enrollment enrollment:enrollments){
           courses.add(courseRepository.findByCourseID(enrollment.getCourse().getCourseID()));
        }
        List<User> users=new ArrayList<>();
        for(Course course:courses){
            users.add(userRepository.findUserByuserId(course.getCourseOwner().getUserId()));
        }
        return users;
    }

    @Override
    public User getUserByCourseId(UUID courseid) {
        Course course=courseRepository.findByCourseID(courseid);
        User user=course.getCourseOwner();
        return user;
    }

    @Override
    public List<User> findUserByRole(Role role) {
        return userRepository.findUserByRole(role);
    }

}

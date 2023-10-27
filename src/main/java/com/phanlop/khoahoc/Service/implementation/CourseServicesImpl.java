package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Entity.*;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Repository.EnrollmentRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServicesImpl implements CourseServices {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EnrollmentRepository enrolmentRepository;
    @Autowired
    private UserRepository userRepository;
    

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> searchByCourseOwner(User user, String text){
        List<Course> courses = user.getSelfCourses().stream().toList();
        List<Course> returnList = new ArrayList<>();
        for (Course course : courses){
            if (course.getCourseName().toLowerCase().contains(text.toLowerCase())
                    || course.getCourseID().toString().equals(text.toLowerCase())){
                returnList.add(course);
            }
        }
        return returnList;
    }

    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(UUID courseId) {
        courseRepository.findById(courseId).ifPresent(course -> courseRepository.delete(course));
    }

    @Override
    public Page<Course> filterByUserAndDepartment(int departmentId, User user, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department == null) {
            return courseRepository.findEnrolledCoursesSortedByDateJoined(user, AccessType.ACCEPT, pageable);
        }
        return courseRepository.findEnrolledCoursesByDepartmentSortedByDateJoined(user, department, AccessType.ACCEPT, pageable);
    }

    @Override
    public Page<Course> filterByUserAndDepartment1(int departmentId, User user, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department == null) {
            return courseRepository.findUnenrolledCoursesSortedByDateCreated(user, pageable);
        }
        return courseRepository.findUnenrolledCoursesByDepartment(user, department, pageable);
    }

    @Override
    public Page<Course> filterByUserAndDepartmentAdmin(int departmentId, User user, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department == null) {
            return courseRepository.findCourseByCourseOwnerOrderByCreateDateDesc(user, pageable);
        }
        return courseRepository.findCourseByCourseOwnerAndDepartmentOrderByCreateDateDesc(user, department, pageable);
    }

    @Override
    public boolean isOwned(Course course, Long userId) {
        return course.getCourseOwner().getUserId().equals(userId);
    }

    @Override
    public boolean isAccess(Course course, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            Enrollment enrollment = enrolmentRepository.findByUserAndCourse(user, course);
            return enrollment.getAccessType() == AccessType.ACCEPT;
        }
        return false;
    }

    @Override
    public List<Course> filterOwnedByUser(List<Course> courses, Long userId) {
        courses.removeIf(c -> !isOwned(c, userId));
        return courses;
    }
    
    @Override
    public List<Course> filterNoOwnedByUser(User user) {
        List<Course> courses = courseRepository.findCourseUserNotOwned(user);
        return courses;
    }

    @Override
    public List<Course> filterAccessByUser(List<Course> courses, Long userId) {
        courses.removeIf(c -> !isAccess(c, userId));
        return courses;
    }

    @Override
    public List<Course> filterBySearch(User user, String search) {
        if (user == null){
            return Collections.emptyList();
        }
        List<Course> list = courseRepository.findBySearchList(user, AccessType.ACCEPT);
        list.removeIf(c -> !c.getCourseName().contains(search));
        return list;
    }

    @Override
    public List<Course> filterBySearch1(String search) {
        List<Course> list = courseRepository.findAll();
        list.removeIf(c -> !c.getCourseName().contains(search));
        return list;
    }

    @Override
    public List<Course> findCourseDangDo(User user) {
        List<Course> courses = user.getSelfCourses().stream().toList();
        List<Course> returnList = new ArrayList<>();
        for (Course course : courses){
            if (course.getStateGuiAdmin() == 0)
                returnList.add(course);
        }
        return returnList;
    }   
    @Override
    public List<Course> sortCoursesByGia(String priceOrder) {
        if (priceOrder.equals("asc")) {
            // Triển khai truy vấn cơ sở dữ liệu để lấy danh sách khóa học và sắp xếp giá tăng dần
            return courseRepository.findByOrderByGiaAsc();
        } 
        else if(priceOrder.equals("desc"))  {
            // Triển khai truy vấn cơ sở dữ liệu để lấy danh sách khóa học và sắp xếp giá giảm dần
            return courseRepository.findByOrderByGiaDesc();
        }
        else{
            return courseRepository.findAll();
        }
    }
    @Override
    public List<Course> filterCoursesByGiaRange(String priceRange) {
        double minPrice, maxPrice;
        switch (priceRange) {
            case "<500000":
                minPrice = 0;
                maxPrice = 500000;
                break;
            case "500000-1000000":
                minPrice = 500000;
                maxPrice = 1000000;
                break;
            case ">1000000":
                minPrice = 1000000;
                maxPrice = Double.MAX_VALUE;
                break;
            default:
                // Trường hợp mặc định, không lọc theo khoảng giá
                return courseRepository.findAll();
        }
        return courseRepository.findByGiaBetween(minPrice, maxPrice);
    }

    @Override
    public List<Course> findCoursesbyTeacherId(User user) {
        List<Enrollment> enrollments=enrolmentRepository.findByUser(user);
        List<Course> courses=new ArrayList<>();
        for(Enrollment enrollment:enrollments){
           courses.add(courseRepository.findByCourseID(enrollment.getCourse().getCourseID()));
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByUserId(User user) {
        return courseRepository.findByCourseOwner(user);
    }
}

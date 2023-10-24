package com.phanlop.khoahoc.Service;

import java.util.List;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;

public interface CartServices {
    List<Course> getCartByUser(User user);
    boolean isInCart(Course course, User user);
    void deleteCart(Course course, User user);
}

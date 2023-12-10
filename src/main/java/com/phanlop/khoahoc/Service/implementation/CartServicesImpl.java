package com.phanlop.khoahoc.Service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CartRepository;
import com.phanlop.khoahoc.Service.CartServices;

@Service
public class CartServicesImpl implements CartServices{
    @Autowired
    private CartRepository cartRepository;

    public List<Course> getCartByUser(User user) {
        // TODO Auto-generated method stub
        List<Course> list = cartRepository.findCourseInCartOfUser(user);
        return list;
    }

    @Override
    public boolean isInCart(Course course, User user) {
        // TODO Auto-generated method stub
        List<Course> list = cartRepository.IsInCart(user, course);
        if(list.isEmpty())
            return true;
        return false;
    }

    @Override
    public void deleteCart(Course course, User user) {
        // TODO Auto-generated method stub
        if(!isInCart(course, user))
            cartRepository.deleteByUserAndCourse(user, course);
    }

    @Override
    public void clearCart(User user) {
        // TODO Auto-generated method stub
        cartRepository.deleteCartByUser(user);
    }

    
    
}

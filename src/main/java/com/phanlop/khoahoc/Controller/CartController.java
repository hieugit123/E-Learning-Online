package com.phanlop.khoahoc.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.CourseDTO;
import com.phanlop.khoahoc.Entity.Cart;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CartRepository;
import com.phanlop.khoahoc.Service.CartServices;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartServices cartServices;
    private final CourseServices courseServices;
    private final UserServices userServices;
    private final CartRepository cartRepository;

    @GetMapping("/add/{courseId}")
    public ResponseEntity<String> add(@PathVariable UUID courseId, Authentication authentication){
        Course course = courseServices.getCourseById(courseId);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        Cart cart = new Cart();
        cart.setCourse(course);
        cart.setUser(user);
        cartRepository.save(cart);
        System.out.println("đã vào ok");
        return ResponseEntity.ok("success");
    }

    @PostMapping("/delete/{courseId}")
    public ResponseEntity<String> delete(@PathVariable UUID courseId, Authentication authentication){
        Course course = courseServices.getCourseById(courseId);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        cartServices.deleteCart(course, user);
        System.out.println("đã vào ok");
        return ResponseEntity.ok("success");
    }
}

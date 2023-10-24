package com.phanlop.khoahoc.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Service.CartServices;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    private final UserServices userServices;
    private final CartServices cartServices;
    @GetMapping
    public String getPageCheckout(Authentication authentication, Model model){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        List<Course> listCourseInCart = cartServices.getCartByUser(user);
        model.addAttribute("listCourseInCart", listCourseInCart);
        return "checkout";
    }

}

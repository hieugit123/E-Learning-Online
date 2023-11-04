package com.phanlop.khoahoc.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.UserServices;

import lombok.RequiredArgsConstructor;

// @Controller
@RequiredArgsConstructor
public class AccountController {
    private final UserServices userServices;

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping("/account_info")
    public String accountInfo() {
        // Do something to get account info
        return "account_info";
    }

    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @PostMapping("/account_info1")
    public String editAccount(@RequestParam String passOld, @RequestParam String passNew, @RequestParam String passNewConfirm, Authentication authentication){
        System.out.println("Da vao cu");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        if(user.getPassword().equals(passOld)){
            if(passNew.equals(passNewConfirm)){
                user.setPassword(passNew);
                userServices.saveUser(user);
            }
        }
        return "account_info";
    }

}

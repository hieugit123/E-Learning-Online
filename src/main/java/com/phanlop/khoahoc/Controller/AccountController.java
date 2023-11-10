package com.phanlop.khoahoc.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.UserServices;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping("/account_info")
    public String accountInfo() {
        // Do something to get account info
        return "account_info";
    }

    //Chưa xong
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @PostMapping("/account_info1")
    public String editAccount(@RequestParam String passOld, @RequestParam String passNew, @RequestParam String passNewConfirm, Authentication authentication){
       
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        // Kiểm tra mật khẩu hiện tại
        if (passwordEncoder.matches(passOld, user.getPassword())) {
            // Mật khẩu hiện tại hợp lệ, thực hiện thay đổi mật khẩu
            String encryptedPassword = passwordEncoder.encode(passNew);
            user.setPassword(encryptedPassword);
            userServices.saveUser(user);
        } else {
            // Mật khẩu hiện tại không đúng, trả về một thông báo lỗi
            // return "redirect:/profile?error";
        }
        return "account_info";
    }

    //Chưa xong
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @PostMapping("/account_info2")
    public String editAcc(Authentication authentication, @RequestParam String email, @RequestParam String fullname){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        User user1 = userServices.getUserByUserName(email);
        if(user1 != null)
            return "coloi";
        user.setEmail(email);
        user.setFullName(fullname);
        
        return "account_info";
    }

}

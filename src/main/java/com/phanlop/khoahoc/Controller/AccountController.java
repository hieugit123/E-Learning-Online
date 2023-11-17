package com.phanlop.khoahoc.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.Entity.File;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Service.FileServices;
import com.phanlop.khoahoc.Service.UserServices;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;
    private final FileServices fileServices;
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping("/account_info")
    public String accountInfo(Model model, Authentication authentication) {
        // Do something to get account info
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user1 = userServices.getUserByUserName(userDetails.getUsername());
        model.addAttribute("user", user1);
        return "account_info";
    }
    @GetMapping("/account_infofix")
    public String accountInfoFixForAdmin(@RequestParam Long userId,Model model) {
        // Do something to get account info
        User user1 = userServices.getUserById(userId);
        model.addAttribute("user", user1);
        return "account_info";
    }


    //Chưa xong
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @PostMapping("/account_info1")
    public ModelAndView editAccount(@RequestParam Long userId,@RequestParam String passOld, @RequestParam String passNew, @RequestParam String passNewConfirm, Authentication authentication, Model model){
       
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
         //Cái này của Nam
        if(user.getUserId()!=userId){
            user=userServices.getUserById(userId);
        }
        // Kiểm tra mật khẩu hiện tại
        if (passwordEncoder.matches(passOld, user.getPassword())) {
            // Mật khẩu hiện tại hợp lệ, thực hiện thay đổi mật khẩu
            if(passNew.equals(passNewConfirm)){
                String encryptedPassword = passwordEncoder.encode(passNew);
                user.setPassword(encryptedPassword);
                userServices.saveUser(user);
                String text = "Password changed successfully!";
                return new ModelAndView("redirect:/account_info?successMessage="+text);
            } else {
                String text = "New password and confirmation password do not match!";
                return new ModelAndView("redirect:/account_info?errorMessage="+text);
            }
        } else {
            String text = "Old password is incorrect!";
            return new ModelAndView("redirect:/account_info?errorMessage="+text);
        }
    }

    //Chưa xong
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
    @PostMapping("/account_info2")
    public ModelAndView editAccountInfo(@RequestParam Long userId,@RequestParam String fullName, @RequestParam String email, @RequestParam(name = "avatarFile", required = false) MultipartFile avatarFile, @RequestParam(required = false, defaultValue = "") String mota, Authentication authentication, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userServices.getUserByUserName(userDetails.getUsername());
        //Cái này của Nam
        if(user.getUserId()!=userId){
            user=userServices.getUserById(userId);
        }
        System.out.println("email: " + email);
        System.out.println("mota: " + mota);
        if (user != null) {
            // Cập nhật thông tin tài khoản
            if(!fullName.equals(""))
                user.setFullName(fullName);
            if(!mota.equals(""))
                user.setMota(mota);
            if(!email.equals("")){
                if(!user.getEmail().equals(email)){
                    User user1 = userServices.getUserByUserName(email);
                    if(user1 == null){
                        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                        Pattern pattern = Pattern.compile(EMAIL_REGEX);
                        Matcher matcher = pattern.matcher(email);
                        boolean check =  matcher.matches();
                        if(check)
                            user.setEmail(email);
                        else
                            return new ModelAndView("redirect:/account_info?errorMessage=Invalid email");
                    } else 
                        return new ModelAndView("redirect:/account_info?errorMessage=Email has been used!");
                }
            }
            // Xử lý ảnh đại diện nếu có
            if (avatarFile != null && !avatarFile.isEmpty()) {
                // Xử lý và lưu ảnh đại diện
                File avatar = fileServices.addFile(avatarFile);
                if (avatar != null) {
                    user.setAvatar(avatar.getFileLink());
                }
            } else
                System.out.println("file rong" + avatarFile);

            // Lưu thông tin tài khoản đã cập nhật
            userServices.saveUser(user);

            String text = "Update Account Successfully!";
            return new ModelAndView("redirect:/account_info?successMessage="+text);
        }

            String text = "User not found!";
            return new ModelAndView("redirect:/account_info?errorMessage="+text);
    }


}

// package com.phanlop.khoahoc.Controller;

// import java.time.LocalDate;
// import java.time.ZoneId;
// import java.time.temporal.ChronoUnit;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.ModelAndView;

// import com.phanlop.khoahoc.Config.CustomUserDetails;
// import com.phanlop.khoahoc.DTO.UserDTO;
// import com.phanlop.khoahoc.Entity.CTHoaDon;
// import com.phanlop.khoahoc.Entity.Cart;
// import com.phanlop.khoahoc.Entity.Course;
// import com.phanlop.khoahoc.Entity.Enrollment;
// import com.phanlop.khoahoc.Entity.File;
// import com.phanlop.khoahoc.Entity.HoaDon;
// import com.phanlop.khoahoc.Entity.Role;
// import com.phanlop.khoahoc.Entity.User;
// import com.phanlop.khoahoc.Repository.CTHDRepository;
// import com.phanlop.khoahoc.Repository.CartRepository;
// import com.phanlop.khoahoc.Repository.HoaDonRepository;
// import com.phanlop.khoahoc.Repository.RoleRepository;
// import com.phanlop.khoahoc.Service.CourseServices;
// import com.phanlop.khoahoc.Service.EnrollmentServices;
// import com.phanlop.khoahoc.Service.FileServices;
// import com.phanlop.khoahoc.Service.UserServices;

// import lombok.RequiredArgsConstructor;

// @Controller
// @RequiredArgsConstructor
// public class AccountController {
//     private final UserServices userServices;
//     private final PasswordEncoder passwordEncoder;
//     private final FileServices fileServices;
//     private final EnrollmentServices enrollmentServices;
//     private final CourseServices courseServices;
//     private final RoleRepository roleRepository;
//     private final CTHDRepository cthdRepository;
//     private final CartRepository cartRepository;
//     private final HoaDonRepository hoaDonRepository;
//     @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
//     @GetMapping("/account_info")
//     public String accountInfo(Model model, Authentication authentication) {
//         // Do something to get account info
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         User user1 = userServices.getUserByUserName(userDetails.getUsername());
//         model.addAttribute("user", user1);
//         return "account_info";
//     }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//     @GetMapping("/account_infofix")
//     public String accountInfoFixForAdmin(@RequestParam Long userId, Authentication authentication,Model model) {
//         // Do something to get account info
//         User user1 = userServices.getUserById(userId);
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         User user2= userServices.getUserByUserName(userDetails.getUsername());
//         model.addAttribute("user", user2);
//         model.addAttribute("user1", user1);
//         return "account_info_admin";
//     }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//     @GetMapping("/account_delete")
//     public String accountdelete(@RequestParam Long userId, Authentication authentication,Model model) {
//         // Do something to get account info
//         User user1 = userServices.getUserById(userId);
//         model.addAttribute("user", user1);
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         User user2= userServices.getUserByUserName(userDetails.getUsername());
//         ///
//         List<Course> courseEnroll=enrollmentServices.getCoursesEnrolledByUser(user1);
//         List<Course> onwner=courseServices.findCourseByUserId(user1);
//         ///
//         model.addAttribute("courseenroll", courseEnroll);
//         model.addAttribute("courOwner", onwner);
//         model.addAttribute("user", user2);
//         model.addAttribute("user1", user1);
//         return "account_delete";
//     }
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//     @PostMapping("/account_delete2")
//     public String accountdelete2(@RequestParam Long userId, Authentication authentication,Model model) {
//         // Do something to get account info
//         User user1 = userServices.getUserById(userId);
//         model.addAttribute("user", user1);
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         User user2= userServices.getUserByUserName(userDetails.getUsername());
//         ///
//         List<Course> courseEnroll=enrollmentServices.getCoursesEnrolledByUser(user1);
//         List<Course> onwner=courseServices.findCourseByUserId(user1);
//         List<CTHoaDon> cthds=cthdRepository.findAll();
//         List<Cart> carts=cartRepository.findAll();
//         ///
//         for (Course course : courseEnroll) {
//             List<Enrollment> enrollments=enrollmentServices.getEnrollmentsByCourse(course);
//             for (Enrollment enrollment : enrollments) {
//                 enrollmentServices.deleteEnrollment(enrollment);
//             }
            
//         for (CTHoaDon ctHoaDon : cthds) {
//             if(ctHoaDon.getCourse().getCourseID()==course.getCourseID()){
//                 cthdRepository.delete(ctHoaDon);
//             }
//         }
//         for(Cart cart:carts){
//             if(cart.getCourse().getCourseID()==course.getCourseID()){
//                 cartRepository.delete(cart);
//             }
//         }
            
//             courseServices.deleteCourse(course.getCourseID());
//         }
//         // List<Chatlog> chatlogs=chatlogRepository.findAll();
//         // for (Chatlog chatlog : chatlogs) {
//         //     if(chatlog.getCourseBuyer().getUserId()==user1.getUserId()){
//         //         chatlogRepository.delete(chatlog);
//         //     }
//         //     else if(chatlog.getCourseOwner().getUserId()==user1.getUserId()){
//         //         chatlogRepository.delete(chatlog);
//         //     }
//         // }
//         List<HoaDon> hoadons=hoaDonRepository.findAll();
//         for (HoaDon hoaDon : hoadons) {
//             if(hoaDon.getUser().getUserId()==user1.getUserId()){
//                 hoaDonRepository.delete(hoaDon);
//             }
//         }
//         Role roleteach=roleRepository.findByRoleName("ROLE_TEACHER");
//         Role rolestu=roleRepository.findByRoleName("ROLE_STUDENT");
//         // rolestu.removeUserById(userId);
//         // roleteach.removeUserById(userId);
//         roleRepository.save(rolestu);
//         roleRepository.save(roleteach);
//         userServices.deleteUser(userId);
//         ///
//         List<User>usr=userServices.getAllUsers();
//         usr.remove(0);
//         List<UserDTO>userDTO=new ArrayList<>();
//         ZoneId zoneId2 = ZoneId.of("Asia/Ho_Chi_Minh");  
//         for(int i=0;i<usr.size();i++){
//             LocalDate local=null;
//             local=LocalDate.ofInstant(usr.get(i).getModifiedDate(),zoneId2);
//                 LocalDate currentDate = LocalDate.now(); 
//                 long daysBetween = ChronoUnit.DAYS.between(local, currentDate);
//                 UserDTO usrDTO=new UserDTO();
//                 usrDTO.setAvatar(usr.get(i).getAvatar());
//                 usrDTO.setFullName(usr.get(i).getFullName());
//                 usrDTO.setEmail(usr.get(i).getEmail());
//                 usrDTO.setCreatedDate(usr.get(i).getCreatedDate());
//                 usrDTO.setModifiedDate(usr.get(i).getModifiedDate());
//                 usrDTO.setUserId(usr.get(i).getUserId());
//                 usrDTO.setOffLine(daysBetween);
//                 usrDTO.setMota(usr.get(i).getMota());
//                 userDTO.add(usrDTO);
             
//         }
//         ////
//         model.addAttribute("user", user2);
//         model.addAttribute("flag", 4);
//         model.addAttribute("users", userDTO);
//         return "admin";
    
//     }

//     //Chưa xong
//     @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
//     @PostMapping("/account_info1")
//     public ModelAndView editAccount(@RequestParam Long userId,@RequestParam String passOld, @RequestParam String passNew, @RequestParam String passNewConfirm, Authentication authentication, Model model){
       
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         User user = userServices.getUserByUserName(userDetails.getUsername());
//          //Cái này của Nam
//         if(user.getUserId()!=userId){
//             user=userServices.getUserById(userId);
//         }
//         // Kiểm tra mật khẩu hiện tại
//         if (passwordEncoder.matches(passOld, user.getPassword())) {
//             // Mật khẩu hiện tại hợp lệ, thực hiện thay đổi mật khẩu
//             if(passNew.equals(passNewConfirm)){
//                 String encryptedPassword = passwordEncoder.encode(passNew);
//                 user.setPassword(encryptedPassword);
//                 userServices.saveUser(user);
//                 String text = "Password changed successfully!";
//                 return new ModelAndView("redirect:/account_info?successMessage="+text);
//             } else {
//                 String text = "New password and confirmation password do not match!";
//                 return new ModelAndView("redirect:/account_info?errorMessage="+text);
//             }
//         } else {
//             String text = "Old password is incorrect!";
//             return new ModelAndView("redirect:/account_info?errorMessage="+text);
//         }
//     }

//     //Chưa xong
//     @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_STUDENT')")
//     @PostMapping("/account_info2")
//     public ModelAndView editAccountInfo(@RequestParam Long userId,@RequestParam String fullName, @RequestParam String email, @RequestParam(name = "avatarFile", required = false) MultipartFile avatarFile, @RequestParam(required = false, defaultValue = "") String mota, Authentication authentication, Model model) {
//         CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//         User user = userServices.getUserByUserName(userDetails.getUsername());
//         //Cái này của Nam
//         if(user.getUserId()!=userId){
//             user=userServices.getUserById(userId);
//         }
//         System.out.println("email: " + email);
//         System.out.println("mota: " + mota);
//         if (user != null) {
//             // Cập nhật thông tin tài khoản
//             if(!fullName.equals(""))
//                 user.setFullName(fullName);
//             if(!mota.equals(""))
//                 user.setMota(mota);
//             if(!email.equals("")){
//                 if(!user.getEmail().equals(email)){
//                     User user1 = userServices.getUserByUserName(email);
//                     if(user1 == null){
//                         String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//                         Pattern pattern = Pattern.compile(EMAIL_REGEX);
//                         Matcher matcher = pattern.matcher(email);
//                         boolean check =  matcher.matches();
//                         if(check)
//                             user.setEmail(email);
//                         else
//                             return new ModelAndView("redirect:/account_info?errorMessage=Invalid email");
//                     } else 
//                         return new ModelAndView("redirect:/account_info?errorMessage=Email has been used!");
//                 }
//             }
//             // Xử lý ảnh đại diện nếu có
//             if (avatarFile != null && !avatarFile.isEmpty()) {
//                 // Xử lý và lưu ảnh đại diện
//                 File avatar = fileServices.addFile(avatarFile);
//                 if (avatar != null) {
//                     user.setAvatar(avatar.getFileLink());
//                 }
//             } else
//                 System.out.println("file rong" + avatarFile);

//             // Lưu thông tin tài khoản đã cập nhật
//             userServices.saveUser(user);

//             String text = "Update Account Successfully!";
//             return new ModelAndView("redirect:/account_info?successMessage="+text);
//         }

//             String text = "User not found!";
//             return new ModelAndView("redirect:/account_info?errorMessage="+text);
//     }


// }

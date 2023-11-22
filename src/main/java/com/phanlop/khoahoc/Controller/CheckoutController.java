package com.phanlop.khoahoc.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phanlop.khoahoc.Config.CustomUserDetails;
import com.phanlop.khoahoc.DTO.UserDTO;
import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.CTHoaDon;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.HoaDon;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.EnrollmentRepository;
import com.phanlop.khoahoc.Service.CTHDServices;
import com.phanlop.khoahoc.Service.UserServices;
import com.phanlop.khoahoc.Service.VNPayServices;
import com.phanlop.khoahoc.Utils.ObjectMapperUtils;

import org.springframework.ui.Model;

import com.phanlop.khoahoc.Entity.ChiTraGV;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.phanlop.khoahoc.Service.CartServices;
import com.phanlop.khoahoc.Service.CourseServices;
import com.phanlop.khoahoc.Service.EmailServices;
import com.phanlop.khoahoc.Service.HoaDonServices;
import com.phanlop.khoahoc.Service.ChiTraServices;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final UserServices userServices;
    private final CartServices cartServices;
    private final HoaDonServices hoadonServices;
    private final CTHDServices cthdServices;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseServices courseServices;
    private final EmailServices emailServices;
    private final VNPayServices vnPayServices;
    private final ChiTraServices chiTraServices;
    
    //THANH TOAN VNPAY
    @GetMapping("/submitOrder/{sumCart}")
    public String submidOrder(@PathVariable String sumCart, HttpServletRequest request, HttpSession session){
        int sum = Integer.parseInt(sumCart);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayServices.createOrder(sum, baseUrl);
        System.out.println(vnpayUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, HttpSession session, Authentication authentication){
        int paymentStatus =vnPayServices.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        
        boolean check = (boolean) session.getAttribute("isChiTra");
        if(check)
            model.addAttribute("flag", 2);
        else
            model.addAttribute("flag", 1);


        if(paymentStatus == 1){
            if(!check){
                List<String> listId = (List<String>) session.getAttribute("listSpPayment");
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userServices.getUserByUserName(userDetails.getUsername());
            int sum = 500;
            //luu hoa don
            HoaDon hd = new HoaDon();
            hd.setTongTien(sum);
            hd.setUser(user);
            hoadonServices.saveHD(hd);
            //luu chi tiet hoa don
            List<Course> listCourseInCart = new ArrayList<>();
            int lenght = listId.size();
            for(int i=0; i<lenght; i++)
                listCourseInCart.add(courseServices.getCourseById(UUID.fromString(listId.get(i))));
            
            for(Course course : listCourseInCart){
                sum = sum + course.getGia();
                CTHoaDon cthd = new CTHoaDon();
                cthd.setHoadon(hd);
                cthd.setHoantien(0);
                cthd.setCourse(course);
                cthd.setGia(course.getGia());
                cthdServices.saveCTHD(cthd);
                hd.getListCTHD().add(cthd);
                hoadonServices.saveHD(hd);
                Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
                enrollmentId.setUserId(user.getUserId());
                enrollmentId.setCourseId(course.getCourseID());
                Enrollment enrollment = new Enrollment();
                enrollment.setId(enrollmentId);
                enrollment.setUser(user);
                enrollment.setAccessType(AccessType.ACCEPT);
                enrollment.setCourse(course);
                enrollmentRepository.save(enrollment);
                cartServices.deleteCart(course, user);
            }
            hd.setTongTien(sum);
            hoadonServices.saveHD(hd);
            //create enrollment
            //deletecart
                String title = "Xác nhận mua hàng từ F9";
                String body = "F9 - UNIVERSITY (chân thành cảm ơn bạn đã lựa chọn F9 là nơi học tập, bổ sung tri thức, nâng cao tầm hiểu biết)";
                boolean isSend = emailServices.sendOTPEmail(user.getEmail(), title, body);
                // if (isSend){
                //     return ResponseEntity.ok("success");
                // }
                // return ResponseEntity.badRequest().body("fail");
            } else {
                Long idChiTra = (Long) session.getAttribute("idChiTra");
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                User admin = userServices.getUserByUserName(userDetails.getUsername());
                ChiTraGV chitra = chiTraServices.findByID(idChiTra);
                chitra.setAdmin(admin);
                chitra.setState(1);
                chiTraServices.save(chitra);
                session.setAttribute("isChiTra", false);
                
            }
        }

        

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }

    // @PostMapping("/{sumCart}")
    // public ResponseEntity<String> checkout(Authentication authentication, @PathVariable String sumCart, @RequestBody List<String> selectedItems){
    //     CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    //     User user = userServices.getUserByUserName(userDetails.getUsername());
    //     int sum = Integer.parseInt(sumCart);
    //     //luu hoa don
    //     HoaDon hd = new HoaDon();
    //     hd.setTongTien(sum);
    //     hd.setUser(user);
    //     hoadonServices.saveHD(hd);
    //     //luu chi tiet hoa don
    //     List<Course> listCourseInCart = new ArrayList<>();
    //     int lenght = selectedItems.size();
    //     for(int i=0; i<lenght; i++)
    //         listCourseInCart.add(courseServices.getCourseById(UUID.fromString(selectedItems.get(i))));
    //     // for(String s : selectedItems){
    //     //     listCourseInCart.add(courseServices.getCourseById(UUID.fromString(e)));
    //     // }
    //     for(Course course : listCourseInCart){
    //         CTHoaDon cthd = new CTHoaDon();
    //         cthd.setHoadon(hd);
    //         cthd.setHoantien(0);
    //         cthd.setCourse(course);
    //         cthd.setGia(course.getGia());
    //         cthdServices.saveCTHD(cthd);
    //         hd.getListCTHD().add(cthd);
    //         hoadonServices.saveHD(hd);
    //         Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
	// 		enrollmentId.setUserId(user.getUserId());
	// 		enrollmentId.setCourseId(course.getCourseID());
	// 		Enrollment enrollment = new Enrollment();
	// 		enrollment.setId(enrollmentId);
	// 		enrollment.setUser(user);
	// 		enrollment.setAccessType(AccessType.ACCEPT);
	// 		enrollment.setCourse(course);
	// 		enrollmentRepository.save(enrollment);
    //         cartServices.deleteCart(course, user);
    //     }

    //     //create enrollment
    //     //deletecart
    //         String title = "Xác nhận mua hàng từ F9";
    //         String body = "F9 - UNIVERSITY (chân thành cảm ơn bạn đã lựa chọn F9 là nơi học tập, bổ sung tri thức, nâng cao tầm hiểu biết)";
    //         boolean isSend = emailServices.sendOTPEmail(user.getEmail(), title, body);
    //         if (isSend){
    //             return ResponseEntity.ok("success");
    //         }
    //         return ResponseEntity.badRequest().body("fail");
    // }

    // @GetMapping("/teacher/hocvien/{courseId}")
    // public List<UserDTO> getListUserEnroll(@PathVariable UUID courseId){
    //     Course course = courseServices.getCourseById(courseId);
    //     List<Enrollment> list = course.getEnrollments();
    //     List<User> listUser = new ArrayList<>();
    //     for(Enrollment e : list) {
    //         listUser.add(e.getUser());
    //     }
    //     return ObjectMapperUtils.mapAll(listUser, UserDTO.class);
    // }
}

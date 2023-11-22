package com.phanlop.khoahoc.Service.implementation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.CTHoaDon;
import com.phanlop.khoahoc.Entity.ChiTraGV;
import com.phanlop.khoahoc.Entity.HoaDon;
import com.phanlop.khoahoc.Entity.Notify;
import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.ChiTraRepository;
import com.phanlop.khoahoc.Repository.HoaDonRepository;
import com.phanlop.khoahoc.Repository.NotifyRepository;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import com.phanlop.khoahoc.Service.MonthlyTaskService;

@Service
public class MonthlyTaskServiceImpl implements MonthlyTaskService{
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HoaDonRepository hdRepo;

    @Autowired
    private ChiTraRepository chiTraRepo;

    @Autowired
    private NotifyRepository notiRepo;
    @Override
    public void processMonthlyTask() {
        System.out.println("Chức năng được thực hiện vào cuối mỗi tháng.");

        Role role = roleRepo.findByRoleName("ROLE_TEACHER");
        List<User> listUser = userRepo.findUserByRole(role);
        int tong = 0;
        List<HoaDon> dshd = hdRepo.findAll();
        LocalDate today=LocalDate.now();
        ZoneId zoneId2 = ZoneId.of("Asia/Ho_Chi_Minh");
        int thang=today.getMonthValue();
        for (int i=0;i<dshd.size();i++) {
            LocalDate localDate=LocalDate.ofInstant(dshd.get(i).getNgayMua(), zoneId2);
            int thanglocal=localDate.getMonthValue();
            if(thanglocal!=thang){
                dshd.remove(i);
            }
        }
        
        // CÒN LỌC HÓA ĐƠN TRONG THÁNG HIỆN TẠI: Chưa làm

        for(User user : listUser){
            System.out.println("user: " + user.getFullName());
            for(HoaDon hd : dshd){
                List<CTHoaDon> ListCthd = hd.getListCTHD();
                for(CTHoaDon cthd : ListCthd){
                    if(cthd.getCourse().getCourseOwner().getEmail().equals(user.getEmail())){
                        tong = tong + cthd.getGia();
                        System.out.println(cthd.getGia());
                    }
                }
            }

                ChiTraGV chitra = new ChiTraGV();
                chitra.setTeacher(user);
                chitra.setSoTienChuyen(tong*85/100);
                chitra.setTongDoanhThu(tong);

                //THÁNG NÀY PHẢI ĐÚNG LÀ THÁNG HIỆN TẠI: Chưa làm
                chitra.setThang(thang);

                chitra.setTyLeShare(85);
                chitra.setState(0);
                chiTraRepo.save(chitra);
                tong = 0;
        }

        //TẠO THÔNG BÁO TỚI ADMIN
        Notify notify = new Notify();
        Role role1 = roleRepo.findByRoleName("ROLE_ADMIN");
        List<User> listUser1 = userRepo.findUserByRole(role1);
        notify.setUser(listUser1.get(0));
        notify.setNotiTitle("Đã tới tháng thanh toán cho teacher");
        notify.setNotiContent("Bạn hãy vào quản lý chi trả và tiến hành thanh toán cho teacher");
        notiRepo.save(notify);
    }
    
}

package com.phanlop.khoahoc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.CTHoaDon;
import com.phanlop.khoahoc.Entity.Cart;
import com.phanlop.khoahoc.Entity.Chatlog;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.DanhGia;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.HoaDon;
import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Repository.CTHDRepository;
import com.phanlop.khoahoc.Repository.CartRepository;
import com.phanlop.khoahoc.Repository.ChatlogRepository;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.DanhGiaRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Repository.EnrollmentRepository;
import com.phanlop.khoahoc.Repository.HoaDonRepository;
import com.phanlop.khoahoc.Repository.LessonRepository;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Repository.UserRepository;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing // Cái này để lưu ngày createDate với modifiedDate - đừng quan tâm
@AllArgsConstructor
public class KhoahocApplication implements CommandLineRunner{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
    private final DanhGiaRepository danhgiaRepository;
	private final DepartmentRepository departmentRepository;
	private final LessonRepository lessonRepository;
	private final RoleRepository roleRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final CartRepository cartRepository;
	private final HoaDonRepository hoaDonRepository;
	private final CTHDRepository cthdRepository;
	private final ChatlogRepository chatlogRepository;

	public static void main(String[] args) {
		SpringApplication.run(KhoahocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeDB();
	}

	@Transactional
	public void initializeDB(){
		// Tạo các role
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("ROLE_ADMIN");

		Role role2 = new Role();
		role2.setRoleId(2);
		role2.setRoleName("ROLE_STUDENT");

		Role role3 = new Role();
		role3.setRoleId(3);
		role3.setRoleName("ROLE_TEACHER");
		roleRepository.save(role);
		roleRepository.save(role2);
		roleRepository.save(role3);
		
		// Tạo userAdmin
		User adminUser = new User();
		adminUser.setUserId(1L);
		adminUser.setFullName("Mai Xuan Hieu");
		adminUser.setEmail("hieuxuan034@gmail.com");
		adminUser.setPassword(passwordEncoder.encode("123456"));
		adminUser.getListRoles().add(role);
		role.getListUsers().add(adminUser);
		userRepository.save(adminUser);
		roleRepository.save(role);

		User guest = new User();
		guest.setUserId(2L);
		guest.setFullName("Guest");
		guest.setEmail("hieum7239@gmail.com");
		guest.setPassword(passwordEncoder.encode("123456"));
		guest.getListRoles().add(role2);
		role2.getListUsers().add(guest);
		userRepository.save(guest);
		roleRepository.save(role2);
                
        User student = new User();
		student.setUserId(3L);
		student.setFullName("Hieu Xuan");
		student.setEmail("hieu@gmail.com");
		student.setPassword(passwordEncoder.encode("123456"));
		student.getListRoles().add(role2);
		role2.getListUsers().add(student);
		userRepository.save(student);
		roleRepository.save(role2);
                
                
        User student1 = new User();
		student1.setUserId(4L);
		student1.setFullName("Hieu Dep Trai");
		student1.setEmail("hieu1@gmail.com");
		student1.setPassword(passwordEncoder.encode("123456"));
		student1.getListRoles().add(role2);
		role2.getListUsers().add(student1);
		userRepository.save(student1);
		roleRepository.save(role2);

		User student2 = new User();
		student2.setUserId(6L);
		student2.setFullName("Cong Minh");
		student2.setEmail("mxuan6699@gmail.com");
		student2.setPassword(passwordEncoder.encode("123456"));
		student2.getListRoles().add(role2);
		role2.getListUsers().add(student2);
		userRepository.save(student2);
		roleRepository.save(role2);

		User student3 = new User();
		student3.setUserId(7L);
		student3.setFullName("Ngoc Thanh");
		student3.setEmail("ngocthanh@gmail.com");
		student3.setPassword(passwordEncoder.encode("123456"));
		student3.getListRoles().add(role2);
		role2.getListUsers().add(student3);
		userRepository.save(student3);
		roleRepository.save(role2);

                
                
        User giaovien = new User();
		giaovien.setUserId(5L);
		giaovien.setFullName("Teacher Hieu");
		giaovien.setEmail("giaovien@gmail.com");
		giaovien.setPassword(passwordEncoder.encode("123456"));
		giaovien.getListRoles().add(role3);
		// giaovien.setMota("Das Thema HTTP Session Replication ist im Kubernetes Umfeld wichtig, da Kubernetes Pods flüchtig sind. Das bedeutet, dass sie jederzeit heruntergefahren werden können, z.B. bei einem Rolling Update. Dieses Problem wird durch die Persistenz der HTTP Session bei Anwendungen gelöst, die einen State haben, also z.B. eine Benutzerinteraktion über ein UI erlauben.");
		role3.getListUsers().add(giaovien);
		userRepository.save(giaovien);
		roleRepository.save(role3);
                
		User giaovien1 = new User();
		giaovien1.setUserId(8L);
		giaovien1.setFullName("Teacher Minh");
		giaovien1.setEmail("giaovien1@gmail.com");
		giaovien1.setPassword(passwordEncoder.encode("123456"));
		giaovien1.getListRoles().add(role3);
		role3.getListUsers().add(giaovien1);
		userRepository.save(giaovien1);
		roleRepository.save(role3);

		Department cntt = new Department();
		cntt.setDepartmentId(1);
		cntt.setDepartmentName("Công nghệ thông tin");
		departmentRepository.save(cntt);

		Department nna = new Department();
		nna.setDepartmentId(2);
		nna.setDepartmentName("Ngôn ngữ Anh");
		departmentRepository.save(nna);

		Department dtvt = new Department();
		dtvt.setDepartmentId(3);
		dtvt.setDepartmentName("Điện tử viễn thông");
		departmentRepository.save(dtvt);

		Department toan = new Department();
		toan.setDepartmentId(4);
		toan.setDepartmentName("Toán ứng dụng");
		departmentRepository.save(toan);

		for (int i = 0 ;i<5;i++){
			Course cslt = new Course();
			cslt.setCourseAvt("https://files.fullstack.edu.vn/f8-prod/courses/7.png");
			cslt.setCourseOwner(giaovien);
			cslt.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			cslt.setCourseName("Cơ sở lập trình thứ "+i);
			cslt.setDepartment(cntt);
            cslt.setStateGuiAdmin(1);
            cslt.setState(1);
			cslt.setGia(499000);
			courseRepository.save(cslt);
                        
                        DanhGia danhgia = new DanhGia();
                        danhgia.setUser(guest);
                        danhgia.setCourse(cslt);
                        danhgia.setContentDanhgia("Khóa học khá bổ ích. Tuyệt vời!");
                        danhgia.setSao(5);
                        danhgia.setTenUser(guest.getFullName());
                        danhgiaRepository.save(danhgia);
                        cslt.getDanhgias().add(danhgia);
                        courseRepository.save(cslt);
                        
                        DanhGia danhgia5 = new DanhGia();
                        danhgia5.setUser(student);
                        danhgia5.setCourse(cslt);
                        danhgia5.setContentDanhgia("Khóa học khá bổ ích. Tuyệt vời!");
                        danhgia5.setSao(4);
                        danhgia5.setTenUser(student.getFullName());
                        danhgiaRepository.save(danhgia5);
                        cslt.getDanhgias().add(danhgia5);
                        courseRepository.save(cslt);
                        
                        DanhGia danhgia6 = new DanhGia();
                        danhgia6.setUser(student1);
                        danhgia6.setCourse(cslt);
                        danhgia6.setContentDanhgia("Khóa học quá tệ. Có 1 vài chỗ tôi xem không hiểu gì cả.");
                        danhgia6.setSao(3);
                        danhgia6.setTenUser(student1.getFullName());
                        danhgiaRepository.save(danhgia6);
                        cslt.getDanhgias().add(danhgia6);
                        courseRepository.save(cslt);
                        
			Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
			enrollmentId.setUserId(guest.getUserId());
			enrollmentId.setCourseId(cslt.getCourseID());
			Enrollment enrollment = new Enrollment();
			enrollment.setId(enrollmentId);
			enrollment.setUser(guest);
			enrollment.setAccessType(AccessType.ACCEPT);
			enrollment.setCourse(cslt);
			enrollmentRepository.save(enrollment);

			Enrollment.EnrollmentId enrollmentId1 = new Enrollment.EnrollmentId();
			enrollmentId1.setUserId(student.getUserId());
			enrollmentId1.setCourseId(cslt.getCourseID());
			Enrollment enrollment1 = new Enrollment();
			enrollment1.setId(enrollmentId1);
			enrollment1.setUser(student);
			enrollment1.setAccessType(AccessType.ACCEPT);
			enrollment1.setCourse(cslt);
			enrollmentRepository.save(enrollment1);

			Enrollment.EnrollmentId enrollmentId15 = new Enrollment.EnrollmentId();
			enrollmentId15.setUserId(student1.getUserId());
			enrollmentId15.setCourseId(cslt.getCourseID());
			Enrollment enrollment15 = new Enrollment();
			enrollment15.setId(enrollmentId15);
			enrollment15.setUser(student1);
			enrollment15.setAccessType(AccessType.ACCEPT);
			enrollment15.setCourse(cslt);
			enrollmentRepository.save(enrollment15);

			//create data cart
			Cart cart1 = new Cart();
			cart1.setCourse(cslt);
			cart1.setUser(student2);
			cartRepository.save(cart1);

			Lesson lesson1 = new Lesson();
			lesson1.setLessonTitle("1. Khái niệm, kỹ thuật cần biết");
			lesson1.setLessonSort(1);
			lesson1.setLessonVideo("https://www.youtube.com/embed/zoELAirXMJY");
			lesson1.setLessonContent("Mô hình Client - Server là mô hình được sử dụng để triển khai các trang web hiện nay. Toàn bộ các trang web mà bạn có thể truy cập đều đang sử dụng mô hình này. Hãy cùng tìm hiểu tổng quan về mô hình Client - Server trước khi bắt đầu vào các khóa học về lập trình web bạn nhé.");
			lesson1.setCourse(cslt);
			lesson1.setXemTruoc(1);
			lessonRepository.save(lesson1);

			Lesson lesson2 = new Lesson();
			lesson2.setLessonTitle("2. Domain là gì");
			lesson2.setLessonSort(2);
			lesson2.setLessonVideo("https://www.youtube.com/embed/M62l1xA5Eu8");
			lesson2.setLessonContent("Domain hay còn gọi là \"Tên miền\", đây là kiến thức các bạn có thể bắt đầu tìm hiểu trước khi đi vào các bài học chuyên sâu (phải làm quen với code).");
			lesson2.setCourse(cslt);
			lessonRepository.save(lesson2);

			Lesson lesson3 = new Lesson();
			lesson3.setLessonTitle("3. Phương hướng học lập trình");
			lesson3.setLessonSort(3);
			lesson3.setLessonVideo("https://www.youtube.com/embed/DpvYHLUiZpc");
			lesson3.setLessonContent("""
                    Bạn cần có mục tiêu cho việc làm của mình, học lập trình cũng vậy, từ đó bạn sẽ lên được lộ trình học lập trình và phương pháp học lập trình phù hợp với bản thân hơn. Nếu không có mục tiêu thì bạn sẽ không thể tìm ra được lộ trình học lập trình đâu nhé.
                    
                    Tiếp theo là sự chủ động, nếu như ngày học phổ thông thì hầu hết chúng ta có tư tưởng là "phải" học. Học lập trình nó khác, phần lớn các bạn đều mong muốn học lập trình để đi làm và kiếm nhiều tiền nên mỗi lúc có thời gian hãy tự chủ động học và suy nghĩ về nó nhé. Bây giờ lớn rồi! Đừng để ai phải nhắc cho tương lai của chính mình nhé!.\
                    """);
			lesson3.setCourse(cslt);
			lessonRepository.save(lesson3);

			Course laptrinhc1 = new Course();
			laptrinhc1.setCourseOwner(giaovien1);
			laptrinhc1.setCourseAvt("https://files.fullstack.edu.vn/f8-prod/courses/21/63e1bcbaed1dd.png");
			laptrinhc1.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			laptrinhc1.setCourseName("Lập trình c cơ bản, nâng cao thứ "+ i);
			laptrinhc1.setDepartment(cntt);
                        laptrinhc1.setStateGuiAdmin(1);
                        laptrinhc1.setState(1);
						laptrinhc1.setGia(450000);
			courseRepository.save(laptrinhc1);

			Lesson lessonc11 = new Lesson();
			lessonc11.setLessonTitle("1. Tổng quan về khóa học Lập trình C++");
			lessonc11.setLessonSort(1);
			lessonc11.setLessonVideo("https://www.youtube.com/embed/WS05AU6YYm4");
			lessonc11.setLessonContent("""
                    Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.
                    
                    Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).
                    
                    Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp\
                    """);
			lessonc11.setCourse(laptrinhc1);
			lessonc11.setXemTruoc(1);
			lessonRepository.save(lessonc11);

			Lesson lessonc22 = new Lesson();
			lessonc22.setLessonTitle("2. Biến trong C++");
                        lessonc22.setLessonSort(2);
			lessonc22.setLessonVideo("https://www.youtube.com/embed/i3nJyEt42Y8");
			lessonc22.setLessonContent("""
                    Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.
                    
                    Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).
                    
                    Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp\
                    """);
			lessonc22.setCourse(laptrinhc1);
			lessonRepository.save(lessonc22);

			Lesson lessonc33 = new Lesson();
			lessonc33.setLessonTitle("3. Vòng lặp For trong C++");
			lessonc33.setLessonSort(3);
			lessonc33.setLessonVideo("https://www.youtube.com/embed/aL59MpOFMe0");
			lessonc33.setLessonContent("""
                    Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.
                    
                    Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).
                    
                    Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp\
                    """);
			lessonc33.setCourse(laptrinhc1);
			lessonRepository.save(lessonc33);

			Course laptrinhc = new Course();
			laptrinhc.setCourseOwner(giaovien);
			laptrinhc.setCourseAvt("https://files.fullstack.edu.vn/f8-prod/courses/21/63e1bcbaed1dd.png");
			laptrinhc.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			laptrinhc.setCourseName("Lập trình c cơ bản, nâng cao thứ "+ i);
			laptrinhc.setDepartment(cntt);
                        laptrinhc.setStateGuiAdmin(0);
                        laptrinhc.setState(0);
						laptrinhc.setGia(450000);
			courseRepository.save(laptrinhc);

                        // DanhGia danhgia1 = new DanhGia();
                        // danhgia1.setUser(student);
                        // danhgia1.setCourse(laptrinhc);
                        // danhgia1.setContentDanhgia("Khóa học khá bổ ích. Cảm ơn tác giả!");
                        // danhgia1.setSao(4);
                        // danhgia1.setTenUser(student.getFullName());
                        // danhgiaRepository.save(danhgia1);
                        // laptrinhc.getDanhgias().add(danhgia1);
                        // courseRepository.save(laptrinhc);

						// DanhGia danhgia11 = new DanhGia();
                        // danhgia11.setUser(student1);
                        // danhgia11.setCourse(laptrinhc);
                        // danhgia11.setContentDanhgia("Khóa học khá bổ ích. Cảm ơn tác giả!");
                        // danhgia11.setSao(4);
                        // danhgia11.setTenUser(student1.getFullName());
                        // danhgiaRepository.save(danhgia11);
                        // laptrinhc.getDanhgias().add(danhgia11);
                        // courseRepository.save(laptrinhc);
                        
                        
			// Enrollment.EnrollmentId enrollmentId2 = new Enrollment.EnrollmentId();
			// enrollmentId2.setUserId(guest.getUserId());
			// enrollmentId2.setCourseId(laptrinhc.getCourseID());
			// Enrollment enrollment2 = new Enrollment();
			// enrollment2.setId(enrollmentId2);
			// enrollment2.setUser(guest);
			// enrollment2.setAccessType(AccessType.ACCEPT);
			// enrollment2.setCourse(laptrinhc);
			// enrollmentRepository.save(enrollment2);

			// Enrollment.EnrollmentId enrollmentId7 = new Enrollment.EnrollmentId();
			// enrollmentId7.setUserId(student1.getUserId());
			// enrollmentId7.setCourseId(laptrinhc.getCourseID());
			// Enrollment enrollment7 = new Enrollment();
			// enrollment7.setId(enrollmentId7);
			// enrollment7.setUser(student1);
			// enrollment7.setAccessType(AccessType.ACCEPT);
			// enrollment7.setCourse(laptrinhc);
			// enrollmentRepository.save(enrollment7);

			// Enrollment.EnrollmentId enrollmentId77 = new Enrollment.EnrollmentId();
			// enrollmentId77.setUserId(student.getUserId());
			// enrollmentId77.setCourseId(laptrinhc.getCourseID());
			// Enrollment enrollment77 = new Enrollment();
			// enrollment77.setId(enrollmentId77);
			// enrollment77.setUser(student);
			// enrollment77.setAccessType(AccessType.ACCEPT);
			// enrollment77.setCourse(laptrinhc);
			// enrollmentRepository.save(enrollment77);

			Lesson lessonc1 = new Lesson();
			lessonc1.setLessonTitle("1. Tổng quan về khóa học Lập trình C++");
			lessonc1.setLessonSort(1);
			lessonc1.setLessonVideo("https://www.youtube.com/embed/WS05AU6YYm4");
			lessonc1.setLessonContent("""
                    Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.
                    
                    Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).
                    
                    Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp\
                    """);
			lessonc1.setCourse(laptrinhc);
			lessonc1.setXemTruoc(1);
			lessonRepository.save(lessonc1);

			Lesson lessonc2 = new Lesson();
			lessonc2.setLessonTitle("2. Biến trong C++");
                        lessonc2.setLessonSort(2);
			lessonc2.setLessonVideo("https://www.youtube.com/embed/i3nJyEt42Y8");
			lessonc2.setLessonContent("""
                    Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.
                    
                    Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).
                    
                    Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp\
                    """);
			lessonc2.setCourse(laptrinhc);
			lessonRepository.save(lessonc2);

			Lesson lessonc3 = new Lesson();
			lessonc3.setLessonTitle("3. Vòng lặp For trong C++");
			lessonc3.setLessonSort(3);
			lessonc3.setLessonVideo("https://www.youtube.com/embed/aL59MpOFMe0");
			lessonc3.setLessonContent("""
                    Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.
                    
                    Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).
                    
                    Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp\
                    """);
			lessonc3.setCourse(laptrinhc);
			lessonRepository.save(lessonc3);

			Course winform = new Course();
			winform.setCourseAvt("https://static.skillshare.com/uploads/discussion/tmp/b8ba300b.png");
			winform.setCourseOwner(giaovien);
			winform.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			winform.setCourseName("Lập trình winform thứ"+i);
			winform.setDepartment(cntt);
                        winform.setStateGuiAdmin(1);
                        winform.setState(1);
						winform.setGia(500000);
			courseRepository.save(winform);
                        
                        DanhGia danhgia2 = new DanhGia();
                        danhgia2.setUser(student3);
                        danhgia2.setCourse(winform);
                        danhgia2.setContentDanhgia("Khóa học khá bổ ích. Tuyệt vời!");
                        danhgia2.setSao(4);
                        danhgia2.setTenUser(student3.getFullName());
                        danhgiaRepository.save(danhgia2);
                        winform.getDanhgias().add(danhgia2);
                        courseRepository.save(winform);



			Enrollment.EnrollmentId enrollmentId3 = new Enrollment.EnrollmentId();
			enrollmentId3.setUserId(guest.getUserId());
			enrollmentId3.setCourseId(winform.getCourseID());
			Enrollment enrollment3 = new Enrollment();
			enrollment3.setId(enrollmentId3);
			enrollment3.setUser(guest);
			enrollment3.setAccessType(AccessType.ACCEPT);
			enrollment3.setCourse(winform);
			enrollmentRepository.save(enrollment3);

			Enrollment.EnrollmentId enrollmentId33 = new Enrollment.EnrollmentId();
			enrollmentId33.setUserId(student3.getUserId());
			enrollmentId33.setCourseId(winform.getCourseID());
			Enrollment enrollment33 = new Enrollment();
			enrollment33.setId(enrollmentId33);
			enrollment33.setUser(student3);
			enrollment33.setAccessType(AccessType.ACCEPT);
			enrollment33.setCourse(winform);
			enrollmentRepository.save(enrollment33);

			Lesson winform1 = new Lesson();
			winform1.setLessonTitle("1. Tổng quan lập trình Winform");
			winform1.setLessonSort(1);
			winform1.setLessonVideo("https://www.youtube.com/embed/dtYVRWfGhzI");
			winform1.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform1.setCourse(winform);
			winform1.setXemTruoc(1);
			lessonRepository.save(winform1);

			Lesson winform2 = new Lesson();
			winform2.setLessonTitle("2. Panel và FlowLayoutPanel");
			winform2.setLessonSort(2);
			winform2.setLessonVideo("https://www.youtube.com/embed/Cljvl3ur1wg");
			winform2.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform2.setCourse(winform);
			lessonRepository.save(winform2);

			Lesson winform3 = new Lesson();
			winform3.setLessonTitle("3. Textbox trong winform");
			winform3.setLessonSort(3);
			winform3.setLessonVideo("https://www.youtube.com/embed/MsSds2bDqKA");
			winform3.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform3.setCourse(winform);
			lessonRepository.save(winform3);

			Course dientu = new Course();
			dientu.setCourseAvt("https://codelearn.io/Upload/Blog/nganh-dien-tu-vien-thong-hoc-gi-63729858518.6825.jpg");
			dientu.setCourseOwner(giaovien);
			dientu.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			dientu.setCourseName("Điện tử và điện tử số "+i);
			dientu.setDepartment(dtvt);
                        dientu.setStateGuiAdmin(1);
                        dientu.setState(1);
			courseRepository.save(dientu);
                        
                        DanhGia danhgia3 = new DanhGia();
                        danhgia3.setUser(student2);
                        danhgia3.setCourse(dientu);
                        danhgia3.setContentDanhgia("Khóa học khá bổ ích. Tuyệt vời! Thanks,you so much!");
                        danhgia3.setSao(5);
                        danhgia3.setTenUser(student2.getFullName());
                        danhgiaRepository.save(danhgia3);
                        dientu.getDanhgias().add(danhgia3);
                        courseRepository.save(dientu);

						DanhGia danhgia8 = new DanhGia();
                        danhgia8.setUser(student3);
                        danhgia8.setCourse(dientu);
                        danhgia8.setContentDanhgia("Khóa học khá bổ ích. Tuy nhiên có 1 số chỗ cần cải thiện.");
                        danhgia8.setSao(4);
                        danhgia8.setTenUser(student3.getFullName());
                        danhgiaRepository.save(danhgia8);
                        dientu.getDanhgias().add(danhgia8);
                        courseRepository.save(dientu);

						DanhGia danhgia9 = new DanhGia();
                        danhgia9.setUser(student);
                        danhgia9.setCourse(dientu);
                        danhgia9.setContentDanhgia("Khóa học khá hay. Tôi thích cách bạn truyền đạt vấn đề.");
                        danhgia9.setSao(4);
                        danhgia9.setTenUser(student.getFullName());
                        danhgiaRepository.save(danhgia9);
                        dientu.getDanhgias().add(danhgia9);
                        courseRepository.save(dientu);

						DanhGia danhgia10 = new DanhGia();
                        danhgia10.setUser(guest);
                        danhgia10.setCourse(dientu);
                        danhgia10.setContentDanhgia("Khóa học sâu sắc và tỉ mỉ. Tôi thích cách bạn truyền đạt vấn đề.");
                        danhgia10.setSao(5);
                        danhgia10.setTenUser(guest.getFullName());
                        danhgiaRepository.save(danhgia10);
                        dientu.getDanhgias().add(danhgia10);
                        courseRepository.save(dientu);

			Enrollment.EnrollmentId enrollmentId34 = new Enrollment.EnrollmentId();
			enrollmentId34.setUserId(student3.getUserId());
			enrollmentId34.setCourseId(dientu.getCourseID());
			Enrollment enrollment34 = new Enrollment();
			enrollment34.setId(enrollmentId34);
			enrollment34.setUser(student3);
			enrollment34.setAccessType(AccessType.ACCEPT);
			enrollment34.setCourse(dientu);
			enrollmentRepository.save(enrollment34);

			Enrollment.EnrollmentId enrollmentId35 = new Enrollment.EnrollmentId();
			enrollmentId35.setUserId(student.getUserId());
			enrollmentId35.setCourseId(dientu.getCourseID());
			Enrollment enrollment35 = new Enrollment();
			enrollment35.setId(enrollmentId35);
			enrollment35.setUser(student);
			enrollment35.setAccessType(AccessType.ACCEPT);
			enrollment35.setCourse(dientu);
			enrollmentRepository.save(enrollment35);

			Enrollment.EnrollmentId enrollmentId36 = new Enrollment.EnrollmentId();
			enrollmentId36.setUserId(student2.getUserId());
			enrollmentId36.setCourseId(dientu.getCourseID());
			Enrollment enrollment36 = new Enrollment();
			enrollment36.setId(enrollmentId36);
			enrollment36.setUser(student2);
			enrollment36.setAccessType(AccessType.ACCEPT);
			enrollment36.setCourse(dientu);
			enrollmentRepository.save(enrollment36);

			Enrollment.EnrollmentId enrollmentId37 = new Enrollment.EnrollmentId();
			enrollmentId37.setUserId(guest.getUserId());
			enrollmentId37.setCourseId(dientu.getCourseID());
			Enrollment enrollment37 = new Enrollment();
			enrollment37.setId(enrollmentId37);
			enrollment37.setUser(guest);
			enrollment37.setAccessType(AccessType.ACCEPT);
			enrollment37.setCourse(dientu);
			enrollmentRepository.save(enrollment37);

			Lesson dientu1 = new Lesson();
			dientu1.setLessonTitle("1. Tổng quan lập trình Winform");
			dientu1.setLessonSort(1);
			dientu1.setLessonVideo("https://www.youtube.com/embed/dtYVRWfGhzI");
			dientu1.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu1.setCourse(dientu);
			dientu1.setXemTruoc(1);
			lessonRepository.save(dientu1);

			Lesson dientu2 = new Lesson();
			dientu2.setLessonTitle("2. Panel và FlowLayoutPanel");
			dientu2.setLessonSort(2);
			dientu2.setLessonVideo("https://www.youtube.com/embed/Cljvl3ur1wg");
			dientu2.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu2.setCourse(dientu);
			lessonRepository.save(dientu2);

			Lesson dientu3 = new Lesson();
			dientu3.setLessonTitle("3. Textbox trong winform");
			dientu3.setLessonSort(3);
			dientu3.setLessonVideo("https://www.youtube.com/embed/MsSds2bDqKA");
			dientu3.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu3.setCourse(dientu);
			lessonRepository.save(dientu3);

			HoaDon hd = new HoaDon();
			hd.setUser(guest);
			hd.setTongTien(999000);
			hoaDonRepository.save(hd);

			CTHoaDon cthd = new CTHoaDon();
			cthd.setHoadon(hd);
			cthd.setCourse(cslt);
			cthd.setGia(499000);
			cthd.setHoantien(0);
			cthdRepository.save(cthd);

			// CTHoaDon cthd1 = new CTHoaDon();
			// cthd1.setHoadon(hd);
			// cthd1.setCourse(laptrinhc);
			// cthd1.setGia(500000);
			// cthd1.setHoantien(0);
			// cthdRepository.save(cthd1);

			CTHoaDon cthd2 = new CTHoaDon();
			cthd2.setHoadon(hd);
			cthd2.setCourse(winform);
			cthd2.setGia(500000);
			cthd2.setHoantien(0);
			cthdRepository.save(cthd2);

			CTHoaDon cthd3 = new CTHoaDon();
			cthd3.setHoadon(hd);
			cthd3.setCourse(dientu);
			cthd3.setGia(0);
			cthd3.setHoantien(0);
			cthdRepository.save(cthd3);
			List<CTHoaDon> list = new ArrayList<>();
			list.add(cthd);
			// list.add(cthd1);
			list.add(cthd2);
			list.add(cthd3);
			hd.setListCTHD(list);
			hoaDonRepository.save(hd);


			HoaDon hd1 = new HoaDon();
			hd1.setUser(student);
			hd1.setTongTien(499000);
			hoaDonRepository.save(hd1);

			CTHoaDon cthd4 = new CTHoaDon();
			cthd4.setHoadon(hd1);
			cthd4.setCourse(cslt);
			cthd4.setGia(499000);
			cthd4.setHoantien(0);
			cthdRepository.save(cthd4);

			// CTHoaDon cthd5 = new CTHoaDon();
			// cthd5.setHoadon(hd1);
			// cthd5.setCourse(laptrinhc);
			// cthd5.setGia(500000);
			// cthd5.setHoantien(0);
			// cthdRepository.save(cthd5);

			CTHoaDon cthd6 = new CTHoaDon();
			cthd6.setHoadon(hd1);
			cthd6.setCourse(dientu);
			cthd6.setGia(0);
			cthd6.setHoantien(0);
			cthdRepository.save(cthd6);
			List<CTHoaDon> list1 = new ArrayList<>();
			list1.add(cthd4);
			// list1.add(cthd5);
			list1.add(cthd6);
			hd1.setListCTHD(list1);
			hoaDonRepository.save(hd1);


			HoaDon hd2 = new HoaDon();
			hd2.setUser(student1);
			hd2.setTongTien(499000);
			hoaDonRepository.save(hd2);

			CTHoaDon cthd7 = new CTHoaDon();
			cthd7.setHoadon(hd2);
			cthd7.setCourse(cslt);
			cthd7.setGia(499000);
			cthd7.setHoantien(0);
			cthdRepository.save(cthd7);

			// CTHoaDon cthd8 = new CTHoaDon();
			// cthd8.setHoadon(hd2);
			// cthd8.setCourse(laptrinhc);
			// cthd8.setGia(500000);
			// cthd8.setHoantien(0);
			// cthdRepository.save(cthd8);

			List<CTHoaDon> list2 = new ArrayList<>();
			list2.add(cthd7);
			// list2.add(cthd8);
			hd2.setListCTHD(list2);
			hoaDonRepository.save(hd2);


			HoaDon hd3 = new HoaDon();
			hd3.setUser(student2);
			hd3.setTongTien(0);
			hoaDonRepository.save(hd3);

			CTHoaDon cthd9 = new CTHoaDon();
			cthd9.setHoadon(hd3);
			cthd9.setCourse(dientu);
			cthd9.setGia(0);
			cthd9.setHoantien(0);
			cthdRepository.save(cthd9);
			List<CTHoaDon> list3 = new ArrayList<>();
			list3.add(cthd9);
			hd3.setListCTHD(list3);
			hoaDonRepository.save(hd3);


			HoaDon hd4 = new HoaDon();
			hd4.setUser(student3);
			hd4.setTongTien(500000);
			hoaDonRepository.save(hd4);

			CTHoaDon cthd10 = new CTHoaDon();
			cthd10.setHoadon(hd4);
			cthd10.setCourse(winform);
			cthd10.setGia(500000);
			cthd10.setHoantien(0);
			cthdRepository.save(cthd10);

			CTHoaDon cthd11 = new CTHoaDon();
			cthd11.setHoadon(hd4);
			cthd11.setCourse(dientu);
			cthd11.setGia(0);
			cthd11.setHoantien(0);
			cthdRepository.save(cthd11);
			List<CTHoaDon> list4 = new ArrayList<>();
			list4.add(cthd10);
			list4.add(cthd11);
			hd4.setListCTHD(list4);
			hoaDonRepository.save(hd4);
		}
		//Chatlog
		Chatlog chatLog=new Chatlog();
		chatLog.setContent("Chao T");
		User chatusr=userRepository.findUserByuserId((long)5);
		chatLog.setCourseOwner(chatusr);
		User buser=userRepository.findUserByuserId((long)2);
		chatLog.setCourseBuyer(buser);
		chatLog.setSendBy(0);
		chatlogRepository.save(chatLog);
                
	}
}
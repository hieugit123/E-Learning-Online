package com.phanlop.khoahoc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.phanlop.khoahoc.Entity.AccessType;
import com.phanlop.khoahoc.Entity.Lesson;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.Department;
import com.phanlop.khoahoc.Entity.Enrollment;
import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.DanhGia;
import com.phanlop.khoahoc.Repository.DanhGiaRepository;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.DepartmentRepository;
import com.phanlop.khoahoc.Repository.EnrollmentRepository;
//import com.phanlop.khoahoc.Repository.InviteRepository;
//import com.phanlop.khoahoc.Repository.NotifyRepository;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Repository.UserRepository;

import lombok.AllArgsConstructor;
import com.phanlop.khoahoc.Repository.LessonRepository;

@SpringBootApplication
@EnableJpaAuditing // Cái này để lưu ngày createDate với modifiedDate - đừng quan tâm
@AllArgsConstructor
public class KhoahocApplication implements CommandLineRunner{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
        private final DanhGiaRepository danhgiaRepository;
	private final DepartmentRepository departmentRepository;
//	private final NotifyRepository notifyRepository;
//	private final InviteRepository inviteRepository;
	private final LessonRepository lessonRepository;
	private final RoleRepository roleRepository;
	private EnrollmentRepository enrollmentRepository;

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

		// Tạo user
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
		guest.setEmail("hieu3@gmail.com");
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
		student2.setUserId(5L);
		student2.setFullName("Hieu Khoai To");
		student2.setEmail("hieu2@gmail.com");
		student2.setPassword(passwordEncoder.encode("123456"));
		student2.getListRoles().add(role2);
		role2.getListUsers().add(student2);
		userRepository.save(student2);
		roleRepository.save(role2);
                

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
			cslt.setCourseOwner(adminUser);
			cslt.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			cslt.setCourseName("Cơ sở lập trình thứ "+i);
			cslt.setDepartment(cntt);
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

			Lesson lesson1 = new Lesson();
			lesson1.setLessonTitle("1. Khái niệm, kỹ thuật cần biết");
			lesson1.setLessonSort(1);
			lesson1.setLessonVideo("https://www.youtube.com/embed/zoELAirXMJY");
			lesson1.setLessonContent("Mô hình Client - Server là mô hình được sử dụng để triển khai các trang web hiện nay. Toàn bộ các trang web mà bạn có thể truy cập đều đang sử dụng mô hình này. Hãy cùng tìm hiểu tổng quan về mô hình Client - Server trước khi bắt đầu vào các khóa học về lập trình web bạn nhé.");
			lesson1.setCourse(cslt);
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
			lesson3.setLessonContent("Bạn cần có mục tiêu cho việc làm của mình, học lập trình cũng vậy, từ đó bạn sẽ lên được lộ trình học lập trình và phương pháp học lập trình phù hợp với bản thân hơn. Nếu không có mục tiêu thì bạn sẽ không thể tìm ra được lộ trình học lập trình đâu nhé.\n" +
					"\n" +
					"Tiếp theo là sự chủ động, nếu như ngày học phổ thông thì hầu hết chúng ta có tư tưởng là \"phải\" học. Học lập trình nó khác, phần lớn các bạn đều mong muốn học lập trình để đi làm và kiếm nhiều tiền nên mỗi lúc có thời gian hãy tự chủ động học và suy nghĩ về nó nhé. Bây giờ lớn rồi! Đừng để ai phải nhắc cho tương lai của chính mình nhé!.");
			lesson3.setCourse(cslt);
			lessonRepository.save(lesson3);


			Course laptrinhc = new Course();
			laptrinhc.setCourseOwner(adminUser);
			laptrinhc.setCourseAvt("https://files.fullstack.edu.vn/f8-prod/courses/21/63e1bcbaed1dd.png");
			laptrinhc.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			laptrinhc.setCourseName("Lập trình c cơ bản, nâng cao thứ "+ i);
			laptrinhc.setDepartment(cntt);
			courseRepository.save(laptrinhc);

                        DanhGia danhgia1 = new DanhGia();
                        danhgia1.setUser(student);
                        danhgia1.setCourse(laptrinhc);
                        danhgia1.setContentDanhgia("Khóa học khá bổ ích. Cảm ơn tác giả!");
                        danhgia1.setSao(4);
                        danhgia1.setTenUser(student.getFullName());
                        danhgiaRepository.save(danhgia1);
                        laptrinhc.getDanhgias().add(danhgia1);
                        courseRepository.save(laptrinhc);
                        
                        
			Enrollment.EnrollmentId enrollmentId2 = new Enrollment.EnrollmentId();
			enrollmentId2.setUserId(guest.getUserId());
			enrollmentId2.setCourseId(laptrinhc.getCourseID());
			Enrollment enrollment2 = new Enrollment();
			enrollment2.setId(enrollmentId2);
			enrollment2.setUser(guest);
			enrollment2.setAccessType(AccessType.ACCEPT);
			enrollment2.setCourse(laptrinhc);
			enrollmentRepository.save(enrollment2);

			Lesson lessonc1 = new Lesson();
			lessonc1.setLessonTitle("1. Tổng quan về khóa học Lập trình C++");
			lessonc1.setLessonSort(1);
			lessonc1.setLessonVideo("https://www.youtube.com/embed/WS05AU6YYm4");
			lessonc1.setLessonContent("Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.\n" +
					"\n" +
					"Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).\n" +
					"\n" +
					"Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp");
			lessonc1.setCourse(laptrinhc);
			lessonRepository.save(lessonc1);

			Lesson lessonc2 = new Lesson();
			lessonc2.setLessonTitle("2. Biến trong C++");
                        lessonc2.setLessonSort(2);
			lessonc2.setLessonVideo("https://www.youtube.com/embed/i3nJyEt42Y8");
			lessonc2.setLessonContent("Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.\n" +
					"\n" +
					"Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).\n" +
					"\n" +
					"Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp");
			lessonc2.setCourse(laptrinhc);
			lessonRepository.save(lessonc2);

			Lesson lessonc3 = new Lesson();
			lessonc3.setLessonTitle("3. Vòng lặp For trong C++");
			lessonc3.setLessonSort(3);
			lessonc3.setLessonVideo("https://www.youtube.com/embed/aL59MpOFMe0");
			lessonc3.setLessonContent("Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.\n" +
					"\n" +
					"Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).\n" +
					"\n" +
					"Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp");
			lessonc3.setCourse(laptrinhc);
			lessonRepository.save(lessonc3);

			Course winform = new Course();
			winform.setCourseAvt("https://static.skillshare.com/uploads/discussion/tmp/b8ba300b.png");
			winform.setCourseOwner(adminUser);
			winform.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			winform.setCourseName("Lập trình winform thứ"+i);
			winform.setDepartment(cntt);
			courseRepository.save(winform);
                        
                        DanhGia danhgia2 = new DanhGia();
                        danhgia2.setUser(student2);
                        danhgia2.setCourse(winform);
                        danhgia2.setContentDanhgia("Khóa học khá bổ ích. Tuyệt vời!");
                        danhgia2.setSao(4);
                        danhgia2.setTenUser(student2.getFullName());
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

			Lesson winform1 = new Lesson();
			winform1.setLessonTitle("1. Tổng quan lập trình Winform");
			winform1.setLessonSort(1);
			winform1.setLessonVideo("https://www.youtube.com/embed/dtYVRWfGhzI");
			winform1.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform1.setCourse(winform);
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
			dientu.setCourseOwner(adminUser);
			dientu.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			dientu.setCourseName("Điện tử và điện tử số "+i);
			dientu.setDepartment(dtvt);
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

			Lesson dientu1 = new Lesson();
			dientu1.setLessonTitle("1. Tổng quan lập trình Winform");
			dientu1.setLessonSort(1);
			dientu1.setLessonVideo("https://www.youtube.com/embed/dtYVRWfGhzI");
			dientu1.setLessonContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu1.setCourse(dientu);
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
		}
                
	}
}
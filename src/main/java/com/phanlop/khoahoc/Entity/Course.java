package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@ToString(exclude = {"courseOwner", "listLessons", "listDiscuss", "department", "enrollments"})
@EqualsAndHashCode(exclude = {"courseOwner", "listLessons", "listDiscuss", "department", "enrollments"})
@EntityListeners(AuditingEntityListener.class)
public class Course {
    public static final String avtDefault = "https://files.fullstack.edu.vn/f8-prod/courses/7.png";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseID;
    @NotBlank(message = "Tên khóa học không được trống")
    private String courseName;
    private String courseAvt = avtDefault;
    private String courseDes;
    @CreatedDate
    private Instant createDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Thêm khóa user_id sở hữu khóa học
    @ManyToOne @JoinColumn(name = "user_id")
    private User courseOwner;

    // Khóa ngoại cho bài học
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Lesson> listLessons = new HashSet<>();

    // Khóa ngoại tới Department
    @NotNull(message = "Vui lòng chọn khoa cho khóa học")
    @ManyToOne @JoinColumn(name="department_id")
    private Department department;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<DanhGia> danhgias = new ArrayList<>();;
}

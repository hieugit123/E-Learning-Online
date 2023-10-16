package com.phanlop.khoahoc.Entity;

// import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
// import java.util.HashSet;d
// import java.util.Set;

@Entity
@Data
@ToString(exclude = {"listFiles", "course"})
@EqualsAndHashCode(exclude = {"listFiles", "course"})
@EntityListeners(AuditingEntityListener.class)
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignId;
    @NotEmpty(message = "Tên bài tập không được trống!")
    private String assignTitle;
    @Column(columnDefinition = "TEXT")
    private String assignDes;
    @CreatedDate
    private Instant createdDate;
    private Instant deadline;

    // Khóa ngoại courseID
    @ManyToOne @JoinColumn(name="lesson_id")
    private Lesson lesson;

//    // Tạo bảng AssignmentFile
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name="assginment_file",
//            joinColumns = @JoinColumn(name="assgin_id"),
//            inverseJoinColumns = @JoinColumn(name="file_id")
//    )
//    private Set<File> listFiles = new HashSet<>();
}

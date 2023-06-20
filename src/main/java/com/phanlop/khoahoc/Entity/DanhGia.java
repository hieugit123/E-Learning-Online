package com.phanlop.khoahoc.Entity;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long danhgiaId;
    private String contentDanhgia;
    @CreatedDate
    private Instant createdAt;
    private int sao;
    private String tenUser;

    @ManyToOne @JoinColumn(name="courseId")
    private Course course;
    
    @ManyToOne @JoinColumn(name="userId")
    private User user;
}

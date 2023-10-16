package com.phanlop.khoahoc.Entity;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

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

package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class ChiTraGV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne @JoinColumn(name = "admin_id")
    private User admin;

    private int tongDoanhThu;
    private int tyLeShare;
    private int soTienChuyen;
    private int thang;
    
    @CreatedDate
    private Instant ngayChiTra;

    private int state;
}
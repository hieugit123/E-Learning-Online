package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class CTHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CTHDId;

    @ManyToOne @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne @JoinColumn(name = "hoadon_id")
    private HoaDon hoadon;

    private int hoantien;

    private int gia;
}

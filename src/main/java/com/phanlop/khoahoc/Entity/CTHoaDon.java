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
    private User course;

    private int hoantien;

    private int gia;
}

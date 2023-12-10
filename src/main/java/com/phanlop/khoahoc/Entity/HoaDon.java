package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long HoaDonId;
    @CreatedDate
    private Instant ngayMua;
    private int tongTien;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "hoadon", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CTHoaDon> listCTHD;
    
   

}
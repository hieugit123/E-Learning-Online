package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartId;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne @JoinColumn(name = "course_id")
    private Course course;
}

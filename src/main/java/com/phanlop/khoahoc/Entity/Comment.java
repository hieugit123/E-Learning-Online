package com.phanlop.khoahoc.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@ToString(exclude = {"user", "lesson"})
@EqualsAndHashCode(exclude = {"user", "lesson"})
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @NotNull(message="Bình luận không được trống")
    private String content;
    @CreatedDate
    private Instant createdAt;
    
    private int state;
    // Khóa ngoại user_id
    @ManyToOne @JoinColumn(name="user_id")
    private User user;

    // Khóa ngoại course_id
    @ManyToOne @JoinColumn(name="lesson_id")
    private Lesson lesson;
}

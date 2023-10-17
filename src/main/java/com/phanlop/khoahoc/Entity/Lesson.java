package com.phanlop.khoahoc.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Data
@ToString(exclude = {"course"})
@EqualsAndHashCode(exclude = {"course"})
@EntityListeners(AuditingEntityListener.class)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonSort;
    @NotEmpty(message="Tên bài học không được trống")
    private String lessonTitle;
    private String lessonVideo;
    @Column(columnDefinition = "TEXT") // Text trong database
    private String lessonContent;
    @CreatedDate
    private Instant lessonDate;
    @LastModifiedDate
    private Instant modifiedDate;

    // Khóa ngoại courseID
    @ManyToOne @JoinColumn(name = "course_id")
    private Course course;
}

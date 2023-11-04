package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = {"uploadedUser", "lessons"})
@EqualsAndHashCode(exclude = {"uploadedUser", "chapters"})
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fileID;
    private String localUrl;
    private String fileName;
    private String fileLink;
    @CreatedDate
    private Instant createdDate;

    // Khóa ngoại user_id
    @ManyToOne @JoinColumn(name="user_id")
    private User uploadedUser;

    // // Tạo table SubmitFile
    // @ManyToMany(mappedBy="submitFiles")
    // private Set<Submit> listSubmits = new HashSet<>();

    // Tạo table CourseDocument
    // @ManyToMany(mappedBy = "listDocuments")
    // private Set<Course> courses = new HashSet<>();

}

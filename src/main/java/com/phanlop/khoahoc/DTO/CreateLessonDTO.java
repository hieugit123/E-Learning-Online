package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateLessonDTO {
    private int lessonId;
    private int lessonSort;
    @NotEmpty(message="Tên chương không được trống")
    private String lessonTitle;
    private MultipartFile lessonVideoMulti;
    private String youtubeUrl;
    private String lessonContent;
    @NotNull
    private UUID courseId;
}

package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private int lessonId;
    private int lessonSort;
    @NotEmpty(message="Tên bài học không được trống")
    private String lessonTitle;
    private String lessonVideo;
    private String lessonContent;
    private Instant createDate;
    private Instant modifiedDate;
}

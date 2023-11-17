package com.phanlop.khoahoc.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentDTO {
    private int assignId;
    @NotEmpty(message = "Tên bài tập không được trống!")
    private String assignTitle;
    private String assignDes;
    private Instant createdDate;
    private Instant deadline;
    private int lessonID;
}

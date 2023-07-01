package com.phanlop.khoahoc.DTO;

import java.time.Instant;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDTO {
    private UUID courseID;
    @NotBlank(message = "Tên khóa học không được trống")
    private String courseName;
    private String courseDes = "";
    private MultipartFile courseAvt;
    private Instant createDate;
    private Instant modifiedDate;
    private int state;
    private int stateGuiAdmin;
    private int gia;
    @NotNull(message = "Vui lòng chọn khoa cho khóa học")
    private Integer departmentId;
}

package com.phanlop.khoahoc.DTO;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminThongkeDTO {


    private UUID courseID;
    private String courseName;
    private int buyCount;
    private int gia;
    private DepartmentDTO department;
    private int revenue;
    private int systemRevenue;
}

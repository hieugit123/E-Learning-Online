package com.phanlop.khoahoc.DTO;

import java.util.UUID;

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

package com.phanlop.khoahoc.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.HoaDon;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTHoaDonDTO {
    private Long CTHDId;

    private Course courseId;

    private HoaDon hoadonId;

    private int hoantien;

    private int gia;
}

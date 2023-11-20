package com.phanlop.khoahoc.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonDTO {
    private Long HoaDonId;
    private Instant ngayMua;
    private int tongTien;
    private Long userId;
}

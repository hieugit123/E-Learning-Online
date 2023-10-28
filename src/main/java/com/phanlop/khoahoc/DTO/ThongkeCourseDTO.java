package com.phanlop.khoahoc.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class ThongkeCourseDTO {
    public UUID courseid;
    public String courseName;
    public double courseRevenue;
    public double getCourseRevenue(){
        return this.courseRevenue;
    }
    public double gia;
    public double getGia(){
        return this.gia;
    }
    public int courseBuyCount;
    public String avatar;
}

package com.phanlop.khoahoc.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseCountDTO {
    public long userId;
    public String fullName;
    public long haveBuy;
    public int spend;
    public long gethaveBuy(){
        return this.haveBuy;
    }
    public int getSpend(){
        return this.spend;
    }
}

package com.phanlop.khoahoc.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LuotMuaDTO {
    private CourseDTO course;
    private List<UserDTO> listUser;
}

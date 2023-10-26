package com.phanlop.khoahoc.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatlogDTO {
    private long id;
    private long teacherid;
    private long userid;
    private String content;
    private Date dateSend;
}

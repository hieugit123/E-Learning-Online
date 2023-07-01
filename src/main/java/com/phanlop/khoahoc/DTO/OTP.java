package com.phanlop.khoahoc.DTO;

import java.io.Serializable;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP implements Serializable {
    public static final long otpTimeOut = 1000*60*5;
    private String email;
    private String otpCode;
    private long sendTime;

    public static String randomOTPCode() {
        Random rand = new Random();
        //tạo 1 số từ 0->999999
        int otp = rand.nextInt(1000000);
        //Nếu otp có ít hơn 6 chữ số, các chữ số dư sẽ được điền bằng số 0 từ trái qua phải.
        //đảm bảo otp luôn là 6 số.
        return "%06d".formatted(otp);
    }

    public static long getCurrentTime() {
//       trả về số kiểu dữ liệu long được sử dụng để lưu trữ giá trị thời gian tính bằng mili giây.
        //trả về thời gian hiện tại từ 1/1/1970 00:00:00 đến hiện tại (mili giây).
        return System.currentTimeMillis();
    }
}

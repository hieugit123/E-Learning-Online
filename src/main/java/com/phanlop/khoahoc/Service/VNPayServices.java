package com.phanlop.khoahoc.Service;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayServices {
     String getMaHD();
     String createOrder(int total, String urlReturn);
     int orderReturn(HttpServletRequest request);
}

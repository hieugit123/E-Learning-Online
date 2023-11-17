package com.phanlop.khoahoc.Service;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayServices {
     String createOrder(int total, String urlReturn);
     int orderReturn(HttpServletRequest request);
}

package com.phanlop.khoahoc.Service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.HoaDon;
import com.phanlop.khoahoc.Repository.HoaDonRepository;
import com.phanlop.khoahoc.Service.HoaDonServices;

@Service
public class HoaDonServicesImpl implements HoaDonServices{
    @Autowired
    private HoaDonRepository hdRepository;
    @Override
    public void saveHD(HoaDon hd) {
        // TODO Auto-generated method stub
        hdRepository.save(hd);
    }
    
}

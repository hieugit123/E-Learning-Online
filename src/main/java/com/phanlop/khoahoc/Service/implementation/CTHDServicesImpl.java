package com.phanlop.khoahoc.Service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.CTHoaDon;
import com.phanlop.khoahoc.Repository.CTHDRepository;
import com.phanlop.khoahoc.Service.CTHDServices;

@Service
public class CTHDServicesImpl implements CTHDServices{
    @Autowired
    private CTHDRepository cthdRepository;
    @Override
    public void saveCTHD(CTHoaDon cthd) {
        // TODO Auto-generated method stub
        cthdRepository.save(cthd);
    }
    
}

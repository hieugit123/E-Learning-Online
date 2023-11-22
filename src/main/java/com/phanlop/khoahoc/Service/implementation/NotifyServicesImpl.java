package com.phanlop.khoahoc.Service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.Notify;
import com.phanlop.khoahoc.Repository.NotifyRepository;
import com.phanlop.khoahoc.Service.NotifyServices;

@Service
public class NotifyServicesImpl implements NotifyServices{

    @Autowired
    private NotifyRepository notifyRepo;
    @Override
    public List<Notify> getList() {
        return notifyRepo.findAll();
    }
    
}

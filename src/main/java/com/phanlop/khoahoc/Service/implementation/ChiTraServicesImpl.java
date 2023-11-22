package com.phanlop.khoahoc.Service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.ChiTraGV;
import com.phanlop.khoahoc.Repository.ChiTraRepository;
import com.phanlop.khoahoc.Service.ChiTraServices;

@Service
public class ChiTraServicesImpl implements ChiTraServices{
    @Autowired
    private ChiTraRepository chitraRepo;
    @Override
    public void save(ChiTraGV chitra) {
        // TODO Auto-generated method stub
        chitraRepo.save(chitra);
    }
    @Override
    public List<ChiTraGV> getAll() {
        // TODO Auto-generated method stub
        return chitraRepo.findAll();
    }
    @Override
    public ChiTraGV findByID(Long id) {
        // TODO Auto-generated method stub
        return chitraRepo.findById(id).get();
    }
    
}

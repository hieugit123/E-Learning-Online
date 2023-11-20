package com.phanlop.khoahoc.Service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Repository.RoleRepository;
import com.phanlop.khoahoc.Service.RoleServices;

@Service
public class RoleServicesImpl implements RoleServices{
    @Autowired
    private RoleRepository roleRepo;
    @Override
    public Role findRoleByName(String name) {
        // TODO Auto-generated method stub
        return roleRepo.findByRoleName(name);
    }
    
}

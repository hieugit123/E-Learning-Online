package com.phanlop.khoahoc.Service;

import java.util.List;

import com.phanlop.khoahoc.Entity.ChiTraGV;

public interface ChiTraServices {
    void save(ChiTraGV chitra);
    List<ChiTraGV> getAll();
    ChiTraGV findByID(Long id);
}

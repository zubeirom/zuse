package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Commission;

import java.util.List;

public interface ICommissionService {
    Commission create(Commission commission);
    void update(Commission commission);
    void delete(int commissionId);
    Commission getOne(int commissionId);
    List<Commission> getAll();
}

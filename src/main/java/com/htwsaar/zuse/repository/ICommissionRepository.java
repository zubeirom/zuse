package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Commission;

import java.util.List;

public interface ICommissionRepository {
    Commission create(Commission commission);
    List<Commission> getAll();
    Commission getOne(int commissionId);
    Commission update(Commission commission);
    void delete(int commissionId);
}

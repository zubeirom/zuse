package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Outgoing;

import java.util.List;

public interface IOutgoingRepository {
    Outgoing create(Outgoing outgoing);
    List<Outgoing> getAll();
    Outgoing getOne(int outgoingId);
    void update(Outgoing outgoing);
    void delete(int outgoingId);
}

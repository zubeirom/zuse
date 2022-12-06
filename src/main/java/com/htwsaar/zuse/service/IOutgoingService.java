package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Outgoing;

import java.util.List;

public interface IOutgoingService {
    Outgoing create(Outgoing outgoing);
    void update(Outgoing outgoing);
    void delete(int outgoingId);
    Outgoing getOne(int outgoingId);
    List<Outgoing> getAll();
}

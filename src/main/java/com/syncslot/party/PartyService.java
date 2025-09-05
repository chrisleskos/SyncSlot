package com.syncslot.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository repository;

    public List<Party> findOrCreate(List<String> partyNames) {
        return repository.findOrCreate(partyNames);
    }
}

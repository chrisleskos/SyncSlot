package com.syncslot.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartyService {

    @Autowired
    private PartyRepository repository;

    public List<Party> findOrCreate(List<String> partyNames) {
        List<Party> existing = repository.findByNameIn(partyNames);

        Set<String> existingNames = existing.stream()
                .map(Party::getName)
                .collect(Collectors.toSet());

        List<Party> toCreate = partyNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> Party.builder().name(name).build())
                .toList();

        if (!toCreate.isEmpty()) {
            existing.addAll(repository.saveAll(toCreate));
        }

        return existing;
    }
}

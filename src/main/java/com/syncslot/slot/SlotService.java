package com.syncslot.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;

    public void createSlot(Slot slot) {
        slotRepository.save(slot);
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public Slot getSlot(Integer id) {
        return new Slot();
    }

    public void editSlot(Integer id) {
        // Edit Slot
    }

    public void deleteSlot(Integer id) {
        // Delete Slot
    }
}

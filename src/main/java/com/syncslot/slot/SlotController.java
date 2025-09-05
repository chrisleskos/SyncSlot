package com.syncslot.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sync-slot/slot")
public class SlotController {
    @Autowired
    private SlotService slotService;

    @GetMapping("/")
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    @PostMapping("/")
    public void saveSlot(Slot slot) {
        slotService.createSlot(slot);
    }
}

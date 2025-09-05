package com.syncslot.slot;

import com.syncslot.party.Party;
import com.syncslot.party.PartyService;
import com.syncslot.slot.mapper.SlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sync-slot/slot")
public class SlotController {
    @Autowired
    private SlotService slotService;

    @Autowired
    public PartyService partyService;

    @Autowired
    public SlotMapper slotMapper;

    @GetMapping("/")
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    @PostMapping("/")
    public ResponseEntity<SlotDto> createSlot(@RequestBody SlotDto slotDto) {
        List<Party> parties = partyService.findOrCreate(slotDto.getPartyNames());
        Slot slot = slotMapper.toEntity(slotDto, parties);
        Slot saved = slotService.createSlot(slot);
        return ResponseEntity.ok(slotMapper.toDto(saved));
    }
}

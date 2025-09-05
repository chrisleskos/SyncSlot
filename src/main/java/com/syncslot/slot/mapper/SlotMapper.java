package com.syncslot.slot.mapper;

import com.syncslot.party.Party;
import com.syncslot.slot.Slot;
import com.syncslot.slot.SlotDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  SlotMapper {
    SlotDto toDto(Slot slot);
    Slot toEntity(SlotDto dto, List<Party> parties);
}

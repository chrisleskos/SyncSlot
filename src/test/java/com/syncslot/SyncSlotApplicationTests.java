package com.syncslot;

import com.syncslot.slot.Slot;
import com.syncslot.slot.SlotDto;
import com.syncslot.slot.SlotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SyncSlotApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SlotRepository slotRepository;

    @Test
    @WithMockUser(username = "testuser")
    void createSlot_shouldPersistAndReturnSlot() {
        // Prepare request body
        SlotDto request = new SlotDto();
        request.setName("Team Meeting");
        request.setFromDate(LocalDate.from(LocalDateTime.of(2025, 9, 5, 10, 0)));
        request.setPartyNames(List.of("Alice", "Bob")); // map to your Party logic

        // Send HTTP POST request
        ResponseEntity<SlotDto> response = restTemplate.postForEntity("/slots", request, SlotDto.class);

        // Check response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verify database
        List<Slot> slots = slotRepository.findByName("Team Meeting");
        assertFalse(slots.isEmpty());
        Slot saved = slots.get(0);
        assertEquals(2, saved.getParties().size());
    }

}

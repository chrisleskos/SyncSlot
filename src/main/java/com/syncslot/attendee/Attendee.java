package com.syncslot.attendee;

import com.syncslot.party.Party;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendee {
    private int id;
    private Party party;
    private Boolean host;
}

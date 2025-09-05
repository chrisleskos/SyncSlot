package com.syncslot.attendee;

import com.syncslot.party.Party;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attendee {
    @Id
    @GeneratedValue
    private Integer attendeeId;
    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;
    private Boolean host;
}

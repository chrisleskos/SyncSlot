package com.syncslot.slot;

import com.syncslot.attendee.Attendee;
import com.syncslot.party.Party;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Slot {
    @Id
    @GeneratedValue
    private int slotId;
    private String name;
    private String description;
    private LocalDate fromDate;
    // - length=1 (apply to all recurrences) - duration.length > 1 (rotational)
    private List<Date> duration;
    // In case of repeating recurrence, defines the end date of the slot. If it's one time, it won't be needed (null)
    private LocalDate toDate;
    // If empty, not recurrent.
    private List<Duration> recurrenceIntervals;
    @OneToMany
    @JoinColumn(name = "slot_id")
    private List<Party> parties;
    @OneToMany
    @JoinColumn(name = "slot_id")
    private List<Attendee> attendees;
    private Status status;
    private Priority priority;
    @OneToOne
    @JoinColumn(name = "slot_id")
    private Place place;
}

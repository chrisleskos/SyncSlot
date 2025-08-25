package com.syncslot.slot;

import com.syncslot.attendee.Attendee;
import com.syncslot.party.Party;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Slot {
    private int id;
    private String name;
    private String desc;
    private Date from;
    private Date duration;
    // In case of repeating recurrence, defines the end date of the slot. If it's one time, it won't be needed (null)
    private Date to;
    // If empty, not recurrent
    private List<Duration> recurrencePattern;
    private Party[] parties;
    private Attendee[] attendees;
    private Status status;
    private Priority priority;
    private Place place;
}

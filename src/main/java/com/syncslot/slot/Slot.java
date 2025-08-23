package com.syncslot.slot;

import com.syncslot.attendee.Attendee;
import com.syncslot.party.Party;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


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
    private Recurrence recurrence;
    private Party[] parties;
    private Attendee[] attendees;
    private Status status;
    private Priority priority;
    //TODO: Think of how to specify the location since it can be on-site or remote (could be website, app, exact map location etc)
}

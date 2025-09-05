package com.syncslot.slot;

import com.syncslot.attendee.Attendee;
import com.syncslot.party.Party;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class SlotDto {
    private String name;
    private String description;
    private LocalDate fromDate;
    private List<Date> duration;
    private LocalDate toDate;

    private List<Duration> recurrenceIntervals;
    private List<String> partyNames;

    private List<Attendee> attendees;
    private Status status;
    private Priority priority;
    private Place place;
}

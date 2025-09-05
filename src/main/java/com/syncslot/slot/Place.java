package com.syncslot.slot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Place {
    @Id
    @GeneratedValue
    private Integer placeId;
    private String name;
    // specifies details e.g. "4th floor, room 361" etc.
    private String details;

    // App/Website link for remote, Google Maps co-ordinates in case of physical location
    private URI uri;
}

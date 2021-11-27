package com.lynas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("event")
public class Event implements Persistable<String> {

    @Id
    private String id;
    private String eventId;
    private String type;
    private String name;
    private String clusterId;
    private Instant timestamp;
    private double value;
    private String initialized;

    @Override
    public boolean isNew() {
        return true;
    }
}

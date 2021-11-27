package com.lynas.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Event {

    private String id;
    private String type;
    private String name;
    private String clusterId;
    private Timestamp timestamp;
    private double value;
    private String initialized;

}



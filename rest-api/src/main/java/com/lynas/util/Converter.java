package com.lynas.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class Converter {

    @Value("${app.time.pattern}")
    private String appTimePattern;
    @Value("${app.time.zone}")
    private String appTimeZone;

    public String longToStringDate(long time){
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(appTimePattern)
                .withZone(ZoneId.of(appTimeZone));
        return DATE_TIME_FORMATTER.format(Instant.ofEpochMilli(time));
    }
}

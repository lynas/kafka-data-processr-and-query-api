package com.lynas.service;

import com.lynas.model.Operation;
import com.lynas.repository.EventRepository;
import com.lynas.util.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventService {

    private final EventRepository repository;
    private final Converter converter;

    EventService(EventRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public Mono<Double> findValue(final Operation operation, final String type, final String clusterId,
                                  final long fromTime, final long toTime) {
        String fromTimeStr = converter.longToStringDate(fromTime);
        String toTimeStr = converter.longToStringDate(toTime);
        switch (operation) {
            case max:
                return repository.findMax(type, clusterId, fromTimeStr, toTimeStr);
            case min:
                return repository.findMin(type, clusterId, fromTimeStr, toTimeStr);
            case average:
                return repository.findAverage(type, clusterId, fromTimeStr, toTimeStr);
            case median:
                return repository.findMedian(type, clusterId, fromTimeStr, toTimeStr);
        }
        throw new RuntimeException("EventService findValue unreachable code");
    }

}
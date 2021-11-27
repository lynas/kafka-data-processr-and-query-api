package com.lynas.controller;

import com.lynas.model.Operation;
import com.lynas.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/events/operation/{operation}/fromTime/{fromTime}/toTime/{toTime}")
    public Mono<Double> getAll(
            @PathVariable Operation operation,
            @PathVariable Long fromTime,
            @PathVariable Long toTime,
            @RequestParam(required = false) Optional<String> eventType,
            @RequestParam(required = false) Optional<String> clusterId
    ) {
        logger.info("/events/operation/"+operation+"/fromTime/"+fromTime+"/toTime/"+toTime+"?eventType="+eventType+"&clusterId="+clusterId);
        return eventService.findValue(operation,eventType.orElse("%%"), clusterId.orElse("%%"), fromTime, toTime);
    }


}

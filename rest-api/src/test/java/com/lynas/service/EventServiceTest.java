package com.lynas.service;

import com.lynas.model.Operation;
import com.lynas.repository.EventRepository;
import com.lynas.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class EventServiceTest {

    @Autowired
    EventRepository repository;
    @Autowired
    Converter converter;

    @Autowired
    EventService eventService;
    @Autowired
    R2dbcEntityTemplate template;

    @Value("${app.test.init.query}")
    private String initQuery;

    @BeforeEach
    void setup(){
        template.getDatabaseClient().sql(initQuery)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findValueMinimum() {
        Mono<Double> value2 = eventService.findValue(Operation.min, "%%", "%%", 1637838482000L, 1637838537000L);
        StepVerifier.create(value2.doOnNext(System.out::println))
                .expectNext(25.19200410443849)
                .verifyComplete();
    }

    @Test
    void findValueMaximum() {
        Mono<Double> value2 = eventService.findValue(Operation.max, "%%", "%%", 1637838482000L, 1637838537000L);
        StepVerifier.create(value2.doOnNext(System.out::println))
                .expectNext(98.79763568200458)
                .verifyComplete();
    }

    @Test
    void findValueAverage() {
        Mono<Double> value2 = eventService.findValue(Operation.average, "%%", "%%", 1637838482000L, 1637838537000L);
        StepVerifier.create(value2.doOnNext(System.out::println))
                .expectNext(64.20112166733814)
                .verifyComplete();
    }

    @Test
    void findValueMaxByTypeAndClusterId() {
        Mono<Double> value2 = eventService.findValue(Operation.max, "TEMPERATURE", "1", 1637838482000L, 1637838537000L);
        StepVerifier.create(value2.doOnNext(System.out::println))
                .expectNext(95.22788183143646)
                .verifyComplete();
    }
}
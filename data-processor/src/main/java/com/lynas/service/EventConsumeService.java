package com.lynas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynas.dto.Event;
import com.lynas.model.EventDB;
import com.lynas.repository.EventRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventConsumeService {

    private final Logger logger = LoggerFactory.getLogger(EventConsumeService.class);
    private final ObjectMapper objectMapper;
    private final EventRepository repository;

    public EventConsumeService(ObjectMapper objectMapper, EventRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }


    @KafkaListener(topics = {"#{'${kafka.stream.topics}'.split(',')}"}, groupId = "${kafka.stream.groupId}")
    public void consume(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String  payload) throws JsonProcessingException {
        Base64 base64Url = new Base64(true);
        String eventString = new String(base64Url.decode(payload));
        logger.info("Event String : " + eventString);

        Event event = objectMapper.readValue(eventString, Event.class);

        logger.info("Event Object : " + event);
        EventDB eventDB = new EventDB();
        eventDB.setClusterId(event.getClusterId());
        eventDB.setId(UUID.randomUUID().toString());
        eventDB.setEventId(event.getId());
        eventDB.setInitialized(event.getInitialized());
        eventDB.setName(event.getName());
        eventDB.setTimestamp(event.getTimestamp().toInstant());
        eventDB.setValue(event.getValue());
        eventDB.setType(event.getType());
        eventDB.isNew();
        repository.save(eventDB).subscribe(savedEvent ->{
            logger.info("Event Saved : " + savedEvent);
        } );

    }
}

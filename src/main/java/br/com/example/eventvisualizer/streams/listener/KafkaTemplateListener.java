package br.com.example.eventvisualizer.streams.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class KafkaTemplateListener implements MessageListener<String, GenericRecord> {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(@NotNull ConsumerRecord<String, GenericRecord> event) {
        log.info("Event received! event={};", event);
        try {
            TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
            final var map = mapper.readValue(event.value().toString(), typeReference);
            map.put("date", convertTimestampToStringDateTime(event.timestamp()));
            map.put("topic", event.topic());
            messagingTemplate.convertAndSend("/topic/update", map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertTimestampToStringDateTime(long timestamp) {
        final var dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        );
        final var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(dateFormatter);
    }

}

package br.com.example.eventvisualizer.streams.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class KafkaTemplateListener implements MessageListener<String, GenericRecord> {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(@NotNull ConsumerRecord<String, GenericRecord> event) {
        log.info("Event received! event={};", event);
        messagingTemplate.convertAndSend("/topic/update", event.value().toString());
    }

}

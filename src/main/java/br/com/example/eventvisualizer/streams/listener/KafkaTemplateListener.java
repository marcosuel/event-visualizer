package br.com.example.eventvisualizer.streams.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.listener.MessageListener;

@Log4j2
public class KafkaTemplateListener implements MessageListener<String, GenericRecord> {
    @Override
    public void onMessage(@NotNull ConsumerRecord<String, GenericRecord> event) {
        log.info("Event received! event={};", event);
    }

}

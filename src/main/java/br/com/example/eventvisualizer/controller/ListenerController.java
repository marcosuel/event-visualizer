package br.com.example.eventvisualizer.controller;

import br.com.example.eventvisualizer.service.KafkaListenerCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listener")
@RequiredArgsConstructor
public class ListenerController {
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory kafkaListenerContainerFactory;
    private final KafkaListenerCreator listenerCreator;

    @PostMapping
    ResponseEntity<Object> updateListener(@RequestParam String topic) {
        KafkaListenerEndpoint listener = listenerCreator.createKafkaListenerEndpoint(topic);
        kafkaListenerEndpointRegistry.registerListenerContainer(listener, kafkaListenerContainerFactory, true);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    ResponseEntity<Object> closeListeners() {
        kafkaListenerEndpointRegistry.getListenerContainerIds().forEach(
                kafkaListenerEndpointRegistry::unregisterListenerContainer
        );

        return ResponseEntity.ok().build();
    }

}

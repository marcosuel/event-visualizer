package br.com.example.eventvisualizer.service;

import br.com.example.eventvisualizer.streams.listener.KafkaTemplateListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory kafkaListenerContainerFactory;
    String kafkaGroupId = "kafkaGroupId";
    static final String LISTENER_ID = "kafkaListenerId-local";

    public void registerListener(String topic) {
        kafkaListenerEndpointRegistry.registerListenerContainer(
                createKafkaListenerEndpoint(topic),
                kafkaListenerContainerFactory,
                true
        );
        unregisterListeners();
    }

    private void unregisterListeners() {
        kafkaListenerEndpointRegistry.getListenerContainerIds().forEach(
                kafkaListenerEndpointRegistry::unregisterListenerContainer
        );
    }

    private KafkaListenerEndpoint createKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(topic);
        kafkaListenerEndpoint.setBean(new KafkaTemplateListener());
        try {
            kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Attempt to call a non-existent method " + e);
        }
        return kafkaListenerEndpoint;
    }

    private MethodKafkaListenerEndpoint<String, String> createDefaultMethodKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(LISTENER_ID);
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        return kafkaListenerEndpoint;
    }
}
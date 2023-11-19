package br.com.example.eventvisualizer.service;

import br.com.example.eventvisualizer.streams.listener.KafkaTemplateListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory kafkaListenerContainerFactory;
//    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplateListener kafkaTemplateListener;
    String kafkaGroupId = "kafkaGroupId";
    private static final String LISTENER_PREFIX = "kafkaListenerId-local-";
    private final AtomicLong listenerIdSuffix = new AtomicLong(1);

    public void registerListener(String topic) {
        destroyActiveListeners();
        kafkaListenerEndpointRegistry.registerListenerContainer(
                createKafkaListenerEndpoint(topic),
                kafkaListenerContainerFactory,
                true
        );
    }

    private void destroyActiveListeners() {
        kafkaListenerEndpointRegistry.getListenerContainers().forEach(MessageListenerContainer::destroy);
    }

    private KafkaListenerEndpoint createKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(topic);
        kafkaListenerEndpoint.setBean(kafkaTemplateListener);
        try {
            kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Attempt to call a non-existent method " + e);
        }
        return kafkaListenerEndpoint;
    }

    private MethodKafkaListenerEndpoint<String, String> createDefaultMethodKafkaListenerEndpoint(String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(LISTENER_PREFIX + listenerIdSuffix.getAndIncrement());
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        return kafkaListenerEndpoint;
    }
}
package br.com.example.eventvisualizer.service;

import br.com.example.eventvisualizer.controller.exchange.ListenerRequest;
import br.com.example.eventvisualizer.streams.listener.KafkaTemplateListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.*;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory kafkaListenerContainerFactory;
    private final KafkaTemplateListener kafkaTemplateListener;
    private final ApplicationContext context;
    private final AtomicLong listenerIdSuffix = new AtomicLong(1);
    private static final String LISTENER_PREFIX = "listener-id-";

    public void registerListener(ListenerRequest request) {
        destroyActiveListeners();
        if (request.updateConfigs()) {
            updateConsumerConfigs(request.bootstrapServers(), request.groupId(), request.schemaRegistry());
        }
        kafkaListenerEndpointRegistry.registerListenerContainer(
                createKafkaListenerEndpoint(request.topic()),
                kafkaListenerContainerFactory,
                true
        );
    }

    public void updateConsumerConfigs(String bootstrapServers, String groupId, String schemaRegistry) {
        final var consumerFactory = (
                (ConcurrentKafkaListenerContainerFactory<?, ?>) context.getBean(ConcurrentKafkaListenerContainerFactory.class)
        ).getConsumerFactory();

        final var newProperties = new HashMap<>(consumerFactory.getConfigurationProperties());
        newProperties.replace(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        newProperties.replace(GROUP_ID_CONFIG, groupId);
        newProperties.replace("schema.registry.url", schemaRegistry);

        consumerFactory.updateConfigs(newProperties);
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
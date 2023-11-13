package br.com.example.eventvisualizer.streams.producer;

import br.com.example.eventvisualizer.model.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer implements IProducer<PaymentEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${kafka.topics.payment}")
    private String topic;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Override
    public void produceEvent(PaymentEvent event) {
        logger.info("method=produceTransactionEvent; Amount={};", event.getAmount());
        kafkaTemplate.send(topic, event);
    }
}

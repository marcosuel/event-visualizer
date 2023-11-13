package br.com.example.eventvisualizer.streams.producer;

import br.com.example.eventvisualizer.model.TransactionEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionProducer implements IProducer<TransactionEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${kafka.topics.transaction}")
    private String topic;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    @Override
    public void produceEvent(TransactionEvent event) {
        logger.info("method=produceTransactionEvent; transactionId={};", event.getTransId());
        kafkaTemplate.send(topic, event);
    }
}

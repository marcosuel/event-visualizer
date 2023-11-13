package br.com.example.eventvisualizer.streams.listener;

import br.com.example.eventvisualizer.model.PaymentEvent;
import br.com.example.eventvisualizer.model.TransactionEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"${kafka.topics.transaction}"}, groupId = "${spring.kafka.consumer.group-id}")
    void listenTransactionEvent(
            ConsumerRecord<String, TransactionEvent> event,
            Acknowledgment ack,
            Consumer<Object, Object> consumer
    ){
        var transaction = event.value();
        logger.info("Event received! Transaction={};", transaction.getTransId());
        ack.acknowledge();
    }

    @KafkaListener(topics = {"${kafka.topics.payment}"}, groupId = "${spring.kafka.consumer.group-id}")
    void listenPaymentEvent(
            ConsumerRecord<String, PaymentEvent> event,
            Acknowledgment ack,
            Consumer<Object, Object> consumer
    ){
        var payment = event.value();
        logger.info("Event received! Amount={};", payment.getAmount());
        ack.acknowledge();
    }

}

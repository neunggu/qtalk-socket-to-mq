package com.quack.talk.chat.queue.producer;

import com.quack.talk.common.chat.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueueProducer {

    @Value("${queue.name}")
    private String QUEUE_NAME;
    @Value("${queue.exchange_name}")
    private String EXCHANGE_NAME;
    @Value("${queue.routing_key}")
    private String ROUTING_KEY;

    private final RabbitTemplate rabbitTemplate;

    public void publish(MessageDTO message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY, message);
    }
}

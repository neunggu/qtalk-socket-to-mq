package com.quack.talk.chat.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quack.talk.common.chat.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener{
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations operations;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            MessageDTO messageDto = objectMapper.readValue(message.getBody(), MessageDTO.class);
            operations.convertAndSend("/sub/chat/room/"+messageDto.getRoomId(), messageDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

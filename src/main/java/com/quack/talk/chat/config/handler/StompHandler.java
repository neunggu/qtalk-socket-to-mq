package com.quack.talk.chat.config.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor{
//    private final ChatService chatService;
//    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        MessageHeaders messageHeaders = message.getHeaders();

        String sessionId = (String) messageHeaders.get("simpSessionId");
//        if (StompCommand.SUBSCRIBE == accessor.getCommand()){
//            String roomId = chatService.getRoomId(Optional.ofNullable((String) messageHeaders.get("simpDestination")).orElse("InvaliedRoomId"));
//            chatRoomRepository.setUserEnterInfo(sessionId, roomId);
//            chatRoomRepository.plusUserCount(roomId);
//        } else if (StompCommand.DISCONNECT == accessor.getCommand()){
//            String roomId = chatRoomRepository.getUserEnterRoomId(sessionId);
//            chatRoomRepository.minusUserCount(roomId);
//            chatRoomRepository.removeUserEnterInfo(sessionId);
//        }
        return message;
    }


}

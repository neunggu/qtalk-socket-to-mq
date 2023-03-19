package com.quack.talk.chat.controller;

import com.quack.talk.chat.service.ChatService;
import com.quack.talk.common.chat.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void chatMessage(MessageDTO message){
        chatService.sendMessage(message);
    }
}

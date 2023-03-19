package com.quack.talk.chat.controller;

import com.quack.talk.chat.service.ChatService;
import com.quack.talk.common.chat.dto.MessageDTO;
import com.quack.talk.common.room.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    @PostMapping("/setChatListener")
    public ResponseEntity setChatListener(@RequestBody RoomDTO roomDto){
        chatService.setEventListener(roomDto.getRoomId());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/message")
    public ResponseEntity sendChatMessage(@RequestBody MessageDTO message){
        chatService.sendMessage(message);
        return ResponseEntity.ok(message);
    }
}

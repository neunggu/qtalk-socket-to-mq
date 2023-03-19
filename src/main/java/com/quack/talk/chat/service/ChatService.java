package com.quack.talk.chat.service;

import com.quack.talk.chat.common.mapper.SocketToMqMapper;
import com.quack.talk.chat.fcm.FcmAsyncService;
import com.quack.talk.chat.queue.producer.QueueProducer;
import com.quack.talk.chat.repository.ChatRoomCacheRepository;
import com.quack.talk.common.chat.dto.MessageDTO;
import com.quack.talk.common.chat.entity.Message;
import com.quack.talk.common.chat.type.MessageType;
import com.quack.talk.common.util.ChatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final TopicListenerService topicListenerService;
    private final ChatRoomCacheRepository chatRoomCacheRepository;
    private final ChatUtil chatUtil;

    private final SocketToMqMapper mapper;
    private final QueueProducer queueProducer;

    private final FcmAsyncService fcmAsyncService;

    public void sendMessage(MessageDTO message){
        setMessageDefaultInfo(message);
        // queue로 전송
        queueProducer.publish(message);

        if (isSaveMessageType(message.getType())) {
            //room에 메세지 저장
            saveChatMessageIntoRoom(message);
            // chat log 저장
            saveChatMessage(message);
        }

        fcmAsyncService.sendToTopic(message);
    }

    public void setEventListener(String roomId){
        topicListenerService.setEventListener(roomId);
    }

    private void setMessageDefaultInfo(MessageDTO messageDto){
        //입장, 퇴장 시 메세지
        setJoinLeaveMessage(messageDto);
        // 접속 유저 카운트 설정
        long userCount = chatRoomCacheRepository.getUserCount(messageDto.getRoomId());
        messageDto.setUnReadCount(userCount);
        // 메세지 보낸 시간 설정
        messageDto.setTimestamp(System.currentTimeMillis());
        // 채팅 id 설정
        String chatId = chatUtil.createChatId(messageDto.getRoomId());
        messageDto.setChatId(chatId);
    }

    private void setJoinLeaveMessage(MessageDTO messageDto){
        MessageType type = messageDto.getType();
        if (MessageType.JOIN.equals(type)) {
            messageDto.setMessage("'"+messageDto.getSender()+"'님이 들어왔습니다.");
            messageDto.setSender("알리미");
            messageDto.setSenderId("info");
        } else if(MessageType.LEAVE.equals(type)){
            messageDto.setMessage("'"+messageDto.getSender()+"'님이 나갔습니다.");
            messageDto.setSender("알리미");
            messageDto.setSenderId("info");
        }
    }

    private void saveChatMessage(MessageDTO messageDto) {
        Message message = mapper.map(messageDto);
        chatRoomCacheRepository.saveChatMessage(message);
    }

    private void saveChatMessageIntoRoom(MessageDTO messageDto) {
        //messageSeq 저장
        Message message = mapper.map(messageDto);
        chatRoomCacheRepository.saveChatMessageIntoRoom(message);
    }

    private boolean isSaveMessageType(MessageType type){
        return type.equals(MessageType.CHAT)
                || type.equals(MessageType.JOIN)
                || type.equals(MessageType.LEAVE);
    }

}

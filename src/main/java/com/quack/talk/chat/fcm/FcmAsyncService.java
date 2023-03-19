package com.quack.talk.chat.fcm;

import com.quack.talk.common.chat.dto.MessageDTO;
import com.quack.talk.common.fcm.dto.NotificationRequestDto;
import com.quack.talk.common.util.ApiUtil;
import com.quack.talk.common.util.FcmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableAsync
public class FcmAsyncService {

    private final ApiUtil apiUtil;
    private final FcmUtil fcmUtil;

    @Value("${fcm.url}")
    private String fcmUrl;

    @Async
    public void sendToTopic(MessageDTO message){
        String topic = fcmUtil.getFcmTopic(message.getRoomId());
        String url = fcmUrl+"/noti/topic";
        NotificationRequestDto notificationRequestDto = NotificationRequestDto.builder()
                .target(topic)
                .title(message.getTitle())
                .sender(message.getSender())
                .body(message.getMessage())
                .build();
        apiUtil.post(url,notificationRequestDto, Map.class);
    }
}

package com.quack.talk.chat.service;

import com.quack.talk.chat.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListenerService {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;

    protected Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        topics = new HashMap<>();
    }

    public void setEventListener(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = ChannelTopic.of(roomId);
            addMessageListener(topic);
            topics.put(roomId, topic);
        }
    }

    public void addMessageListener(ChannelTopic topic) {
        redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

}

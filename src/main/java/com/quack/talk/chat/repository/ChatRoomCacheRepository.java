package com.quack.talk.chat.repository;

import com.quack.talk.common.chat.entity.Message;
import com.quack.talk.common.room.entity.Room;
import com.quack.talk.common.util.MongoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
// TODO:: link to mongodb, redis cache
public class ChatRoomCacheRepository {
    private final MongoTemplate mongoTemplate;
    private final MongoUtil mongoUtil;

    @Value("${mongodb.collection.rooms}")
    private String COLLECTION_ROOMS;
    @Value("${mongodb.collection.history_messages}")
    private String COLLECTION_HISTORY_MESSAGE;

    @Value("${mongodb.collection.rooms_messages}")
    private String COLLECTION_ROOMS_MESSAGES;

    public long getUserCount(String roomId){
        Query query = mongoUtil.getQuery("roomId", roomId);
        Room room = mongoTemplate.findOne(query, Room.class, COLLECTION_ROOMS);
        return room.getUserCount();
    }

    public void saveChatMessage(Message message) {
        mongoTemplate.insert(message, COLLECTION_HISTORY_MESSAGE);
    }

    public void saveChatMessageIntoRoom(Message message) {
        Query query = mongoUtil.getQuery("roomId", message.getRoomId());
        Update updatePush = mongoUtil.updatePush("messages", message);
        mongoTemplate.upsert(query, updatePush, COLLECTION_ROOMS_MESSAGES);
    }
}

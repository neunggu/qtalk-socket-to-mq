package com.quack.talk.chat.common.mapper;

import com.quack.talk.common.chat.dto.MessageDTO;
import com.quack.talk.common.chat.entity.Message;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocketToMqMapper {
    MessageDTO map(Message obj);

    @InheritInverseConfiguration
    Message map(MessageDTO obj);


}

package com.quack.talk.chat.model.server;

import lombok.Data;

import java.io.Serializable;

@Data
public class Nick implements Serializable {

    private String ip;
    private String port;
    private String nick;

}

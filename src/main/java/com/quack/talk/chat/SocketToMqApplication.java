package com.quack.talk.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.quack.talk.*"})
public class SocketToMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketToMqApplication.class, args);
    }

}

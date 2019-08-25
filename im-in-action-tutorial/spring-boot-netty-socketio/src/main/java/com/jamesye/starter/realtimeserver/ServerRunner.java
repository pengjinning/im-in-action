package com.jamesye.starter.realtimeserver;


import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ServerRunner {

    @Autowired
    SocketIOServer socketIOServer;

    @PostConstruct
    public void init() {
        socketIOServer.start();
    }

    @PreDestroy
    public void shutdown() {
        socketIOServer.stop();
    }
}

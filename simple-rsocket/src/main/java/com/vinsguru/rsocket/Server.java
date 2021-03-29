package com.vinsguru.rsocket;

import com.vinsguru.rsocket.service.SocketAcceptorImpl;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;

public class Server {

    public static void main(String[] args) {

        RSocketServer socketServer = RSocketServer.create(new SocketAcceptorImpl());
        CloseableChannel closeableChannel = socketServer.bindNow(TcpServerTransport.create(6565));

        // keep listening
        closeableChannel.onClose().block();


    }

}

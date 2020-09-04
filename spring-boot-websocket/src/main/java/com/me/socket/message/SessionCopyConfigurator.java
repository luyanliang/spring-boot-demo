package com.me.socket.message;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/8/10
 */
public class SessionCopyConfigurator extends ServerEndpointConfig.Configurator{

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        if (request.getHttpSession() != null) {
            HttpSession session = (HttpSession) request.getHttpSession();
            sec.getUserProperties().put(HttpSession.class.getName(),session);
        }
    }
}

package org.tele2chat.hadler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.tele2chat.model.Message;
import org.tele2chat.service.MessageService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Component
public class ChatHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());


    @Autowired
    public ChatHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message msg = messageService.save(session,
                new ObjectMapper().readTree(message.getPayload()).get("text").asText());

        String payload = "{ \"text\": \"" + msg.getText() + "\", " + "\"timestamp\": \"" + msg.getTimestamp() + "\", " + "\"user\": { \"username\": \"" + msg.getUser()
                .getUsername() + "\", " + "\"picture\": \"" + msg.getUser().getPicture() + "\" } }";

        synchronized (sessions) {
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(payload));
                }
            }
        }

    }
}

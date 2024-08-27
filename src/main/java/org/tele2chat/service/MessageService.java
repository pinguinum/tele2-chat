package org.tele2chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.tele2chat.model.Message;
import org.tele2chat.repository.MessagesRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessagesRepository messagesRepository;
    private final UserService userService;

    @Autowired
    public MessageService(MessagesRepository messagesRepository, UserService userService) {
        this.messagesRepository = messagesRepository;
        this.userService = userService;
    }

    public Message save(WebSocketSession session, String text) {
        Message msg = new Message();
        msg.setText(text);
        msg.setUser(userService.getAuthenticatedUser((Authentication) session.getPrincipal()));
        msg.setTimestamp(LocalDateTime.now());
        return messagesRepository.save(msg);
    }

    public List<Message> getMessages() {
        return messagesRepository.findAll();
    }
}

package com.shipdoc.domain.chatbot.service;


import com.shipdoc.domain.chatbot.entity.Chat;
import com.shipdoc.domain.chatbot.repository.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
;

@Service
public class ChatService implements IChatService {

    @Autowired
    ChatRepository chatRepository;


    @Override
    public Chat addChat(Chat c) {
        chatRepository.save(c);
        return c;
    }

    @Override
    public void deleteChat(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Chat updateChat(Chat c) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Chat retrieveChat(Long id) {
        // TODO Auto-generated method stub

        return null;
    }

}
package com.shipdoc.domain.chatbot.service;



import com.shipdoc.domain.chatbot.entity.Chat;

import java.util.List;



public interface IChatService {



    Chat addChat(Chat c);

    void deleteChat(Long id);

    Chat updateChat(Chat c);

    Chat retrieveChat(Long id);

}
package com.shipdoc.domain.chatbot.repository;


import com.shipdoc.domain.chatbot.entity.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{



}
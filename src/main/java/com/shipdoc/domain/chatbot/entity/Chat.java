package com.shipdoc.domain.chatbot.entity;

import java.io.Serializable;
import java.util.Date;



import com.shipdoc.global.entity.BaseEntity;
import jakarta.persistence.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateSent", nullable = false, updatable = false)
    @CreationTimestamp
    private Date dateSent;
    @Column(length = 65555)
    private String messageReceived;
    @Column(length = 65555)
    private String messageSent;





    public Chat(String messageSent, String messageReceived) {
        this.messageSent = messageSent;
        this.messageReceived = messageReceived;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
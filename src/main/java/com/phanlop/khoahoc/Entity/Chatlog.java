package com.phanlop.khoahoc.Entity;
import lombok.Data;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
@Entity
@Data
@Table(name = "chatlog")
@EntityListeners(AuditingEntityListener.class)
public class Chatlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne @JoinColumn(name = "owner_id")
    private User courseOwner;

    @ManyToOne @JoinColumn(name = "user_id")
    private User courseBuyer;
    @ManyToOne @JoinColumn(name = "courseID")
    private Course course;


    @Column(name = "content")
    private String content;
    @Column(name = "sendBy")
    private int sendBy;

    @Column(name = "date_send")
    private Date dateSend;

    
    @PrePersist
    public void prePersist() {
        this.dateSend = new Date();
    }
}
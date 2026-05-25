package com.pfe.learningplatform.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    /*
     * =====================================
     * MESSAGE ID
     * =====================================
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =====================================
     * MESSAGE CONTENT
     * =====================================
     */

    @Column(columnDefinition = "TEXT")
    private String content;

    /*
     * =====================================
     * MESSAGE SENDER
     * USER / AI
     * =====================================
     */

    private String sender;

    /*
     * =====================================
     * MESSAGE DATE
     * =====================================
     */

    private LocalDateTime createdAt;

    /*
     * =====================================
     * CONVERSATION
     * =====================================
     */

    @ManyToOne

    @JoinColumn(name = "conversation_id")

    private ChatConversation conversation;
}
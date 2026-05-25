package com.pfe.learningplatform.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_conversations")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatConversation {

    /*
     * =====================================
     * CONVERSATION ID
     * =====================================
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =====================================
     * CONVERSATION TITLE
     * =====================================
     */

    private String title;

    /*
     * =====================================
     * CREATION DATE
     * =====================================
     */

    private LocalDateTime createdAt;

    /*
     * =====================================
     * USER ID
     * =====================================
     */

    private Long userId;

    /*
     * =====================================
     * CONVERSATION MESSAGES
     * =====================================
     */

    @OneToMany(

            mappedBy = "conversation",

            cascade = CascadeType.ALL,

            orphanRemoval = true
    )

    private List<ChatMessage> messages =
            new ArrayList<>();
}
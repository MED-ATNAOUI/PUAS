package com.pfe.learningplatform.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationResponse {

    /*
     * =====================================
     * CONVERSATION ID
     * =====================================
     */

    private Long id;

    /*
     * =====================================
     * CONVERSATION TITLE
     * =====================================
     */

    private String title;

    /*
     * =====================================
     * MESSAGES
     * =====================================
     */

    private List<MessageResponse> messages;
}
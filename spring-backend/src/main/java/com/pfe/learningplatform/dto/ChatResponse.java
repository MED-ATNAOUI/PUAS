package com.pfe.learningplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {

    /*
     * =====================================
     * AI RESPONSE
     * =====================================
     */

    private String response;

    /*
     * =====================================
     * CONVERSATION ID
     * =====================================
     */

    private Long conversationId;
}
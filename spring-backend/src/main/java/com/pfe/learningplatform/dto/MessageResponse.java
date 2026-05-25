package com.pfe.learningplatform.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    /*
     * =====================================
     * MESSAGE ID
     * =====================================
     */

    private Long id;

    /*
     * =====================================
     * MESSAGE CONTENT
     * =====================================
     */

    private String content;

    /*
     * =====================================
     * SENDER
     * USER / AI
     * =====================================
     */

    private String sender;

    /*
     * =====================================
     * DATE
     * =====================================
     */

    private LocalDateTime createdAt;
}
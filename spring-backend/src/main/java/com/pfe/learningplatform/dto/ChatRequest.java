package com.pfe.learningplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {

    @NotBlank(message = "Le message est obligatoire")
    private String message;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;

    private Long conversationId;
}
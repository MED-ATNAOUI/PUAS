package com.pfe.learningplatform.service;

import com.pfe.learningplatform.dto.*;

import com.pfe.learningplatform.exception.ResourceNotFoundException;

import com.pfe.learningplatform.model.ChatConversation;
import com.pfe.learningplatform.model.ChatMessage;

import com.pfe.learningplatform.repository.ChatConversationRepository;
import com.pfe.learningplatform.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    /*
     * =====================================
     * DEPENDENCIES
     * =====================================
     */

    private final RestTemplate restTemplate;

    private final ChatConversationRepository
            conversationRepository;

    private final ChatMessageRepository
            messageRepository;

    /*
     * =====================================
     * PYTHON AI URL
     * =====================================
     */

    @Value("${python.ai.url}")
    private String pythonAiUrl;

    /*
     * =====================================
     * SEND MESSAGE TO AI
     * =====================================
     */

    public ChatResponse sendMessage(

            ChatRequest request
    ) {

        /*
         * =================================
         * GET OR CREATE CONVERSATION
         * =================================
         */

        ChatConversation conversation;

        if (request.getConversationId() != null) {

            conversation = conversationRepository
                    .findById(request.getConversationId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Conversation non trouvée"
                            )
                    );

        } else {

            // create new conversation
            conversation = ChatConversation.builder()
                    .title(
                            request.getMessage().length() > 50
                                    ? request.getMessage().substring(0, 50) + "..."
                                    : request.getMessage()
                    )
                    .createdAt(LocalDateTime.now())
                    .userId(request.getUserId())
                    .build();

            conversation = conversationRepository
                    .save(conversation);
        }

        /*
         * =================================
         * SAVE USER MESSAGE
         * =================================
         */

        ChatMessage userMessage = ChatMessage.builder()
                .content(request.getMessage())
                .sender("USER")
                .createdAt(LocalDateTime.now())
                .conversation(conversation)
                .build();

        messageRepository.save(userMessage);

        /*
         * =================================
         * CALL PYTHON AI SERVICE
         * =================================
         */

        String url = pythonAiUrl + "/api/chat";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        // Build a simple request body for the Python service
        // The Python ChatRequest only needs "message"
        java.util.Map<String, Object> pythonRequest =
                java.util.Map.of(
                        "message", request.getMessage()
                );

        HttpEntity<java.util.Map<String, Object>> entity =
                new HttpEntity<>(
                        pythonRequest,
                        headers
                );

        String aiResponseText;

        try {

            ResponseEntity<ChatResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            ChatResponse.class
                    );

            aiResponseText = response.getBody() != null
                    ? response.getBody().getResponse()
                    : "Désolé, je n'ai pas pu générer de réponse.";

        } catch (Exception e) {

            aiResponseText =
                    "Service IA temporairement indisponible.";
        }

        /*
         * =================================
         * SAVE AI MESSAGE
         * =================================
         */

        ChatMessage aiMessage = ChatMessage.builder()
                .content(aiResponseText)
                .sender("AI")
                .createdAt(LocalDateTime.now())
                .conversation(conversation)
                .build();

        messageRepository.save(aiMessage);

        /*
         * =================================
         * RETURN RESPONSE
         * =================================
         */

        return ChatResponse.builder()
                .response(aiResponseText)
                .conversationId(conversation.getId())
                .build();
    }

    /*
     * =====================================
     * GET USER CONVERSATIONS
     * =====================================
     */

    public List<ConversationResponse> getConversations(
            Long userId
    ) {

        List<ChatConversation> conversations =
                conversationRepository.findByUserId(userId);

        List<ConversationResponse> responses =
                new ArrayList<>();

        for (ChatConversation conv : conversations) {

            List<MessageResponse> messages =
                    mapMessages(conv.getMessages());

            responses.add(
                    ConversationResponse.builder()
                            .id(conv.getId())
                            .title(conv.getTitle())
                            .messages(messages)
                            .build()
            );
        }

        return responses;
    }

    /*
     * =====================================
     * GET SINGLE CONVERSATION
     * =====================================
     */

    public ConversationResponse getConversation(
            Long conversationId
    ) {

        ChatConversation conversation =
                conversationRepository.findById(conversationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Conversation non trouvée"
                                )
                        );

        List<MessageResponse> messages =
                mapMessages(conversation.getMessages());

        return ConversationResponse.builder()
                .id(conversation.getId())
                .title(conversation.getTitle())
                .messages(messages)
                .build();
    }

    /*
     * =====================================
     * DELETE CONVERSATION
     * =====================================
     */

    public void deleteConversation(
            Long conversationId
    ) {

        ChatConversation conversation =
                conversationRepository.findById(conversationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Conversation non trouvée"
                                )
                        );

        conversationRepository.delete(conversation);
    }

    /*
     * =====================================
     * MAP MESSAGES HELPER
     * =====================================
     */

    private List<MessageResponse> mapMessages(
            List<ChatMessage> messages
    ) {

        List<MessageResponse> responses =
                new ArrayList<>();

        if (messages != null) {

            for (ChatMessage msg : messages) {

                responses.add(
                        MessageResponse.builder()
                                .id(msg.getId())
                                .content(msg.getContent())
                                .sender(msg.getSender())
                                .createdAt(msg.getCreatedAt())
                                .build()
                );
            }
        }

        return responses;
    }
}
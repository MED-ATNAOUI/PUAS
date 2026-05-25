package com.pfe.learningplatform.controller;

import com.pfe.learningplatform.dto.ChatRequest;
import com.pfe.learningplatform.dto.ChatResponse;
import com.pfe.learningplatform.dto.ConversationResponse;

import com.pfe.learningplatform.service.ChatService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatController {

    /*
     * =====================================
     * CHAT SERVICE
     * =====================================
     */

    private final ChatService chatService;

    /*
     * =====================================
     * SEND MESSAGE
     * =====================================
     */

    @PostMapping
    public ChatResponse sendMessage(

            @Valid
            @RequestBody
            ChatRequest request
    ) {

        return chatService.sendMessage(
                request
        );
    }

    /*
     * =====================================
     * GET USER CONVERSATIONS
     * =====================================
     */

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<ConversationResponse>>
    getConversations(

            @PathVariable Long userId
    ) {

        List<ConversationResponse> conversations =
                chatService.getConversations(userId);

        return ResponseEntity.ok(conversations);
    }

    /*
     * =====================================
     * GET CONVERSATION WITH MESSAGES
     * =====================================
     */

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<ConversationResponse>
    getConversation(

            @PathVariable Long conversationId
    ) {

        ConversationResponse conversation =
                chatService.getConversation(
                        conversationId
                );

        return ResponseEntity.ok(conversation);
    }

    /*
     * =====================================
     * DELETE CONVERSATION
     * =====================================
     */

    @DeleteMapping("/conversation/{conversationId}")
    public ResponseEntity<String> deleteConversation(

            @PathVariable Long conversationId
    ) {

        chatService.deleteConversation(
                conversationId
        );

        return ResponseEntity.ok(
                "Conversation supprimée"
        );
    }
}
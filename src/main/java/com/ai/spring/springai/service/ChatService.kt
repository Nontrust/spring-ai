package com.ai.spring.springai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatClient: ChatClient
) {
    fun chat(message: String): String =
        chatClient.prompt()
            .user(message)
            .call()
            .content() ?: throw IllegalStateException("AI의 응답이 없습니다.")
}